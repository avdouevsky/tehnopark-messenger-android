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
import android.widget.LinearLayout;
import android.widget.TextView;


import com.mshvdvskgmail.technoparkmessenger.Fragments;
import com.mshvdvskgmail.technoparkmessenger.R;
import com.mshvdvskgmail.technoparkmessenger.Controller;
import com.mshvdvskgmail.technoparkmessenger.events.SwitchFragmentEvent;
import com.mshvdvskgmail.technoparkmessenger.helpers.ArgsBuilder;
import com.mshvdvskgmail.technoparkmessenger.helpers.ICommand;
import com.mshvdvskgmail.technoparkmessenger.network.REST;
import com.mshvdvskgmail.technoparkmessenger.network.model.Attachment;
import com.mshvdvskgmail.technoparkmessenger.network.model.Chat;
import com.mshvdvskgmail.technoparkmessenger.view.MediaListView;
import com.mshvdvskgmail.technoparkmessenger.view.MemberListView;

import org.greenrobot.eventbus.EventBus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Created by mshvdvsk on 29/03/2017.
 *
 */
public class FragmentGroupsSettings extends BaseFragment {
    private final static String TAG = FragmentGroupsSettings.class.toString();

    private MediaListView viewMediaList;
    private MemberListView viewMemberList;

    private TextView tvGroupName;
    private TextView tvGroupStatus;
    private TextView tvGroupCreator;
    private TextView tvGroupCreated;
    private EditText editView;
    private LinearLayout frameInfo;
    private FrameLayout flMediaSeparator;
    private  View root;

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
        root = inflater.inflate(R.layout.fragment_group_settings, container, false);
        Log.d(TAG, "onCreateView ");

        viewMediaList = (MediaListView) root.findViewById(R.id.viewMediaList);
        viewMemberList = (MemberListView) root.findViewById(R.id.viewMemberList);

        tvGroupName = (TextView) root.findViewById(R.id.fragment_group_settings_name);
        tvGroupStatus = (TextView) root.findViewById(R.id.fragment_group_settings_status);
        tvGroupCreator = (TextView) root.findViewById(R.id.fragment_group_settings_creator);
        tvGroupCreated = (TextView) root.findViewById(R.id.fragment_group_settings_tv_date);

        editView = (EditText) root.findViewById(R.id.fragment_group_settings_name_edit_container_et);
        FrameLayout frameBack = (FrameLayout)root.findViewById(R.id.fragment_group_settings_fl_back);
        flMediaSeparator = (FrameLayout)root.findViewById(R.id.fragment_group_settings_fl_media_separator);
        frameInfo = (LinearLayout)root.findViewById(R.id.fragment_group_settings_name_container);

        imageViewEdit = (ImageView) root.findViewById(R.id.imageViewEdit);


        chat = ArgsBuilder.create().chat();

        frameBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(frameInfo.getVisibility() != VISIBLE){
                    frameInfo.setVisibility(VISIBLE);
                    editView.setVisibility(GONE);
                }else{
                    if(isAdded()) getActivity().onBackPressed();
                }
            }
        });

        if(chat == null){
            Log.w(TAG, "Chat is null");
            getActivity().onBackPressed();
            return root;
        }

        imageViewEdit.setVisibility(chat != null && chat.admin.equals(Controller.getInstance().getAuth().getUser().id) ? VISIBLE : GONE);
        imageViewEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(frameInfo.getVisibility() == VISIBLE){
                    frameInfo.setVisibility(GONE);
                    editView.setVisibility(VISIBLE);
                    editView.setText(chat.name);
                }else{
                    frameInfo.setVisibility(VISIBLE);
                    editView.setVisibility(GONE);
                    if(chat != null) REST.getInstance().chatName(Controller.getInstance().getAuth().getUser().token, chat.uuid, editView.getText().toString())
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


//        imageViewEdit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(tvGroupName.getVisibility() == View.VISIBLE){
//                    tvGroupName.setVisibility(View.GONE);
//                    editView.setVisibility(View.VISIBLE);
//                }else{
//                    tvGroupName.setVisibility(View.VISIBLE);
//                    editView.setVisibility(View.GONE);
//                    REST.getInstance().chatName(Controller.getInstance().getAuth().getUser().token, chat.uuid, editView.getText().toString())
//                            .subscribe(new REST.DataSubscriber<Chat>() {
//                                @Override
//                                public void onData(Chat data) {
//                                    chat = data;
//                                    setChatInfo();
//                                }
//                            });
//                }
//            }
//        });

        viewMemberList.setAddListener(new ICommand<Void>() {
            @Override
            public void exec(Void data) {
                EventBus.getDefault().postSticky(new SwitchFragmentEvent(Fragments.ADD_MEMBER, ArgsBuilder.create().chat(chat).bundle()));
            }
        });

        viewMediaList.setCountClickListener(new ICommand<Void>() {
            @Override
            public void exec(Void data) {
                EventBus.getDefault().postSticky(new SwitchFragmentEvent(Fragments.MEDIA, ArgsBuilder.create().chat(chat).bundle()));
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
                        if (viewMediaList.getFileCounter()==0){
                            viewMediaList.setVisibility(GONE);
                            flMediaSeparator.setVisibility(GONE);
                        } else{
                            viewMediaList.setVisibility(VISIBLE);
                            flMediaSeparator.setVisibility(VISIBLE);
                        }
                    }
                });
    }

    private void setDate(){
        TextView tvDateCreated = (TextView) root.findViewById(R.id.fragment_group_settings_tv_date);
        String date = new String();

        tvDateCreated.setText("ДАТА СОЗДАНИЯ: "+ chat.date);
    }

    private void setChatInfo() {
        tvGroupName.setText(chat.name);
        boolean isAdmin = chat.admin.equals(Controller.getInstance().getAuth().getUser().id);
        tvGroupStatus.setText(isAdmin ? "Вы администратор" : "");
        tvGroupCreator.setVisibility(isAdmin ? VISIBLE : GONE);

        Locale russianLocale = new Locale("ru","RU");
        SimpleDateFormat dateFormatFromServer = new SimpleDateFormat("yyyy-M-dd HH:mm", russianLocale);
        Date dateFromServer = null;
        try {
            dateFromServer = dateFormatFromServer.parse(chat.date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat dateFormatRequired = new SimpleDateFormat("dd MMMM yyyy", russianLocale);
        String sCertDate = dateFormatRequired.format(dateFromServer);
        String dateCreatetPrefix = getContext().getString(R.string.date_created);
        tvGroupCreated.setText(dateCreatetPrefix + " " + sCertDate + " Г.");
    }


    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
        final Chat clone = this.chat;
        REST.getInstance().groups(Controller.getInstance().getAuth().getUser().token)
                .subscribe(new REST.DataSubscriber<List<Chat>>() {
                    @Override
                    public void onData(List<Chat> data) {
                        for (Chat chat : data){
                            if (chat.id.equals(clone.id)&&chat.getUsers().size()!=clone.getUsers().size()){
                                ArgsBuilder.create().chat(chat);
                                viewMemberList.setData(chat);
                                break;
                            }
                        }
                    }
                });
        loadData();
    }
}