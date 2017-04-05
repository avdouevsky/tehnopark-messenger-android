package com.mshvdvskgmail.technoparkmessenger.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.mshvdvskgmail.technoparkmessenger.Controller;
import com.mshvdvskgmail.technoparkmessenger.R;
import com.mshvdvskgmail.technoparkmessenger.activities.MainActivity;
import com.mshvdvskgmail.technoparkmessenger.models.ChatsListItem;
import com.mshvdvskgmail.technoparkmessenger.network.REST;
import com.mshvdvskgmail.technoparkmessenger.network.model.Chat;
import com.mshvdvskgmail.technoparkmessenger.network.model.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mshvdvsk on 16/03/2017.
 */

public class ChatsListAdapter extends RecyclerView.Adapter<ChatsListAdapter.ViewHolder> {
    private ArrayList<Chat> chatsList;
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


    public ChatsListAdapter(ArrayList <Chat> chatsList, Context context) {
        this.chatsList = chatsList;
        this.context = context;
        count = 0;
    }

    @Override
    public ChatsListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        rowView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_chatslist_item, parent, false);
        ChatsListAdapter.ViewHolder viewHolder = new ChatsListAdapter.ViewHolder(rowView);
        viewHolder.context = context;
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ChatsListAdapter.ViewHolder holder, int position) {
        if (position==0){
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) holder.mView.getLayoutParams();
            params.topMargin = 25;
        } else {
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) holder.mView.getLayoutParams();
            params.topMargin = 0;
        }

        if (position==chatsList.size()-1){
            FrameLayout mFrameLayout = holder.mFrameLayout;
            mFrameLayout.setVisibility(View.GONE);
        }

        ImageView profileIcon = (ImageView) holder.mView.findViewById(R.id.profile_icon);
        Picasso.with(context).load(R.drawable.pushkin).transform(new RoundedCornersTransformation(360,0)).into(profileIcon);

        currentItem = chatsList.get(position);
        holder.setChat(currentItem);
//        count++;
        Log.w("currentItem.Users()", ""+currentItem.Users());
        User user = currentItem.Users().get(0);
        name = user.cn;
        lastLine = "";
        time = currentItem.date;
        hasNew = false;
        newCount = 0;

//        name = currentItem.getName();
//        lastLine = currentItem.getLastLine();
//        time = currentItem.getTime();
//        isOnline = currentItem.isOnline();
//        hasNew = currentItem.hasNew();
//        newCount = currentItem.getNewCount();

        itemName = (TextView) holder.mView.findViewById(R.id.name);
        itemLastMessage = (TextView) holder.mView.findViewById(R.id.last_message);
        itemTime = (TextView) holder.mView.findViewById(R.id.time);
        itemOnline = (ImageView) holder.mView.findViewById(R.id.online_dot);
        itemNewNotifi = (FrameLayout) holder.mView.findViewById(R.id.notification_new);
        itemNotifiCount = (TextView) holder.mView.findViewById(R.id.number_of_new_messages);
        itemNewNotifi = (FrameLayout) holder.mView.findViewById(R.id.notification_new);
        itemNotifiCount = (TextView) holder.mView.findViewById(R.id.number_of_new_messages);

        itemName.setText(name);
        itemLastMessage.setText(lastLine);
        itemTime.setText(time);


        REST.getInstance().user_status(Controller.getInstance().getAuth().getUser().token.session_id, Controller.getInstance().getAuth().getUser().token.token, user.id).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.body() == "1") {
                    itemOnline.setVisibility(View.VISIBLE);
                } else itemOnline.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                itemOnline.setVisibility(View.GONE);
            }
        });

//        if (isOnline) {
//            itemOnline.setVisibility(View.VISIBLE);
//        } else itemOnline.setVisibility(View.GONE);


        if (hasNew) {
            itemNewNotifi.setVisibility(View.VISIBLE);
            itemNotifiCount.setVisibility(View.VISIBLE);
            itemNotifiCount.setText(Integer.toString(newCount));
        } else {
            itemNewNotifi.setVisibility(View.GONE);
            itemNotifiCount.setVisibility(View.GONE);
        }


    }

    @Override
    public int getItemCount() {
        return chatsList.size();
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
