package com.mshvdvskgmail.technoparkmessenger.adapters;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;

import com.mshvdvskgmail.technoparkmessenger.R;
import com.mshvdvskgmail.technoparkmessenger.ChatController;
import com.mshvdvskgmail.technoparkmessenger.Controller;
import com.mshvdvskgmail.technoparkmessenger.network.model.Chat;
import com.mshvdvskgmail.technoparkmessenger.network.model.Message;
import com.mshvdvskgmail.technoparkmessenger.view.MessageView;
import android.view.ViewGroup.MarginLayoutParams;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mshvdvsk on 30/03/2017.
 */

public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.MessageViewHolder> {
    private final static String TAG = ChatListAdapter.class.toString();
//    @IdRes
//    private final static int MARGIN_BOTTOM_BIG = R.dimen.message_item_bottom_big;
//    @IdRes
//    private final static int MARGIN_BOTTOM_SMALL = R.dimen.message_item_bottom_small;

    private Context context;
    private List<Message> messages = new ArrayList<>();
    private ViewGroup.MarginLayoutParams params;

    private Chat chat;

    public ChatListAdapter(Context context, Chat chat) {
        this.context = context;
        this.chat = chat;

    }

    public void setData(List<Message> messages){
        this.messages = messages;
        sort();
        notifyDataSetChanged();
    }

    public void addData(List<Message> messages){
        this.messages.addAll(messages);
        sort();
        notifyDataSetChanged();
    }

    public void addData(Message message){
        this.messages.add(message);
        sort();
        notifyDataSetChanged();
    }

    private void sort(){
        /* do nothing */
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MessageViewHolder(new MessageView(context));
    }

    @Override
    public void onBindViewHolder(MessageViewHolder holder, int position) {
        Message message = messages.get(position);
//        View a = holder.getView();
////        params = (ViewGroup.MarginLayoutParams) a.getLayoutParams();
//        LayoutParams lp = (LayoutParams) a.getLayoutParams();

//        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) holder.test.getLayoutParams();

//        params

//        try{
//            params = (ViewGroup.MarginLayoutParams) holder.getView().getLayoutParams();
//            Log.d("wow", "message.user_token " + message.user_token);
//
//            if (messages.get(position).sender!=messages.get(position+1).sender){
//                params.bottomMargin = (int)context.getResources().getDimension(R.dimen.message_item_bottom_big);
//            } else {
//                params.bottomMargin = (int)context.getResources().getDimension(R.dimen.message_item_bottom_small);
//            }
//        } catch (Exception e){
////            params.bottomMargin = (int)context.getResources().getDimension(R.dimen.message_item_bottom_big);
//        }


        Log.d("wowchick", "params" + params);
        holder.getView().setData(message, chat.peer2peer == 1);
        if(message.getStatus() != Message.Status.READ) {
            ChatController.getInstance().r.sendMessageStatus(
                    Controller.getInstance().getAuth().user.token, Controller.getInstance().getAuth().user.id, chat.uuid, message.uuid, message.local_id, Message.Status.DELIVERED);
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public static class MessageViewHolder extends RecyclerView.ViewHolder {
        MessageView test;
        public MessageViewHolder(View itemView) {
            super(itemView);
            test = (MessageView)itemView;
        }

        public MessageView getView(){
            return (MessageView) itemView;
        }
    }
}
