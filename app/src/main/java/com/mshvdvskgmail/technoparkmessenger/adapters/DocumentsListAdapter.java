package com.mshvdvskgmail.technoparkmessenger.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mshvdvskgmail.technoparkmessenger.R;
import com.mshvdvskgmail.technoparkmessenger.models.DocumentsListItem;

import java.util.ArrayList;

import ca.barrenechea.widget.recyclerview.decoration.StickyHeaderAdapter;

/**
 * Created by mshvdvsk on 22/03/2017
 */

public class DocumentsListAdapter extends RecyclerView.Adapter<DocumentsListAdapter.ViewHolder>
        implements StickyHeaderAdapter<DocumentsListAdapter.HeaderHolder> {

    private final static String TAG = DocumentsListAdapter.class.toString();

    private ArrayList<DocumentsListItem> documents = new ArrayList<>();
    private Context context;
    private FrameLayout frameDocType;
    private FrameLayout frameBottom;
    private FrameLayout frameSelectItem;
    private LinearLayout linearDocInfo;
    private ImageView imageSelectIcon;
    private ImageView imageCheckMarkIcon;
    private LayoutInflater inflater;
    public boolean isAnimated;
    public boolean isPressed;

    public DocumentsListAdapter(ArrayList <DocumentsListItem> documents, Context context) {
        this.documents = documents;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public DocumentsListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_documents_item, parent, false);
        DocumentsListAdapter.ViewHolder viewHolder = new DocumentsListAdapter.ViewHolder(rowView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(DocumentsListAdapter.ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder");

        frameBottom = holder.frameBottom;
        frameDocType = holder.frameDocType;
        frameSelectItem = holder.frameSelectItem;
        linearDocInfo = holder.linearDocInfo;
        imageSelectIcon = holder.imageSelectIcon;
        imageCheckMarkIcon = holder.imageCheckMarkIcon;

        float d = context.getResources().getDisplayMetrics().density;
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) holder.mView.getLayoutParams();
        ViewGroup.MarginLayoutParams params2 = (ViewGroup.MarginLayoutParams) frameDocType.getLayoutParams();

        int topMargin = (int)(-10 * d);
        int bottomMargin = (int)(40 * d);
        int leftMarginBasic = (int)(-37 * d);
        int leftMarginChanged = (int)(5.5 * d);


        if (position == 0){
            params.topMargin = topMargin;
            params.bottomMargin = 0;
        } else if (position == documents.size()-1){
            params.topMargin = 0;
            params.bottomMargin = bottomMargin;
        } else {
            params.topMargin = 0;
            params.bottomMargin = 0;
        }

        if (isPressed){
            params2.leftMargin = leftMarginChanged;
            imageSelectIcon.setVisibility(View.VISIBLE);

            if(isAnimated){
                final Animation slideToLeft = AnimationUtils.loadAnimation(context, R.anim.icon_slide_to_left);
                final Animation fadeOut = AnimationUtils.loadAnimation(context, R.anim.fade_out);
                slideToLeft.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        isAnimated = false;
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        isPressed = false;
                        clearSelected();
                        notifyDataSetChanged();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                frameDocType.startAnimation(slideToLeft);
                imageSelectIcon.startAnimation(fadeOut);
                linearDocInfo.startAnimation(slideToLeft);
            }

        } else {
            params2.leftMargin = leftMarginBasic;
            imageSelectIcon.setVisibility(View.INVISIBLE);

            if(isAnimated){
                final Animation slideToRight = AnimationUtils.loadAnimation(context, R.anim.icon_slide_to_right);
                final Animation fadeIn = AnimationUtils.loadAnimation(context, R.anim.fade_in);

                slideToRight.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        isAnimated = false;
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        isPressed = true;
                        notifyDataSetChanged();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {}
                });
                frameDocType.startAnimation(slideToRight);
                imageSelectIcon.startAnimation(fadeIn);
                linearDocInfo.startAnimation(slideToRight);
            }
        }

        frameDocType.requestLayout();




        frameSelectItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                documents.get(position).setPressed(!documents.get(position).isPressed());
                notifyDataSetChanged();
            }
        });

        if (documents.get(position).isPressed()){
            imageSelectIcon.setBackground(context.getResources().getDrawable(R.drawable.ic_select_dot_checked));
            imageCheckMarkIcon.setVisibility(View.VISIBLE);
        } else {
            imageSelectIcon.setBackground(context.getResources().getDrawable(R.drawable.ic_select_dot_unchecked));
            imageCheckMarkIcon.setVisibility(View.INVISIBLE);
        }
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
        final View view = inflater.inflate(R.layout.header_media, parent, false);
        return new DocumentsListAdapter.HeaderHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(DocumentsListAdapter.HeaderHolder viewHolder, int position) {
        viewHolder.header.setText(""+documents.get(position).getDataSent());
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        View mView;
        FrameLayout frameBottom;
        FrameLayout frameDocType;
        FrameLayout frameSelectItem;
        LinearLayout linearDocInfo;
        ImageView imageSelectIcon;
        ImageView imageCheckMarkIcon;

        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            frameBottom = (FrameLayout) itemView.findViewById(R.id.bottom_line);
            frameDocType = (FrameLayout) itemView.findViewById(R.id.doc_type_frame);
            linearDocInfo = (LinearLayout) itemView.findViewById(R.id.doc_info);
            imageSelectIcon = (ImageView) itemView.findViewById(R.id.select_item);
            imageCheckMarkIcon = (ImageView) itemView.findViewById(R.id.icon_checked);
            frameSelectItem = (FrameLayout) itemView.findViewById(R.id.frame_select_item);
        }
    }

    static class HeaderHolder extends RecyclerView.ViewHolder {
        public TextView header;

        public HeaderHolder(View itemView) {
            super(itemView);
            header = (TextView) itemView.findViewById(R.id.text_item);
        }
    }

    public void clearSelected(){
        for (DocumentsListItem a : documents){
            a.setPressed(false);
        }
    }
}
