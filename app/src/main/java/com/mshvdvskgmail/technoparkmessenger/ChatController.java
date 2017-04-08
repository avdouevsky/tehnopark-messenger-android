package com.mshvdvskgmail.technoparkmessenger;

import com.mshvdvskgmail.technoparkmessenger.network.RMQChat;

/**
 * Created by vlad on 06.04.17.
 */

public class ChatController {
    private static ChatController instance = new ChatController();

    public RMQChat r;
    public static ChatController getInstance() {
        return instance;
    }


}
