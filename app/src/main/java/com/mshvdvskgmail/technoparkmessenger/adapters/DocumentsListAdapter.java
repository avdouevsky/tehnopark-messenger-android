package com.mshvdvskgmail.technoparkmessenger.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mshvdvskgmail.technoparkmessenger.R;
import com.mshvdvskgmail.technoparkmessenger.models.MessageEvent;
import com.mshvdvskgmail.technoparkmessenger.models.ModelDocumentsItem;
import com.mshvdvskgmail.technoparkmessenger.models.ModelMediaList;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import ca.barrenechea.widget.recyclerview.decoration.StickyHeaderAdapter;

/**
 * Created by mshvdvsk on 22/03/2017.
 */

public class DocumentsListAdapter extends RecyclerView.Adapter<DocumentsListAdapter.ViewHolder>
        implements StickyHeaderAdapter<DocumentsListAdapter.HeaderHolder> {

    private ArrayList<ModelDocumentsItem> documents;
    private Context context;
    private FrameLayout mFrame;
    private FrameLayout bottomItemSeparator;
    private LinearLayout mLinear;
    private ImageView mImage;
    private LayoutInflater mInflater;
    public boolean isAnimated;
    public boolean isPressed;

    public DocumentsListAdapter(ArrayList <ModelDocumentsItem> documents, Context context) {
        this.documents = documents;
        this.context = context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public DocumentsListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_documents_item, parent, false);
        DocumentsListAdapter.ViewHolder viewHolder = new DocumentsListAdapter.ViewHolder(rowView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(DocumentsListAdapter.ViewHolder holder, int position) {

        bottomItemSeparator = holder.mBottom;
        mFrame = holder.mImage;
        mLinear = holder.mLinear;
        mImage = holder.selectIcon;

        float d = context.getResources().getDisplayMetrics().density;
        final ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) holder.mView.getLayoutParams();
        final ViewGroup.MarginLayoutParams params2 = (ViewGroup.MarginLayoutParams) mFrame.getLayoutParams();


        int topMargin = (int)(-10 * d);
        int bottomMargin = (int)(40 * d);
        int rightMargin = (int)(37 * d);
        int leftMarginBasic = (int)(-30 * d);
        int leftMarginChanged = (int)(12.5 * d);


        if (position==0){
            params.topMargin = topMargin;
            params.bottomMargin = 0;
        } else if (position == documents.size()-1){
            params.topMargin = 0;
            params.bottomMargin = bottomMargin;
        } else {
            params.topMargin = 0;
            params.bottomMargin = 0;
        }

//        isPressed = true;

//        params2.leftMargin = leftMarginBasic;
//        params2.leftMargin = leftMarginChanged;



        if (isPressed){
            params2.leftMargin = leftMarginChanged;
        } else {
            params2.leftMargin = leftMarginBasic;
        }


        if (position < documents.size()-1){
            if (documents.get(position).getDataSent().charAt(0) ==
                    documents.get(position+1).getDataSent().charAt(0)) {
                bottomItemSeparator.setVisibility(View.VISIBLE);
            } else {
                bottomItemSeparator.setVisibility(View.INVISIBLE);
            }
        } else if (position == documents.size()-1){
            bottomItemSeparator.setVisibility(View.INVISIBLE);
        }


//        setFadeAnimation(holder.itemView);

//        isAnimated = true;
//
        if(isAnimated){
            final Animation slideToRight = AnimationUtils.loadAnimation(context, R.anim.icon_slide_to_right);
            final Animation fadeIn = AnimationUtils.loadAnimation(context, R.anim.fade_in);

            slideToRight.setStartOffset(0);
            final ViewGroup.MarginLayoutParams iconParams = (ViewGroup.MarginLayoutParams) mFrame.getLayoutParams();
            slideToRight.setAnimationListener(new Animation.AnimationListener() {


                @Override
                public void onAnimationStart(Animation animation) {
                    isAnimated = false;
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    isPressed = true;
//                    notifyDataSetChanged();
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            mFrame.startAnimation(slideToRight);
            mImage.startAnimation(fadeIn);
            mLinear.startAnimation(slideToRight);
        }


        mImage = holder.selectIcon;
        mImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });





//        mFrame.post(new Runnable() {
//            @Override
//            public void run() {
//                mFrame.startAnimation(slideToRight);
//                mFrame.startAnimation(slideToRight);
//            }
//        });

        // old animation version via .post
//        if (isAnimated) {
//            final Animation slideToRight = AnimationUtils.loadAnimation(context, R.anim.icon_slide_to_right);
//            slideToRight.setStartOffset(100);
//            mFrame = holder.mImage;
//            mFrame.post(new Runnable() {
//                @Override
//                public void run() {
//                    mFrame.startAnimation(slideToRight);
//                    mFrame.startAnimation(slideToRight);
//                }
//            });
//        }
    }

    @Override
    public int getItemCount() {
        return documents.size();
    }

    @Override
    public long getHeaderId(int position) {
        return documents.get(position).getDataSent().subSequence(0, 1).charAt(0);
    }

    @Override
    public DocumentsListAdapter.HeaderHolder onCreateHeaderViewHolder(ViewGroup parent) {
        final View view = mInflater.inflate(R.layout.header_media, parent, false);
        return new DocumentsListAdapter.HeaderHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(DocumentsListAdapter.HeaderHolder viewHolder, int position) {
//        viewHolder.header.setText(""+media.get(position).getDate());
        viewHolder.header.setText(""+documents.get(position).getDataSent());
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        View mView;
        FrameLayout mBottom;
        FrameLayout mImage;
        LinearLayout mLinear;
        ImageView selectIcon;

        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            mBottom = (FrameLayout) itemView.findViewById(R.id.bottom_line);
            mImage = (FrameLayout) itemView.findViewById(R.id.image_frame);
            mLinear = (LinearLayout) itemView.findViewById(R.id.doc_info);
            selectIcon = (ImageView) itemView.findViewById(R.id.select_item);
        }
    }

    static class HeaderHolder extends RecyclerView.ViewHolder {
        public TextView header;

        public HeaderHolder(View itemView) {
            super(itemView);
            header = (TextView) itemView.findViewById(R.id.text_item);
        }
    }

    private void setFadeAnimation(View view) {
        AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(10000);
        view.startAnimation(anim);
    }

}
