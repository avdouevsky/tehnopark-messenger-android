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
import com.mshvdvskgmail.technoparkmessenger.network.model.Attachment;
import com.mshvdvskgmail.technoparkmessenger.network.model.User;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import ca.barrenechea.widget.recyclerview.decoration.StickyHeaderAdapter;

import static android.R.attr.data;
import static com.mshvdvskgmail.technoparkmessenger.R.id.recycler_item_documents_tv_name;
import static com.mshvdvskgmail.technoparkmessenger.R.id.recycler_item_documents_tv_size;

/**
 * Created by mshvdvsk on 22/03/2017
 */

public class DocumentsListAdapter extends RecyclerView.Adapter<DocumentsListAdapter.ViewHolder>
        implements StickyHeaderAdapter<DocumentsListAdapter.HeaderHolder> {

    private final static String TAG = DocumentsListAdapter.class.toString();

//    private ArrayList<DocumentsListItem> documents = new ArrayList<>();
    private List<Attachment> files;
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
    String mimeType  = "application";

    public DocumentsListAdapter(List<Attachment> files, Context context) {
        this.files = files;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void setData(List<Attachment> files){
        this.files = files;
        sort();
        notifyDataSetChanged();
    }

    private void sort(){}

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
        } else if (position == files.size()-1){
            params.topMargin = 0;
            params.bottomMargin = bottomMargin;
        } else {
            params.topMargin = 0;
            params.bottomMargin = 0;
        }


        if((files.get(position).mime.indexOf(mimeType)!=-1)){
            holder.mView.setVisibility(View.VISIBLE);
            holder.tvType.setText(files.get(position).name.substring(files.get(position).name.lastIndexOf('.') + 1));
            if(holder.tvType.getText().equals("")||holder.tvType.getText().length()>4){
                holder.tvType.setText("file");
            }
            holder.tvIconType.setText(files.get(position).name.substring(files.get(position).name.lastIndexOf('.') + 1));
            if(holder.tvIconType.getText().equals("")||holder.tvIconType.getText().length()>4){
                holder.tvIconType.setText("file");
            }
            holder.tvName.setText(files.get(position).name);
            holder.tvSize.setText(humanReadableByteCount(Long.parseLong(files.get(position).size), true));
        } else {
            holder.mView.setVisibility(View.GONE);
        }


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
//                frameDocType.startAnimation(slideToLeft);
//                imageSelectIcon.startAnimation(fadeOut);
//                linearDocInfo.startAnimation(slideToLeft);
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
//                slideToRight.setAnimationListener(new Animation.AnimationListener() {
//                    @Override
//                    public void onAnimationStart(Animation animation) {
//                        isAnimated = false;
//                    }
//
//                    @Override
//                    public void onAnimationEnd(Animation animation) {
//                        isPressed = true;
//                        notifyDataSetChanged();
//                    }
//
//                    @Override
//                    public void onAnimationRepeat(Animation animation) {}
//                });
//                frameDocType.startAnimation(slideToRight);
//                imageSelectIcon.startAnimation(fadeIn);
//                linearDocInfo.startAnimation(slideToRight);
//            }
//        }
//
//        frameDocType.requestLayout();
//
//
//
//
//        frameSelectItem.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                documents.get(position).setPressed(!documents.get(position).isPressed());
//                notifyDataSetChanged();
//            }
//        });
//
//        if (documents.get(position).isPressed()){
//            imageSelectIcon.setBackground(context.getResources().getDrawable(R.drawable.ic_select_dot_checked));
//            imageCheckMarkIcon.setVisibility(View.VISIBLE);
//        } else {
//            imageSelectIcon.setBackground(context.getResources().getDrawable(R.drawable.ic_select_dot_unchecked));
//            imageCheckMarkIcon.setVisibility(View.INVISIBLE);
//        }



    }

    @Override
    public int getItemCount() {
        if (files!=null){
            return files.size();
        } else {
            return 0;
        }
    }

    @Override
    public long getHeaderId(int position) {
        return convertIntoMonth(files.get(position).time).subSequence(0, 1).charAt(0);

        //documents.get(position).getDataSent().subSequence(0, 1).charAt(0);
    }

    @Override
    public DocumentsListAdapter.HeaderHolder onCreateHeaderViewHolder(ViewGroup parent) {
        final View view = inflater.inflate(R.layout.header_media, parent, false);
        return new DocumentsListAdapter.HeaderHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(DocumentsListAdapter.HeaderHolder viewHolder, int position) {
        viewHolder.header.setText(convertIntoMin(files.get(position).time));//+documents.get(position).getDataSent());
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        View mView;
        FrameLayout frameBottom;
        FrameLayout frameDocType;
        FrameLayout frameSelectItem;
        LinearLayout linearDocInfo;
        ImageView imageSelectIcon;
        ImageView imageCheckMarkIcon;
        TextView tvIconType;
        TextView tvName;
        TextView tvSize;
        TextView tvType;


        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            frameBottom = (FrameLayout) itemView.findViewById(R.id.recycler_item_documents_fl_bottom_line);
            frameDocType = (FrameLayout) itemView.findViewById(R.id.recycler_item_documents_fl_icon);
            linearDocInfo = (LinearLayout) itemView.findViewById(R.id.recycler_item_documents_ll_link_info);
            imageSelectIcon = (ImageView) itemView.findViewById(R.id.recycler_item_documents_image_unchecked);
            imageCheckMarkIcon = (ImageView) itemView.findViewById(R.id.recycler_item_documents_image_checked);
            frameSelectItem = (FrameLayout) itemView.findViewById(R.id.recycler_item_documents_fl_selector);
            tvIconType = (TextView) itemView.findViewById(R.id.recycler_item_documents_tv_type_icon);
            tvName = (TextView) itemView.findViewById(R.id.recycler_item_documents_tv_name);
            tvSize = (TextView) itemView.findViewById(R.id.recycler_item_documents_tv_size);
            tvType = (TextView) itemView.findViewById(R.id.recycler_item_documents_tv_type);
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
//        for (DocumentsListItem a : documents){
//            a.setPressed(false);
//        }
    }

    private String humanReadableByteCount(long bytes, boolean si) {
        int unit = si ? 1000 : 1024;
        if (bytes < unit) return bytes + " B";
        int exp = (int) (Math.log(bytes) / Math.log(unit));
        String pre = (si ? "KMGTPE" : "KMGTPE").charAt(exp-1) + (si ? "" : "i");
        Log.w("kek", ""+bytes / Math.pow(unit, exp));

        return String.format("%.0f %sB", bytes / Math.pow(unit, exp), pre);
    }

    private String convertIntoMonth(String date){
        long dateLong = Long.parseLong(date);
        Date dateDate = new Date(dateLong * 1000);
        Locale russianLocale = new Locale("ru","RU");
        SimpleDateFormat dateFormatRequired = new SimpleDateFormat("mm", russianLocale);
        String monthName = dateFormatRequired.format(dateDate);
        return monthName;
    }

    private String convertIntoMin(String date){
        long dateLong = Long.parseLong(date);
        Date dateDate = new Date(dateLong * 1000);
        Locale russianLocale = new Locale("ru","RU");
        SimpleDateFormat dateFormatRequired = new SimpleDateFormat("hh:mm", russianLocale);
        String monthName = dateFormatRequired.format(dateDate);
        return monthName;
    }
}
