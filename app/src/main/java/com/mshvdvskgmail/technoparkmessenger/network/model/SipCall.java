package com.mshvdvskgmail.technoparkmessenger.network.model;

import android.support.annotation.Nullable;

import com.mshvdvskgmail.technoparkmessenger.Controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by andrey on 23.01.2017.
 */
/*
*
{"time":"2017-03-27 13:30:28","status":1,
"data":[
{"calldate":"2017-03-22 18:31:54","src":"10005","dst":"10004","duration":2,"billsec":0,"disposition":"BUSY","src_u":null,"dst_u":null},
{"calldate":"2017-03-22 18:38:05","src":"10004","dst":"10005","duration":3,"billsec":0,"disposition":"BUSY","src_u":null,"dst_u":null},
{"calldate":"2017-03-22 18:40:06","src":"10005","dst":"10006","duration":63,"billsec":61,"disposition":"ANSWERED","src_u":null,"dst_u":null},
{"calldate":"2017-03-22 18:41:21","src":"10005","dst":"10006","duration":3,"billsec":0,"disposition":"NO ANSWER","src_u":null,"dst_u":null},
{"calldate":"2017-03-22 18:41:35","src":"10005","dst":"10006","duration":14,"billsec":12,"disposition":"ANSWERED","src_u":null,"dst_u":null},
{"calldate":"2017-03-22 18:41:55","src":"10005","dst":"10006","duration":63,"billsec":62,"disposition":"ANSWERED","src_u":null,"dst_u":null},
{"calldate":"2017-03-22 18:43:07","src":"10006","dst":"10005","duration":180,"billsec":178,"disposition":"ANSWERED","src_u":{"name":"testme1","id":"testme1","unique_id":"testme1","cn":"TestMe1 Justest","sn":"Justest","title":"junior","description":"Funny user in our organisation","phone":"+75559875564","ipphone":"10006:987431675b7352934275abc32933f118"},"dst_u":{"name":"testme1","id":"testme1","unique_id":"testme1","cn":"TestMe1 Justest","sn":"Justest","title":"junior","description":"Funny user in our organisation","phone":"+75559875564","ipphone":"10006:987431675b7352934275abc32933f118"}}
],
"token":null,"error":null,"profiler":{"action":"0.109634"}}

* }
* */
public class SipCall {
    private final static String TAG = SipCall.class.toString();

    public String calldate;
    public int calltime;
    public String src;
    public String dst;
    public int duration;
    public int billsec;
    public String disposition;

    @Nullable
    public User src_u;

    @Nullable
    public User dst_u;

    public boolean isIncoming(){
        return dst_u != null && dst_u.id.equals(Controller.getInstance().getAuth().getUser().id);
    }

    @Nullable
    public User opposite(){
        return isIncoming() ? src_u : dst_u;
    }

    public boolean isMissed(){
        return !isIncoming() && disposition.equals("NO ANSWER");
    }

    public String getTime(){
        SimpleDateFormat in_format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat out_format = new SimpleDateFormat("HH:mm");
        try {
            Date date = in_format.parse(calldate);
            String datetime = out_format.format(date);
            return datetime;
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "";
    }
}
