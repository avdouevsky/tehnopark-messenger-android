package com.mshvdvskgmail.technoparkmessenger.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mshvdvskgmail.technoparkmessenger.Controller;
import com.mshvdvskgmail.technoparkmessenger.R;
import com.mshvdvskgmail.technoparkmessenger.network.REST;
import com.mshvdvskgmail.technoparkmessenger.network.model.Attachment;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

/**
 * Created by andrey on 20.04.2017.
 */

public class AttachmentView extends FrameLayout {
    private FrameLayout frameMime;
    private ImageView imFile;
    private TextView tvMime;
    private LinearLayout layoutInfo;
    private TextView tvFileName;
    private TextView tvFileSize;
    private ImageView imPreview;

    public AttachmentView(Context context) {
        this(context, null);
    }

    public AttachmentView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AttachmentView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        buildUI();
    }

    private void buildUI(){
        FrameLayout.inflate(getContext(), R.layout.view_attachment, this);

        if(isInEditMode()) return;

        frameMime = (FrameLayout) findViewById(R.id.frameMime);
        imFile = (ImageView) findViewById(R.id.imFile);
        tvMime = (TextView) findViewById(R.id.tvMime);
        layoutInfo = (LinearLayout) findViewById(R.id.layoutInfo);
        tvFileName = (TextView) findViewById(R.id.tvFileName);
        tvFileSize = (TextView) findViewById(R.id.tvFileSize);
        imPreview = (ImageView) findViewById(R.id.imPreview);
    }

    public void setData(@Nullable Attachment attachment){
        if(isInEditMode()) return;

        if(attachment == null){
            setVisibility(GONE);
            return;
        }

        boolean image = attachment.mime.startsWith("image/");
        frameMime.setVisibility(image ? GONE : VISIBLE);
        layoutInfo.setVisibility(image ? GONE : VISIBLE);
        imPreview.setVisibility(image ? VISIBLE : GONE);

        if(image){
            REST.getInstance().getPicasso()
                    .load("http://t-mes.xsrv.ru/basic/web/?r=messages/attach/get&debug=1&view=1&uuid=" + attachment.uuid)
                    .resizeDimen(R.dimen.chat_item_image_width_max, R.dimen.chat_item_image_height_max)
                    .centerCrop()
                    .transform(new RoundedCornersTransformation(Math.round(getResources().getDimension(R.dimen.chat_image_inner_corner)),0))
                    .into(imPreview);
        }else{

        }

        //todo other
    }
}
