package com.galvis.tvhome;

import com.google.zxing.common.BitMatrix;
import org.junit.Test;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/** Plain JVM tests for ReportIssueDialog URL formatting and QR matrix generation. */
public class ReportIssueTest {

    @Test
    public void buildIssueUrlEncodesDeviceTelemetryCorrectly() throws Exception {
        String url = ReportIssueDialog.buildIssueUrl(
                "TCL 55C635",
                "BeyondTV",
                "11 (API 30)",
                "1920x1080 @320dpi",
                1250,
                2048,
                "TCL/BeyondTV/BeyondTV:11/RP1A.200720.011/220915:user/release-keys",
                "1.0.0"
        );

        assertNotNull(url);
        assertTrue(url.startsWith(ReportIssueDialog.GITHUB_REPO_ISSUES_URL));
        assertTrue(url.contains("title="));
        assertTrue(url.contains("body="));

        // Decode query parameters to verify contents
        String query = url.substring(url.indexOf('?') + 1);
        String[] params = query.split("&");
        String title = null;
        String body = null;
        for (String param : params) {
            if (param.startsWith("title=")) {
                title = URLDecoder.decode(param.substring("title=".length()), StandardCharsets.UTF_8.name());
            } else if (param.startsWith("body=")) {
                body = URLDecoder.decode(param.substring("body=".length()), StandardCharsets.UTF_8.name());
            }
        }

        assertEquals("[Beta Report] TCL 55C635", title);
        assertNotNull(body);
        assertTrue(body.contains("TCL 55C635 (BeyondTV)"));
        assertTrue(body.contains("Android 11 (API 30)"));
        assertTrue(body.contains("1920x1080 @320dpi"));
        assertTrue(body.contains("1250MB free / 2048MB total"));
        assertTrue(body.contains("GALVis v1.0.0"));
    }

    @Test
    public void generateQrMatrixProducesValidSquareBitMatrix() throws Exception {
        String testUrl = "https://github.com/galvis-project/galvis/issues/new?title=Test";
        BitMatrix matrix = ReportIssueDialog.generateQrMatrix(testUrl, 256);

        assertNotNull(matrix);
        assertEquals(256, matrix.getWidth());
        assertEquals(256, matrix.getHeight());

        // Ensure there is at least one black module in the matrix
        boolean hasBlackModule = false;
        for (int x = 0; x < matrix.getWidth(); x++) {
            for (int y = 0; y < matrix.getHeight(); y++) {
                if (matrix.get(x, y)) {
                    hasBlackModule = true;
                    break;
                }
            }
            if (hasBlackModule) break;
        }
        assertTrue("QR matrix should contain black modules", hasBlackModule);
    }
}
