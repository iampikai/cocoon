package com.iampikai.cocoon;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.content.SharedPreferences;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;

import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.iampikai.cocoon.adapters.TabSwitcherAdapter;
import com.iampikai.cocoon.datamodels.TabDataModel;
import com.iampikai.cocoon.webengine.NestedWebView;

public class MainActivity extends AppCompatActivity {

    TabManager tabManager;
    SharedPreferences preferences;

    RecyclerView tabRecycler;
    Toolbar toolbar;
    View editUrlView, displayUrlView;
    ImageView clearUrl;
    EditText urlEdit;
    BottomNavigationView bottomNavigationView;
    FloatingActionButton fab;
    public static Dialog tabDialog;
    public static ProgressBar progressBar;
    public static NestedWebView webView;
    public static TextView toolbarTitle, toolbarUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabManager = TabManager.getInstance();
        //setUpPreferences();
        viewsInit();

    }

    private void viewsInit() {

        webView = new NestedWebView(getApplicationContext());
        webView = webView.getWebView(R.id.webview, MainActivity.this);

//        bottomNavigationView = findViewById(R.id.bottom_navigation);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        editUrlView = toolbar.findViewById(R.id.editUrlView);
        displayUrlView = toolbar.findViewById(R.id.displayUrlView);
        clearUrl = toolbar.findViewById(R.id.clear_url);
        urlEdit = toolbar.findViewById(R.id.editUrlEditText);

        urlEdit.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    webView.loadUrl(v.getText().toString());
                    clearUrl.callOnClick();
                    clearUrl.callOnClick();
                }
                return false;
            }
        });

        displayUrlView.setOnClickListener(displayUrlViewListener);
        clearUrl.setOnClickListener(clearUrlViewListener);
        progressBar = findViewById(R.id.progressBar);
//        toolbarTitle = toolbar.findViewById(R.id.toolbar_title);
        toolbarUrl = toolbar.findViewById(R.id.toolbar_url);

//        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
//                int id = menuItem.getItemId();
//                switch (id) {
//                    case R.id.new_tab:
////                        saveCurrentTabToList();
//                        tabManager.newTab();
//                        tabManager.loadTab(tabManager.getCurrentTab());
//                        return true;
//                    case R.id.open_settings:
//                        Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
//                        startActivity(intent);
//                        return true;
//                }
//                return false;
//            }
//        });
        tabManager.newTab();
        tabManager.loadTab(tabManager.getCurrentTab());
    }

