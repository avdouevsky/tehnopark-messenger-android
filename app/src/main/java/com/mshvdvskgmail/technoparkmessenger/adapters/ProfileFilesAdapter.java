package com.mshvdvskgmail.technoparkmessenger.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mshvdvskgmail.technoparkmessenger.R;
import com.mshvdvskgmail.technoparkmessenger.helpers.ICommand;
import com.mshvdvskgmail.technoparkmessenger.models.ProfileAttachment;
import com.mshvdvskgmail.technoparkmessenger.network.model.Attachment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mshvdvsk on 03/02/2017.
 */

public class ProfileFilesAdapter extends RecyclerView.Adapter<ProfileFilesAdapter.ViewHolder> {
    private List<Attachment> files = new ArrayList<>();
    private Context context;

    private ICommand<Attachment> clickListener;

    public ProfileFilesAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<Attachment> attachments){
        files = attachments;
        sort();
        notifyDataSetChanged();
    }

    private void sort(){
        /* do nothing */
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_profile_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(rowView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ProfileFilesAdapter.ViewHolder holder, int position) {
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

        String mime = "FILE";

//        substring(filename.lastIndexOf('.') + 1)
        holder.type.setText(files.get(position).name.substring(files.get(position).name.lastIndexOf('.') + 1));
        if(holder.type.getText().equals("")||holder.type.getText().length()>4){
            holder.type.setText("file");
        }
        holder.name.setText(files.get(position).name);
//        if(files.get(position).name != null) holder.name.setText(files.get(position).name);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(clickListener != null) clickListener.exec(files.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return files.size();
    }

    public void setClickListener(ICommand<Attachment> clickListener) {
        this.clickListener = clickListener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        //recycler_item_profile_attached_file_tv_name
        //recycler_item_profile_attached_file_tv_type
        View mView;
        TextView type;
        TextView name;
        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            name = (TextView) itemView.findViewById(R.id.recycler_item_profile_attached_file_tv_name);
            type = (TextView) itemView.findViewById(R.id.recycler_item_profile_attached_file_tv_type);
        }

    }
}