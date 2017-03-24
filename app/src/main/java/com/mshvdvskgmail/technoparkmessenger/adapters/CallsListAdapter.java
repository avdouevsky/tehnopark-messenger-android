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
import com.mshvdvskgmail.technoparkmessenger.models.CallsList;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

/**
 * Created by mshvdvsk on 20/03/2017.
 */

public class CallsListAdapter extends RecyclerView.Adapter<CallsListAdapter.ViewHolder> {
    private ArrayList<CallsList> callsList;
    private View rowView;
    private Context context;
    private CallsList currentItem;
    private int count;

    private String name;
    private String time;
    private boolean isMissed;
    private boolean isIncoming;
    private boolean isOnline;

    private TextView itemName;
    private TextView itemTime;
    private ImageView itemOnline;
    private ImageView itemIncomingStatus;


    public CallsListAdapter(ArrayList <CallsList> callsList, Context context) {
        this.callsList = callsList;
        this.context = context;
        count = 0;
    }

    @Override
    public CallsListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        rowView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_calls_item, parent, false);
        CallsListAdapter.ViewHolder viewHolder = new CallsListAdapter.ViewHolder(rowView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CallsListAdapter.ViewHolder holder, int position) {
        if (position==0){
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) holder.mView.getLayoutParams();
            params.topMargin = 25;
        } else {
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) holder.mView.getLayoutParams();
            params.topMargin = 0;
        }

        if (position==callsList.size()-1){
            FrameLayout mFrameLayout = holder.mFrameLayout;
            mFrameLayout.setVisibility(View.GONE);
        }

        ImageView profileIcon = (ImageView) holder.mView.findViewById(R.id.profile_icon);
        Picasso.with(context).load(R.drawable.pushkin).transform(new RoundedCornersTransformation(360,0)).into(profileIcon);

        currentItem = callsList.get(count);
        count++;

        name = currentItem.getName();
        time = currentItem.getTime();
        isOnline = currentItem.isOnline();
        isMissed = currentItem.isMissed();
        isIncoming = currentItem.isIncoming();

        itemName = (TextView) holder.mView.findViewById(R.id.name);
        itemTime = (TextView) holder.mView.findViewById(R.id.time);
        itemOnline = (ImageView) holder.mView.findViewById(R.id.online_dot);
        itemIncomingStatus = (ImageView) holder.mView.findViewById(R.id.call_status);

        itemName.setText(name);
        itemTime.setText(time);

        if (isOnline) {
            itemOnline.setVisibility(View.VISIBLE);
        } else itemOnline.setVisibility(View.GONE);

        if (isMissed) {
            itemIncomingStatus.setVisibility(View.GONE);
            itemName.setTextColor(context.getResources().getColor(R.color.calls_missed));
            itemTime.setTextColor(context.getResources().getColor(R.color.calls_missed));
        } else if (isIncoming){
            itemIncomingStatus.setImageResource(R.drawable.ic_calls_inc);
        } else {
            itemIncomingStatus.setImageResource(R.drawable.ic_calls_out);
        }


    }

    @Override
    public int getItemCount() {
        return callsList.size();
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
