package com.mshvdvskgmail.technoparkmessenger.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.mshvdvskgmail.technoparkmessenger.Fragments;
import com.mshvdvskgmail.technoparkmessenger.R;
import com.mshvdvskgmail.technoparkmessenger.events.SwitchFragmentEvent;
import com.mshvdvskgmail.technoparkmessenger.helpers.ArgsBuilder;
import com.mshvdvskgmail.technoparkmessenger.network.model.User;
import com.mshvdvskgmail.technoparkmessenger.view.ContactItemView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import ca.barrenechea.widget.recyclerview.decoration.StickyHeaderAdapter;

/**
 * Created by mshvdvsk on 19/03/2017.
 */

public class ContactsListAdapter extends RecyclerView.Adapter<ContactsListAdapter.ViewHolder> implements SectionIndexer,
        StickyHeaderAdapter<ContactsListAdapter.HeaderHolder> {
    public static final String TAG = ContactsListAdapter.class.getCanonicalName();

    private Context context;
    private LayoutInflater mInflater;
    private List<User> users = new ArrayList<>();

    public ContactsListAdapter(Context context) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
    }

    public void setData(List<User> users){
        this.users = users;
        sort();
        notifyDataSetChanged();
    }

    public void addData(List<User> users){
        this.users.addAll(users);
        sort();
        notifyDataSetChanged();
    }

    private void sort(){
        Collections.sort(users, new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                return o1.cn.substring(0,1).toUpperCase().compareTo(o2.cn.substring(0,1).toUpperCase());
            }
        });
        /* do nothing */
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(new ContactItemView(context));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.getView().setData(users.get(position));
        holder.getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().postSticky(new SwitchFragmentEvent(Fragments.PROFILE, ArgsBuilder.create().user(users.get(holder.getAdapterPosition())).bundle()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public Object[] getSections() {
        String[] chars = new String[SideSelector.ALPHABET.length];
        for (int i = 0; i < SideSelector.ALPHABET.length; i++) {
            chars[i] = String.valueOf(SideSelector.ALPHABET[i]);
        }
        return chars;
    }


    public int getPositionForSection(int i) {
        Log.d(TAG, "getPositionForSection " + i);
        return (int) (users.size() * ((float)i/(float)getSections().length));
    }

    public int getSectionForPosition(int i) {
        return 0;
    }



    @Override
    public long getHeaderId(int position) {
//        if (position == 0) { // don't show header for first item
//            return StickyHeaderDecoration.NO_HEADER_ID;
//        }
        return users.get(position).cn.substring(0, 1).toUpperCase().charAt(0);
    }

    @Override
    public HeaderHolder onCreateHeaderViewHolder(ViewGroup parent) {
        final View view = mInflater.inflate(R.layout.header_contacts, parent, false);
        return new HeaderHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(HeaderHolder viewHolder, int position) {
        Log.d(TAG, "onBindHeaderViewHolder " + position);
        viewHolder.header.setText(users.get(position).cn.substring(0, 1).toUpperCase());
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(ContactItemView itemView) {
            super(itemView);
        }

        public ContactItemView getView(){
            return (ContactItemView)itemView;
        }
    }

    static class HeaderHolder extends RecyclerView.ViewHolder {
        public TextView header;

        public HeaderHolder(View itemView) {
            super(itemView);
            header = (TextView) itemView.findViewById(R.id.view_header_contacts_tx_text);
        }
    }

}