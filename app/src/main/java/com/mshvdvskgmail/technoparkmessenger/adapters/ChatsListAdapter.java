package com.mshvdvskgmail.technoparkmessenger.adapters;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mshvdvskgmail.technoparkmessenger.Controller;
import com.mshvdvskgmail.technoparkmessenger.R;
import com.mshvdvskgmail.technoparkmessenger.fragments.FragmentChat;
import com.mshvdvskgmail.technoparkmessenger.fragments.FragmentSearch;
import com.mshvdvskgmail.technoparkmessenger.activities.MainActivity;
import com.mshvdvskgmail.technoparkmessenger.models.ChatsListItem;
import com.mshvdvskgmail.technoparkmessenger.network.REST;
import com.mshvdvskgmail.technoparkmessenger.network.model.Chat;
import com.mshvdvskgmail.technoparkmessenger.network.model.ChatUser;
import com.mshvdvskgmail.technoparkmessenger.network.model.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

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

    private FragmentManager fManager;

    public ChatsListAdapter(ArrayList <Chat> chatsList, Context context) {
        this.chatsList = chatsList;
        this.context = context;
        this.fManager = fManager;
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

        ImageView profileIcon = (ImageView) holder.mView.findViewById(R.id.recycler_item_chatslist_image_profile_pic);
        Picasso.with(context).load(R.drawable.pushkin).transform(new RoundedCornersTransformation(360,0)).into(profileIcon);

        currentItem = chatsList.get(position);
        holder.setChat(currentItem);
//        count++;
//        Log.w("currentItem.Users()", ""+currentItem.Users());
//        List<User> usersList = currentItem.Users();
//        User user = new User();
//        if(usersList.size() > 0) {
//            user = currentItem.Users().get(0);
//        }
        ChatUser user = currentItem.users.get(0);
        Log.d("temp", "users "+currentItem.users);
        Log.d("temp", "user "+user);

        if(user.User().id == Controller.getInstance().getAuth().getUser().id){
            user = currentItem.users.get(1);
        }

        itemName = (TextView) holder.mView.findViewById(R.id.recycler_item_chatslist_tv_name);
        itemLastMessage = (TextView) holder.mView.findViewById(R.id.recycler_item_chatslist_tv_message);
        itemTime = (TextView) holder.mView.findViewById(R.id.recycler_item_chatslist_tv_time);
        itemOnline = (ImageView) holder.mView.findViewById(R.id.recycler_item_chatslist_image_online);
        itemNewNotifi = (FrameLayout) holder.mView.findViewById(R.id.recycler_item_chatslist_fl_notification);
        itemNotifiCount = (TextView) holder.mView.findViewById(R.id.recycler_item_chatslist_tv_notification);
//        if(currentItem.admin.equals(user.id)){
//            user = currentItem.users.get(1);
//        }
        if(user.User()!=null) {
            name = user.User().cn;
            REST.getInstance().user_status(Controller.getInstance().getAuth().getUser().token.session_id, Controller.getInstance().getAuth().getUser().token.token, user.User().id).enqueue(new Callback<String>() {
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
        }
        lastLine = "";
        try {
            lastLine = currentItem.last.message_text;
        }catch (NullPointerException e){

        }

        time = currentItem.getTimeAsString();
//        hasNew = false;
        newCount = currentItem.unread;

        hasNew = (boolean) (currentItem.unread != 0);


//        name = currentItem.getName();
//        lastLine = currentItem.getLastLine();
//        time = currentItem.getTime();
//        isOnline = currentItem.isOnline();
//        hasNew = currentItem.hasNew();
//        newCount = currentItem.getNewCount();

        itemName = (TextView) holder.mView.findViewById(R.id.recycler_item_chatslist_tv_name);
        itemLastMessage = (TextView) holder.mView.findViewById(R.id.recycler_item_chatslist_tv_message);
        itemTime = (TextView) holder.mView.findViewById(R.id.recycler_item_chatslist_tv_time);
        itemOnline = (ImageView) holder.mView.findViewById(R.id.recycler_item_chatslist_image_online);
        itemNewNotifi = (FrameLayout) holder.mView.findViewById(R.id.recycler_item_chatslist_fl_notification);
        itemNotifiCount = (TextView) holder.mView.findViewById(R.id.recycler_item_chatslist_tv_notification);


        itemName.setText(name);
        itemLastMessage.setText(lastLine);
        itemTime.setText(time);




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

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentChat chat = new FragmentChat();
                //TODO
//                fManager.beginTransaction()
//                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
//                        .replace(R.id.container, chat)
//                        .addToBackStack(null)
//                        .commit();
            }
        });

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
            mFrameLayout = (FrameLayout) itemView.findViewById(R.id.recycler_item_chatslist_fl_separator);
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
