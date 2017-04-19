package com.mshvdvskgmail.technoparkmessenger.network.model;

/**
 * Created by vlad on 07.04.17.
 */
import java.io.Serializable;

public class ChatUser implements Serializable {
    public int chat_rooms_id;
    public String users_id;

    public User user;
}
