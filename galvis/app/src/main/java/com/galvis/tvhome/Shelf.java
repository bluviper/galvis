package com.galvis.tvhome;

import java.util.List;

public final class Shelf {
    public static final int TYPE_ALL_APPS = 0;
    public static final int TYPE_CONTENT = 1;

    final int type;
    final String title;
    final List<Item> items;

    public Shelf(int type, String title, List<Item> items) {
        this.type = type;
        this.title = title;
        this.items = items;
    }

    public static final class Item {
        final String title;
        final String subtitle;
        final String badge;
        final int badgeColor;
        final int accentColor;
        final String deepLinkUri;
        final String packageName;
        final AppAdapter.Entry appEntry;

        // Content Card Item (Netflix, YouTube, Disney+)
        public Item(String title, String subtitle, String badge, int badgeColor, int accentColor, String deepLinkUri, String packageName) {
            this.title = title;
            this.subtitle = subtitle;
            this.badge = badge;
            this.badgeColor = badgeColor;
            this.accentColor = accentColor;
            this.deepLinkUri = deepLinkUri;
            this.packageName = packageName;
            this.appEntry = null;
        }

        // App Item (All Apps shelf)
        public Item(AppAdapter.Entry appEntry) {
            this.title = appEntry.label;
            this.subtitle = "";
            this.badge = "APP";
            this.badgeColor = 0xFF4A5568;
            this.accentColor = appEntry.ambient;
            this.deepLinkUri = null;
            this.packageName = appEntry.pkg;
            this.appEntry = appEntry;
        }
    }
}
