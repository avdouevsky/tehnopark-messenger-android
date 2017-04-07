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

import com.mshvdvskgmail.technoparkmessenger.R;
import com.mshvdvskgmail.technoparkmessenger.adapters.ChatListAdapter;
import com.mshvdvskgmail.technoparkmessenger.models.MessageChatItem;

import java.util.ArrayList;

/**
 * Created by mshvdvsk on 30/03/2017.
 */

public class FragmentChat extends Fragment {
    private View rootView;
    private RecyclerView recyclerView;
    private LinearLayoutManager lm;
    private ArrayList<MessageChatItem> messages;
    private ChatListAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_chat, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.fragment_chat_rv_messages);
        recyclerView.setHasFixedSize(true);
        lm = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(lm);
        messages = new ArrayList<>();

        FrameLayout flBackButton = (FrameLayout) rootView.findViewById(R.id.fragment_chat_fl_back);
        flBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentMainFourTabScreen main = new FragmentMainFourTabScreen();
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .replace(R.id.container, main)
                        .addToBackStack(null)
                        .commit();
            }
        });


        MessageChatItem dummyObject0 = new MessageChatItem();
        dummyObject0.setText("привет");
        dummyObject0.setTime("15:40");
        dummyObject0.setType(7);
        dummyObject0.setStatus(0);
        dummyObject0.setIncoming(true);

        MessageChatItem dummyObject1 = new MessageChatItem();
        dummyObject1.setText("привет");
        dummyObject1.setTime("15:40");
        dummyObject1.setType(0);
        dummyObject1.setStatus(0);
        dummyObject1.setIncoming(true);

        MessageChatItem dummyObject11 = new MessageChatItem();
        dummyObject11.setText("привет");
        dummyObject11.setTime("15:40");
        dummyObject11.setType(1);
        dummyObject11.setStatus(4);
        dummyObject11.setIncoming(false);


        MessageChatItem dummyObject2 = new MessageChatItem();
        dummyObject2.setText("привет привет");
        dummyObject2.setTime("15:40");
        dummyObject2.setType(1);
        dummyObject2.setStatus(4);
        dummyObject2.setIncoming(false);

        MessageChatItem dummyObject22 = new MessageChatItem();
        dummyObject22.setText("привет привет");
        dummyObject22.setTime("15:40");
        dummyObject22.setType(1);
        dummyObject22.setStatus(4);
        dummyObject22.setIncoming(false);

        MessageChatItem dummyObject21 = new MessageChatItem();
        dummyObject21.setText("привет привет");
        dummyObject21.setTime("15:40");
        dummyObject21.setType(0);
        dummyObject21.setStatus(4);
        dummyObject21.setIncoming(true);

        MessageChatItem dummyObject3 = new MessageChatItem();
        dummyObject3.setText("доброе утро любимый");
        dummyObject3.setTime("15:40");
        dummyObject3.setType(0);
        dummyObject3.setStatus(4);
        dummyObject3.setIncoming(true);

        MessageChatItem dummyObject31 = new MessageChatItem();
        dummyObject31.setText("доброе утро любимый");
        dummyObject31.setTime("15:40");
        dummyObject31.setType(1);
        dummyObject31.setStatus(4);
        dummyObject31.setIncoming(false);

        MessageChatItem dummyObject4 = new MessageChatItem();
        dummyObject4.setText("ты уже умерла или еще нет?");
        dummyObject4.setTime("15:40");
        dummyObject4.setType(1);
        dummyObject4.setStatus(4);
        dummyObject4.setIncoming(false);

        MessageChatItem dummyObject41 = new MessageChatItem();
        dummyObject41.setText("ты уже умерла или еще нет?");
        dummyObject41.setTime("15:40");
        dummyObject41.setType(0);
        dummyObject41.setStatus(4);
        dummyObject41.setIncoming(true);

        MessageChatItem dummyObject5 = new MessageChatItem();
        dummyObject5.setText("еще нет, дорогой, подожди 30 лет");
        dummyObject5.setTime("15:40");
        dummyObject5.setType(0);
        dummyObject5.setStatus(4);
        dummyObject5.setIncoming(true);


        MessageChatItem dummyObject51 = new MessageChatItem();
        dummyObject51.setText("еще нет, дорогой, подожди 30 лет");
        dummyObject51.setTime("15:40");
        dummyObject51.setType(1);
        dummyObject51.setStatus(4);
        dummyObject51.setIncoming(false);



        MessageChatItem dummyObject6 = new MessageChatItem();
        dummyObject6.setText("ох, ты меня успокоила, а то я думал, ждать 50 придется");
        dummyObject6.setTime("00:40");
        dummyObject6.setType(1);
        dummyObject6.setStatus(4);
        dummyObject6.setIncoming(false);


        MessageChatItem dummyObject61 = new MessageChatItem();
        dummyObject61.setText("ох, ты меня успокоила, а то я думал, ждать 50 придется");
        dummyObject61.setTime("00:40");
        dummyObject61.setType(0);
        dummyObject61.setStatus(4);
        dummyObject61.setIncoming(true);

        MessageChatItem dummyObject611 = new MessageChatItem();
        dummyObject611.setTime("00:40");
        dummyObject611.setType(2);
        dummyObject611.setStatus(4);
        dummyObject611.setIncoming(true);

        MessageChatItem dummyObject6111 = new MessageChatItem();
        dummyObject6111.setTime("00:40");
        dummyObject6111.setFileName("Документация по проекту...");
        dummyObject6111.setFileSize("25 КБ");
        dummyObject6111.setFileType("LSD");
        dummyObject6111.setType(4);
        dummyObject6111.setStatus(4);
        dummyObject6111.setIncoming(true);

        MessageChatItem dummyObject64 = new MessageChatItem();
        dummyObject64.setTime("00:40");
        dummyObject64.setFileName("Документация по проекту...");
        dummyObject64.setFileSize("25 КБ");
        dummyObject64.setFileType("LSD");
        dummyObject64.setType(5);
        dummyObject64.setStatus(4);
        dummyObject64.setIncoming(false);

        MessageChatItem dummyObject666 = new MessageChatItem();
        dummyObject666.setText("ПРОПУЩЕННЫЙ АУДИОЗВОНОК В 14:30");
        dummyObject666.setFileName("Документация по проекту...");
        dummyObject666.setFileSize("25 КБ");
        dummyObject666.setFileType("LSD");

        dummyObject666.setType(6);
        dummyObject666.setStatus(4);
        dummyObject666.setIncoming(false);



