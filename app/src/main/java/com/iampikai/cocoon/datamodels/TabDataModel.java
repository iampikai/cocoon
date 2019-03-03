package com.iampikai.cocoon.datamodels;

import android.graphics.Bitmap;
import android.webkit.WebBackForwardList;

public class TabDataModel {

    private Bitmap favicon, snapshot;
    private String title, url;
    private WebBackForwardList backForwardList;

    public TabDataModel(Bitmap favicon, Bitmap snapshot, String title, String url) {
        this.favicon = favicon;
        this.snapshot = snapshot;
        this.title = title;
        this.url = url;
    }

    public Bitmap getFavicon() {
        return favicon;
    }

    public void setFavicon(Bitmap favicon) {
        this.favicon = favicon;
    }

    public Bitmap getSnapshot() {
        return snapshot;
    }

    public void setSnapshot(Bitmap snapshot) {
        this.snapshot = snapshot;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public WebBackForwardList getBackForwardList() {
        return backForwardList;
    }

    public void saveCurrentState(Bitmap favicon, Bitmap snapshot, String title, String url, WebBackForwardList backForwardList) {
        this.favicon = favicon;
        this.snapshot = snapshot;
        this.title = title;
        this.url = url;
        this.backForwardList = backForwardList;
    }
}
