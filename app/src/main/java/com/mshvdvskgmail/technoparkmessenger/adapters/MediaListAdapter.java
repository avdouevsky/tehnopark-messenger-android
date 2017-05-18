package com.mshvdvskgmail.technoparkmessenger.adapters;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.mshvdvskgmail.technoparkmessenger.R;
import com.mshvdvskgmail.technoparkmessenger.models.LinksItem;
import com.mshvdvskgmail.technoparkmessenger.models.MediaList;
import com.mshvdvskgmail.technoparkmessenger.network.REST;
import com.mshvdvskgmail.technoparkmessenger.network.model.Attachment;
import com.squareup.picasso.Picasso;

import org.pjsip.pjsua2.VideoWindow;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import ca.barrenechea.widget.recyclerview.decoration.*;

/**
 * Created by mshvdvsk on 21/03/2017.
 */

public class MediaListAdapter extends RecyclerView.Adapter<MediaListAdapter.ViewHolder>
        implements StickyHeaderAdapter<MediaListAdapter.HeaderHolder> {
    private final static String TAG = MediaListAdapter.class.toString();

    private List<Attachment> attachments;
    private Context context;
    private FrameLayout mFrame;
    private LayoutInflater mInflater;
    private MediaList current;

    private ImageView pictureFirst;
    private ImageView pictureSecond;
    private ImageView pictureThird;
    private ImageView pictureForth;

    private ImageView pictureSelectorFirst;
    private ImageView pictureSelectorSecond;
    private ImageView pictureSelectorThird;
    private ImageView pictureSelectorForth;

    public boolean isSelecting;
    private int currentItem;


    public MediaListAdapter(List<Attachment> attachments, Context context) {
        this.attachments = attachments;
        this.context = context;
        currentItem = 0;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public MediaListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_media_item, parent, false);
        MediaListAdapter.ViewHolder viewHolder = new MediaListAdapter.ViewHolder(rowView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MediaListAdapter.ViewHolder holder, final int position) {

        pictureFirst = holder.pictureFirst;
        pictureSecond = holder.pictureSecond;
        pictureThird = holder.pictureThird;
        pictureForth = holder.pictureForth;

        pictureSelectorFirst = holder.pictureSelectorFirst;
        pictureSelectorSecond = holder.pictureSelectorSecond;
        pictureSelectorThird = holder.pictureSelectorThird;
        pictureSelectorForth = holder.pictureSelectorForth;

        boolean isSameMonth = true;
        Log.d(TAG, "i=" + currentItem);
//        for (int i = 0; i <4; i++){
//            if (attachments.size()>currentItem){
//                Log.d(TAG, "http://t-mes.xsrv.ru/basic/web/?r=messages/attach/get&debug=1&view=1&uuid="+attachments.get(currentItem).uuid);
//                pictureFirst.setVisibility(View.VISIBLE);
//                switch (i){
//                    case 0:
//                        REST.getInstance().getPicasso()
//                                .load("http://t-mes.xsrv.ru/basic/web/?r=messages/attach/get&debug=1&view=1&uuid="+attachments.get(currentItem).uuid)
//                                .resizeDimen(R.dimen.chat_item_avatar_size, R.dimen.chat_item_avatar_size)
//                                .centerCrop()
//                                .into(pictureFirst);
//                        break;
//                    case 1:
//                        REST.getInstance().getPicasso()
//                                .load("http://t-mes.xsrv.ru/basic/web/?r=messages/attach/get&debug=1&view=1&uuid="+attachments.get(currentItem).uuid)
//                                .resizeDimen(R.dimen.chat_item_avatar_size, R.dimen.chat_item_avatar_size)
//                                .centerCrop()
//                                .into(pictureSecond);
//                        break;
//                    case 2:
//                        REST.getInstance().getPicasso()
//                                .load("http://t-mes.xsrv.ru/basic/web/?r=messages/attach/get&debug=1&view=1&uuid="+attachments.get(currentItem).uuid)
//                                .resizeDimen(R.dimen.chat_item_avatar_size, R.dimen.chat_item_avatar_size)
//                                .centerCrop()
//                                .into(pictureThird);
//                        break;
//                    case 3:
//                        REST.getInstance().getPicasso()
//                                .load("http://t-mes.xsrv.ru/basic/web/?r=messages/attach/get&debug=1&view=1&uuid="+attachments.get(currentItem).uuid)
//                                .resizeDimen(R.dimen.chat_item_avatar_size, R.dimen.chat_item_avatar_size)
//                                .centerCrop()
//                                .into(pictureForth);
//                        break;
//
//                }
//                currentItem++;
//            } else {
//
//            }
//        }

//
        if (attachments.size()>currentItem){
            Log.d(TAG, "http://t-mes.xsrv.ru/basic/web/?r=messages/attach/get&debug=1&view=1&uuid="+attachments.get(currentItem).uuid);
            pictureSecond.setVisibility(View.VISIBLE);
            REST.getInstance().getPicasso()
                    .load("http://t-mes.xsrv.ru/basic/web/?r=messages/attach/get&debug=1&view=1&uuid="+attachments.get(currentItem).uuid)
                    .resizeDimen(R.dimen.chat_item_avatar_size, R.dimen.chat_item_avatar_size)
                    .centerCrop()
                    .into(pictureFirst);
            if(attachments.size()-1!=currentItem){
                currentItem++;
            }
        }

        if (!convertIntoMonth(attachments.get(currentItem).time).equals(convertIntoMonth(attachments.get(currentItem-1).time))){
            isSameMonth = false;
        }

        if (isSameMonth&&attachments.size()>currentItem){
            Log.d(TAG, "http://t-mes.xsrv.ru/basic/web/?r=messages/attach/get&debug=1&view=1&uuid="+attachments.get(currentItem).uuid);
            pictureSecond.setVisibility(View.VISIBLE);
            REST.getInstance().getPicasso()
                    .load("http://t-mes.xsrv.ru/basic/web/?r=messages/attach/get&debug=1&view=1&uuid="+attachments.get(currentItem).uuid)
                    .resizeDimen(R.dimen.chat_item_avatar_size, R.dimen.chat_item_avatar_size)
                    .centerCrop()
                    .into(pictureSecond);
            if(attachments.size()-1!=currentItem){
                currentItem++;
            }
        } else {
            pictureSecond.setVisibility(View.GONE);
        }

        if (isSameMonth&&!convertIntoMonth(attachments.get(currentItem).time).equals(convertIntoMonth(attachments.get(currentItem-1).time))){
            isSameMonth = false;
        }

        if (isSameMonth&&attachments.size()>currentItem) {
            Log.d(TAG, "http://t-mes.xsrv.ru/basic/web/?r=messages/attach/get&debug=1&view=1&uuid="+attachments.get(currentItem).uuid);
            pictureThird.setVisibility(View.VISIBLE);
            REST.getInstance().getPicasso()
                    .load("http://t-mes.xsrv.ru/basic/web/?r=messages/attach/get&debug=1&view=1&uuid="+attachments.get(currentItem).uuid)
                    .resizeDimen(R.dimen.chat_item_avatar_size, R.dimen.chat_item_avatar_size)
                    .centerCrop()
                    .into(pictureThird);
            if(attachments.size()-1!=currentItem){
                currentItem++;
            }
        } else {
            pictureThird.setVisibility(View.GONE);
        }

        if (isSameMonth&&!convertIntoMonth(attachments.get(currentItem).time).equals(convertIntoMonth(attachments.get(currentItem-1).time))){
            isSameMonth = false;
        }

        if (isSameMonth&&attachments.size()>currentItem) {
            Log.d(TAG, "http://t-mes.xsrv.ru/basic/web/?r=messages/attach/get&debug=1&view=1&uuid="+attachments.get(currentItem).uuid);
            pictureForth.setVisibility(View.VISIBLE);
            REST.getInstance().getPicasso()
                    .load("http://t-mes.xsrv.ru/basic/web/?r=messages/attach/get&debug=1&view=1&uuid="+attachments.get(currentItem).uuid)
                    .resizeDimen(R.dimen.chat_item_avatar_size, R.dimen.chat_item_avatar_size)
                    .centerCrop()
                    .into(pictureForth);
            if(attachments.size()-1!=currentItem){
                currentItem++;
            }
        } else {
            pictureForth.setVisibility(View.GONE);
        }





//        if (position < media.size()-1 && media.get(position).getDate().charAt(0) ==
//                media.get(position+1).getDate().charAt(0)){
//            mFrame = holder.mBottom;
//            mFrame.setVisibility(View.GONE);
//        } else if (position == media.size()-1){
//            mFrame = holder.mBottom;
//            mFrame.setVisibility(View.INVISIBLE);
//        } else {
//            mFrame = holder.mBottom;
//            mFrame.setVisibility(View.VISIBLE);
//        }
//
//        /* filling the pictures */
//
//        /* if displayed */
//        if (media.get(position).isEmptyFirst()) {
//            pictureFirst.setVisibility(View.INVISIBLE);
//        } else {
//            pictureFirst.setVisibility(View.VISIBLE);
//            /* if user is selecting items */
//            if (isSelecting) {
//                pictureSelectorFirst.setVisibility(View.VISIBLE);
//                /* if item was marked */
//                if (media.get(position).isPressedFirst()){
//                    pictureSelectorFirst.setImageResource(R.drawable.ic_select_dot_checked_with_check_mark);
//                } else {
//                    pictureSelectorFirst.setImageResource(R.drawable.icon_select_photo);
//                }
//            } else pictureSelectorFirst.setVisibility(View.INVISIBLE);
//            /* adding a listener */
//            pictureFirst.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    media.get(position).setPressedFirst(!media.get(position).isPressedFirst());
//                    notifyDataSetChanged();
//                }
//            });
//        }
//
//        /* if displayed */
//        if (media.get(position).isEmptySecond()) {
//            pictureSecond.setVisibility(View.INVISIBLE);
//        } else {
//            pictureSecond.setVisibility(View.VISIBLE);
//            /* if user is selecting items */
//            if (isSelecting) {
//                pictureSelectorSecond.setVisibility(View.VISIBLE);
//                /* if item was marked */
//                if (media.get(position).isPressedSecond()){
//                    pictureSelectorSecond.setImageResource(R.drawable.ic_select_dot_checked_with_check_mark);
//                } else {
//                    pictureSelectorSecond.setImageResource(R.drawable.icon_select_photo);
//                }
//            } else pictureSelectorSecond.setVisibility(View.INVISIBLE);
//            /* adding a listener */
//            pictureSecond.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    media.get(position).setPressedSecond(!media.get(position).isPressedSecond());
//                    notifyDataSetChanged();
//                }
//            });
//        }

//        /* if displayed */
//        if (media.get(position).isEmptyThird()) {
//            pictureThird.setVisibility(View.INVISIBLE);
//        } else {
//            pictureThird.setVisibility(View.VISIBLE);
//            /* if user is selecting items */
//            if (isSelecting) {
//                pictureSelectorThird.setVisibility(View.VISIBLE);
//                /* if item was marked */
//                if (media.get(position).isPressedThird()){
//                    pictureSelectorThird.setImageResource(R.drawable.ic_select_dot_checked_with_check_mark);
//                } else {
//                    pictureSelectorThird.setImageResource(R.drawable.icon_select_photo);
//                }
//            } else pictureSelectorThird.setVisibility(View.INVISIBLE);
//            /* adding a listener */
//            pictureThird.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    media.get(position).setPressedThird(!media.get(position).isPressedThird());
//                    notifyDataSetChanged();
//                }
//            });
//        }

        /* if displayed */
//        if (media.get(position).isEmptyForth()) {
//            pictureForth.setVisibility(View.INVISIBLE);
//        } else {
//            pictureForth.setVisibility(View.VISIBLE);
//            /* if user is selecting items */
//            if (isSelecting) {
//                pictureSelectorForth.setVisibility(View.VISIBLE);
//                /* if item was marked */
//                if (media.get(position).isPressedForth()){
//                    pictureSelectorForth.setImageResource(R.drawable.ic_select_dot_checked_with_check_mark);
//                } else {
//                    pictureSelectorForth.setImageResource(R.drawable.icon_select_photo);
//                }
//            } else pictureSelectorForth.setVisibility(View.INVISIBLE);
//            /* adding a listener */
//            pictureForth.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    media.get(position).setPressedForth(!media.get(position).isPressedForth());
//                    notifyDataSetChanged();
//                }
//            });
//        }

    }

    @Override
    public int getItemCount() {
        return attachments.size();
    }

    @Override
    public long getHeaderId(int position) {
        return attachments.get(position).size.subSequence(0, 1).charAt(0);
    }

    @Override
    public MediaListAdapter.HeaderHolder onCreateHeaderViewHolder(ViewGroup parent) {
        final View view = mInflater.inflate(R.layout.header_media, parent, false);
        return new MediaListAdapter.HeaderHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(MediaListAdapter.HeaderHolder viewHolder, int position) {
//        viewHolder.header.setText(""+media.get(position).getDate());
        if(position<4){
            viewHolder.header.setText("<4");
        } else if (position==5){
            viewHolder.header.setText("5");
        } else  viewHolder.header.setText("January");
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        View mView;
        FrameLayout mBottom;
        ImageView pictureFirst;
        ImageView pictureSecond;
        ImageView pictureThird;
        ImageView pictureForth;
        ImageView pictureSelectorFirst;
        ImageView pictureSelectorSecond;
        ImageView pictureSelectorThird;
        ImageView pictureSelectorForth;


        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            mBottom = (FrameLayout) itemView.findViewById(R.id.recycler_item_media_fl_bottom_line);
            pictureFirst = (ImageView) itemView.findViewById(R.id.recycler_item_media_image_card_first);
            pictureSecond = (ImageView) itemView.findViewById(R.id.recycler_item_media_image_card_second);
            pictureThird = (ImageView) itemView.findViewById(R.id.recycler_item_media_image_card_third);
            pictureForth = (ImageView) itemView.findViewById(R.id.recycler_item_media_image_card_forth);
            pictureSelectorFirst = (ImageView) itemView.findViewById(R.id.recycler_item_media_image_selector_first);
            pictureSelectorSecond = (ImageView) itemView.findViewById(R.id.recycler_item_media_image_selector_second);
            pictureSelectorThird = (ImageView) itemView.findViewById(R.id.recycler_item_media_image_selector_third);
            pictureSelectorForth = (ImageView) itemView.findViewById(R.id.recycler_item_media_image_selector_forth);
        }
    }

    static class HeaderHolder extends RecyclerView.ViewHolder {
        public TextView header;

        public HeaderHolder(View itemView) {
            super(itemView);
            header = (TextView) itemView.findViewById(R.id.view_header_media_tx_text);
        }
    }

    public void clearSelected(){
//        for (MediaList a : media){
//            a.setPressedFirst(false);
//            a.setPressedSecond(false);
//            a.setPressedThird(false);
//            a.setPressedForth(false);
//        }
    }

    private String convertIntoMonth(String date){
        long dateLong = Long.parseLong(date);
        Date dateDate = new Date(dateLong * 1000);
        Locale russianLocale = new Locale("ru","RU");
        SimpleDateFormat dateFormatRequired = new SimpleDateFormat("mm", russianLocale);
        String monthName = dateFormatRequired.format(dateDate);
        return monthName;
    }

}
