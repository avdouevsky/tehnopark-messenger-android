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
import com.mshvdvskgmail.technoparkmessenger.fragments.BaseFragment;
import com.mshvdvskgmail.technoparkmessenger.fragments.FragmentAddMember;
import com.mshvdvskgmail.technoparkmessenger.models.ContactsListItem;
import com.mshvdvskgmail.technoparkmessenger.network.model.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

/**
 * Created by mshvdvsk on 07/04/2017.
 */

public class SelectedContactsItemAdapter extends RecyclerView.Adapter<SelectedContactsItemAdapter.ViewHolder> {

    private ArrayList<User> contacts;
    private Context context;
    private FrameLayout mFrame;
    private LayoutInflater mInflater;
    private ContactsListItem current;
    private String name;
    private String officePosition;

    private View rootView;
    private TextView tvName;
    private ImageView imagePicture;
    private FrameLayout flCancel;

    public boolean isSelecting;

    public BaseFragment parentFragment;


    public SelectedContactsItemAdapter(ArrayList <User> contacts, Context context, BaseFragment fragment) {
        this.contacts = contacts;
        this.context = context;
        this.parentFragment = fragment;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_item_contacts_selected, parent, false);
        ViewHolder viewHolder = new ViewHolder(rowView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        rootView = holder.rootView;
        tvName = holder.tvName;
        imagePicture = holder.imagePicture;
        flCancel = holder.flCancel;

        name = contacts.get(position).getName();
        tvName.setText(name);

        imagePicture = holder.imagePicture;
        Picasso.with(context).load(R.drawable.pushkin).transform(new RoundedCornersTransformation(360,0)).into(imagePicture);

        flCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                contacts.get(position).setPressed(!contacts.get(position).isPressed());
                contacts.remove(position);
                parentFragment.setSelected_contacts(contacts);
//                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        View rootView;
        TextView tvName;
        ImageView imagePicture;
        FrameLayout flCancel;

        public ViewHolder(View itemView) {
            super(itemView);
            rootView = itemView;
            tvName = (TextView) itemView.findViewById(R.id.recycler_item_contacts_selected_tv_name);
            imagePicture = (ImageView) itemView.findViewById(R.id.recycler_item_contacts_selected_image_picture);
            flCancel = (FrameLayout) itemView.findViewById(R.id.recycler_item_contacts_selected_fl_cancel);
        }
    }
}
