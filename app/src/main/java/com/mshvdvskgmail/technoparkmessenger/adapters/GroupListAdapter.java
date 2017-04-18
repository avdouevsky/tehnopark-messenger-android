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
import com.mshvdvskgmail.technoparkmessenger.activities.MainActivity;
import com.mshvdvskgmail.technoparkmessenger.models.ChatsListItem;
import com.mshvdvskgmail.technoparkmessenger.models.GroupsListItem;
import com.mshvdvskgmail.technoparkmessenger.network.model.Chat;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

/**
 * Created by mshvdvsk on 29/03/2017.
 */

public class GroupListAdapter extends RecyclerView.Adapter<GroupListAdapter.ViewHolder> {
    private ArrayList<Chat> groupsList;
    private View rowView;
    private Context context;
    private Chat currentItem;
    private int count;

    private String name;
    private String lastLine;
    private String time;
    private boolean isOnline;
    private boolean hasNew;
    private int newCount;


    private TextView itemName;
    private TextView itemLastMessage;
    private TextView itemTime;
    private ImageView itemOnline;
    private FrameLayout itemNewNotifi;
    private TextView itemNotifiCount;


    public GroupListAdapter(ArrayList <Chat> groupsList, Context context) {
        this.groupsList = groupsList;
        this.context = context;
        count = 0;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        rowView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_groups_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(rowView);
        viewHolder.context = context;
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
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

//        currentItem = groupsList.get(count);
//        count++;
        currentItem = groupsList.get(position);
        holder.setChat(currentItem);

        name = currentItem.name;
//        lastLine = "Сообщение из чата";//currentItem.getLastMessage();
//        time = currentItem.date;

        lastLine = "";
        try {
            lastLine = currentItem.last.message_text;
        }catch (NullPointerException e){

        }

        time = currentItem.getTimeAsString();
//        hasNew = false;
        newCount = currentItem.unread;

        hasNew = (boolean) (currentItem.unread != 0);


        itemName = (TextView) holder.mView.findViewById(R.id.name);
        itemLastMessage = (TextView) holder.mView.findViewById(R.id.last_message);
        itemTime = (TextView) holder.mView.findViewById(R.id.time);

        itemName.setText(name);
        itemLastMessage.setText(lastLine);
        itemTime.setText(time);

    }

    @Override
    public int getItemCount() {
        return groupsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        View mView;
        FrameLayout mFrameLayout;
        Chat chat;
        Context context;

        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            mFrameLayout = (FrameLayout) itemView.findViewById(R.id.item_separator);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((MainActivity) context).executeAction("showChat", chat);
                }
            });
        }

        public void setChat(Chat achat){
            chat = achat;
        }
    }

}
