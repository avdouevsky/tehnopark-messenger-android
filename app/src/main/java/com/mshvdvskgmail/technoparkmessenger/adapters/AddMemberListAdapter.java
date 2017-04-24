package com.mshvdvskgmail.technoparkmessenger.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.mshvdvskgmail.technoparkmessenger.R;
import com.mshvdvskgmail.technoparkmessenger.helpers.ICommand;
import com.mshvdvskgmail.technoparkmessenger.models.ContactsListItem;
import com.mshvdvskgmail.technoparkmessenger.models.MediaList;
import com.mshvdvskgmail.technoparkmessenger.fragments.FragmentAddMember;
import com.mshvdvskgmail.technoparkmessenger.models.ContactsListItem;
import com.mshvdvskgmail.technoparkmessenger.models.MediaList;
import com.mshvdvskgmail.technoparkmessenger.network.model.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import ca.barrenechea.widget.recyclerview.decoration.StickyHeaderAdapter;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

/**
 * Created by mshvdvsk on 07/04/2017.
 */
public class AddMemberListAdapter extends RecyclerView.Adapter<AddMemberListAdapter.ViewHolder> {
    private Context context;
    private List<User> contacts = new ArrayList<>();

    private ICommand<User> clickListener;

    public AddMemberListAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<User> users){
        contacts = users;
        sort();
        notifyDataSetChanged();
    }

    public void addData(List<User> users){
        contacts.addAll(users);
        sort();
        notifyDataSetChanged();
    }

    private void sort(){
        //do nothing
    }

    public void setClickListener(ICommand<User> clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return ViewHolder.create(context, parent);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.setData(contacts.get(position));

        holder.getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(clickListener != null) clickListener.exec(contacts.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        FrameLayout flSeparator;
        TextView tvName;
        TextView tvPosition;
        ImageView imagePicture;
        FrameLayout flSelector;
        ImageView imageSelectBackground;
        ImageView imageCheckMarkIcon;

        public static ViewHolder create(Context context, ViewGroup parent){
            View rowView = LayoutInflater.from(context).inflate(R.layout.recycler_add_member_item, parent, false);
            return new ViewHolder(rowView);
        }

        public ViewHolder(View itemView) {
            super(itemView);

            flSeparator = (FrameLayout) itemView.findViewById(R.id.recycler_item_add_member_fl_separator);
            tvName = (TextView) itemView.findViewById(R.id.recycler_item_add_member_tv_name);
            tvPosition = (TextView) itemView.findViewById(R.id.recycler_item_add_member_tv_position);
            imagePicture = (ImageView) itemView.findViewById(R.id.recycler_item_add_member_image_picture);
            flSelector = (FrameLayout) itemView.findViewById(R.id.recycler_item_add_member_fl_selector);
            imageSelectBackground = (ImageView) itemView.findViewById(R.id.recycler_item_add_member_image_selector_background);
            imageCheckMarkIcon = (ImageView) itemView.findViewById(R.id.recycler_item_add_member_image_selector_check_mark);
        }

        public View getView(){
            return itemView;
        }

        public void setData(User user){
            tvName.setText(user.cn);
            tvPosition.setText(user.getOfficePosition());
            //TODO!!!
            Picasso.with(itemView.getContext())
                    .load(user.avatar)
                    .placeholder(R.drawable.icon_user)
                    .error(R.drawable.icon_user)
                    .resizeDimen(R.dimen.chat_item_avatar_size, R.dimen.chat_item_avatar_size)
                    .centerCrop().transform(new RoundedCornersTransformation(360,0))
                    .into(imagePicture);
            if (user.uiSelected){
                imageSelectBackground.setBackgroundResource(R.drawable.ic_select_dot_checked);
                imageCheckMarkIcon.setVisibility(View.VISIBLE);
            } else {
                imageSelectBackground.setBackgroundResource(R.drawable.ic_select_dot_unchecked);
                imageCheckMarkIcon.setVisibility(View.INVISIBLE);
            }
        }
    }
}