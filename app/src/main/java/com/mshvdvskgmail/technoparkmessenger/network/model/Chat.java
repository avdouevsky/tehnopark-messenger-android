package com.mshvdvskgmail.technoparkmessenger.network.model;

import android.util.Log;

import com.mshvdvskgmail.technoparkmessenger.Controller;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andrey on 23.01.2017.
 */
/*
*
{"time":"2017-04-04 08:30:16","status":1,"data":[
{"id":12,"name":"chat","date":"0000-00-00 00:00:00","users_id":"testme1_mc64_ru","uuid":"","peer2peer":0},
{"id":13,"name":"chat","date":"0000-00-00 00:00:00","users_id":"testme1_mc64_ru","uuid":"","peer2peer":0},
{"id":14,"name":"chat","date":"0000-00-00 00:00:00","users_id":"testme1_mc64_ru","uuid":"","peer2peer":0},{"id":29,"name":"chat","date":"2017-03-21 17:13:20","users_id":"testme1","uuid":"room_6adec5ebd13bb3ad62e199c08716b4ac","peer2peer":0},{"id":31,"name":"chat","date":"2017-03-21 17:34:14","users_id":"testme1","uuid":"room_fe1f4cd3ab946cf15c151bd53f35b6df","peer2peer":0},{"id":35,"name":"chat","date":"2017-03-23 14:55:57","users_id":"testme1","uuid":"room_9cac23bc4f28b8ea475ee8f91b32241c","peer2peer":0},{"id":36,"name":"chat","date":"2017-03-23 17:08:42","users_id":"testme1","uuid":"room_153bfa9eaaeccb970c241977cb11fdf2","peer2peer":0}],"token":null,"error":null,"profiler":{"action":"0.051895"}}
* */
public class Chat {
    private final static String TAG = Chat.class.toString();

    public String id;
    public String name;
    public String date;
    public String users_id;
    public String uuid;
    public int peer2peer;

    public List<User> Users(){
        List<User> retVal = new ArrayList<>();
        users_id = "testme1";
        String[] tmp_users = users_id.split(",");
        Log.w("Chat", "Users: "+tmp_users);
        if(tmp_users.length > 0) {
//            tmp_users = new String[1];
            tmp_users[0] = users_id;

        }
        for (String user: tmp_users) {
            Log.w("Chat", "passing "+user);

            User from_contact = Controller.getInstance().getContactWithName(user);
            if(from_contact != null)
                retVal.add(from_contact);
        }

        return retVal;
    }
}
