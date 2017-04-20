package com.mshvdvskgmail.technoparkmessenger.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mshvdvskgmail.technoparkmessenger.R;
import com.mshvdvskgmail.technoparkmessenger.ChatController;
import com.mshvdvskgmail.technoparkmessenger.Controller;
import com.mshvdvskgmail.technoparkmessenger.network.model.Chat;
import com.mshvdvskgmail.technoparkmessenger.network.model.Message;
import com.mshvdvskgmail.technoparkmessenger.view.MessageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mshvdvsk on 30/03/2017.
 */

public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.MessageViewHolder> {
    private final static String TAG = ChatListAdapter.class.toString();

    private Context context;
    private List<Message> messages = new ArrayList<>();

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
        public MessageViewHolder(View itemView) {
            super(itemView);
        }

        public MessageView getView(){
            return (MessageView) itemView;
        }
    }
}
