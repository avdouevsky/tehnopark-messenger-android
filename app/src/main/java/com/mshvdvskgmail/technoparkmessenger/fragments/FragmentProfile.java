package com.mshvdvskgmail.technoparkmessenger.fragments;

import android.content.DialogInterface;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.transition.Transition;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.mshvdvskgmail.technoparkmessenger.R;
import com.mshvdvskgmail.technoparkmessenger.adapters.ProfileFilesAdapter;
import com.mshvdvskgmail.technoparkmessenger.models.ProfileAttachment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

/**
 * Created by mshvdvsk on 03/02/2017.
 */

public class FragmentProfile extends Fragment {
    private View mRootView;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private ArrayList<ProfileAttachment> files;
    private ProfileFilesAdapter mAdapter;
    private AlertDialog alert;

    public FragmentProfile() {}

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mRootView = inflater.inflate(R.layout.fragment_profile, container, false);
        setAdapterContent(mRootView);
        setIconsTouchListeners(mRootView);
        return mRootView;
    }

    private void setAdapterContent(View mRootView) {
        mRecyclerView = (RecyclerView) mRootView.findViewById(R.id.fragment_profile_rv_files);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        files = new ArrayList<>();
        ProfileAttachment dummyObject = new ProfileAttachment();
        for (int i = 0; i < 10; i++){
            files.add(dummyObject);
        }
        mAdapter = new ProfileFilesAdapter(files, getContext());
        mRecyclerView.setAdapter(mAdapter);
        setOnClickListener(mRecyclerView);

        ImageView profileIcon = (ImageView) mRootView.findViewById(R.id.fragment_profile_image_profile_picture);
        Picasso.with(getContext()).load(R.drawable.pushkin).transform(new RoundedCornersTransformation(360,0)).into(profileIcon);

    }

    private void setIconsTouchListeners(View mRootView) {
        FrameLayout flBackButton = (FrameLayout) mRootView.findViewById(R.id.fragment_profile_fl_back);
        //flBackButton.setVisibility(View.GONE);
        flBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("blablalba", "hello");
                FragmentMainFourTabScreen main = new FragmentMainFourTabScreen();
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .replace(R.id.container, main)
                        .addToBackStack(null)
                        .commit();
            }
        });

        ImageView mImage = (ImageView) mRootView.findViewById(R.id.fragment_profile_image_message);
        mImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
                alertDialog.setTitle("ОК, СПАСИБО");
                alertDialog.setMessage("Все работает ок, не так ли?");
                alertDialog.setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alertDialog.setNegativeButton("Не знаю", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alert = alertDialog.create();
                alert.show();
            }
        });

        mImage = (ImageView) mRootView.findViewById(R.id.fragment_profile_image_call);
        mImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
                alertDialog.setTitle("ОК, СПАСИБО");
                alertDialog.setMessage("Все работает ок, не так ли?");
                alertDialog.setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alertDialog.setNegativeButton("Не знаю", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alert = alertDialog.create();
                alert.show();
            }
        });

        TextView mText = (TextView) mRootView.findViewById(R.id.fragment_profile_tv_items_count);
        mText.setText(""+files.size());

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
                    // потом поможет определить выбранный элемент
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                    alertDialog.setTitle("Фокус");
                    alertDialog.setMessage("Ты выбрал номер " + (position+1) + ", верно?");
                    alertDialog.setPositiveButton("Но как?!", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    alertDialog.setNegativeButton("Не может быть!", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    alert = alertDialog.create();
                    alert.show();
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
