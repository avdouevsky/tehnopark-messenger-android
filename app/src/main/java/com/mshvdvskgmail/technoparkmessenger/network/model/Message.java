package com.mshvdvskgmail.technoparkmessenger.network.model;

import android.util.Log;

import java.util.List;

/**
 * Created by andrey on 23.01.2017.
 */

public class Message {
    private final static String TAG = Message.class.toString();

    //public static String DIALOG = "dialog";

    protected String type;
    public String room;
    public int date;
    public int version = 1;
//    public Map<String, String> headers;
    public List<Attachment> attachments;
    public String message = "";
    public Token user_token;
    public WifiToken wifi_token;
    public User sender;
    public String uuid;
    public String local_id;

    protected String status = "sent";


    public Status getStatus() {
        if(status == null) return Status.READ;
        try {
            return Status.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            Log.w(TAG, e.getMessage());
            return Status.READ;
        }
    }

    public void setStatus(Status status) {
        this.status = status.toString();
    }

    public Type getType() {
        if(type == null) return Type.UNKNOWN;
        try {
            return Type.valueOf(type.toUpperCase().replace('-', '_'));
        } catch (IllegalArgumentException e) {
            Log.w(TAG, e.getMessage());
            return Type.UNKNOWN;
        }
    }

    public enum Status{
        SENT, DELIVERED, READ;

        @Override
        public String toString() {
            return super.toString().toLowerCase();
        }
    }

    public enum Type{
        ERROR, DIALOG, CHIN_CHIN, CHIN_FRIEND, CHIN_ACCEPT, MESSAGE_STATUS, UNKNOWN, USER_STATUS;

        @Override
        public String toString() {
            return super.toString().toLowerCase().replace('_', '-');
        }

    }
}
