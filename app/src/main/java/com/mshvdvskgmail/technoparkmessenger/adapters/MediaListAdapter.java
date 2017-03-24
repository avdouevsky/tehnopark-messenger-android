package com.mshvdvskgmail.technoparkmessenger.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.mshvdvskgmail.technoparkmessenger.R;
import com.mshvdvskgmail.technoparkmessenger.models.ModelMediaList;

import java.util.ArrayList;

import ca.barrenechea.widget.recyclerview.decoration.*;

/**
 * Created by mshvdvsk on 21/03/2017.
 */

public class MediaListAdapter extends RecyclerView.Adapter<MediaListAdapter.ViewHolder>
        implements StickyHeaderAdapter<MediaListAdapter.HeaderHolder> {

    private ArrayList<ModelMediaList> media;
    private Context context;
    private FrameLayout mFrame;
    private LayoutInflater mInflater;

    public MediaListAdapter(ArrayList <ModelMediaList> media, Context context) {
        this.media = media;
        this.context = context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public MediaListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_media_item, parent, false);
        MediaListAdapter.ViewHolder viewHolder = new MediaListAdapter.ViewHolder(rowView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MediaListAdapter.ViewHolder holder, int position) {

        if (position < media.size()-1 && media.get(position).getDate().charAt(0) ==
                media.get(position+1).getDate().charAt(0)){
            mFrame = holder.mBottom;
            mFrame.setVisibility(View.GONE);
        } else if (position == media.size()-1){
            mFrame = holder.mBottom;
            mFrame.setVisibility(View.INVISIBLE);
        } else {
            mFrame = holder.mBottom;
            mFrame.setVisibility(View.VISIBLE);
        }

//        if(position==2){
//            mFrame = holder.mBottom;
//            mFrame.setVisibility(View.VISIBLE);
//        }
    }

    @Override
    public int getItemCount() {
        return media.size();
    }

    @Override
    public long getHeaderId(int position) {
        return media.get(position).getDate().subSequence(0, 1).charAt(0);
    }

    @Override
    public MediaListAdapter.HeaderHolder onCreateHeaderViewHolder(ViewGroup parent) {
        final View view = mInflater.inflate(R.layout.header_media, parent, false);
        return new MediaListAdapter.HeaderHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(MediaListAdapter.HeaderHolder viewHolder, int position) {
//        viewHolder.header.setText(""+media.get(position).getDate());
        viewHolder.header.setText(""+media.get(position).getDate());
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
