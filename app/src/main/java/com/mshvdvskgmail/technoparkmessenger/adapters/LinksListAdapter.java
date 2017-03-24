package com.mshvdvskgmail.technoparkmessenger.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.mshvdvskgmail.technoparkmessenger.R;
import com.mshvdvskgmail.technoparkmessenger.models.ModelDocumentsItem;
import com.mshvdvskgmail.technoparkmessenger.models.ModelLinksItem;

import java.util.ArrayList;

import ca.barrenechea.widget.recyclerview.decoration.StickyHeaderAdapter;

/**
 * Created by mshvdvsk on 23/03/2017.
 */

public class LinksListAdapter extends RecyclerView.Adapter<LinksListAdapter.ViewHolder>
        implements StickyHeaderAdapter<LinksListAdapter.HeaderHolder> {

    private ArrayList<ModelLinksItem> links;
    private Context context;
    private FrameLayout mFrame;
    private LayoutInflater mInflater;

    public LinksListAdapter(ArrayList <ModelLinksItem> links, Context context) {
        this.links = links;
        this.context = context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public LinksListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_links_item, parent, false);
        LinksListAdapter.ViewHolder viewHolder = new LinksListAdapter.ViewHolder(rowView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(LinksListAdapter.ViewHolder holder, int position) {
        mFrame = holder.mBottom;
        ViewGroup.MarginLayoutParams params;
        if (position==0){
            params = (ViewGroup.MarginLayoutParams) holder.mView.getLayoutParams();
            int dpValue = -10; // margin in dips
            float d = context.getResources().getDisplayMetrics().density;
            int margin = (int)(dpValue * d); // margin in pixels
            params.topMargin = margin;
        } else {
            params = (ViewGroup.MarginLayoutParams) holder.mView.getLayoutParams();
            params.topMargin = 0;
        }

        if (position < links.size()-1){
            if (links.get(position).getLinkSent().charAt(0) ==
                    links.get(position+1).getLinkSent().charAt(0)) {
                mFrame.setVisibility(View.VISIBLE);
            } else {
                mFrame.setVisibility(View.INVISIBLE);
            }
        } else if (position == links.size()-1){
            mFrame.setVisibility(View.INVISIBLE);
            params = (ViewGroup.MarginLayoutParams) holder.mView.getLayoutParams();
            int dpValue = 40; // margin in dips
            float d = context.getResources().getDisplayMetrics().density;
            int margin = (int)(dpValue * d); // margin in pixels
            params.bottomMargin = margin;
        } else {
            mFrame.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return links.size();
    }

    @Override
    public long getHeaderId(int position) {
        return links.get(position).getLinkSent().subSequence(0, 1).charAt(0);
    }

    @Override
    public LinksListAdapter.HeaderHolder onCreateHeaderViewHolder(ViewGroup parent) {
        final View view = mInflater.inflate(R.layout.header_media, parent, false);
        return new LinksListAdapter.HeaderHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(LinksListAdapter.HeaderHolder viewHolder, int position) {
//        viewHolder.header.setText(""+media.get(position).getDate());
        viewHolder.header.setText(""+links.get(position).getLinkSent());
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        View mView;
        FrameLayout mBottom;

        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            mBottom = (FrameLayout) itemView.findViewById(R.id.bottom_line);
        }
    }

    static class HeaderHolder extends RecyclerView.ViewHolder {
        public TextView header;

        public HeaderHolder(View itemView) {
            super(itemView);
            header = (TextView) itemView.findViewById(R.id.text_item);
        }
    }
}