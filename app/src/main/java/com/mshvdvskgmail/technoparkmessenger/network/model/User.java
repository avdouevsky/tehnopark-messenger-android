package com.mshvdvskgmail.technoparkmessenger.network.model;

import android.os.Parcelable;
import android.util.Log;

import com.mshvdvskgmail.technoparkmessenger.Controller;
import com.mshvdvskgmail.technoparkmessenger.network.REST;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by andrey on 23.01.2017.
 */
/*
*
* {
* "time":"2017-03-29 10:59:01","status":1,
* "data":
* {
* "name":"vlad_dev",
* "id":"vlad_dev",
* "unique_id":"vlad_dev",
* "cn":"Vlad Mr. Developer",
* "sn":"Developer",
* "title":"Руководитель",
* "description":null,
* "phone":null,
* "ipphone":"10007:fbf56f5264805fbee4399754a6544dc7",
* "token":{
*   "ttl":86400,
*   "user_id":"vlad_dev",
*   "unique_id":"vlad_dev",
*   "session_id":109,
*   "date":1490785141,
*   "token":"1EpcQXALkRprzvVTPhJ+vRVbtDVQ3Yr/8ubJMKwVGj1C3O00ovx+DGvIQCHLQgsSbVlqGdkf0OIop+OpATbRW5DtEiajFwMWj7mteK8DrJoXv78RKK4HnoUKa87M1Rh31PeBSOrr/dYvGB2NDeeRR69cfEeshf/J5OoP6PLDHok="
*  },
*  "queue":"user-vlad_dev-123"
*  },
*  "token":null,"error":null,"profiler":{"action":"0.175165"}
* }
* */
public class User implements Serializable {
    private final static String TAG = User.class.toString();

    public String id;
    public String unique_id;
    public String name;
    public String cn;
    public String sn;
    public String title;
    public String description;
    public String phone;
    public String ipphone;
    public String mobile;
    public String mail;
    public String avatar;

    public Token token;
    public String queue;

    public int online;

    public String getName() {
        return name;
    }

    public String getOfficePosition() {
        return description;
    }
}
