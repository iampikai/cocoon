package com.iampikai.cocoon.database;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.iampikai.cocoon.R;
import com.iampikai.cocoon.datamodels.TabDataModel;

import java.util.ArrayList;

public class AppData {
    ArrayList<TabDataModel> tabDataModelArrayList;
    public static int currentTab = 0;
    private static AppData appData;

    AppData(Context context) {
        Bitmap dumbit = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);
        tabDataModelArrayList = new ArrayList<>();
        tabDataModelArrayList.add(new TabDataModel(dumbit, dumbit, "New Tab", "about:blank"));

    }

    public static AppData getAppData(Context context) {
        if (appData == null) {
            appData = new AppData(context);
        }
        return appData;
    }

    public ArrayList<TabDataModel> getTabDataModelArrayList() {
        return tabDataModelArrayList;
    }

    public void setTabDataModelArrayList(ArrayList<TabDataModel> tabDataModelArrayList) {
        this.tabDataModelArrayList = tabDataModelArrayList;
    }
}