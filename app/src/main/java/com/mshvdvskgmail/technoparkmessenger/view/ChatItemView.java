package com.mshvdvskgmail.technoparkmessenger.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mshvdvskgmail.technoparkmessenger.Controller;
import com.mshvdvskgmail.technoparkmessenger.R;
import com.mshvdvskgmail.technoparkmessenger.network.model.Chat;
import com.mshvdvskgmail.technoparkmessenger.network.model.ChatUser;
import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

/**
 * Created by andrey on 19.04.2017.
 */

public class ChatItemView extends LinearLayout {
    private TextView tvName;
    private TextView tvLastMessage;
    private TextView tvTime;
    private ImageView imFace;
    private ImageView imOnline;
    private TextView tvCount;

    public ChatItemView(Context context) {
        this(context, null);
    }

    public ChatItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ChatItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        buildUI();
    }

    private void buildUI() {
        LinearLayout.inflate(getContext(), R.layout.view_item_chat, this);

        tvName = (TextView) findViewById(R.id.tvName);
        tvLastMessage = (TextView) findViewById(R.id.recycler_item_chatslist_tv_message);
        tvTime = (TextView) findViewById(R.id.tvTime);
        imOnline = (ImageView) findViewById(R.id.recycler_item_chatslist_image_online);
        imFace = (ImageView) findViewById(R.id.imFace);
        tvCount = (TextView) findViewById(R.id.recycler_item_chatslist_tv_notification);
    }

    public void setData(Chat data){
        if(data.peer2peer == 1){
            for(ChatUser cu : data.users)
                if(cu.users_id != null && cu.user != null && !cu.users_id.equals(Controller.getInstance().getAuth().getUser().id)){
                    if(cu.user.avatar != null) Picasso.with(getContext())
                            .load(cu.user.avatar)
                            .placeholder(R.drawable.icon_user)
                            .error(R.drawable.icon_user)
                            .resizeDimen(R.dimen.chat_item_avatar_size, R.dimen.chat_item_avatar_size)
                            .centerCrop().transform(new RoundedCornersTransformation(360,0))
                            .into(imFace);

                    tvName.setText(cu.user.cn);
                }else{
                    tvName.setText("Unknown");
                }
        }else{
            tvName.setText(data.name);
            imFace.setImageResource(R.drawable.ic_group_icon);
        }


        tvTime.setText(data.getTimeAsString());
        tvLastMessage.setText(data.last != null ? data.last.message_text : "");
        tvCount.setVisibility(data.unread == 0 ? GONE : VISIBLE);
        tvCount.setText(String.format("%d", data.unread));


        //Picasso.with(context).load(R.drawable.pushkin).transform(new RoundedCornersTransformation(360,0)).into(profileIcon);
    }
}
