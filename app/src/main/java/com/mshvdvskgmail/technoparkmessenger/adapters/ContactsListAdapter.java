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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import ca.barrenechea.widget.recyclerview.decoration.StickyHeaderAdapter;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

/**
 * Created by mshvdvsk on 19/03/2017.
 */

public class ContactsListAdapter extends RecyclerView.Adapter<ContactsListAdapter.ViewHolder> implements SectionIndexer,
        StickyHeaderAdapter<ContactsListAdapter.HeaderHolder> {

    private ArrayList<ContactsListItem> contactsList;
    private View rowView;
    private Context context;
    private ContactsListItem currentItem;

    private String name;
    private String officePosition;
    private boolean isOnline;

    private TextView tvItemName;
    private TextView tvItemPosition;
    private ImageView imageItemOnline;
    private FrameLayout frameSeparator;
    private LayoutInflater mInflater;

    public static final String TAG = ContactsListAdapter.class.getCanonicalName();


    public ContactsListAdapter(ArrayList <ContactsListItem> contactsList, Context context) {
        this.contactsList = contactsList;
        this.context = context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public ContactsListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        rowView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_contacts_item, parent, false);
        ContactsListAdapter.ViewHolder viewHolder = new ContactsListAdapter.ViewHolder(rowView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ContactsListAdapter.ViewHolder holder, int position) {
        if (position+1 == contactsList.size() || contactsList.get(position).getName().charAt(0) !=
                contactsList.get(position+1).getName().charAt(0)){
            frameSeparator = holder.mFrameLayout;
            frameSeparator.setVisibility(View.GONE);
        } else {
            frameSeparator = holder.mFrameLayout;
            frameSeparator.setVisibility(View.VISIBLE);
        }

        ImageView profileIcon = (ImageView) holder.mView.findViewById(R.id.profile_icon);
        Picasso.with(context).load(R.drawable.pushkin).transform(new RoundedCornersTransformation(360,0)).into(profileIcon);

        currentItem = contactsList.get(position);

        name = currentItem.getName();
        officePosition = currentItem.getOfficePosition();
        isOnline = currentItem.isOnline();

        tvItemName = (TextView) holder.mView.findViewById(R.id.name);
        tvItemPosition = (TextView) holder.mView.findViewById(R.id.office_position);
        imageItemOnline = (ImageView) holder.mView.findViewById(R.id.online_dot);

        tvItemName.setText(name);
        tvItemPosition.setText(officePosition);

        if (isOnline) {
            imageItemOnline.setVisibility(View.VISIBLE);
        } else imageItemOnline.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return contactsList.size();
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
        return (int) (contactsList.size() * ((float)i/(float)getSections().length));
    }

    public int getSectionForPosition(int i) {
        return 0;
    }



    @Override
    public long getHeaderId(int position) {
//        if (position == 0) { // don't show header for first item
//            return StickyHeaderDecoration.NO_HEADER_ID;
//        }
        return contactsList.get(position).getName().subSequence(0, 1).charAt(0);
    }

    @Override
    public HeaderHolder onCreateHeaderViewHolder(ViewGroup parent) {
        final View view = mInflater.inflate(R.layout.header_test, parent, false);
        return new HeaderHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(HeaderHolder viewHolder, int position) {
        viewHolder.header.setText(""+contactsList.get(position).getName().charAt(0));
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

    static class HeaderHolder extends RecyclerView.ViewHolder {
        public TextView header;

        public HeaderHolder(View itemView) {
            super(itemView);
            header = (TextView) itemView.findViewById(R.id.text_item);
        }
    }

}