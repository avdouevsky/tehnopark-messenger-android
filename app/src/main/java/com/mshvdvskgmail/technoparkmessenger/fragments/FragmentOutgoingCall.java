package com.mshvdvskgmail.technoparkmessenger.fragments;

import android.content.DialogInterface;
import android.graphics.Typeface;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.mshvdvskgmail.technoparkmessenger.R;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

/**
 * Created by mshvdvsk on 09/03/2017.
 */

public class FragmentOutgoingCall extends Fragment {
    private View mRootView;
    private AlertDialog alert;
    private FrameLayout frameLoudspeakers;
    private FrameLayout frameMessage;
    private FrameLayout frameMute;
    private FrameLayout frameEndCall;
    private FrameLayout dotOne;
    private FrameLayout dotTwo;
    private FrameLayout dotThree;
    private FrameLayout dotFour;
    private FrameLayout dotFive;
    private FrameLayout dotSix;
    private ImageView mIconLoudspeaker;
    private ImageView mIconMute;
    private TextView mMuteText;
    private boolean frameLoudspeakersPressed;
    private boolean frameMutePressed;
    private Handler handler;
    private int animationStatus = 0;



    public FragmentOutgoingCall() {}

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_call_outgoing, container, false);
        inflatePicture(mRootView);
        addListeners(mRootView);
        animateDots();
        return mRootView;
    }

    private void inflatePicture(View mRootView) {
        ImageView profileIcon = (ImageView) mRootView.findViewById(R.id.fragment_call_outgoing_image_picture);
        Picasso.with(getContext()).load(R.drawable.pushkin).transform(new RoundedCornersTransformation(360,0)).into(profileIcon);
    }

    private void addListeners(final View mRootView) {

        frameLoudspeakers = (FrameLayout) mRootView.findViewById(R.id.fragment_call_outgoing_fl_loudspeaker);
        frameLoudspeakers.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                if(!frameLoudspeakersPressed) {
                    mIconLoudspeaker = (ImageView) mRootView.findViewById(R.id.fragment_call_outgoing_image_loudspeaker);
                    mIconLoudspeaker.setImageResource(R.drawable.loudspeaker_no_sound);
                    frameLoudspeakersPressed = true;
                } else if (frameLoudspeakersPressed) {
                    mIconLoudspeaker = (ImageView) mRootView.findViewById(R.id.fragment_call_outgoing_image_loudspeaker);
                    mIconLoudspeaker.setImageResource(R.drawable.loudspeaker);
                    frameLoudspeakersPressed = false;
                }
            }
        });

        frameMessage = (FrameLayout) mRootView.findViewById(R.id.fragment_call_outgoing_fl_message);
        frameMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*  show toast reaction */

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
//                Intent myIntent = new Intent(MainActivity.this, ActivityProfile.class);
//                startActivity(myIntent);
            }
        });

        frameMute = (FrameLayout) mRootView.findViewById(R.id.fragment_call_outgoing_fl_mute);
        frameMute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!frameMutePressed) {
                    mIconMute = (ImageView) mRootView.findViewById(R.id.fragment_call_outgoing_image_mute);
                    mMuteText = (TextView) mRootView.findViewById(R.id.fragment_call_outgoing_tv_mute);
                    mMuteText.setText(R.string.incoming_call_third_icon_on);
                    mIconMute.setImageResource(R.drawable.unmute);
                    frameMutePressed = true;
                } else if (frameMutePressed) {
                    mIconMute = (ImageView) mRootView.findViewById(R.id.fragment_call_outgoing_image_mute);
                    mMuteText = (TextView) mRootView.findViewById(R.id.fragment_call_outgoing_tv_mute);
                    mMuteText.setText(R.string.incoming_call_third_icon);
                    mIconMute.setImageResource(R.drawable.mute);
                    frameMutePressed = false;
                }
            }
        });


        frameEndCall = (FrameLayout) mRootView.findViewById(R.id.fragment_call_outgoing_fl_call_end);
        frameEndCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*  show toast reaction */

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
//                Intent myIntent = new Intent(MainActivity.this, ActivityProfile.class);
//                startActivity(myIntent);
            }
        });
    }

    private void animateDots() {
        handler = new Handler();

        dotOne = (FrameLayout) mRootView.findViewById(R.id.fragment_call_outgoing_fl_dot_one);
        dotTwo = (FrameLayout) mRootView.findViewById(R.id.fragment_call_outgoing_fl_dot_two);
        dotThree = (FrameLayout) mRootView.findViewById(R.id.fragment_call_outgoing_fl_dot_three);
        dotFour = (FrameLayout) mRootView.findViewById(R.id.fragment_call_outgoing_fl_dot_four);
        dotFive = (FrameLayout) mRootView.findViewById(R.id.fragment_call_outgoing_fl_dot_five);
        dotSix = (FrameLayout) mRootView.findViewById(R.id.fragment_call_outgoing_fl_dot_six);

        handler.postDelayed((new Runnable() {
            @Override
            public void run() {

                TransitionDrawable transition;

                switch (animationStatus) {
                    case 0:
                        dotOne.setBackground(getResources().getDrawable(R.drawable.transition_dot_50_to_10));
                        transition = (TransitionDrawable) dotOne.getBackground();
                        transition.startTransition(120);

                        dotThree.setBackground(getResources().getDrawable(R.drawable.transition_dot_10_to_50));
                        transition = (TransitionDrawable) dotThree.getBackground();
                        transition.startTransition(120);

                        dotFour.setBackground(getResources().getDrawable(R.drawable.transition_dot_10_to_50));
                        transition = (TransitionDrawable) dotFour.getBackground();
                        transition.startTransition(120);

                        dotSix.setBackground(getResources().getDrawable(R.drawable.transition_dot_50_to_10));
                        transition = (TransitionDrawable) dotSix.getBackground();
                        transition.startTransition(120);

                        animationStatus = 1;
                        handler.postDelayed(this, 400);
                        break;
                    case 1:
                        dotOne.setBackground(getResources().getDrawable(R.drawable.transition_dot_10_to_20));
                        transition = (TransitionDrawable) dotOne.getBackground();
                        transition.startTransition(120);

                        dotTwo.setBackground(getResources().getDrawable(R.drawable.transition_dot_20_to_50));
                        transition = (TransitionDrawable) dotTwo.getBackground();
                        transition.startTransition(120);

                        dotThree.setBackground(getResources().getDrawable(R.drawable.transition_dot_50_to_20));
                        transition = (TransitionDrawable) dotThree.getBackground();
                        transition.startTransition(120);

                        dotFour.setBackground(getResources().getDrawable(R.drawable.transition_dot_50_to_20));
                        transition = (TransitionDrawable) dotFour.getBackground();
                        transition.startTransition(120);


                        dotFive.setBackground(getResources().getDrawable(R.drawable.transition_dot_20_to_50));
                        transition = (TransitionDrawable) dotFive.getBackground();
                        transition.startTransition(120);

                        dotSix.setBackground(getResources().getDrawable(R.drawable.transition_dot_10_to_20));
                        transition = (TransitionDrawable) dotSix.getBackground();
                        transition.startTransition(120);

                        animationStatus = 2;
                        handler.postDelayed(this, 400);
                        break;
                    case 2:
                        dotOne.setBackground(getResources().getDrawable(R.drawable.transition_dot_20_to_50));
                        transition = (TransitionDrawable) dotOne.getBackground();
                        transition.startTransition(120);

                        dotTwo.setBackground(getResources().getDrawable(R.drawable.transition_dot_50_to_20));
                        transition = (TransitionDrawable) dotTwo.getBackground();
                        transition.startTransition(120);

                        dotThree.setBackground(getResources().getDrawable(R.drawable.transntion_dot_20_to_10));
                        transition = (TransitionDrawable) dotThree.getBackground();
                        transition.startTransition(120);

                        dotFour.setBackground(getResources().getDrawable(R.drawable.transntion_dot_20_to_10));
                        transition = (TransitionDrawable) dotFour.getBackground();
                        transition.startTransition(120);

                        dotFive.setBackground(getResources().getDrawable(R.drawable.transition_dot_50_to_20));
                        transition = (TransitionDrawable) dotFive.getBackground();
                        transition.startTransition(120);

                        dotSix.setBackground(getResources().getDrawable(R.drawable.transition_dot_20_to_50));
                        transition = (TransitionDrawable) dotSix.getBackground();
                        transition.startTransition(120);

                        animationStatus = 0;
                        handler.postDelayed(this, 800);
                        break;
                }
            }
        }), 800);
    }

}
