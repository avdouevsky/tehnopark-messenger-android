package com.mshvdvskgmail.technoparkmessenger.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mshvdvskgmail.technoparkmessenger.Fragments;
import com.mshvdvskgmail.technoparkmessenger.events.SwitchFragmentEvent;
import com.mshvdvskgmail.technoparkmessenger.network.model.SipCall;
import com.mshvdvskgmail.technoparkmessenger.view.CallItemView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mshvdvsk on 20/03/2017.
 */

public class CallsListAdapter extends RecyclerView.Adapter<CallsListAdapter.ViewHolder> {
    private List<SipCall> calls = new ArrayList<>();
    private Context context;

    public CallsListAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<SipCall> data){
        calls = data;
        sort();
        notifyDataSetChanged();
    }

    public void addData(List<SipCall> data){
        calls.addAll(data);
        sort();
        notifyDataSetChanged();
    }

    private void sort(){
        /* do nothing */
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(new CallItemView(context));
    }

    @Override
    public void onBindViewHolder(CallsListAdapter.ViewHolder holder, int position) {
        SipCall call = calls.get(position);
        holder.getView().setData(call);

        holder.getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().postSticky(new SwitchFragmentEvent(Fragments.OUTGOING_CALL, null));
            }
        });
    }

    @Override
    public int getItemCount() {
        return calls.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(CallItemView itemView) {
            super(itemView);
        }

        public CallItemView getView(){
            return (CallItemView) itemView;
        }
    }

}
