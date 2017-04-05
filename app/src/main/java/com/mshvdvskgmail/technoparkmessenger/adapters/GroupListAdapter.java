package com.mshvdvskgmail.technoparkmessenger.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.mshvdvskgmail.technoparkmessenger.R;
import com.mshvdvskgmail.technoparkmessenger.models.ChatsListItem;
import com.mshvdvskgmail.technoparkmessenger.models.GroupsListItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

/**
 * Created by mshvdvsk on 29/03/2017.
 */

public class GroupListAdapter extends RecyclerView.Adapter<GroupListAdapter.ViewHolder> {
    private ArrayList<GroupsListItem> groupsList;
    private View rowView;
    private Context context;
    private GroupsListItem currentItem;
    private int count;

    private String name;
    private String lastLine;
    private String time;


    private TextView tvName;
    private TextView tvLastMessage;
    private TextView tvTime;


    public GroupListAdapter(ArrayList <GroupsListItem> groupsList, Context context) {
        this.groupsList = groupsList;
        this.context = context;
        count = 0;
    }

    @Override
    public GroupListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        rowView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_groups_item, parent, false);
        GroupListAdapter.ViewHolder viewHolder = new GroupListAdapter.ViewHolder(rowView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(GroupListAdapter.ViewHolder holder, int position) {
        if (position==0){
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) holder.mView.getLayoutParams();
            params.topMargin = 25;
        } else {
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) holder.mView.getLayoutParams();
            params.topMargin = 0;
        }

        if (position==groupsList.size()-1){
            FrameLayout mFrameLayout = holder.mFrameLayout;
            mFrameLayout.setVisibility(View.GONE);
        }

//        ImageView profileIcon = (ImageView) holder.mView.findViewById(R.id.profile_icon);
//        Picasso.with(context).load(R.drawable.pushkin).transform(new RoundedCornersTransformation(360,0)).into(profileIcon);

        currentItem = groupsList.get(count);
        count++;

        name = currentItem.getName();
        lastLine = currentItem.getLastMessage();
        time = currentItem.getTime();

        tvName = holder.tvName;
        tvLastMessage = holder.tvLastMessage;
        tvTime = holder.tvTime;

        tvName.setText(name);
        tvLastMessage.setText(lastLine);
        tvName.setText(time);

    }

    @Override
    public int getItemCount() {
        return groupsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        View mView;
        FrameLayout mFrameLayout;
        TextView tvName;
        TextView tvLastMessage;
        TextView tvTime;

        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            mFrameLayout = (FrameLayout) itemView.findViewById(R.id.recycler_item_groups_fl_item_separator);
            tvName = (TextView) itemView.findViewById(R.id.recycler_item_groups_tv_name);
            tvLastMessage = (TextView) itemView.findViewById(R.id.recycler_item_groups_tv_last_message);
            tvTime = (TextView) itemView.findViewById(R.id.recycler_item_groups_tv_time);
        }
    }

}
