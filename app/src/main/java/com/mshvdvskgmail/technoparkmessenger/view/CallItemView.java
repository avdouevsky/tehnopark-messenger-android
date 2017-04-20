package com.mshvdvskgmail.technoparkmessenger.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.mshvdvskgmail.technoparkmessenger.R;
import com.mshvdvskgmail.technoparkmessenger.network.model.SipCall;
import com.mshvdvskgmail.technoparkmessenger.network.model.User;
import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

/**
 * Created by andrey on 20.04.2017.
 */

public class CallItemView extends FrameLayout {
    private TextView tvName;
    private TextView tvTime;
    //private ImageView imageOnline;
    private ImageView imDirection;
    private ImageView imFace;

    public CallItemView(Context context) {
        this(context, null);
    }

    public CallItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CallItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        buildUI();
    }

    private void buildUI(){
        FrameLayout.inflate(getContext(), R.layout.view_item_call, this);

        tvName = (TextView) findViewById(R.id.tvName);
        tvTime = (TextView) findViewById(R.id.tvTime);
        imDirection = (ImageView) findViewById(R.id.imDirection);
        imFace = (ImageView) findViewById(R.id.imFace);
    }

    public void setData(SipCall data){
        User user2 = data.opposite();
        if(user2 == null){
            tvName.setText("Unknown");
        }else{
            tvName.setText(user2.name);
            if(user2.avatar != null)
                Picasso.with(getContext())
                .load(user2.avatar)
                .placeholder(R.drawable.icon_user)
                .error(R.drawable.icon_user)
                .resizeDimen(R.dimen.chat_item_avatar_size, R.dimen.chat_item_avatar_size)
                .centerCrop().transform(new RoundedCornersTransformation(360,0))
                .into(imFace);
        }
        imDirection.setVisibility(data.isMissed() ? INVISIBLE : VISIBLE);
        imDirection.setImageResource(data.isIncoming() ? R.drawable.ic_calls_inc : R.drawable.ic_calls_out);

        tvTime.setText(data.getTime());
        if(data.isMissed()){
            tvName.setTextColor(getResources().getColor(R.color.calls_missed));
            tvTime.setTextColor(getResources().getColor(R.color.calls_missed));
        }else{
            tvName.setTextColor(getResources().getColor(R.color.colorBlueTextPrimary));
            tvTime.setTextColor(getResources().getColor(R.color.backgroundGrey));
        }
    }
}
