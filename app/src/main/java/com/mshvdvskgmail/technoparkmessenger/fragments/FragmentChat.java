package com.mshvdvskgmail.technoparkmessenger.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

        recyclerView = (RecyclerView) rootView.findViewById(R.id.messages);
        recyclerView.setHasFixedSize(true);
        lm = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(lm);
        messages = new ArrayList<>();


        MessageChatItem dummyObject1 = new MessageChatItem();
        dummyObject1.setText("привет");
        dummyObject1.setTime("15:40");
        dummyObject1.setType(0);
        dummyObject1.setStatus(0);

        MessageChatItem dummyObject11 = new MessageChatItem();
        dummyObject11.setText("привет");
        dummyObject11.setTime("15:40");
        dummyObject11.setType(1);
        dummyObject11.setStatus(4);


        MessageChatItem dummyObject2 = new MessageChatItem();
        dummyObject2.setText("привет привет");
        dummyObject2.setTime("15:40");
        dummyObject2.setType(1);
        dummyObject2.setStatus(4);

        MessageChatItem dummyObject21 = new MessageChatItem();
        dummyObject21.setText("привет привет");
        dummyObject21.setTime("15:40");
        dummyObject21.setType(0);
        dummyObject21.setStatus(4);

        MessageChatItem dummyObject3 = new MessageChatItem();
        dummyObject3.setText("доброе утро любимый");
        dummyObject3.setTime("15:40");
        dummyObject3.setType(0);
        dummyObject3.setStatus(4);

        MessageChatItem dummyObject31 = new MessageChatItem();
        dummyObject31.setText("доброе утро любимый");
        dummyObject31.setTime("15:40");
        dummyObject31.setType(1);
        dummyObject31.setStatus(4);

        MessageChatItem dummyObject4 = new MessageChatItem();
        dummyObject4.setText("ты уже умерла или еще нет?");
        dummyObject4.setTime("15:40");
        dummyObject4.setType(1);
        dummyObject4.setStatus(4);

        MessageChatItem dummyObject41 = new MessageChatItem();
        dummyObject41.setText("ты уже умерла или еще нет?");
        dummyObject41.setTime("15:40");
        dummyObject41.setType(0);
        dummyObject41.setStatus(4);

        MessageChatItem dummyObject5 = new MessageChatItem();
        dummyObject5.setText("еще нет, дорогой, подожди 30 лет");
        dummyObject5.setTime("15:40");
        dummyObject5.setType(0);
        dummyObject5.setStatus(4);

        MessageChatItem dummyObject51 = new MessageChatItem();
        dummyObject51.setText("еще нет, дорогой, подожди 30 лет");
        dummyObject51.setTime("15:40");
        dummyObject51.setType(1);
        dummyObject51.setStatus(4);


        MessageChatItem dummyObject6 = new MessageChatItem();
        dummyObject6.setText("ох, ты меня успокоила, а то я думал, ждать 50 придется");
        dummyObject6.setTime("00:40");
        dummyObject6.setType(1);
        dummyObject6.setStatus(4);

        MessageChatItem dummyObject61 = new MessageChatItem();
        dummyObject61.setText("ох, ты меня успокоила, а то я думал, ждать 50 придется");
        dummyObject61.setTime("00:40");
        dummyObject61.setType(0);
        dummyObject61.setStatus(4);


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

        messages.add(dummyObject1);
        messages.add(dummyObject11);

        messages.add(dummyObject2);
        messages.add(dummyObject21);

        messages.add(dummyObject3);
        messages.add(dummyObject31);

        messages.add(dummyObject4);
        messages.add(dummyObject41);

        messages.add(dummyObject5);
        messages.add(dummyObject51);

        messages.add(dummyObject6);
        messages.add(dummyObject61);




//        for (int i = 0; i < 10; i++){
//            contacts.add(dummyObject);
//        }
        adapter = new ChatListAdapter(messages, getContext());
        recyclerView.setAdapter(adapter);

        return rootView;
    }
}
