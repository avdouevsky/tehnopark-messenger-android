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
    private TextView tvFileType;
    private TextView tvFileSize;
    private TextView tvFileName;
    private ImageView imageStatus;
    private ImageView imageBlobCorner;


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
            case 4: rowView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recycler_message_item_incoming_doc, parent, false);
                ChatListAdapter.FileHolder viewHolder4 = new ChatListAdapter.FileHolder(rowView);
                return viewHolder4;
            case 5: rowView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recycler_message_item_outgoing_doc, parent, false);
                ChatListAdapter.FileHolder viewHolder5 = new ChatListAdapter.FileHolder(rowView);
                return viewHolder5;
            case 6: rowView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recycler_message_item_missed_call, parent, false);
                    ChatListAdapter.MissedCallHolder viewHolder6 = new ChatListAdapter.MissedCallHolder(rowView);
                    return viewHolder6;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        int dpValue = 7; // margin in dips
        float d = context.getResources().getDisplayMetrics().density;
        int marginTopSmall = (int)(dpValue * d); // margin in pixels

        dpValue = 23; // margin in dips
        int marginTopBig = (int)(dpValue * d); // margin in pixels


//        params = (ViewGroup.MarginLayoutParams) holder.view.getLayoutParams();

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

                /* setting blob corner and blob margin */

                imageBlobCorner = msgHolder.imageBlobCorner;

                try{
                    if (messagesList.get(position).isIncoming()!=messagesList.get(position+1).isIncoming()){
                        imageBlobCorner.setVisibility(View.VISIBLE);
                        params = (ViewGroup.MarginLayoutParams) msgHolder.view.getLayoutParams();
                        params.topMargin = marginTopSmall;
                        params.bottomMargin = 0;
                    } else {
                        imageBlobCorner.setVisibility(View.INVISIBLE);
                        params = (ViewGroup.MarginLayoutParams) msgHolder.view.getLayoutParams();
                        params.topMargin = marginTopBig;
                        params.bottomMargin = 0;
                    }
                } catch (Exception e) {
                    imageBlobCorner.setVisibility(View.VISIBLE);

                    if (messagesList.get(position).isIncoming()!=messagesList.get(position-1).isIncoming()) {
                        params = (ViewGroup.MarginLayoutParams) msgHolder.view.getLayoutParams();
                        params.topMargin = marginTopBig;
                        params.bottomMargin = marginTopBig;
                    } else {
                        params = (ViewGroup.MarginLayoutParams) msgHolder.view.getLayoutParams();
                        params.topMargin = marginTopSmall;
                        params.bottomMargin = marginTopBig;
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

                /* setting blob corner and blob margin */

                imageBlobCorner = picHolder.imageBlobCorner;

                try{
                    if (messagesList.get(position).isIncoming()!=messagesList.get(position+1).isIncoming()){
                        imageBlobCorner.setVisibility(View.VISIBLE);
                        params = (ViewGroup.MarginLayoutParams) picHolder.view.getLayoutParams();
                        params.topMargin = marginTopSmall;
                        params.bottomMargin = 0;
                    } else {
                        imageBlobCorner.setVisibility(View.INVISIBLE);
                        params = (ViewGroup.MarginLayoutParams) picHolder.view.getLayoutParams();
                        params.topMargin = marginTopBig;
                        params.bottomMargin = 0;
                    }
                } catch (Exception e) {
                    imageBlobCorner.setVisibility(View.VISIBLE);

                    if (messagesList.get(position).isIncoming()!=messagesList.get(position-1).isIncoming()) {
                        params = (ViewGroup.MarginLayoutParams) picHolder.view.getLayoutParams();
                        params.topMargin = marginTopBig;
                        params.bottomMargin = marginTopBig;
                    } else {
                        params = (ViewGroup.MarginLayoutParams) picHolder.view.getLayoutParams();
                        params.topMargin = marginTopSmall;
                        params.bottomMargin = marginTopBig;
                    }

                }

                break;

            case 4:
            case 5:

                FileHolder fileHolder = (FileHolder) holder;

                tvFileType = fileHolder.tvFileType;
                tvFileType.setText(messagesList.get(position).getFileType());

                tvFileName = fileHolder.tvFileName;
                tvFileName.setText(messagesList.get(position).getFileName());

                tvFileSize = fileHolder.tvFileSize;
                tvFileSize.setText(messagesList.get(position).getFileSize());

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

                imageBlobCorner = fileHolder.imageBlobCorner;

                try{
                    if (messagesList.get(position).isIncoming()!=messagesList.get(position+1).isIncoming()){
                        imageBlobCorner.setVisibility(View.VISIBLE);
                        params = (ViewGroup.MarginLayoutParams) fileHolder.view.getLayoutParams();
                        params.topMargin = marginTopSmall;
                        params.bottomMargin = 0;
                    } else {
                        imageBlobCorner.setVisibility(View.INVISIBLE);
                        params = (ViewGroup.MarginLayoutParams) fileHolder.view.getLayoutParams();
                        params.topMargin = marginTopBig;
                        params.bottomMargin = 0;
                    }
                } catch (Exception e) {
                    imageBlobCorner.setVisibility(View.VISIBLE);

                    if (messagesList.get(position).isIncoming()!=messagesList.get(position-1).isIncoming()) {
                        params = (ViewGroup.MarginLayoutParams) fileHolder.view.getLayoutParams();
                        params.topMargin = marginTopBig;
                        params.bottomMargin = marginTopBig;
                    } else {
                        params = (ViewGroup.MarginLayoutParams) fileHolder.view.getLayoutParams();
                        params.topMargin = marginTopSmall;
                        params.bottomMargin = marginTopBig;
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
        ImageView imageBlobCorner;

        public MessageViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            text = (TextView) itemView.findViewById(R.id.text);
            time = (TextView) itemView.findViewById(R.id.time);
            status = (ImageView) itemView.findViewById(R.id.status);
            imageBlobCorner= (ImageView) itemView.findViewById(R.id.corner);
        }
    }

    public static class MissedCallHolder extends RecyclerView.ViewHolder {
        View view;
        TextView text;
        ImageView imageBlobCorner;

        public MissedCallHolder(View itemView) {
            super(itemView);
            view = itemView;
            text = (TextView) itemView.findViewById(R.id.text);
            imageBlobCorner= (ImageView) itemView.findViewById(R.id.corner);
        }
    }

    public static class PicHolder extends RecyclerView.ViewHolder {
        View view;
        ImageView pic;
        TextView time;
        ImageView status;
        ImageView imageBlobCorner;

        public PicHolder(View itemView) {
            super(itemView);
            view = itemView;
            pic = (ImageView) itemView.findViewById(R.id.picture);
            time = (TextView) itemView.findViewById(R.id.time);
            status = (ImageView) itemView.findViewById(R.id.status);
            imageBlobCorner= (ImageView) itemView.findViewById(R.id.corner);
        }
    }

    public static class FileHolder extends RecyclerView.ViewHolder {
        View view;
        TextView tvFileType;
        TextView tvFileName;
        TextView tvFileSize;
        TextView tvTime;
        ImageView imageStatus;
        ImageView imageBlobCorner;

        public FileHolder(View itemView) {
            super(itemView);
            view = itemView;
            tvFileType = (TextView) itemView.findViewById(R.id.fileType);
            tvFileName = (TextView) itemView.findViewById(R.id.fileName);
            tvFileSize = (TextView) itemView.findViewById(R.id.fileSize);
            tvTime = (TextView) itemView.findViewById(R.id.time);
            imageStatus = (ImageView) itemView.findViewById(R.id.status);
            imageBlobCorner= (ImageView) itemView.findViewById(R.id.corner);
        }
    }
}
