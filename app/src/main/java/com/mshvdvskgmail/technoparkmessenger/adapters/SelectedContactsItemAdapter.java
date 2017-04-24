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
import com.mshvdvskgmail.technoparkmessenger.helpers.ICommand;
import com.mshvdvskgmail.technoparkmessenger.models.ContactsListItem;
import com.mshvdvskgmail.technoparkmessenger.fragments.BaseFragment;
import com.mshvdvskgmail.technoparkmessenger.fragments.FragmentAddMember;
import com.mshvdvskgmail.technoparkmessenger.models.ContactsListItem;
import com.mshvdvskgmail.technoparkmessenger.network.model.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

/**
 * Created by mshvdvsk on 07/04/2017.
 */

public class SelectedContactsItemAdapter extends RecyclerView.Adapter<SelectedContactsItemAdapter.ViewHolder> {
    private Context context;

    private List<User> contacts = new ArrayList<>();

    private ICommand<User> clickListener;

    public SelectedContactsItemAdapter(Context context){
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

    public void addData(User user){
        contacts.add(0, user);
        sort();
        notifyDataSetChanged();
    }

    public void removeData(User user){
        contacts.remove(user);
        sort();
        notifyDataSetChanged();
    }

    public List<User> getData(){
        return contacts;
    }

    private void sort(){
        //do nothong
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return ViewHolder.create(context, parent);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.setData(contacts.get(position));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
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

    public void setClickListener(ICommand<User> clickListener) {
        this.clickListener = clickListener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        ImageView imagePicture;
        FrameLayout flCancel;

        public ViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.recycler_item_contacts_selected_tv_name);
            imagePicture = (ImageView) itemView.findViewById(R.id.recycler_item_contacts_selected_image_picture);
            flCancel = (FrameLayout) itemView.findViewById(R.id.recycler_item_contacts_selected_fl_cancel);
        }

        public static ViewHolder create(Context context, ViewGroup parent){
            View rowView = LayoutInflater.from(context)
                    .inflate(R.layout.recycler_item_contacts_selected, parent, false);
            return new ViewHolder(rowView);
        }

        public void setData(User user){
            tvName.setText(user.getName());
            Picasso.with(itemView.getContext())
                    .load(user.avatar)
                    .placeholder(R.drawable.icon_user)
                    .error(R.drawable.icon_user)
                    .resizeDimen(R.dimen.chat_item_avatar_size, R.dimen.chat_item_avatar_size)
                    .centerCrop().transform(new RoundedCornersTransformation(360,0))
                    .into(imagePicture);
            //todo set user
        }

        public View getView(){
            return itemView;
        }
    }
}
