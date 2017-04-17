package com.mshvdvskgmail.technoparkmessenger.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.mshvdvskgmail.technoparkmessenger.Controller;
import com.mshvdvskgmail.technoparkmessenger.R;
import com.mshvdvskgmail.technoparkmessenger.network.REST;
import com.mshvdvskgmail.technoparkmessenger.network.model.SipCall;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mshvdvsk on 20/03/2017.
 */

public class CallsListAdapter extends RecyclerView.Adapter<CallsListAdapter.ViewHolder> {
    private ArrayList<SipCall> callsList;
    private View rowView;
    private Context context;
    private SipCall currentItem;
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


    public CallsListAdapter(ArrayList <SipCall> callsList, Context context) {
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

        currentItem = callsList.get(position);
//        count++;
        try {
            name = currentItem.Opposite().cn;
        }catch (NullPointerException e){
            name = "test";
        }
        time = currentItem.getTime();
//        isOnline = currentItem.Opposite().isOnline();
        isMissed = currentItem.isMissed();
        isIncoming = currentItem.isIncoming();

        itemName = (TextView) holder.mView.findViewById(R.id.name);
        itemTime = (TextView) holder.mView.findViewById(R.id.time);
        itemOnline = (ImageView) holder.mView.findViewById(R.id.online_dot);
        itemIncomingStatus = (ImageView) holder.mView.findViewById(R.id.call_status);

        itemName.setText(name);
        itemTime.setText(time);
try {

        REST.getInstance().user_status(Controller.getInstance().getAuth().getUser().token.session_id, Controller.getInstance().getAuth().getUser().token.token, currentItem.Opposite().id).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.body() == "1") {
                    itemOnline.setVisibility(View.VISIBLE);
                } else itemOnline.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                itemOnline.setVisibility(View.GONE);
            }
        });

}catch (NullPointerException e){

}

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
