package com.mshvdvskgmail.technoparkmessenger.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mshvdvskgmail.technoparkmessenger.Fragments;
import com.mshvdvskgmail.technoparkmessenger.R;
import com.mshvdvskgmail.technoparkmessenger.Controller;
import com.mshvdvskgmail.technoparkmessenger.R;
import com.mshvdvskgmail.technoparkmessenger.activities.MainActivity;
import com.mshvdvskgmail.technoparkmessenger.adapters.ContactsListAdapter;
import com.mshvdvskgmail.technoparkmessenger.adapters.GroupFilesAdapter;
import com.mshvdvskgmail.technoparkmessenger.adapters.GroupMembersAdapter;
import com.mshvdvskgmail.technoparkmessenger.adapters.ProfileFilesAdapter;
import com.mshvdvskgmail.technoparkmessenger.adapters.SideSelector;
import com.mshvdvskgmail.technoparkmessenger.events.SwitchFragmentEvent;
import com.mshvdvskgmail.technoparkmessenger.models.ContactsListItem;
import com.mshvdvskgmail.technoparkmessenger.models.MemberListItem;
import com.mshvdvskgmail.technoparkmessenger.models.ProfileAttachment;
import com.mshvdvskgmail.technoparkmessenger.network.REST;
import com.mshvdvskgmail.technoparkmessenger.network.model.Chat;
import com.mshvdvskgmail.technoparkmessenger.network.model.ChatUser;
import com.mshvdvskgmail.technoparkmessenger.network.model.User;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import ca.barrenechea.widget.recyclerview.decoration.StickyHeaderDecoration;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by mshvdvsk on 29/03/2017.
 */

public class FragmentGroupsSettings extends Fragment {
    private RecyclerView viewFiles;
    private RecyclerView viewMembers;
    private LinearLayoutManager lm;
    private ArrayList<ProfileAttachment> files;
    private ArrayList<User> members;
    private GroupFilesAdapter adapter;
    private GroupMembersAdapter membersAdapter;
    private AlertDialog alert;
    private Chat activeChat;
    private FrameLayout flAddMember;
    private LinearLayout llMedia;

    public FragmentGroupsSettings() {}

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        activeChat = (Chat) getArguments().getSerializable("chat");

        View view = inflater.inflate(R.layout.fragment_group_settings, container, false);
        setChatInfo(view);
        setAttachedFilesAdapterContent(view);
        setMembersAdapterContent(view);
        setIconsTouchListeners(view);
        return view;
    }

    private void setChatInfo(View rootView){
        TextView name = (TextView) rootView.findViewById(R.id.fragment_group_settings_name);
        name.setText(activeChat.name);
        TextView status = (TextView) rootView.findViewById(R.id.fragment_group_settings_status);
        if(activeChat.admin.equals(Controller.getInstance().getAuth().getUser().unique_id)){
            status.setText("Вы администратор");
        }else{
            status.setText("");
        }
    }

    private void setAttachedFilesAdapterContent(View rootView) {

        viewFiles = (RecyclerView) rootView.findViewById(R.id.fragment_group_settings_rv_files);
        viewFiles.setHasFixedSize(true);
        lm = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        viewFiles.setLayoutManager(lm);

        files = new ArrayList<>();
//        ProfileAttachment dummyObject = new ProfileAttachment();
//        for (int i = 0; i < 10; i++){
//            files.add(dummyObject);
//        }

        TextView mText = (TextView) rootView.findViewById(R.id.fragment_group_settings_tv_files_count);
        mText.setText(""+files.size());


        adapter = new GroupFilesAdapter(files, getContext());
        viewFiles.setAdapter(adapter);
        setOnClickListener(viewFiles);

    }

    private void setMembersAdapterContent(View rootView) {

        viewMembers = (RecyclerView) rootView.findViewById(R.id.fragment_group_settings_rv_members);
        viewMembers.setHasFixedSize(true);

        lm = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        viewMembers.setLayoutManager(lm);

        members = new ArrayList<>();
        for (ChatUser tmp_chat_user: activeChat.users) {
            members.add(tmp_chat_user.User());
        }
/*
        MemberListItem dummyObject1 = new MemberListItem();
        dummyObject1.setName("kek");
        dummyObject1.setOfficePosition("CEO");
        dummyObject1.setOnline(false);

        MemberListItem dummyObject2 = new MemberListItem();
        dummyObject2.setName("lol");
        dummyObject2.setOfficePosition("CEO");
        dummyObject2.setOnline(true);

        MemberListItem dummyObject3 = new MemberListItem();
        dummyObject3.setName("push");
        dummyObject3.setOfficePosition("CEO");
        dummyObject3.setOnline(true);

        MemberListItem dummyObject4 = new MemberListItem();
        dummyObject4.setName("push");
        dummyObject4.setOfficePosition("CEO");
        dummyObject4.setOnline(false);

        MemberListItem dummyObject5 = new MemberListItem();
        dummyObject5.setName("push");
        dummyObject5.setOfficePosition("CEO");
        dummyObject5.setOnline(true);


        members.add(dummyObject1);
        members.add(dummyObject2);
        members.add(dummyObject3);
        members.add(dummyObject4);
        members.add(dummyObject5);
*/
        TextView totalNumber = (TextView) rootView.findViewById(R.id.fragment_group_settings_tv_members_count);
        totalNumber.setText(""+members.size());

        LinearLayout addMembers = (LinearLayout)rootView.findViewById(R.id.fragment_group_settings_ll_add);
        addMembers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getContext()).executeAction("showGroupSettingsMembers", activeChat);
            }
        });

        membersAdapter = new GroupMembersAdapter(members, getContext());
        viewMembers.setAdapter(membersAdapter);