//    private void setUpPreferences() {
//        preferences = android.preference.PreferenceManager.
//                getDefaultSharedPreferences(this);
//        SharedPreferences.OnSharedPreferenceChangeListener listener
//                = new SharedPreferences.OnSharedPreferenceChangeListener() {
//            @Override
//            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
//                switch (s) {
//                    case "ad_toggle":
//                        Settings.setAdsBlock(sharedPreferences.getBoolean(s, Settings.isAdsBlock()));
//                        break;
//                    case "analytics_toggle":
//                        Settings.setAnalyticsBlock(sharedPreferences.getBoolean(s, Settings.isAnalyticsBlock()));
//                        break;
//                    case "social_toggle":
//                        Settings.setSocialBlock(sharedPreferences.getBoolean(s, Settings.isSocialBlock()));
//                        break;
//                    case "script_toggle":
//                        Settings.setScriptBlock(sharedPreferences.getBoolean(s, Settings.isScriptBlock()));
//                        webView.getSettings().setJavaScriptEnabled(Settings.isScriptBlock());
//                        break;
//                    case "safebrowse_toggle":
//                        Settings.setSafebrowseEnabled(sharedPreferences.getBoolean(s, Settings.isSafebrowseEnabled()));
//                        if (Build.VERSION.SDK_INT >= 26)
//                            webView.getSettings().setSafeBrowsingEnabled(Settings.isSafebrowseEnabled());
//                        break;
//                    case "popup_toggle":
//                        Settings.setPopupsEnabled(sharedPreferences.getBoolean(s, Settings.isPopupsEnabled()));
//                        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(Settings.isPopupsEnabled());
//                        break;
//                    case "privatebrowse_toggle":
//                        Settings.setPrivateToggle(sharedPreferences.getBoolean(s, Settings.isPrivateToggle()));
////                        webView.getSettings().setpr;
//                        break;
//                    case "useragent_toggle":
//                        Settings.setUserAgentToggle(sharedPreferences.getBoolean(s, Settings.isUserAgentToggle()));
//                        webView.getSettings().setGeolocationEnabled(Settings.isUserAgentToggle());
//                        break;
//                    case "track_toggle":
//                        Settings.setTrackEnabled(sharedPreferences.getBoolean(s, Settings.isTrackEnabled()));
//                        webView.getSettings().setGeolocationEnabled(Settings.isTrackEnabled());
//                        break;
//                    case "autocomplete_toggle":
//                        Settings.setAutoCompleteToggle(sharedPreferences.getBoolean(s, Settings.isAutoCompleteToggle()));
////                        webView.getSettings().setGeolocationEnabled(Settings.isAutoCompleteToggle());
//                        break;
//                }
//            }
//        };
//        preferences.registerOnSharedPreferenceChangeListener(listener);
//    }

    public void openTabOverview(View view) {

        tabDialog = new Dialog(this, R.style.AppTheme);
        tabDialog.setContentView(R.layout.tab_switcher_dialog);
        tabRecycler = tabDialog.findViewById(R.id.recycle);
        fab = tabDialog.findViewById(R.id.fab);

        final TabSwitcherAdapter tabSwitcherAdapter = new TabSwitcherAdapter(getApplicationContext(), MainActivity.this);
        tabRecycler.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        tabRecycler.setAdapter(tabSwitcherAdapter);
        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int index = viewHolder.getAdapterPosition(); //swiped position
                if (direction == ItemTouchHelper.RIGHT) {//swipe right
                    tabManager.closeTab(index);
                    tabSwitcherAdapter.notifyItemRemoved(index);
                    tabSwitcherAdapter.notifyDataSetChanged();
                }
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(tabRecycler);
        tabDialog.show();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                saveCurrentTabToList();
                tabManager.newTab();
                tabManager.loadTab(tabManager.getCurrentTab());
                tabDialog.dismiss();
            }
        });
        fab.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                tabManager.closeAllTab();
                tabDialog.dismiss();
                return true;
            }
        });
    }

    public void saveCurrentTabToList() {
        TabDataModel tabDataModel = tabManager.getTabList().get(tabManager.getCurrentTab());
        tabDataModel.saveCurrentState(webView.getFavicon(),
                screenshot(webView, 0.25f),
                webView.getTitle(),
                webView.getUrl(),
                webView.copyBackForwardList());
    }

    View.OnClickListener displayUrlViewListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            displayUrlView.setVisibility(View.GONE);
            editUrlView.setVisibility(View.VISIBLE);
        }
    };

    View.OnClickListener clearUrlViewListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (urlEdit.getText().toString().equals("") || urlEdit.getText().toString().equals(null)) {
                displayUrlView.setVisibility(View.VISIBLE);
                editUrlView.setVisibility(View.GONE);
            } else {
                urlEdit.setText("");
            }
        }
    };

    public Bitmap screenshot(NestedWebView webView, float scale11) {
        try {
            int height = 450;
            Bitmap bitmap = Bitmap.createBitmap(webView.getWidth(), height, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            webView.draw(canvas);
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

//    public ProgressBar getProgressBar() {
//        return progressBar;
//    }
//
//    public TextView getToolbarTitle() {
//        return toolbarTitle;
//    }
//
//    public TextView getToolbarUrl() {
//        return toolbarUrl;
//    }
//
//    public static NestedWebView getWebView() {
//        return webView;
//    }
//
//    public Dialog getTabdialog() {
//        return tabDialog;
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else
            super.onBackPressed();
    }

}