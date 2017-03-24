package com.mshvdvskgmail.technoparkmessenger.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mshvdvskgmail.technoparkmessenger.R;
import com.mshvdvskgmail.technoparkmessenger.models.ProfileAttachment;

import java.util.ArrayList;

/**
 * Created by mshvdvsk on 03/02/2017.
 */

public class ProfileFilesAdapter extends RecyclerView.Adapter<ProfileFilesAdapter.ViewHolder> {

    private ArrayList<ProfileAttachment> files;
    private Context context;

    public ProfileFilesAdapter(ArrayList <ProfileAttachment> files, Context context) {
        this.files = files;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_profile_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(rowView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ProfileFilesAdapter.ViewHolder holder, int position) {
        if (position==0){
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) holder.mView.getLayoutParams();
            int dpValue = 37; // margin in dips
            float d = context.getResources().getDisplayMetrics().density;
            int margin = (int)(dpValue * d); // margin in pixels
            params.leftMargin = margin;
        } else {
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) holder.mView.getLayoutParams();
            params.leftMargin = 0;
        }
    }

    @Override
    public int getItemCount() {
        return files.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        View mView;
        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }
    }
}