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
 * Created by andrey on 24.04.2017.
 */

public class MemberItemView extends FrameLayout {
    private TextView tvName;
    private TextView tvPosition;
    private TextView tvAdmin;
    private ImageView imageOnline;
    private ImageView imageProfile;


    public MemberItemView(Context context) {
        this(context, null);
    }

    public MemberItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MemberItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        buildUI();
    }

    private void buildUI(){
        FrameLayout.inflate(getContext(), R.layout.view_member_item, this);

        tvName = (TextView) findViewById(R.id.recycler_item_group_member_tv_name);
        tvPosition = (TextView) findViewById(R.id.recycler_item_group_member_tv_position);
        tvAdmin = (TextView) findViewById(R.id.recycler_item_group_member_tv_admin);
        imageOnline = (ImageView) findViewById(R.id.recycler_item_group_member_image_online);
        imageProfile = (ImageView) findViewById(R.id.recycler_item_group_member_image_picture);
        tvAdmin = (TextView) findViewById(R.id.recycler_item_group_member_tv_admin);

    }

    public void setData(User user, boolean isAdmin){
        if(user.avatar != null) Picasso.with(getContext())
                .load(user.avatar)
                .placeholder(R.drawable.icon_user)
                .error(R.drawable.icon_user)
                .resizeDimen(R.dimen.chat_item_avatar_size, R.dimen.chat_item_avatar_size)
                .centerCrop().transform(new RoundedCornersTransformation(360,0))
                .into(imageProfile);

        tvName.setText(user.cn);
        tvPosition.setText(user.title);

        imageOnline.setVisibility(user.online == 1 ? VISIBLE : GONE);
        tvAdmin.setVisibility(isAdmin ? VISIBLE : GONE);
    }
}
