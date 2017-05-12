package com.mshvdvskgmail.technoparkmessenger.adapters;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.mshvdvskgmail.technoparkmessenger.R;
import com.mshvdvskgmail.technoparkmessenger.helpers.ArgsBuilder;
import com.mshvdvskgmail.technoparkmessenger.helpers.ICommand;
import com.mshvdvskgmail.technoparkmessenger.network.model.Chat;
import com.mshvdvskgmail.technoparkmessenger.network.model.User;
import com.mshvdvskgmail.technoparkmessenger.view.MemberItemView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

/**
 * Created by mshvdvsk on 29/03/2017.
 */

public class GroupMembersAdapter extends RecyclerView.Adapter<GroupMembersAdapter.ViewHolder> {
    public static final String TAG = GroupMembersAdapter.class.getCanonicalName();

    private Context context;
    private List<User> members = new ArrayList<>();
    private ICommand<User> clickListener;
    private Chat chat;

    public GroupMembersAdapter(Context context) {
        this.context = context;
    }

    public void setData(@Nullable List<User> users){
        if(users == null) users = new ArrayList<>();
        members = users;
        sort();
        notifyDataSetChanged();
    }

    public void addData(List<User> users){
        members.addAll(users);
        sort();
        notifyDataSetChanged();
    }

    private void sort(){
        // do nothing
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(new MemberItemView(context));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Chat chat = ArgsBuilder.create().chat();    //TODO не красиво :(
        holder.getView().setData(members.get(position), chat != null && chat.admin.equals(members.get(position).id));
        holder.getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(clickListener != null) clickListener.exec(members.get(holder.getAdapterPosition()));
            }
        });

//        if (currentItem.isAdmin()){
//            TextView admin = (TextView) holder.mView.findViewById(R.id.recycler_item_group_member_tv_admin);
//            admin.setVisibility(View.VISIBLE);
//        }
    }

    public void setClickListener(ICommand<User> clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public int getItemCount() {
        return members.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }

        public MemberItemView getView(){
            return (MemberItemView) itemView;
        }
    }

}
