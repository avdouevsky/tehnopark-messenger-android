package com.mshvdvskgmail.technoparkmessenger.network.model;

import java.io.Serializable;

/**
 * Created by andrey on 23.01.2017.
 */

public class Token implements Serializable {
    public int ttl = 0;
    public String user_id = "";
    public String unique_id = "";
    public int session_id = 0;
    public int date = 0;
    public String token = "";
}
