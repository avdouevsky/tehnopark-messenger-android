package com.mshvdvskgmail.technoparkmessenger.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.mshvdvskgmail.technoparkmessenger.R;
import com.mshvdvskgmail.technoparkmessenger.network.model.User;
import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

/**
 * Created by andrey on 20.04.2017.
 */

public class ContactItemView extends FrameLayout {
    private TextView tvName;
    private TextView tvLastMessage;
    private ImageView imFace;
    private ImageView imOnline;


    public ContactItemView(Context context) {
        this(context, null);
    }

    public ContactItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ContactItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        buildUI();
    }

    private void buildUI(){
        FrameLayout.inflate(getContext(), R.layout.view_item_contact, this);

        tvName = (TextView) findViewById(R.id.tvName);
        tvLastMessage = (TextView) findViewById(R.id.recycler_item_chatslist_tv_message);
        imOnline = (ImageView) findViewById(R.id.recycler_item_chatslist_image_online);
        imFace = (ImageView) findViewById(R.id.imFace);
    }

    public void setData(User user){
        if(user.avatar != null) Picasso.with(getContext())
                .load(user.avatar)
                .placeholder(R.drawable.icon_user)
                .error(R.drawable.icon_user)
                .resizeDimen(R.dimen.chat_item_avatar_size, R.dimen.chat_item_avatar_size)
                .centerCrop().transform(new RoundedCornersTransformation(360,0))
                .into(imFace);
        tvName.setText(user.cn == null ? "Unknown" : user.cn);
        imOnline.setVisibility(user.online == 1 ? VISIBLE : INVISIBLE);
        tvLastMessage.setText(user.title);
    }
}
