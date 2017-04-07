package com.mshvdvskgmail.technoparkmessenger.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.mshvdvskgmail.technoparkmessenger.R;
import com.mshvdvskgmail.technoparkmessenger.models.ContactsListItem;
import com.mshvdvskgmail.technoparkmessenger.models.MediaList;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import ca.barrenechea.widget.recyclerview.decoration.StickyHeaderAdapter;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

/**
 * Created by mshvdvsk on 07/04/2017.
 */

public class AddMemberListAdapter extends RecyclerView.Adapter<AddMemberListAdapter.ViewHolder> {

    private ArrayList<ContactsListItem> contacts;
    private Context context;
    private FrameLayout mFrame;
    private LayoutInflater mInflater;
    private ContactsListItem current;
    private String name;
    private String officePosition;

    private View rootView;
    private FrameLayout flSeparator;
    private TextView tvName;
    private TextView tvPosition;
    private ImageView imagePicture;
    private FrameLayout flSelector;
    private ImageView imageSelectBackground;
    private ImageView imageCheckMarkIcon;

    public boolean isSelecting;


    public AddMemberListAdapter(ArrayList <ContactsListItem> contacts, Context context) {
        this.contacts = contacts;
        this.context = context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public AddMemberListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_add_member_item, parent, false);
        AddMemberListAdapter.ViewHolder viewHolder = new AddMemberListAdapter.ViewHolder(rowView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AddMemberListAdapter.ViewHolder holder, final int position) {
        rootView = holder.rootView;
        flSeparator = holder.flSeparator;
        tvName = holder.tvName;
        tvPosition = holder.tvPosition;
        imagePicture = holder.imagePicture;
        flSelector = holder.flSelector;
        imageSelectBackground = holder.imageSelectBackground;
        imageCheckMarkIcon = holder.imageCheckMarkIcon;

        name = contacts.get(position).getName();
        tvName.setText(name);

        officePosition = contacts.get(position).getOfficePosition();
        tvPosition.setText(officePosition);

        imagePicture = holder.imagePicture;
        Picasso.with(context).load(R.drawable.pushkin).transform(new RoundedCornersTransformation(360,0)).into(imagePicture);

        imageSelectBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contacts.get(position).setPressed(!contacts.get(position).isPressed());
                notifyDataSetChanged();
            }
        });

        if (contacts.get(position).isPressed()){
            imageSelectBackground.setBackground(context.getResources().getDrawable(R.drawable.ic_select_dot_checked));
            imageCheckMarkIcon.setVisibility(View.VISIBLE);
        } else {
            imageSelectBackground.setBackground(context.getResources().getDrawable(R.drawable.ic_select_dot_unchecked));
            imageCheckMarkIcon.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        View rootView;
        FrameLayout flSeparator;
        TextView tvName;
        TextView tvPosition;
        ImageView imagePicture;
        FrameLayout flSelector;
        ImageView imageSelectBackground;
        ImageView imageCheckMarkIcon;

        public ViewHolder(View itemView) {
            super(itemView);
            rootView = itemView;
            flSeparator = (FrameLayout) itemView.findViewById(R.id.recycler_item_add_member_fl_separator);
            tvName = (TextView) itemView.findViewById(R.id.recycler_item_add_member_tv_name);
            tvPosition = (TextView) itemView.findViewById(R.id.recycler_item_add_member_tv_position);
            imagePicture = (ImageView) itemView.findViewById(R.id.recycler_item_add_member_image_picture);
            flSelector = (FrameLayout) itemView.findViewById(R.id.recycler_item_add_member_fl_selector);
            imageSelectBackground = (ImageView) itemView.findViewById(R.id.recycler_item_add_member_image_selector_background);
            imageCheckMarkIcon = (ImageView) itemView.findViewById(R.id.recycler_item_add_member_image_selector_check_mark);
        }
    }
}