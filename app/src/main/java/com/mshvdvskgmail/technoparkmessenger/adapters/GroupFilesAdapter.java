package com.mshvdvskgmail.technoparkmessenger.adapters;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mshvdvskgmail.technoparkmessenger.R;
import com.mshvdvskgmail.technoparkmessenger.models.ProfileAttachment;
import com.mshvdvskgmail.technoparkmessenger.network.model.Attachment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mshvdvsk on 29/03/2017.
 */
public class GroupFilesAdapter extends RecyclerView.Adapter<GroupFilesAdapter.ViewHolder> {
    private Context context;
    private List<Attachment> files = new ArrayList<>();

    public GroupFilesAdapter(Context context) {
        this.context = context;
    }

    public void setData(@Nullable List<Attachment> attachments){
        if(attachments == null) attachments = new ArrayList<>();
        sort();
        this.files = attachments;
        notifyDataSetChanged();
    }

    public void addData(@Nullable List<Attachment> attachments){
        if(attachments == null) return;
        files.addAll(attachments);
        sort();
        notifyDataSetChanged();
    }

    private void sort(){
        // do nothing
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_group_attached_item, parent, false);
        return new ViewHolder(rowView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (position == 0){
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) holder.getView().getLayoutParams();
            int dpValue = 37; // margin in dips
            float d = context.getResources().getDisplayMetrics().density;
            int margin = (int)(dpValue * d); // margin in pixels
            params.leftMargin = margin;
        } else {
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) holder.getView().getLayoutParams();
            params.leftMargin = 0;
        }
    }

    @Override
    public int getItemCount() {
        return files.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }

        public View getView(){
            return itemView;
        }
    }
}
