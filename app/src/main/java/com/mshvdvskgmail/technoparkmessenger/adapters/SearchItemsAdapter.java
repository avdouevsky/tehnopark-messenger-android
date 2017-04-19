package com.mshvdvskgmail.technoparkmessenger.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mshvdvskgmail.technoparkmessenger.R;
import com.mshvdvskgmail.technoparkmessenger.models.ProfileAttachment;
import com.mshvdvskgmail.technoparkmessenger.models.SearchItem;

import java.util.ArrayList;

/**
 * Created by mshvdvsk on 04/04/2017.
 */

public class SearchItemsAdapter extends RecyclerView.Adapter<SearchItemsAdapter.ViewHolder> {

    private ArrayList<SearchItem> searchResult;
    private Context context;
    private View mView;
    private TextView tvName;
    private TextView tvMessage;
    private TextView tvTime;

    public SearchItemsAdapter(ArrayList <SearchItem> searchResult, Context context) {
        this.searchResult = searchResult;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_search_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(rowView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        tvName = holder.tvName;
        tvMessage = holder.tvMessage;
        tvTime = holder.tvTime;
        tvName.setText(searchResult.get(position).getName());
        tvMessage.setText(searchResult.get(position).getMessage());
        tvTime.setText(searchResult.get(position).getTime());
    }

    @Override
    public int getItemCount() {
        return searchResult.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        View mView;
        TextView tvName;
        TextView tvMessage;
        TextView tvTime;
        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            tvName = (TextView) itemView.findViewById(R.id.recycler_item_search_tv_name);
            tvMessage = (TextView) itemView.findViewById(R.id.recycler_item_search_tv_message);
            tvTime = (TextView) itemView.findViewById(R.id.recycler_item_search_tv_time);
        }
    }
}
