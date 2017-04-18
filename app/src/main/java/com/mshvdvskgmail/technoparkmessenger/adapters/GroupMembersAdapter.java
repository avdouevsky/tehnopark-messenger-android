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
import com.mshvdvskgmail.technoparkmessenger.network.model.User;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Member;
import java.util.ArrayList;

import ca.barrenechea.widget.recyclerview.decoration.StickyHeaderAdapter;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

/**
 * Created by mshvdvsk on 29/03/2017.
 */

public class GroupMembersAdapter extends RecyclerView.Adapter<GroupMembersAdapter.ViewHolder> {

    private ArrayList<User> members;
    private View rowView;
    private Context context;
    private User currentItem;

    private String name;
    private String officePosition;
    private boolean isOnline;

    private TextView tvItemName;
    private TextView tvItemPosition;
    private ImageView imageItemOnline;
    private FrameLayout frameSeparator;

    public static final String TAG = GroupMembersAdapter.class.getCanonicalName();


    public GroupMembersAdapter(ArrayList <User> members, Context context) {
        this.members = members;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        rowView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_group_member_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(rowView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

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

        ImageView profileIcon = (ImageView) holder.mView.findViewById(R.id.profile_icon);
        Picasso.with(context).load(R.drawable.pushkin).transform(new RoundedCornersTransformation(360,0)).into(profileIcon);

        currentItem = members.get(position);

        name = currentItem.getName();
        officePosition = currentItem.getOfficePosition();
//        isOnline = currentItem.isOnline();

        tvItemName = (TextView) holder.mView.findViewById(R.id.name);
        tvItemPosition = (TextView) holder.mView.findViewById(R.id.office_position);
        imageItemOnline = (ImageView) holder.mView.findViewById(R.id.online_dot);

        tvItemName.setText(name);
        tvItemPosition.setText(officePosition);

        if (isOnline) {
            imageItemOnline.setVisibility(View.VISIBLE);
        } else imageItemOnline.setVisibility(View.GONE);

//        if (currentItem.isAdmin()){
//            TextView admin = (TextView) holder.mView.findViewById(R.id.admin);
//            admin.setVisibility(View.VISIBLE);
//        }
    }

    @Override
    public int getItemCount() {
        return members.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        View mView;
        FrameLayout mFrameLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            mFrameLayout = (FrameLayout) itemView.findViewById(R.id.item_separator);
        }
    }

}
