package com.mshvdvskgmail.technoparkmessenger.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.transition.Transition;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.TransitionInflater;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.mshvdvskgmail.technoparkmessenger.Controller;
import com.mshvdvskgmail.technoparkmessenger.Fragments;
import com.mshvdvskgmail.technoparkmessenger.R;
import com.mshvdvskgmail.technoparkmessenger.adapters.ProfileFilesAdapter;
import com.mshvdvskgmail.technoparkmessenger.events.SwitchFragmentEvent;
import com.mshvdvskgmail.technoparkmessenger.helpers.ArgsBuilder;
import com.mshvdvskgmail.technoparkmessenger.helpers.ICommand;
import com.mshvdvskgmail.technoparkmessenger.network.REST;
import com.mshvdvskgmail.technoparkmessenger.network.model.Attachment;
import com.mshvdvskgmail.technoparkmessenger.network.model.Chat;
import com.mshvdvskgmail.technoparkmessenger.network.model.User;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;
import rx.android.schedulers.AndroidSchedulers;

import static com.mshvdvskgmail.technoparkmessenger.R.id.*;

/**
 * Created by mshvdvsk on 03/02/2017.
 */

public class FragmentProfile extends BaseFragment {
    private View mRootView;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private ProfileFilesAdapter mAdapter;
    private TextView tvCount;

    private ImageView imOnline;
    private User user;

    public FragmentProfile() {}

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //user = (User) getArguments().getSerializable("user");
        user = ArgsBuilder.create(getArguments()).user();

        mRootView = inflater.inflate(R.layout.fragment_profile, container, false);

        tvCount = (TextView) mRootView.findViewById(R.id.fragment_profile_tv_items_count);

        TextView name = (TextView)mRootView.findViewById(fragment_profile_tv_name);
        name.setText(user.cn);

        TextView phone = (TextView)mRootView.findViewById(R.id.fragment_profile_tv_phone);
        phone.setText(user.mobile + " " + user.mail);

        TextView title = (TextView)mRootView.findViewById(fragment_profile_tv_title);
        title.setText(user.description);

        imOnline = (ImageView) mRootView.findViewById(R.id.imOnline);
        imOnline.setVisibility(user.online == 1 ? View.VISIBLE : View.GONE);

        setAdapterContent(mRootView);
        setIconsTouchListeners(mRootView);
        return mRootView;
    }

    private void setAdapterContent(View mRootView) {
        mRecyclerView = (RecyclerView) mRootView.findViewById(R.id.fragment_profile_rv_files);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        REST.getInstance().get_user_attachments(Controller.getInstance().getAuth().getUser().token, user)
                .subscribe(new REST.DataSubscriber<List<Attachment>>() {
                    @Override
                    public void onData(List<Attachment> data) {
                        mAdapter.setData(data);
                        tvCount.setText(""+ mAdapter.getItemCount());
                    }
                });

        mAdapter = new ProfileFilesAdapter(getContext());
        mRecyclerView.setAdapter(mAdapter);
        setOnClickListener(mRecyclerView);

        ImageView profileIcon = (ImageView) mRootView.findViewById(R.id.fragment_profile_image_profile_picture);
        if(user.avatar != null)
            Picasso.with(getContext())
                    .load(user.avatar)
                    .resizeDimen(R.dimen.chat_item_avatar_size,R.dimen.chat_item_avatar_size)
                    .centerCrop()
                    .placeholder(R.drawable.icon_user)
                    .error(R.drawable.icon_user).transform(new RoundedCornersTransformation(360,0)).into(profileIcon);

        mAdapter.setClickListener(new ICommand<Attachment>() {
            @Override
            public void exec(Attachment data) {
                REST.getInstance().get_attachment(Controller.getInstance().getAuth().getUser().token, data)
                        .subscribe(new REST.DataSubscriber<Attachment>() {
                            @Override
                            public void onData(Attachment data) {
                                if(data.url != null){
                                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(data.url));
                                    startActivity(browserIntent);
                                }
                            }
                        });
            }
        });
    }

    private void setIconsTouchListeners(View mRootView) {
        FrameLayout flBackButton = (FrameLayout) mRootView.findViewById(R.id.fragment_profile_fl_back);
        //flBackButton.setVisibility(View.GONE);
        flBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isAdded()) getActivity().onBackPressed();
            }
        });

        ImageView mImage = (ImageView) mRootView.findViewById(R.id.fragment_profile_image_message);
        mImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                REST.getInstance().chat(Controller.getInstance().getAuth().getUser().token.session_id, Controller.getInstance().getAuth().getUser().token.token, user.id, "")
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new REST.DataSubscriber<Chat>(){
                            @Override
                            public void onData(Chat data) {
                                EventBus.getDefault().postSticky(new SwitchFragmentEvent(Fragments.CHAT, ArgsBuilder.create().chat(data).bundle(), SwitchFragmentEvent.Direction.REPLACE));
                            }
                        });
            }
        });

        mImage = (ImageView) mRootView.findViewById(R.id.fragment_profile_image_call);
        mImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });




        final FragmentProfile a = this;
        final FragmentProfilePicture b = new FragmentProfilePicture();

        mImage = (ImageView) mRootView.findViewById(R.id.fragment_profile_image_profile_picture);

        final ImageView c = mImage;

        mImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {



                    // Inflate transitions to apply
                    Transition changeTransform = TransitionInflater.from(getContext()).
                            inflateTransition(R.transition.transition_of_circle_images);
                    Transition explodeTransform = TransitionInflater.from(getContext()).
                            inflateTransition(android.R.transition.explode);

                    // Setup exit transition on first fragment
                    a.setSharedElementReturnTransition(changeTransform);
//                    a.setExitTransition(explodeTransform);

                    // Setup enter transition on second fragment
                    b.setSharedElementEnterTransition(changeTransform);
//                    b.setEnterTransition(explodeTransform);

                    // Add second fragment by replacing first
                    FragmentTransaction ft = getFragmentManager().beginTransaction()
                            .replace(R.id.container, b)
                            .addToBackStack("transaction")
                            .addSharedElement(c, "profile");
                    // Apply the transaction
                    ft.commit();
                }
                else {
                    // Code to run on older devices
                }
            }
        });

    }

    private void setOnClickListener(RecyclerView mRecyclerView){
        final GestureDetector mGestureDetector =
                new GestureDetector(getActivity(), new GestureDetector.SimpleOnGestureListener(){
                    @Override
                    public boolean onSingleTapUp(MotionEvent e) {
                        return true;
                    }
                });

        mRecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
                View child = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());

                if (child != null && mGestureDetector.onTouchEvent(motionEvent)) {
                    int position = recyclerView.getChildLayoutPosition(child); // пока не нужно, но
                    //todo  потом поможет определить выбранный элемент

                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {}

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {}
        });
    }

//    public void take_me_back (View v) {
//        FragmentMainFourTabScreen main = new FragmentMainFourTabScreen();
//        getActivity().getSupportFragmentManager()
//                .beginTransaction()
//                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
//                .replace(R.id.container, main)
//                .addToBackStack(null)
//                .commit();
//    }
}
