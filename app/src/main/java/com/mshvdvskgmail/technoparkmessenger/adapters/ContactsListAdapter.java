package com.mshvdvskgmail.technoparkmessenger.adapters;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.mshvdvskgmail.technoparkmessenger.Controller;
import com.mshvdvskgmail.technoparkmessenger.R;
import com.mshvdvskgmail.technoparkmessenger.fragments.FragmentProfile;
import com.mshvdvskgmail.technoparkmessenger.TechnoparkApp;
import com.mshvdvskgmail.technoparkmessenger.activities.MainActivity;
import com.mshvdvskgmail.technoparkmessenger.fragments.FragmentMainFourTabScreen;
import com.mshvdvskgmail.technoparkmessenger.fragments.FragmentProfile;
import com.mshvdvskgmail.technoparkmessenger.models.ContactsListItem;
import com.mshvdvskgmail.technoparkmessenger.network.REST;
import com.mshvdvskgmail.technoparkmessenger.network.model.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import ca.barrenechea.widget.recyclerview.decoration.StickyHeaderAdapter;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mshvdvsk on 19/03/2017.
 */

public class ContactsListAdapter extends RecyclerView.Adapter<ContactsListAdapter.ViewHolder> implements SectionIndexer,
        StickyHeaderAdapter<ContactsListAdapter.HeaderHolder> {

    private ArrayList<User> contactsList;
    private View rowView;
    private Context context;
    private User currentItem;

    private String name;
    private String officePosition;
    private boolean isOnline;

    private TextView tvName;
    private TextView tvPosition;
    private ImageView imageOnline;
    private FrameLayout frameSeparator;
    private ImageView imagePicture;
    private LayoutInflater mInflater;

    private FragmentManager fManager;


    public static final String TAG = ContactsListAdapter.class.getCanonicalName();

    public Context getContext(){
        return context;
    }

    public ContactsListAdapter(ArrayList <User> contactsList, Context context) {
        this.contactsList = contactsList;
        this.context = context;
        this.fManager = fManager;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public ContactsListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        rowView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_contacts_item, parent, false);
        ContactsListAdapter.ViewHolder viewHolder = new ContactsListAdapter.ViewHolder(rowView);
        viewHolder.context = context;
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ContactsListAdapter.ViewHolder holder, int position) {
        if (position+1 == contactsList.size() || contactsList.get(position).cn.charAt(0) !=
                contactsList.get(position+1).cn.charAt(0)){
            frameSeparator = holder.mFrameLayout;
        if (position+1 == contactsList.size() || contactsList.get(position).getName().charAt(0) !=
                contactsList.get(position+1).getName().charAt(0)){
            frameSeparator = holder.frameSeparator;
            frameSeparator.setVisibility(View.GONE);
        } else {
            frameSeparator = holder.frameSeparator;
            frameSeparator.setVisibility(View.VISIBLE);
        }

        imagePicture = holder.imagePicture;
        Picasso.with(context).load(R.drawable.pushkin).transform(new RoundedCornersTransformation(360,0)).into(imagePicture);

        currentItem = contactsList.get(position);
        holder.setContact(currentItem);
        name = currentItem.cn;
        officePosition = currentItem.title;
//        isOnline = currentItem.isOnline();

        tvName = holder.tvName;
        tvPosition = holder.tvPosition;
        imageOnline = holder.imageOnline;

        tvName.setText(name);
        tvPosition.setText(officePosition);

        REST.getInstance().user_status(Controller.getInstance().getAuth().getUser().token.session_id, Controller.getInstance().getAuth().getUser().token.token, currentItem.id).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.body() == "1") {
                    imageItemOnline.setVisibility(View.VISIBLE);
                } else imageItemOnline.setVisibility(View.GONE);
            }
//        if (isOnline) {
//            imageOnline.setVisibility(View.VISIBLE);
//        } else imageOnline.setVisibility(View.GONE);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentProfile profile = new FragmentProfile();
                fManager.beginTransaction()
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .replace(R.id.container, profile)
                        .addToBackStack(null)
                        .commit();
            }
        });

    }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                imageItemOnline.setVisibility(View.GONE);
            }
        });


        ImageView profileIcon = (ImageView) holder.mView.findViewById(R.id.profile_icon);
        Picasso.with(context)
                .load("http://213.247.249.84/basic/web/index.php?r=messages/json/avatar&name="+currentItem.name)
//                .load("http://213.247.249.84/basic/web/index.php?r=messages/json/avatar&name=testme1")
//                    .networkPolicy(NetworkPolicy.OFFLINE)
                .placeholder(R.drawable.pushkin)
                .error(R.drawable.pushkin)
                .centerCrop()
                .resize(300, 300)
                .onlyScaleDown()
                .transform(new RoundedCornersTransformation(360,0)).into(profileIcon);

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
        return contactsList.get(position).cn.subSequence(0, 1).charAt(0);
    }

    @Override
    public HeaderHolder onCreateHeaderViewHolder(ViewGroup parent) {
        final View view = mInflater.inflate(R.layout.header_contacts, parent, false);
        return new HeaderHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(HeaderHolder viewHolder, int position) {
        viewHolder.header.setText(""+contactsList.get(position).cn.charAt(0));

    }




    public static class ViewHolder extends RecyclerView.ViewHolder {

        View mView;
        TextView tvName;
        TextView tvPosition;
        ImageView imageOnline;
        ImageView imagePicture;
        FrameLayout frameSeparator;
        FrameLayout mFrameLayout;
        User contact;
        Context context;

        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            tvName = (TextView) itemView.findViewById(R.id.recycler_item_contacts_tv_name);
            tvPosition = (TextView) itemView.findViewById(R.id.recycler_item_contacts_tv_office_position);
            imagePicture = (ImageView) itemView.findViewById(R.id.recycler_item_contacts_image_picture);
            imageOnline = (ImageView) itemView.findViewById(R.id.recycler_item_contacts_image_online);
            frameSeparator = (FrameLayout) itemView.findViewById(R.id.recycler_item_contacts_fl_separator);
            mFrameLayout = (FrameLayout) itemView.findViewById(R.id.item_separator);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((MainActivity) context).executeAction("showProfile", contact);
                }
            });
        }

        public void setContact(User user){
            contact = user;
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