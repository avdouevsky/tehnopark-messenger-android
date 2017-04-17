package com.mshvdvskgmail.technoparkmessenger.network.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by vlad on 17.04.17.
 */

public class LastMessage implements Serializable {
    private final static String TAG = LastMessage.class.toString();

    public int id;
    public int date;
    public String uuid;
    public String users_id;
    public int chat_rooms_id;
    public String message_text;


    public String getTimeAsString() {
        return new SimpleDateFormat("HH:mm").format(new Date(date * 1000L));
    }
    /**
     *
     * last: {
     id: 113,
     date: 1492428061,
     uuid: "dialog_58f4a51d642af2.73815253",
     users_id: "vlad_dev",
     chat_rooms_id: 67,
     message_text: "ttt",
     message_header: "",
     message_log: "{"type":"dialog","version":1,"date":1492428061,"headers":[],"attachments":[],"message":"ttt","user_token":{"ttl":86400,"user_id":"vlad_dev","unique_id":"vlad_dev","session_id":450,"date":1492428049,"token":"bHlTfzy\/SvtNBode1M+1euN+\/B6+FCILxblzZXIVbNM01S7O1kHMYbP7+ppbPGTCpjRLi5dr5Z+KegcIOxxLkSKPo51Vkq0fXjRq4Uhpe5WZqTm6KU8gJKSXmDUJ7WwMmG8rGGkHsFiT36n9OOC8v1JDuu3gC3BN+WR2T5kpEj4="},"sender":{"name":"Vlad Mr. Developer","id":"vlad_dev","unique_id":"vlad_dev","cn":"Vlad Mr. Developer","sn":"Developer","title":"Руководитель","displayname":"Vlad Mr. Developer","givenname":"Vlad","description":null,"phone":null,"ipphone":"10007:fbf56f5264805fbee4399754a6544dc7","mobile":"+7(123)456-7898","mail":"vlad@dev.ru"},"uuid":"dialog_58f4a51d642af2.73815253","status":"sent","room":"room_d771cb0dc99ca396dda9d56ef895f948","room_id":67,"bar_id":0,"local_id":"no local id"}",
     with_media: 0
     }
     */
}
