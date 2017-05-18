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
import com.mshvdvskgmail.technoparkmessenger.models.LinksItem;
import com.mshvdvskgmail.technoparkmessenger.network.REST;
import com.mshvdvskgmail.technoparkmessenger.network.model.Attachment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import ca.barrenechea.widget.recyclerview.decoration.StickyHeaderAdapter;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

import static com.mshvdvskgmail.technoparkmessenger.R.id.imPreview;
import static com.mshvdvskgmail.technoparkmessenger.R.id.recycler_item_links_image_icon;

/**
 * Created by mshvdvsk on 23/03/2017.
 */

public class LinksListAdapter extends RecyclerView.Adapter<LinksListAdapter.ViewHolder>
        implements StickyHeaderAdapter<LinksListAdapter.HeaderHolder> {

    private final static String TAG = LinksListAdapter.class.toString();

    //    private ArrayList<LinksItem> links;
    private List<Attachment> files;
    private Context context;
    private FrameLayout frameBottom;
    private LayoutInflater inflater;
    private FrameLayout frameLinkPicture;
    private LinearLayout linearLinkInfo;
    private FrameLayout frameSelectItem;
    private ImageView imageSelectIcon;
    private ImageView imageCheckMarkIcon;

    public boolean isAnimated;
    public boolean isPressed;

    public LinksListAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void setData(List<Attachment> files){
        this.files = files;
        sort();
        notifyDataSetChanged();
    }
    
    private void sort(){
        // TODO: 18.05.2017
    }

    @Override
    public LinksListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_links_item, parent, false);
        LinksListAdapter.ViewHolder viewHolder = new LinksListAdapter.ViewHolder(rowView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(LinksListAdapter.ViewHolder holder, final int position) {

        frameBottom = holder.frameBottom;
        frameLinkPicture = holder.frameLinkPicture;
        frameSelectItem = holder.frameSelectItem;
        linearLinkInfo = holder.linearLinkInfo;
        imageSelectIcon = holder.imageSelectIcon;
        imageCheckMarkIcon = holder.imageCheckMarkIcon;

        if(files!=null){
            holder.tvLink.setText(files.get(position).name);
//            holder.imageLinkLogo;
            Log.d(TAG, "wow link =  " + (files.get(position).icon+files.get(position).uuid));
            REST.getInstance().getPicasso()
                    .load((files.get(position).icon+files.get(position).uuid))
                    .resizeDimen(R.dimen.media_picture, R.dimen.media_picture)
                    .centerCrop()
                    .into(holder.imageLinkLogo);

//            REST.getInstance().getPicasso()
//                    .load((files.get(position).icon)
//                    .resizeDimen(R.dimen.chat_item_image_width_max, R.dimen.chat_item_image_height_max)
//                    .centerCrop()
//                    .transform(new RoundedCornersTransformation(Math.round(getResources().getDimension(R.dimen.chat_image_inner_corner)),0))
//                    .into(holder.imageLinkLogo);

        }



//        float d = context.getResources().getDisplayMetrics().density;
//        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) holder.mView.getLayoutParams();
//        ViewGroup.MarginLayoutParams params2 = (ViewGroup.MarginLayoutParams) frameLinkPicture.getLayoutParams();
//
//        int topMargin = (int)(-10 * d);
//        int bottomMargin = (int)(40 * d);
//        int leftMarginBasic = (int)(-37 * d);
//        int leftMarginChanged = (int)(5.5 * d);
//
//        if (position == 0){
//            params.topMargin = topMargin;
//            params.bottomMargin = 0;
//        } else if (position == links.size()-1){
//            params.topMargin = 0;
//            params.bottomMargin = bottomMargin;
//        } else {
//            params.topMargin = 0;
//            params.bottomMargin = 0;
//        }
//
//        if (position < links.size()-1){
//            if (links.get(position).getLinkSent().charAt(0) ==
//                    links.get(position+1).getLinkSent().charAt(0)) {
//                frameBottom.setVisibility(View.VISIBLE);
//            } else {
//                frameBottom.setVisibility(View.INVISIBLE);
//            }
//        } else frameBottom.setVisibility(View.INVISIBLE);
//
//        if (isPressed){
//            params2.leftMargin = leftMarginChanged;
//            imageSelectIcon.setVisibility(View.VISIBLE);
//
//            if(isAnimated){
//                final Animation slideToLeft = AnimationUtils.loadAnimation(context, R.anim.icon_slide_to_left);
//                final Animation fadeOut = AnimationUtils.loadAnimation(context, R.anim.fade_out);
//                slideToLeft.setAnimationListener(new Animation.AnimationListener() {
//                    @Override
//                    public void onAnimationStart(Animation animation) {
//                        isAnimated = false;
//                    }
//
//                    @Override
//                    public void onAnimationEnd(Animation animation) {
//                        isPressed = false;
//                        clearSelected();
//                        notifyDataSetChanged();
//                    }
//
//                    @Override
//                    public void onAnimationRepeat(Animation animation) {
//
//                    }
//                });
//                frameLinkPicture.startAnimation(slideToLeft);
//                imageSelectIcon.startAnimation(fadeOut);
//                linearLinkInfo.startAnimation(slideToLeft);
//            }
//
//        } else {
//            params2.leftMargin = leftMarginBasic;
//            imageSelectIcon.setVisibility(View.INVISIBLE);
//
//            if(isAnimated){
//                final Animation slideToRight = AnimationUtils.loadAnimation(context, R.anim.icon_slide_to_right);
//                final Animation fadeIn = AnimationUtils.loadAnimation(context, R.anim.fade_in);
//
//                slideToRight.setStartOffset(0);
//                final ViewGroup.MarginLayoutParams iconParams = (ViewGroup.MarginLayoutParams) frameLinkPicture.getLayoutParams();
//                slideToRight.setAnimationListener(new Animation.AnimationListener() {
//
//
//                    @Override
//                    public void onAnimationStart(Animation animation) {
//                        isAnimated = false;
//                    }
//
//                    @Override
//                    public void onAnimationEnd(Animation animation) {
//                        isPressed = true;
////                    frameDocType.requestLayout();
//                        notifyDataSetChanged();
//                    }
//
//                    @Override
//                    public void onAnimationRepeat(Animation animation) {
//
//                    }
//                });
//                frameLinkPicture.startAnimation(slideToRight);
//                imageSelectIcon.startAnimation(fadeIn);
//                linearLinkInfo.startAnimation(slideToRight);
//            }
//        }
//
//        frameLinkPicture.requestLayout();
//
//        frameSelectItem.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                links.get(position).setPressed(!links.get(position).isPressed());
//                notifyDataSetChanged();
//            }
//        });
//
//        if (links.get(position).isPressed()){
//            imageSelectIcon.setBackground(context.getResources().getDrawable(R.drawable.ic_select_dot_checked));
//            imageCheckMarkIcon.setVisibility(View.VISIBLE);
//        } else {
//            imageSelectIcon.setBackground(context.getResources().getDrawable(R.drawable.ic_select_dot_unchecked));
//            imageCheckMarkIcon.setVisibility(View.INVISIBLE);
//        }

    }

    @Override
    public int getItemCount() {
        if(files!=null){
            return files.size();
        } else return 0;
    }

    @Override
    public long getHeaderId(int position) {
        return 0;//links.get(position).getLinkSent().subSequence(0, 1).charAt(0);
    }

    @Override
    public LinksListAdapter.HeaderHolder onCreateHeaderViewHolder(ViewGroup parent) {
        final View view = inflater.inflate(R.layout.header_media, parent, false);
        return new LinksListAdapter.HeaderHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(LinksListAdapter.HeaderHolder viewHolder, int position) {
//        viewHolder.header.setText(""+media.get(position).getDate());
        viewHolder.header.setText("");//+links.get(position).getLinkSent());
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        View mView;
        FrameLayout frameBottom;
        FrameLayout frameSelectItem;
        FrameLayout frameLinkPicture;
        LinearLayout linearLinkInfo;
        ImageView imageSelectIcon;
        ImageView imageCheckMarkIcon;
        TextView tvTitle;
        TextView tvLink;
        ImageView imageLinkLogo;


        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            frameBottom = (FrameLayout) itemView.findViewById(R.id.recycler_item_links_fl_bottom_line);
            imageSelectIcon = (ImageView) itemView.findViewById(R.id.recycler_item_links_image_selector_unchecked);
            imageCheckMarkIcon = (ImageView) itemView.findViewById(R.id.recycler_item_links_image_selector_checked);
            frameSelectItem = (FrameLayout) itemView.findViewById(R.id.recycler_item_links_fl_selector);
            frameLinkPicture = (FrameLayout) itemView.findViewById(R.id.recycler_item_links_fl_icon);
            linearLinkInfo = (LinearLayout) itemView.findViewById(R.id.recycler_item_links_ll_link_info);
            tvTitle = (TextView) itemView.findViewById(R.id.recycler_item_links_tv_title);
            tvLink = (TextView) itemView.findViewById(R.id.recycler_item_links_tv_link);
            tvLink = (TextView) itemView.findViewById(R.id.recycler_item_links_tv_link);
            imageLinkLogo = (ImageView) itemView.findViewById(R.id.recycler_item_links_image_icon);
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
//        for (LinksItem a : links){
//            a.setPressed(false);
//        }
    }

}