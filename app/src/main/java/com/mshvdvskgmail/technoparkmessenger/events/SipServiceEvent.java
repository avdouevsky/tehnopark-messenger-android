package com.mshvdvskgmail.technoparkmessenger.events;

/**
 * Created by andrey on 15.05.2017.
 */

public class SipServiceEvent {
    private Type type;
    private String data;

    public SipServiceEvent(Type type) {
        this.type = type;
    }

    public SipServiceEvent(Type type, String data) {
        this.type = type;
        this.data = data;
    }

    public Type getType() {
        return type;
    }

    public String getData() {
        return data;
    }

    public enum Type{
        ANSWER, HANGUP, CALL;
    }
}
