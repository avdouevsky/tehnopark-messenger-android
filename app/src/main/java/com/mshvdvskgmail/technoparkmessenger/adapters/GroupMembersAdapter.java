package com.mshvdvskgmail.technoparkmessenger.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.mshvdvskgmail.technoparkmessenger.R;
import com.mshvdvskgmail.technoparkmessenger.models.ContactsListItem;
import com.mshvdvskgmail.technoparkmessenger.models.MemberListItem;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Member;
import java.util.ArrayList;

import ca.barrenechea.widget.recyclerview.decoration.StickyHeaderAdapter;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

/**
 * Created by mshvdvsk on 29/03/2017.
 */

public class GroupMembersAdapter extends RecyclerView.Adapter<GroupMembersAdapter.ViewHolder> {

    private ArrayList<MemberListItem> members;
    private View rowView;
    private Context context;
    private MemberListItem currentItem;

    private String name;
    private String officePosition;
    private boolean isOnline;

    private TextView tvName;
    private TextView tvPosition;
    private ImageView imageOnline;
    private FrameLayout frameSeparator;
    private ImageView profileIcon;

    public static final String TAG = GroupMembersAdapter.class.getCanonicalName();


    public GroupMembersAdapter(ArrayList <MemberListItem> members, Context context) {
        this.members = members;
        this.context = context;
    }

    @Override
    public GroupMembersAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        rowView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_group_member_item, parent, false);
        GroupMembersAdapter.ViewHolder viewHolder = new GroupMembersAdapter.ViewHolder(rowView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(GroupMembersAdapter.ViewHolder holder, int position) {

//        float d = context.getResources().getDisplayMetrics().density;
//        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) holder.mView.getLayoutParams();
//
//        int topMargin = (int)(-10 * d);
//
//        if (position == 0){
//            params.topMargin = topMargin;
//        } else {
//            params.topMargin = 0;
//        }

        profileIcon = holder.imageProfile;
        Picasso.with(context).load(R.drawable.pushkin).transform(new RoundedCornersTransformation(360,0)).into(profileIcon);

        currentItem = members.get(position);

        tvName = holder.tvName;
        tvPosition = holder.tvPosition;
        imageOnline = holder.imageOnline;

        name = currentItem.getName();
        officePosition = currentItem.getOfficePosition();
        isOnline = currentItem.isOnline();

        tvName.setText(name);
        tvPosition.setText(officePosition);

        if (isOnline) {
            imageOnline.setVisibility(View.VISIBLE);
        } else imageOnline.setVisibility(View.GONE);

        if (currentItem.isAdmin()){
            TextView admin = (TextView) holder.mView.findViewById(R.id.recycler_item_group_member_tv_admin);
            admin.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return members.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        View mView;
        TextView tvName;
        TextView tvPosition;
        ImageView imageOnline;
        ImageView imageProfile;

        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            tvName = (TextView) itemView.findViewById(R.id.recycler_item_group_member_tv_name);
            tvPosition = (TextView) itemView.findViewById(R.id.recycler_item_group_member_tv_position);
            imageOnline = (ImageView) itemView.findViewById(R.id.recycler_item_group_member_image_online);
            imageProfile = (ImageView) itemView.findViewById(R.id.recycler_item_group_member_image_picture);
        }
    }
}
