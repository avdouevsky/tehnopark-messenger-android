package com.mshvdvskgmail.technoparkmessenger.view;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mshvdvskgmail.technoparkmessenger.Controller;
import com.mshvdvskgmail.technoparkmessenger.R;
import com.mshvdvskgmail.technoparkmessenger.network.model.Message;

/**
 * Created by andrey on 20.04.2017.
 */
public class MessageView extends FrameLayout {
    private FrameLayout frameContent;
    private TextView tvName;
    private TextView tvText;
    private AttachmentView viewAttachment;
    private TextView tvTime;
    private ImageView imStatus;
    private ImageView cornerRight;
    private ImageView cornerLeft;

    @DrawableRes
    private final static int message_outgoing_background = R.drawable.message_outgoing_background;

    @DrawableRes
    private final static int message_incoming_background = R.drawable.message_incoming_background;

    public MessageView(Context context) {
        this(context, null);
    }

    public MessageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MessageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        buildUI();
    }

    private void buildUI(){
        FrameLayout.inflate(getContext(), R.layout.view_message, this);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(-1, -2);
        setLayoutParams(params);

        frameContent = (FrameLayout) findViewById(R.id.frameContent);
        tvName = (TextView) findViewById(R.id.tvName);
        tvText = (TextView) findViewById(R.id.tvText);
        viewAttachment = (AttachmentView) findViewById(R.id.viewAttachment);
        tvTime = (TextView) findViewById(R.id.tvTime);
        imStatus = (ImageView) findViewById(R.id.imStatus);
        cornerRight = (ImageView) findViewById(R.id.cornerRight);
        cornerLeft = (ImageView) findViewById(R.id.cornerLeft);
    }

    public void setData(Message data, boolean p2p){
        boolean out = data.sender.id.equals(Controller.getInstance().getAuth().getUser().id);

        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)frameContent.getLayoutParams();
        params.gravity = out ? Gravity.RIGHT : Gravity.LEFT;

        frameContent.setBackground(getResources().getDrawable(out ? message_outgoing_background : message_incoming_background));

        tvName.setVisibility(!p2p && !out ? VISIBLE : GONE);
        cornerLeft.setVisibility(!out ? VISIBLE : GONE);
        cornerRight.setVisibility(!out ? GONE : VISIBLE);
        imStatus.setVisibility(out ? VISIBLE : GONE);

        viewAttachment.setVisibility(data.attachments == null || data.attachments.size() == 0 ? GONE : VISIBLE);
        if(data.attachments == null || data.attachments.size() == 0) viewAttachment.setData(null);
        else viewAttachment.setData(data.attachments.get(0));

        tvText.setText(data.message);
        tvTime.setText(data.getTime());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
