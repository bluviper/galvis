package com.galvis.tvhome;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.common.BitMatrix;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * TV-optimised issue reporting modal for beta testers.
 * Displays live TV telemetry alongside an instantly scannable QR code that opens
 * a pre-populated GitHub issue on the tester's phone with zero remote typing.
 */
public final class ReportIssueDialog {

    static final String GITHUB_REPO_ISSUES_URL = "https://github.com/galvis-project/galvis/issues/new";

    private ReportIssueDialog() {}

    static String buildIssueUrl(String model, String product, String androidOs, String display,
                                long availRamMb, long totalRamMb, String fingerprint, String appVersion) {
        StringBuilder body = new StringBuilder();
        body.append("### Hardware & Environment\n");
        body.append("- **Device:** ").append(model).append(" (").append(product).append(")\n");
        body.append("- **OS:** Android ").append(androidOs).append("\n");
        body.append("- **Display:** ").append(display).append("\n");
        if (totalRamMb > 0) {
            body.append("- **RAM:** ").append(availRamMb).append("MB free / ").append(totalRamMb).append("MB total\n");
        }
        body.append("- **Build Fingerprint:** `").append(fingerprint).append("`\n");
        body.append("- **Launcher Version:** GALVis v").append(appVersion).append("\n\n");
        body.append("### Issue Description\n");
        body.append("<!-- Please describe what happened, expected behavior, and steps to reproduce -->\n");

        String title = "[Beta Report] " + model;
        try {
            return GITHUB_REPO_ISSUES_URL
                    + "?title=" + URLEncoder.encode(title, StandardCharsets.UTF_8.name())
                    + "&body=" + URLEncoder.encode(body.toString(), StandardCharsets.UTF_8.name());
        } catch (Exception e) {
            return GITHUB_REPO_ISSUES_URL;
        }
    }

    static BitMatrix generateQrMatrix(String text, int size) throws Exception {
        QRCodeWriter writer = new QRCodeWriter();
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.MARGIN, 1);
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
        return writer.encode(text, BarcodeFormat.QR_CODE, size, size, hints);
    }

    public static void show(Activity activity) {
        if (activity == null || activity.isFinishing()) return;

        DisplayMetrics dm = activity.getResources().getDisplayMetrics();
        String displayStr = dm.widthPixels + "x" + dm.heightPixels + " @" + dm.densityDpi + "dpi";

        ActivityManager am = (ActivityManager) activity.getSystemService(Context.ACTIVITY_SERVICE);
        long totalRamMb = 0;
        long availRamMb = 0;
        if (am != null) {
            ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
            am.getMemoryInfo(mi);
            totalRamMb = mi.totalMem / (1024 * 1024);
            availRamMb = mi.availMem / (1024 * 1024);
        }

        long heapUsedMb = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / (1024 * 1024);
        long heapMaxMb = Runtime.getRuntime().maxMemory() / (1024 * 1024);

        String appVersion = "1.0.0";
        try {
            appVersion = activity.getPackageManager()
                    .getPackageInfo(activity.getPackageName(), 0).versionName;
        } catch (Exception ignored) {}

        String model = Build.MANUFACTURER + " " + Build.MODEL;
        String androidOs = Build.VERSION.RELEASE + " (API " + Build.VERSION.SDK_INT + ")";

        // Clean on-screen diagnostic summary
        StringBuilder sb = new StringBuilder();
        sb.append("• Model: ").append(model).append("\n");
        sb.append("• OS: Android ").append(androidOs).append("\n");
        sb.append("• Display: ").append(displayStr).append("\n");
        if (totalRamMb > 0) {
            sb.append("• RAM: ").append(availRamMb).append(" MB free / ").append(totalRamMb).append(" MB total\n");
        }
        sb.append("• Java Heap: ").append(heapUsedMb).append(" MB / ").append(heapMaxMb).append(" MB\n");
        sb.append("• Build: ").append(Build.DISPLAY != null ? Build.DISPLAY : Build.ID).append("\n");
        sb.append("• Version: GALVis™ v").append(appVersion);
        String telemetrySummary = sb.toString();

        String issueUrl = buildIssueUrl(model, Build.PRODUCT, androidOs, displayStr,
                availRamMb, totalRamMb, Build.FINGERPRINT, appVersion);

        Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_report_issue);

        Window window = dialog.getWindow();
        if (window != null) {
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            window.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        ImageView imgQr = dialog.findViewById(R.id.qrCode);
        TextView txtTelemetry = dialog.findViewById(R.id.txtTelemetry);
        TextView btnOpenTv = dialog.findViewById(R.id.btnOpenTv);
        TextView btnCopyLink = dialog.findViewById(R.id.btnCopyLink);
        TextView btnClose = dialog.findViewById(R.id.btnClose);

        txtTelemetry.setText(telemetrySummary);

        // Generate crisp QR code bitmap
        try {
            BitMatrix matrix = generateQrMatrix(issueUrl, 400);
            int width = matrix.getWidth();
            int height = matrix.getHeight();
            Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    bmp.setPixel(x, y, matrix.get(x, y) ? Color.BLACK : Color.WHITE);
                }
            }
            imgQr.setImageBitmap(bmp);
        } catch (Exception e) {
            imgQr.setVisibility(View.GONE);
        }

        final String finalUrl = issueUrl;
        btnOpenTv.setOnClickListener(v -> {
            try {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(finalUrl));
                browserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                activity.startActivity(browserIntent);
            } catch (ActivityNotFoundException | SecurityException e) {
                Toast.makeText(activity, R.string.not_available, Toast.LENGTH_SHORT).show();
            }
        });

        btnCopyLink.setOnClickListener(v -> {
            ClipboardManager clipboard = (ClipboardManager) activity.getSystemService(Context.CLIPBOARD_SERVICE);
            if (clipboard != null) {
                ClipData clip = ClipData.newPlainText("GALVis Issue Report",
                        telemetrySummary + "\n\n" + finalUrl);
                clipboard.setPrimaryClip(clip);
                Toast.makeText(activity, R.string.report_issue_copied, Toast.LENGTH_SHORT).show();
            }
        });

        btnClose.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
        btnOpenTv.requestFocus();
    }
}
