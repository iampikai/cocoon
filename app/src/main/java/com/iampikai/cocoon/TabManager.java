package com.iampikai.cocoon;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.iampikai.cocoon.datamodels.TabDataModel;
import com.iampikai.cocoon.webengine.NestedWebView;

import java.util.ArrayList;

public class TabManager {

    private static ArrayList<TabDataModel> tabList;
    private static int currentTab;
    private static TabManager tabManager;

    private TabManager() {
        tabList = new ArrayList<>();
    }

    public int getCurrentTab() {
        return currentTab;
    }

    public void setCurrentTab(int currentTab) {
        TabManager.currentTab = currentTab;
    }

    public static TabManager getInstance() {
        if (tabManager == null) {
            tabManager = new TabManager();
        }
        return tabManager;
    }

    public void newTab() {
        if (tabList.isEmpty())
            currentTab = 0;
        else
            currentTab = tabList.size();
        tabList.add(new TabDataModel(null, null, "New Tab", "about:blank"));
        Log.e("TAB_LIST",tabList.toString());
    }

    public void loadTab(int index) {
        Log.e("LOADED",String.valueOf(index));
        String url = tabList.get(index).getUrl();
        NestedWebView webView = MainActivity.getWebView();
        webView.loadUrl(url);
    }

    public void closeTab(int index) {
        Log.e("CLOSED_TAB", String.valueOf(index));
        if (tabList.size() == 1) {
            tabList.remove(index);
            newTab();
        } else {
            if (index == 0)
                currentTab = index;
            else
                currentTab = index - 1;
            tabList.remove(index);
        }
        loadTab(currentTab);
    }

    public void closeAllTab() {
        tabList.clear();
        newTab();
    }

    public ArrayList<TabDataModel> getTabList() {
        return tabList;
    }
}