//        MessageChatItem dummyObject1 = new MessageChatItem();
//        dummyObject1.setText("привет");
//        dummyObject1.setTime("15:40");
//        dummyObject1.setType(0);
//        dummyObject1.setStatus(0);
//
//
//        MessageChatItem dummyObject2 = new MessageChatItem();
//        dummyObject2.setText("fuck fuck fucabkdasjkldsahjkdashjkldashjkdasjklh sdalhasdjklhasdjdas");
//        dummyObject2.setTime("15:40");
//        dummyObject2.setType(0);
////        dummyObject2.setStatus(1);
//
//        MessageChatItem dummyObject3 = new MessageChatItem();
//        dummyObject3.setText("fuck fuck fucabkdasjkldsahjkdashjkldashjkdasjklh sdalhasdjklhasdjdas");
//        dummyObject3.setTime("15:40");
//        dummyObject3.setType(1);
//        dummyObject3.setStatus(2);
//
//        MessageChatItem dummyObject4 = new MessageChatItem();
//        dummyObject4.setText("fuck fuck fucabkdasjkldsahjkdashjkldashjkdasjklh sdalhasdjklhasdjdas");
//        dummyObject4.setTime("15:40");
//        dummyObject4.setType(1);
//        dummyObject4.setStatus(3);
//
//        MessageChatItem dummyObject5 = new MessageChatItem();
//        dummyObject5.setText("ПРОПУЩЕННЫЙ АУДИОЗВОНОК 17.03.2017");
//        dummyObject5.setType(6);
//        dummyObject5.setStatus(4);
//
//        MessageChatItem dummyObject6 = new MessageChatItem();
//        dummyObject6.setText("fuck fuck fucabkdasjkldsahjkdashjkldashjkdasjklh sdalhasdjklhasdjdas");
//        dummyObject6.setTime("00:40");
//        dummyObject6.setType(3);
//        dummyObject6.setStatus(4);

        messages.add(dummyObject0);
        messages.add(dummyObject1);
        messages.add(dummyObject11);
        messages.add(dummyObject2);
        messages.add(dummyObject22);
        messages.add(dummyObject21);
        messages.add(dummyObject3);
        messages.add(dummyObject31);
        messages.add(dummyObject4);
        messages.add(dummyObject41);
        messages.add(dummyObject5);
        messages.add(dummyObject51);
        messages.add(dummyObject6);
        messages.add(dummyObject61);
        messages.add(dummyObject611);
        messages.add(dummyObject6111);
        messages.add(dummyObject64);
        messages.add(dummyObject666);

        adapter = new ChatListAdapter(messages, getContext());
        recyclerView.setAdapter(adapter);

        return rootView;
    }
}
