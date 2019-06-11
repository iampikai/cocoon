package com.iampikai.cocoon.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.iampikai.cocoon.MainActivity;
import com.iampikai.cocoon.R;
import com.iampikai.cocoon.TabManager;
import com.iampikai.cocoon.datamodels.TabDataModel;

import java.util.ArrayList;

public class TabSwitcherAdapter extends RecyclerView.Adapter<TabSwitcherAdapter.ViewHolder> {

    private ArrayList<TabDataModel> mData;
    private LayoutInflater mInflater;
    private MainActivity activity;
    TabManager tabManager;
    TabDataModel tdm;

    public TabSwitcherAdapter(Context context, MainActivity activity) {
        tabManager = TabManager.getInstance();
        this.mInflater = LayoutInflater.from(context);
        this.mData = tabManager.getTabList();
        this.activity = activity;
    }

    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.tab_cards, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.titleView.setText(mData.get(position).getTitle());
        holder.snapshotView.setImageBitmap(mData.get(position).getSnapshot());
        holder.faviconView.setImageBitmap(mData.get(position).getFavicon());
        holder.itemView.setOnClickListener(v -> onTabClick(holder.getAdapterPosition()));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void onTabClick(int position) {
        Log.e("Position", String.valueOf(position));
        tdm = tabManager.getTabList().get(position);
        tabManager.setCurrentTab(position);
        MainActivity.webView.loadUrl(tdm.getUrl());
        MainActivity.toolbarTitle.setText(tdm.getTitle());
        MainActivity.toolbarUrl.setText(tdm.getUrl());
        MainActivity.tabDialog.dismiss();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView titleView;
        ImageView faviconView;
        ImageView snapshotView;

        ViewHolder(View itemView) {
            super(itemView);
            titleView = itemView.findViewById(R.id.url_text);
            faviconView = itemView.findViewById(R.id.favicon);
            snapshotView = itemView.findViewById(R.id.snapshot);
        }
    }
}
