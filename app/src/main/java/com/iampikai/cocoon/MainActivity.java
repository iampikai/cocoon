package com.iampikai.cocoon;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Build;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.iampikai.cocoon.adapters.OverviewAdapter;
import com.iampikai.cocoon.database.AppData;
import com.iampikai.cocoon.datamodels.TabDataModel;
import com.iampikai.cocoon.settings.Settings;
import com.iampikai.cocoon.webengine.NestedWebView;

public class MainActivity extends AppCompatActivity {

    SharedPreferences preferences;
    public static Boolean images_toggle;
    Dialog tabDialog;
    RecyclerView tabRecycler;
    Toolbar toolbar;
    View editUrlView, displayUrlView;
    ImageView clearUrl, overviewNewtab, overviewBack;
    EditText urlEdit;
    ProgressBar progressBar;
    NestedWebView webView;
    TextView toolbarTitle, toolbarUrl;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webView = new NestedWebView(getApplicationContext());
        webView = webView.getWebView(R.id.webview, MainActivity.this);
        setUpPreferences();
        viewsInit();


    }

    private void viewsInit() {

//        AppBarLayout appBarLayout = findViewById(R.id.appbar);
//        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
//            @Override
//            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
//                //Log.e("Offset", Integer.toString(verticalOffset));
//                if (verticalOffset == -143) {
//                    fab.hide();
//                    //fab.animate().translationY(fab.getHeight() + fab.getPaddingBottom());
//                } else if (verticalOffset == 0) {
//                    fab.show();
//                    //fab.animate().translationY(0);
//                }
//            }
//        });

        bottomNavigationView = findViewById(R.id.bottom_navigation);
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
        progressBar = toolbar.findViewById(R.id.progressBar);
        toolbarTitle = toolbar.findViewById(R.id.toolbar_title);
        toolbarUrl = toolbar.findViewById(R.id.toolbar_url);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                switch (id) {
                    case R.id.new_tab:
                        newTab();
                        return true;
                }

                return false;
            }
        });

    }

    public void openWebView(String url) {

    }

    private void setUpPreferences() {
        preferences = android.preference.PreferenceManager.
                getDefaultSharedPreferences(this);
        SharedPreferences.OnSharedPreferenceChangeListener listener
                = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
                Log.e("JavaScriptEnabled", Boolean.toString(webView.getSettings().getJavaScriptEnabled()));
                switch (s) {
                    case "ad_toggle":
                        Settings.setAdsBlock(sharedPreferences.getBoolean(s, Settings.isAdsBlock()));
                        break;
                    case "analytics_toggle":
                        Settings.setAnalyticsBlock(sharedPreferences.getBoolean(s, Settings.isAnalyticsBlock()));
                        break;
                    case "social_toggle":
                        Settings.setSocialBlock(sharedPreferences.getBoolean(s, Settings.isSocialBlock()));
                        break;
                    case "script_toggle":
                        Log.e("VALUE", Boolean.toString(webView.getSettings().getJavaScriptEnabled()));
                        Settings.setScriptBlock(sharedPreferences.getBoolean(s, Settings.isScriptBlock()));
                        webView.getSettings().setJavaScriptEnabled(Settings.isScriptBlock());
                        break;
                    case "safebrowse_toggle":
                        Settings.setSafebrowseEnabled(sharedPreferences.getBoolean(s, Settings.isSafebrowseEnabled()));
                        if (Build.VERSION.SDK_INT >= 26)
                            webView.getSettings().setSafeBrowsingEnabled(Settings.isSafebrowseEnabled());
                        break;
                    case "popup_toggle":
                        Settings.setPopupsEnabled(sharedPreferences.getBoolean(s, Settings.isPopupsEnabled()));
                        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(Settings.isPopupsEnabled());
                        break;
                    case "privatebrowse_toggle":
                        Settings.setPrivateToggle(sharedPreferences.getBoolean(s, Settings.isPrivateToggle()));
//                        webView.getSettings().setpr;
                        break;
                    case "useragent_toggle":
                        Settings.setUserAgentToggle(sharedPreferences.getBoolean(s, Settings.isUserAgentToggle()));
                        webView.getSettings().setGeolocationEnabled(Settings.isUserAgentToggle());
                        break;
                    case "track_toggle":
                        Settings.setTrackEnabled(sharedPreferences.getBoolean(s, Settings.isTrackEnabled()));
                        webView.getSettings().setGeolocationEnabled(Settings.isTrackEnabled());
                        break;
                    case "autocomplete_toggle":
                        Settings.setAutoCompleteToggle(sharedPreferences.getBoolean(s, Settings.isAutoCompleteToggle()));
//                        webView.getSettings().setGeolocationEnabled(Settings.isAutoCompleteToggle());
                        break;
                }
            }
        };
        preferences.registerOnSharedPreferenceChangeListener(listener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.home_overflow, menu);
