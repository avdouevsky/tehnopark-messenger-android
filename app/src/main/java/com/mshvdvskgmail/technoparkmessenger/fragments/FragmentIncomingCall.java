package com.mshvdvskgmail.technoparkmessenger.fragments;

import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.mshvdvskgmail.technoparkmessenger.R;
import com.mshvdvskgmail.technoparkmessenger.events.SipServiceEvent;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

/**
 * Created by mshvdvsk on 09/03/2017.
 */

public class FragmentIncomingCall extends BaseFragment {
    private View mRootView;
    private AlertDialog alert;
    private FrameLayout frameDeny;
    private FrameLayout frameMessage;
    private FrameLayout frameAnswer;
    private FrameLayout dotOne;
    private FrameLayout dotTwo;
    private FrameLayout dotThree;
    private FrameLayout dotFour;
    private FrameLayout dotFive;
    private FrameLayout dotSix;
    private Handler handler;
    private int animationStatus = 0;

    public FragmentIncomingCall() {}

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_call_incoming, container, false);
        inflatePicture(mRootView);
        addListeners(mRootView);
        animateDots();
        return mRootView;
    }

    private void inflatePicture(View mRootView) {
        ImageView profileIcon = (ImageView) mRootView.findViewById(R.id.fragment_call_incoming_image_picture);
        Picasso.with(getContext()).load(R.drawable.pushkin).transform(new RoundedCornersTransformation(360,0)).into(profileIcon);
    }

    private void addListeners(View mRootView) {
        frameDeny = (FrameLayout) mRootView.findViewById(R.id.fragment_call_incoming_fl_deny);
        frameDeny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //EventBus.getDefault().postSticky(new SwitchFragmentEvent(Fragments.DENIED_CALL, null));
                EventBus.getDefault().postSticky(new SipServiceEvent(SipServiceEvent.Type.HANGUP));
            }
        });

        frameMessage = (FrameLayout) mRootView.findViewById(R.id.fragment_call_incoming_fl_message);
        frameMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        frameAnswer = (FrameLayout) mRootView.findViewById(R.id.fragment_call_incoming_fl_answer);
        frameAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().postSticky(new SipServiceEvent(SipServiceEvent.Type.ANSWER));
            }
        });
    }

    private void animateDots() {
        handler = new Handler();

        dotOne = (FrameLayout) mRootView.findViewById(R.id.fragment_call_incoming_fl_dot_one);
        dotTwo = (FrameLayout) mRootView.findViewById(R.id.fragment_call_incoming_fl_dot_two);
        dotThree = (FrameLayout) mRootView.findViewById(R.id.fragment_call_incoming_fl_dot_three);
        dotFour = (FrameLayout) mRootView.findViewById(R.id.fragment_call_incoming_fl_dot_four);
        dotFive = (FrameLayout) mRootView.findViewById(R.id.fragment_call_incoming_fl_dot_five);
        dotSix = (FrameLayout) mRootView.findViewById(R.id.fragment_call_incoming_fl_dot_six);

        handler.postDelayed((new Runnable() {
            @Override
            public void run() {
                if(!isAdded()) return;

                TransitionDrawable transition;

                switch (animationStatus) {
                    case 0:
                        dotOne.setBackground(getResources().getDrawable(R.drawable.transition_dot_10_to_50));
                        transition = (TransitionDrawable) dotOne.getBackground();
                        transition.startTransition(120);

                        dotThree.setBackground(getResources().getDrawable(R.drawable.transition_dot_50_to_10));
                        transition = (TransitionDrawable) dotThree.getBackground();
                        transition.startTransition(120);

                        dotFour.setBackground(getResources().getDrawable(R.drawable.transition_dot_50_to_10));
                        transition = (TransitionDrawable) dotFour.getBackground();
                        transition.startTransition(120);

                        dotSix.setBackground(getResources().getDrawable(R.drawable.transition_dot_10_to_50));
                        transition = (TransitionDrawable) dotSix.getBackground();
                        transition.startTransition(120);

                        animationStatus = 1;
                        handler.postDelayed(this, 400);
                        break;
                    case 1:
                        dotOne.setBackground(getResources().getDrawable(R.drawable.transition_dot_50_to_20));
                        transition = (TransitionDrawable) dotOne.getBackground();
                        transition.startTransition(120);

                        dotTwo.setBackground(getResources().getDrawable(R.drawable.transition_dot_20_to_50));
                        transition = (TransitionDrawable) dotTwo.getBackground();
                        transition.startTransition(120);

                        dotThree.setBackground(getResources().getDrawable(R.drawable.transition_dot_10_to_20));
                        transition = (TransitionDrawable) dotThree.getBackground();
                        transition.startTransition(120);

                        dotFour.setBackground(getResources().getDrawable(R.drawable.transition_dot_10_to_20));
                        transition = (TransitionDrawable) dotFour.getBackground();
                        transition.startTransition(120);


                        dotFive.setBackground(getResources().getDrawable(R.drawable.transition_dot_20_to_50));
                        transition = (TransitionDrawable) dotFive.getBackground();
                        transition.startTransition(120);

                        dotSix.setBackground(getResources().getDrawable(R.drawable.transition_dot_50_to_20));
                        transition = (TransitionDrawable) dotSix.getBackground();
                        transition.startTransition(120);

                        animationStatus = 2;
                        handler.postDelayed(this, 400);
                        break;
                    case 2:
                        dotOne.setBackground(getResources().getDrawable(R.drawable.transntion_dot_20_to_10));
                        transition = (TransitionDrawable) dotOne.getBackground();
                        transition.startTransition(120);

                        dotTwo.setBackground(getResources().getDrawable(R.drawable.transition_dot_50_to_20));
                        transition = (TransitionDrawable) dotTwo.getBackground();
                        transition.startTransition(120);

                        dotThree.setBackground(getResources().getDrawable(R.drawable.transition_dot_20_to_50));
                        transition = (TransitionDrawable) dotThree.getBackground();
                        transition.startTransition(120);

                        dotFour.setBackground(getResources().getDrawable(R.drawable.transition_dot_20_to_50));
                        transition = (TransitionDrawable) dotFour.getBackground();
                        transition.startTransition(120);

                        dotFive.setBackground(getResources().getDrawable(R.drawable.transition_dot_50_to_20));
                        transition = (TransitionDrawable) dotFive.getBackground();
                        transition.startTransition(120);

                        dotSix.setBackground(getResources().getDrawable(R.drawable.transntion_dot_20_to_10));
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
