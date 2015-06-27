package com.example.anthony.bigscreenforreddit;

/**
 * Class created to show information related to the user interface where that items contains just a
 * title and one icon.
 *
 * Created by Anthony on 6/26/2015.
 */
public class IconInfo {
    private final int title;
    private final int iconId;

    public IconInfo(int title, int iconId) {
        this.title = title;
        this.iconId = iconId;
    }

    public int getTitle() {
        return title;
    }

    public int getIconId() {
        return iconId;
    }
}
