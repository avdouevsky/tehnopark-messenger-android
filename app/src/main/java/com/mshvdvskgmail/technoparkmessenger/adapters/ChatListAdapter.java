package com.mshvdvskgmail.technoparkmessenger.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mshvdvskgmail.technoparkmessenger.R;
import com.mshvdvskgmail.technoparkmessenger.fragments.FragmentDocumentsList;
import com.mshvdvskgmail.technoparkmessenger.models.MessageChatItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

/**
 * Created by mshvdvsk on 30/03/2017.
 */

public class ChatListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final static String TAG = FragmentDocumentsList.class.toString();
    private ArrayList<MessageChatItem> messagesList;
    private View rowView;
    private ViewGroup.MarginLayoutParams params;
    private LinearLayout.LayoutParams params2;
    private Context context;
    private MessageChatItem currentItem;
    private int count;

    private String text;
    private String time;
    private int status;
    private int type;

    private TextView tvText;
    private TextView tvTime;
    private ImageView imageStatus;


    public ChatListAdapter(ArrayList <MessageChatItem> messagesList, Context context) {
        this.messagesList = messagesList;
        this.context = context;
        count = 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType){
            case 0: rowView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recycler_message_item_incoming_text, parent, false);
                    ChatListAdapter.MessageViewHolder viewHolder0 = new ChatListAdapter.MessageViewHolder(rowView);
                    return viewHolder0;
            case 1: rowView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recycler_message_item_outgoing_text, parent, false);
                    ChatListAdapter.MessageViewHolder viewHolder1 = new ChatListAdapter.MessageViewHolder(rowView);
                    return viewHolder1;
            case 2: rowView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recycler_message_item_incoming_pic, parent, false);
                    ChatListAdapter.PicHolder viewHolder2 = new ChatListAdapter.PicHolder(rowView);
                    return viewHolder2;
            case 3: rowView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recycler_message_item_outgoing_pic, parent, false);
                    ChatListAdapter.PicHolder viewHolder3 = new ChatListAdapter.PicHolder(rowView);
                    return viewHolder3;

            case 6: rowView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recycler_message_item_missed_call, parent, false);
                    ChatListAdapter.MissedCallHolder viewHolder6 = new ChatListAdapter.MissedCallHolder(rowView);
                    return viewHolder6;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

//        params = (ViewGroup.MarginLayoutParams) holder0.view.getLayoutParams();
//
//        if (position==0){
//            params.topMargin = 25;
//        } else {
//            params.topMargin = 0;
//        }

//        params2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//        if (messagesList.get(position).isIncoming()){
//            params2.gravity = Gravity.LEFT;
//            holder.view.setLayoutParams(params2);
//        } else {
//            params2.gravity = Gravity.RIGHT;
//            holder.view.setLayoutParams(params2);
//        }

        type = messagesList.get(position).getType();

        switch (type) {


            case 0:
            case 1:

                MessageViewHolder msgHolder = (MessageViewHolder) holder;

                tvText = msgHolder.text;
                tvTime = msgHolder.time;
                imageStatus = msgHolder.status;

                tvText.setText(messagesList.get(position).getText());
                tvTime.setText(messagesList.get(position).getTime());

                  /* if outgoing, show status */
                if (messagesList.get(position).getType() == 1) {

                    status = messagesList.get(position).getStatus();
                    switch (status) {
                        case 0:
                            imageStatus.setVisibility(View.GONE);
                            break;
                        case 1:
                            imageStatus.setVisibility(View.VISIBLE);
                            imageStatus.setImageResource(R.drawable.ic_message_pending);
                            break;
                        case 2:
                            imageStatus.setVisibility(View.VISIBLE);
                            imageStatus.setImageResource(R.drawable.ic_message_sent);
                            break;
                        case 3:
                            imageStatus.setVisibility(View.VISIBLE);
                            imageStatus.setImageResource(R.drawable.ic_message_recieved);
                            break;
                        case 4:
                            imageStatus.setVisibility(View.VISIBLE);
                            imageStatus.setImageResource(R.drawable.ic_message_read);
                            break;
                    }
                }
                break;

            case 2:
            case 3:

                PicHolder picHolder = (PicHolder) holder;

                imageStatus = picHolder.status;

                tvTime = picHolder.time;
                tvTime.setText(messagesList.get(position).getTime());

                ImageView pic = picHolder.pic;
                Picasso.with(context).load(R.drawable.oh_my_cat).transform(new RoundedCornersTransformation(20,0)).into(pic); // TODO обрезалка углов работает плохо

                if (messagesList.get(position).getType() == 3) {

                    status = messagesList.get(position).getStatus();
                    switch (status) {
                        case 0:
                            imageStatus.setVisibility(View.GONE);
                            break;
                        case 1:
                            imageStatus.setVisibility(View.VISIBLE);
                            imageStatus.setImageResource(R.drawable.ic_message_pending);
                            break;
                        case 2:
                            imageStatus.setVisibility(View.VISIBLE);
                            imageStatus.setImageResource(R.drawable.ic_message_sent);
                            break;
                        case 3:
                            imageStatus.setVisibility(View.VISIBLE);
                            imageStatus.setImageResource(R.drawable.ic_message_recieved);
                            break;
                        case 4:
                            imageStatus.setVisibility(View.VISIBLE);
                            imageStatus.setImageResource(R.drawable.ic_message_read);
                            break;
                    }
                }
                break;

            case 6:

                MissedCallHolder missCallHolder = (MissedCallHolder) holder;

                tvText = missCallHolder.text;
                tvText.setText(messagesList.get(position).getText());
                break;
        }




    }

    @Override
    public int getItemCount() {
        return messagesList.size();
    }

    @Override
    public int getItemViewType(int position) {
        type = messagesList.get(position).getType();
        switch (type) {
            case 0: return 0; // incoming text
            case 1: return 1; // outgoing text
            case 2: return 2; // incoming pic
            case 3: return 3; // outgoing pic
            case 4: return 4; // incoming doc
            case 5: return 5; // outgoing doc
            case 6: return 6; // missed call
        }
        return -1;
    }

    public static class MessageViewHolder extends RecyclerView.ViewHolder {
        View view;
        TextView text;
        TextView time;
        ImageView status;

        public MessageViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            text = (TextView) itemView.findViewById(R.id.text);
            time = (TextView) itemView.findViewById(R.id.time);
            status = (ImageView) itemView.findViewById(R.id.status);
        }
    }

    public static class MissedCallHolder extends RecyclerView.ViewHolder {
        View view;
        TextView text;

        public MissedCallHolder(View itemView) {
            super(itemView);
            view = itemView;
            text = (TextView) itemView.findViewById(R.id.text);
        }
    }

    public static class PicHolder extends RecyclerView.ViewHolder {
        View view;
        ImageView pic;
        TextView time;
        ImageView status;

        public PicHolder(View itemView) {
            super(itemView);
            view = itemView;
            pic = (ImageView) itemView.findViewById(R.id.picture);
            time = (TextView) itemView.findViewById(R.id.time);
            status = (ImageView) itemView.findViewById(R.id.status);
        }
    }

}
