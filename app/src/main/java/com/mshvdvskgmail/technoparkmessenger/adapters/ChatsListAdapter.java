package com.mshvdvskgmail.technoparkmessenger.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import com.mshvdvskgmail.technoparkmessenger.Fragments;
import com.mshvdvskgmail.technoparkmessenger.events.SwitchFragmentEvent;
import com.mshvdvskgmail.technoparkmessenger.helpers.ArgsBuilder;
import com.mshvdvskgmail.technoparkmessenger.helpers.ICommand;
import com.mshvdvskgmail.technoparkmessenger.network.model.Chat;
import com.mshvdvskgmail.technoparkmessenger.view.ChatItemView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by mshvdvsk on 16/03/2017.
 */
public class ChatsListAdapter extends RecyclerView.Adapter<ChatsListAdapter.ViewHolder> {
    private List<Chat> chatsList = new ArrayList<>();
    private Context context;
    private ICommand<Chat> clickListener;

    public ChatsListAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<Chat> chats){
        chatsList = chats;
        sort();
        notifyDataSetChanged();
    }

    public void addData(List<Chat> chats){
        chatsList.addAll(chats);
        sort();
        notifyDataSetChanged();
    }

    private void sort(){
        Collections.sort(chatsList, new Comparator<Chat>() {
            public int compare(Chat chat1, Chat chat2) {

                return chat1.date.compareTo(chat2.date);
            }
        });
    }

    public void setClickListener(ICommand<Chat> clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(new ChatItemView(context));
    }

    @Override
    public void onBindViewHolder(final ChatsListAdapter.ViewHolder holder, int position) {
        holder.getView().setData(chatsList.get(position));
        holder.getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(clickListener != null) clickListener.exec(chatsList.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return chatsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(ChatItemView itemView) {
            super(itemView);
        }

        public ChatItemView getView(){
            return (ChatItemView) itemView;
        }
    }
}
