package com.iampikai.cocoon.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.iampikai.cocoon.MainActivity;
import com.iampikai.cocoon.R;
import com.iampikai.cocoon.TabManager;
import com.iampikai.cocoon.datamodels.TabDataModel;

import java.util.ArrayList;

public class TabSwitcherAdapter extends RecyclerView.Adapter<TabSwitcherAdapter.ViewHolder> {

    private ArrayList<TabDataModel> mData;
    private LayoutInflater mInflater;
    private AdapterView.OnItemClickListener mClickListener;
    private MainActivity activity;

    // data is passed into the constructor
    public TabSwitcherAdapter(Context context, MainActivity activity) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = TabManager.getInstance().getTabList();
        this.activity = activity;
    }

    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.tab_cards, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each cell
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        if (position == 0) {
            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) holder
                    .titleView.getLayoutParams();
            //layoutParams.topMargin=140;
        }
        holder.titleView.setText(mData.get(position).getTitle());
        holder.snapshotView.setImageBitmap(mData.get(position).getSnapshot());
        holder.faviconView.setImageBitmap(mData.get(position).getFavicon());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity, "Item: " + position, Toast.LENGTH_SHORT).show();
                TabManager.getInstance().setCurrentTab(position);
                TabDataModel tdm = TabManager.getInstance().getTabList().get(position);
                activity.getWebView().loadUrl(tdm.getUrl());
                activity.getToolbarTitle().setText(tdm.getTitle());
                activity.getToolbarUrl().setText(tdm.getUrl());
//                activity.getWebView().c=tdm.getBackForwardList();

                activity.getTabdialog().dismiss();
            }
        });
    }

    // total number of cells
    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

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