//        return super.onCreateOptionsMenu(menu);
//    }

    public void openTabOverview(View view) {
        TabDataModel tdm = AppData.getAppData(getApplicationContext()).getTabDataModelArrayList().get(AppData.currentTab);

        tdm.saveCurrentState(webView.getFavicon(), screenshot(webView, 0.25f), webView.getTitle(), webView.getUrl(), webView.copyBackForwardList());

        tabDialog = new Dialog(this, R.style.AppTheme);
        tabDialog.setContentView(R.layout.tab_dialog);
        tabRecycler = tabDialog.findViewById(R.id.recycle);
//        overviewBack = tabdialog.findViewById(R.id.overview_back);
//        overviewNewtab = tabdialog.findViewById(R.id.overview_newtab);
//        overviewCounter = tabdialog.findViewById(R.id.overview_counter);
//        overviewBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                tabdialog.dismiss();
//
//            }
//        });
//        overviewNewtab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                tabdialog.dismiss();
//                newTab();
//            }
//        });

        final OverviewAdapter overviewAdapter = new OverviewAdapter(getApplicationContext(), MainActivity.this);
        tabRecycler.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        tabRecycler.setAdapter(overviewAdapter);
        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition(); //swiped position

                if (direction == ItemTouchHelper.RIGHT) {//swipe right

                    AppData.getAppData(getApplicationContext()).getTabDataModelArrayList().remove(position);
                    overviewAdapter.notifyItemRemoved(position);
                    overviewAdapter.notifyDataSetChanged();

                    Log.e("Position", Integer.toString(position));

                }

            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(tabRecycler);
        tabDialog.show();
    }

    public void newTab() {
        int newTabIndex = AppData.getAppData(getApplicationContext()).getTabDataModelArrayList().size();
        AppData.getAppData(getApplicationContext()).getTabDataModelArrayList().add(new TabDataModel(null, null, "newtab", "about:blank"));
        Log.e("Overview-loadurl", AppData.getAppData(getApplicationContext()).getTabDataModelArrayList().get(newTabIndex).getUrl());
        Log.e("Overview-size", String.valueOf(Integer.valueOf(AppData.getAppData(getApplicationContext()).getTabDataModelArrayList().size())));
        Log.e("Overview-newindex", String.valueOf(newTabIndex));
        AppData.currentTab = newTabIndex;
        webView.loadUrl(AppData.getAppData(getApplicationContext()).getTabDataModelArrayList().get(newTabIndex).getUrl());
        toolbarTitle.setText("New Tab");
        toolbarUrl.setText("Make a search");
    }


    View.OnClickListener displayUrlViewListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            displayUrlView.setVisibility(View.GONE);
            editUrlView.setVisibility(View.VISIBLE);

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

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//        switch (id) {
//            case R.id.settings_menu:
//                Intent intent = new Intent(this, SettingsActivity.class);
//                startActivity(intent);
//                return true;
//            case R.id.reset:
//                resetSettings();
//                Toast.makeText(this, "Factory settings restored", Toast.LENGTH_SHORT).show();
//                return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    View.OnClickListener clearUrlViewListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (urlEdit.getText().toString().equals("") || urlEdit.getText().toString().equals(null)) {
                Log.e("Toolbar-clearUrl", "already clean");
                displayUrlView.setVisibility(View.VISIBLE);
                editUrlView.setVisibility(View.GONE);
            } else {
                Log.e("Toolbar-clearUrl", "now clean");
                urlEdit.setText("");
            }
        }
    };

    public ProgressBar getProgressBar() {
        return progressBar;
    }

    public TextView getToolbarTitle() {
        return toolbarTitle;
    }

    public TextView getToolbarUrl() {
        return toolbarUrl;
    }

    public NestedWebView getWebView() {
        return webView;
    }

    public Dialog getTabdialog() {
        return tabDialog;
    }

    public void resetSettings() {
        Settings.setPopupsEnabled(false);
        Settings.setPrivateToggle(true);
        Settings.setUserAgentToggle(false);
        Settings.setTrackEnabled(true);
        Settings.setAutoCompleteToggle(false);
        Settings.setSafebrowseEnabled(true);
        Settings.setScriptBlock(false);
        Settings.setAdsBlock(true);
        Settings.setSocialBlock(true);
        Settings.setAnalyticsBlock(true);

    }

}
