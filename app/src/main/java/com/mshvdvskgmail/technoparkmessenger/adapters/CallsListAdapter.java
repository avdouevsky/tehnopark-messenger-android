package com.mshvdvskgmail.technoparkmessenger.adapters;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.mshvdvskgmail.technoparkmessenger.Controller;
import com.mshvdvskgmail.technoparkmessenger.Fragments;
import com.mshvdvskgmail.technoparkmessenger.R;
import com.mshvdvskgmail.technoparkmessenger.events.SwitchFragmentEvent;
import com.mshvdvskgmail.technoparkmessenger.fragments.FragmentChatGroup;
import com.mshvdvskgmail.technoparkmessenger.fragments.FragmentIncomingCall;
import com.mshvdvskgmail.technoparkmessenger.fragments.FragmentOutgoingCall;
import com.mshvdvskgmail.technoparkmessenger.models.CallsList;
import com.mshvdvskgmail.technoparkmessenger.network.REST;
import com.mshvdvskgmail.technoparkmessenger.network.model.SipCall;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

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

    private TextView tvName;
    private TextView tvTime;
    private ImageView imageOnline;
    private ImageView imageStatus;
    private ImageView imagePicture;

    private FragmentManager fManager;

    public CallsListAdapter(ArrayList <SipCall> callsList, Context context) {
        this.callsList = callsList;
        this.context = context;
        this.fManager = fManager;
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
            FrameLayout mFrameLayout = holder.flSeparator;
            mFrameLayout.setVisibility(View.GONE);
        }

        imagePicture = holder.imagePicture;
        Picasso.with(context).load(R.drawable.pushkin).transform(new RoundedCornersTransformation(360,0)).into(imagePicture);

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

        tvName = holder.tvName;
        tvTime = holder.tvTime;
        imageOnline = holder.imageOnline;
        imageStatus = holder.imageStatus;

        tvName.setText(name);
        tvTime.setText(time);

        if (isOnline) {
            imageOnline.setVisibility(View.VISIBLE);
        } else imageOnline.setVisibility(View.GONE);
        //TODO
//        REST.getInstance().user_status(Controller.getInstance().getAuth().getUser().token.session_id,
//                Controller.getInstance().getAuth().getUser().token.token, currentItem.Opposite().id).enqueue(new Callback<String>() {
//            @Override
//            public void onResponse(Call<String> call, Response<String> response) {
//                if (response.body() == "1") {
////                    itemOnline.setVisibility(View.VISIBLE);
//                }
////                else itemOnline.setVisibility(View.GONE);
//            }
//
//            @Override
//            public void onFailure(Call<String> call, Throwable t) {
////                itemOnline.setVisibility(View.GONE);
//            }
//        });

//}catch (NullPointerException e){
//
//}

        if (isMissed) {
            imageStatus.setVisibility(View.GONE);
            tvName.setTextColor(context.getResources().getColor(R.color.calls_missed));
            tvTime.setTextColor(context.getResources().getColor(R.color.calls_missed));
        } else if (isIncoming){
            imageStatus.setImageResource(R.drawable.ic_calls_inc);
        } else {
            imageStatus.setImageResource(R.drawable.ic_calls_out);
        }

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().postSticky(new SwitchFragmentEvent(Fragments.INCOMING_CALL, null));
                  //TODO FragmentIncomingCall call = new FragmentIncomingCall();
//                fManager.beginTransaction()
//                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
//                        .replace(R.id.container, call)
//                        .addToBackStack(null)
//                        .commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return callsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private View mView;
        private FrameLayout flSeparator;
        private TextView tvName;
        private TextView tvTime;
        private ImageView imageOnline;
        private ImageView imageStatus;
        private ImageView imagePicture;

        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            flSeparator = (FrameLayout) itemView.findViewById(R.id.recycler_item_calls_fl_separator);
            tvName = (TextView) itemView.findViewById(R.id.recycler_item_calls_tv_name);
            tvTime = (TextView) itemView.findViewById(R.id.recycler_item_calls_tv_time);
            imageOnline = (ImageView) itemView.findViewById(R.id.recycler_item_calls_image_online);
            imageStatus = (ImageView) itemView.findViewById(R.id.recycler_item_calls_image_status);
            imagePicture = (ImageView) mView.findViewById(R.id.recycler_item_calls_image_picture);
        }
    }

}
