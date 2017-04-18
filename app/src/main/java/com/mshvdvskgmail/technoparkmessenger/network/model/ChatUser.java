package com.mshvdvskgmail.technoparkmessenger.network.model;

/**
 * Created by vlad on 07.04.17.
 */

import com.mshvdvskgmail.technoparkmessenger.Controller;

import java.io.Serializable;

/**
 * id: 2,
 chat_rooms_id: 13,
 users_id: "testme1",
 push_on: 1
 */
public class ChatUser implements Serializable {
    public int id;
    public int chat_rooms_id;
    public String users_id;
    public int push_on;

    public User User(){
        return Controller.getInstance().getContactWithName(users_id);
    }


}
