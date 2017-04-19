package com.mshvdvskgmail.technoparkmessenger.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.mshvdvskgmail.technoparkmessenger.Fragments;
import com.mshvdvskgmail.technoparkmessenger.R;
import com.mshvdvskgmail.technoparkmessenger.adapters.ChatGroupListAdapter;
import com.mshvdvskgmail.technoparkmessenger.adapters.ChatListAdapter;
import com.mshvdvskgmail.technoparkmessenger.events.SwitchFragmentEvent;
import com.mshvdvskgmail.technoparkmessenger.models.MessageChatItem;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

/**
 * Created by mshvdvsk on 06/04/2017.
 */

public class FragmentChatGroup extends Fragment {
    private View rootView;
    private RecyclerView recyclerView;
    private LinearLayoutManager lm;
    private ArrayList<MessageChatItem> messages;
    private ChatGroupListAdapter adapter;
    private LinearLayout llGroupSettings;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_chat_group, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.fragment_chat_group_rv_messages);
        recyclerView.setHasFixedSize(true);
        lm = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(lm);
        messages = new ArrayList<>();

        llGroupSettings = (LinearLayout) rootView.findViewById(R.id.fragment_chat_group_ll_group_settings);
        llGroupSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().postSticky(new SwitchFragmentEvent(Fragments.GROUPS_SETTINGS, null));
                //TODO FragmentGroupsSettings groupSettings = new FragmentGroupsSettings();
//                getActivity().getSupportFragmentManager()
//                        .beginTransaction()
//                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
//                        .replace(R.id.container, groupSettings)
//                        .addToBackStack(null)
//                        .commit();
            }
        });

        FrameLayout flBackButton = (FrameLayout) rootView.findViewById(R.id.fragment_chat_group_fl_back);
        flBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().postSticky(new SwitchFragmentEvent(Fragments.MAIN_FOUR_TAB_SCREEN, null));
                //TODO FragmentMainFourTabScreen main = new FragmentMainFourTabScreen();
//                getActivity().getSupportFragmentManager()
//                        .beginTransaction()
//                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
//                        .replace(R.id.container, main)
//                        .addToBackStack(null)
//                        .commit();
            }
        });

        MessageChatItem time = new MessageChatItem();
        time.setText("СЕГОДНЯ");
        time.setType(7);

        MessageChatItem inc1 = new MessageChatItem();
        inc1.setName("Алескндр Пушкин");
        inc1.setText("привет");
        inc1.setTime("15:40");
        inc1.setType(0);
        inc1.setStatus(0);
        inc1.setIncoming(true);

        MessageChatItem inc2 = new MessageChatItem();
        inc2.setName("Алескндр Пушкин");
        inc2.setText("привет");
        inc2.setTime("15:40");
        inc2.setType(0);
        inc2.setStatus(0);
        inc2.setIncoming(true);

        MessageChatItem out1 = new MessageChatItem();
        out1.setName("я");
        out1.setText("привет");
        out1.setTime("15:40");
        out1.setType(1);
        out1.setStatus(0);
        out1.setIncoming(false);

        MessageChatItem inc3 = new MessageChatItem();
        inc3.setName("Алескндр Пушкин");
        inc3.setText("ну как дела?");
        inc3.setTime("15:40");
        inc3.setType(0);
        inc3.setStatus(0);
        inc3.setIncoming(true);

        MessageChatItem out2 = new MessageChatItem();
        out2.setName("я");
        out2.setText("привет");
        out2.setTime("15:40");
        out2.setType(1);
        out2.setStatus(0);
        out2.setIncoming(false);

        MessageChatItem inc4 = new MessageChatItem();
        inc4.setName("Алескндр Пушкин");
        inc4.setText("ну как дела?");
        inc4.setTime("15:40");
        inc4.setType(2);
        inc4.setStatus(0);
        inc4.setIncoming(true);

        MessageChatItem out3 = new MessageChatItem();
        out3.setName("я");
        out3.setText("привет");
        out3.setTime("15:40");
        out3.setType(3);
        out3.setStatus(0);
        out3.setIncoming(false);

        MessageChatItem inc5 = new MessageChatItem();
        inc5.setName("Алескндр Пушкин");
        inc5.setFileName("LSD");
        inc5.setFileType("LSD");
        inc5.setFileSize("24GB");
        inc5.setText("ну как дела?");
        inc5.setTime("15:40");
        inc5.setType(4);
        inc5.setStatus(0);
        inc5.setIncoming(true);

        MessageChatItem out4 = new MessageChatItem();
        out4.setName("я");
        out4.setFileType("LSD");
        out4.setFileSize("24GB");
        out4.setFileName("LSD");
        out4.setText("привет");
        out4.setTime("15:40");
        out4.setType(5);
        out4.setStatus(0);
        out4.setIncoming(false);



        messages.add(time);
        messages.add(inc1);
        messages.add(inc2);
        messages.add(out1);
        messages.add(inc3);
        messages.add(out2);
        messages.add(inc4);
        messages.add(out3);
        messages.add(inc5);
        messages.add(out4);



        adapter = new ChatGroupListAdapter(messages, getContext());
        recyclerView.setAdapter(adapter);

        return rootView;
    }
}
