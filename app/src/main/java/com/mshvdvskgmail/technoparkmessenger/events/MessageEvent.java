package com.mshvdvskgmail.technoparkmessenger.events;

import com.mshvdvskgmail.technoparkmessenger.network.model.Message;

/**
 * Created by mshvdvsk on 23/03/2017.
 */

public class MessageEvent {

    private Message message;
    private boolean state;

    public MessageEvent(Message message) {
        this.message = message;
    }
    public MessageEvent(boolean state) {
        this.state = state;
    }

    public Message getMessage() {
        return message;
    }

    public boolean getState() {
        return state;
    }

}