//        setOnClickListener(viewMembers);

    }



    private void setIconsTouchListeners(final View mRootView) {
        FrameLayout edit = (FrameLayout)mRootView.findViewById(R.id.fragment_group_settings_fl_edit);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout name = (LinearLayout)mRootView.findViewById(R.id.fragment_group_settings_name_container);
                LinearLayout nameEdit = (LinearLayout)mRootView.findViewById(R.id.fragment_group_settings_name_edit_container);
                EditText newName = (EditText)mRootView.findViewById(R.id.fragment_group_settings_name_edit_container_et);

                if(name.getVisibility() == View.VISIBLE){
                    name.setVisibility(View.GONE);
                    nameEdit.setVisibility(View.VISIBLE);

                }else{

                    name.setVisibility(View.VISIBLE);
                    nameEdit.setVisibility(View.GONE);
//                    REST.getInstance().chatName();
                    REST.getInstance().chatName(Controller.getInstance().getAuth().getUser().token.session_id, Controller.getInstance().getAuth().getUser().token.token, activeChat.uuid, newName.getText().toString())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new REST.DataSubscriber<Chat>() {

                                @Override
                                public void onData(Chat data) {
                                    activeChat = data;
                                    setChatInfo(mRootView);
                                }
                            });
                }
            }
        });
        FrameLayout mFrame = (FrameLayout)mRootView.findViewById(R.id.fragment_group_settings_fl_back);
        mFrame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                fm.popBackStack();
                /*AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
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
                alert.show();*/
            }
        });

        flAddMember = (FrameLayout) mRootView.findViewById(R.id.fragment_group_settings_fl_add_member);
        flAddMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().postSticky(new SwitchFragmentEvent(Fragments.ADD_MEMBER, null));
                //TODO FragmentAddMember addMember = new FragmentAddMember();
//                getFragmentManager()
//                        .beginTransaction()
//                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
//                        .replace(R.id.container, addMember)
//                        .addToBackStack(null)
//                        .commit();
            }
        });

        llMedia = (LinearLayout) mRootView.findViewById(R.id.fragment_group_settings_ll_media);
        llMedia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().postSticky(new SwitchFragmentEvent(Fragments.MEDIA, null));
                //TODO FragmentMedia addMember = new FragmentMedia();
//                getFragmentManager()
//                        .beginTransaction()
//                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
//                        .replace(R.id.container, addMember)
//                        .addToBackStack(null)
//                        .commit();
//                alert.show();
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
//                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
//                    alertDialog.setTitle("Фокус");
//                    alertDialog.setMessage("Ты выбрал номер " + (position+1) + ", верно?");
//                    alertDialog.setPositiveButton("Но как?!", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//
//                        }
//                    });
//                    alertDialog.setNegativeButton("Не может быть!", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//
//                        }
//                    });
//                    alert = alertDialog.create();
//                    alert.show();
                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {}

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {}
        });
    }
}