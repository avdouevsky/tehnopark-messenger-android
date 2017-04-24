package com.mshvdvskgmail.technoparkmessenger.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.mshvdvskgmail.technoparkmessenger.R;
import com.mshvdvskgmail.technoparkmessenger.Controller;
import com.mshvdvskgmail.technoparkmessenger.helpers.ArgsBuilder;
import com.mshvdvskgmail.technoparkmessenger.network.REST;
import com.mshvdvskgmail.technoparkmessenger.network.model.Attachment;
import com.mshvdvskgmail.technoparkmessenger.network.model.Chat;
import com.mshvdvskgmail.technoparkmessenger.view.MediaListView;
import com.mshvdvskgmail.technoparkmessenger.view.MemberListView;

import java.util.List;

/**
 * Created by mshvdvsk on 29/03/2017.
 *
 */
public class FragmentGroupsSettings extends Fragment {
    private final static String TAG = FragmentGroupsSettings.class.toString();

    private MediaListView viewMediaList;
    private MemberListView viewMemberList;

    private TextView tvGroupName;
    private TextView tvGroupStatus;
    private EditText editView;

    private ImageView imageViewEdit;

    private Chat chat;

    public FragmentGroupsSettings() {}

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_group_settings, container, false);

        viewMediaList = (MediaListView) root.findViewById(R.id.viewMediaList);
        viewMemberList = (MemberListView) root.findViewById(R.id.viewMemberList);

        tvGroupName = (TextView) root.findViewById(R.id.fragment_group_settings_name);
        tvGroupStatus = (TextView) root.findViewById(R.id.fragment_group_settings_status);
        editView = (EditText) root.findViewById(R.id.fragment_group_settings_name_edit_container_et);
        FrameLayout frameBack = (FrameLayout)root.findViewById(R.id.fragment_group_settings_fl_back);

        imageViewEdit = (ImageView) root.findViewById(R.id.imageViewEdit);


        chat = ArgsBuilder.create().chat();

        frameBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isAdded()) getActivity().onBackPressed();
            }
        });

        if(chat == null){
            Log.w(TAG, "Chat is null");
            getActivity().onBackPressed();
            return root;
        }

        imageViewEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tvGroupName.getVisibility() == View.VISIBLE){
                    tvGroupName.setVisibility(View.GONE);
                    editView.setVisibility(View.VISIBLE);
                }else{
                    tvGroupName.setVisibility(View.VISIBLE);
                    editView.setVisibility(View.GONE);
                    REST.getInstance().chatName(Controller.getInstance().getAuth().getUser().token, chat.uuid, editView.getText().toString())
                            .subscribe(new REST.DataSubscriber<Chat>() {
                                @Override
                                public void onData(Chat data) {
                                    chat = data;
                                    setChatInfo();
                                }
                            });
                }
            }
        });

        loadData();
//        setMembersAdapterContent();
//        setIconsTouchListeners();
        return root;
    }

    private void loadData(){
        viewMemberList.setData(chat);
        setChatInfo();
        REST.getInstance().get_room_attachments(Controller.getInstance().getAuth().getUser().token, chat.uuid)
                .subscribe(new REST.DataSubscriber<List<Attachment>>() {
                    @Override
                    public void onData(List<Attachment> data) {
                        viewMediaList.setData(data);
                    }
                });
    }

    private void setChatInfo(){
        tvGroupName.setText(chat.name);

        boolean isAdmin = chat.admin.equals(Controller.getInstance().getAuth().getUser().id);
        tvGroupStatus.setText(isAdmin ? "Вы администратор" : "");
    }

//
//        flAddMember.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                EventBus.getDefault().postSticky(new SwitchFragmentEvent(Fragments.ADD_MEMBER, null));
//                //TODO FragmentAddMember addMember = new FragmentAddMember();
////                getFragmentManager()
////                        .beginTransaction()
////                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
////                        .replace(R.id.container, addMember)
////                        .addToBackStack(null)
////                        .commit();
//            }
//        });
//
//
//        llMedia.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                EventBus.getDefault().postSticky(new SwitchFragmentEvent(Fragments.MEDIA, null));
//                //TODO FragmentMedia addMember = new FragmentMedia();
////                getFragmentManager()
////                        .beginTransaction()
////                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
////                        .replace(R.id.container, addMember)
////                        .addToBackStack(null)
////                        .commit();
////                alert.show();
//            }
//        });
//    }

//    private void setOnClickListener(RecyclerView mRecyclerView){
//        final GestureDetector mGestureDetector =
//                new GestureDetector(getActivity(), new GestureDetector.SimpleOnGestureListener(){
//                    @Override
//                    public boolean onSingleTapUp(MotionEvent e) {
//                        return true;
//                    }
//                });
//
//        mRecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
//            @Override
//            public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
//                View child = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
//
//                if (child != null && mGestureDetector.onTouchEvent(motionEvent)) {
//                    int position = recyclerView.getChildLayoutPosition(child); // пока не нужно, но
//                    // потом поможет определить выбранный элемент
////                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
////                    alertDialog.setTitle("Фокус");
////                    alertDialog.setMessage("Ты выбрал номер " + (position+1) + ", верно?");
////                    alertDialog.setPositiveButton("Но как?!", new DialogInterface.OnClickListener() {
////                        @Override
////                        public void onClick(DialogInterface dialog, int which) {
////
////                        }
////                    });
////                    alertDialog.setNegativeButton("Не может быть!", new DialogInterface.OnClickListener() {
////                        @Override
////                        public void onClick(DialogInterface dialog, int which) {
////
////                        }
////                    });
////                    alert = alertDialog.create();
////                    alert.show();
//                }
//                return false;
//            }
//
//            @Override
//            public void onTouchEvent(RecyclerView rv, MotionEvent e) {}
//
//            @Override
//            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {}
//        });
//    }
}