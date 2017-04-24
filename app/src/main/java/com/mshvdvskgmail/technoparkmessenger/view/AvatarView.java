package com.mshvdvskgmail.technoparkmessenger.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.mshvdvskgmail.technoparkmessenger.R;
import com.mshvdvskgmail.technoparkmessenger.network.model.User;
import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

/**
 * Created by andrey on 24.04.2017.
 */
public class AvatarView extends FrameLayout {
    private final static int SMALL = 0;
    private final static int MEDIUM = 1;
    private final static int BIG = 2;

    private ImageView imOnline;
    private ImageView imFace;

    private boolean showOnline = true;
    private int size = SMALL;

    public AvatarView(Context context) {
        this(context, null);
    }

    public AvatarView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AvatarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        if(attrs != null){
            TypedArray a = getContext().getTheme().obtainStyledAttributes(
                    attrs,
                    R.styleable.AvatarView,
                    0, 0);

            size = a.getInt(R.styleable.AvatarView_face_size, size);
            showOnline = a.getBoolean(R.styleable.AvatarView_show_online, showOnline);
            a.recycle();
        }

        buildUI();
    }

    private void buildUI(){
        FrameLayout.inflate(getContext(), R.layout.view_avatar_small, this);

        imOnline = (ImageView) findViewById(R.id.imOnline);
        imFace = (ImageView) findViewById(R.id.imFace);
    }

    public void setData(@NonNull User user){
        if(user.avatar != null) Picasso.with(getContext())
                .load(user.avatar)
                .placeholder(R.drawable.icon_user)
                .error(R.drawable.icon_user)
                .resizeDimen(R.dimen.chat_item_avatar_size, R.dimen.chat_item_avatar_size)
                .centerCrop().transform(new RoundedCornersTransformation(360,0))
                .into(imFace);
        imOnline.setVisibility(user.online == 1 && showOnline ? VISIBLE : INVISIBLE);
    }
}
