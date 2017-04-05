package com.mshvdvskgmail.technoparkmessenger.events;

import com.mshvdvskgmail.technoparkmessenger.network.model.Message;

/**
 * Created by mshvdvsk on 23/03/2017.
 */

public class MessageEvent {

    private Message message;

    public MessageEvent(Message message) {
        this.message = message;
    }

    public Message getMessage() {
        return message;
    }
}
