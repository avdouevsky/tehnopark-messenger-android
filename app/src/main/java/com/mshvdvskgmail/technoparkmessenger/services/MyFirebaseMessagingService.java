package com.mshvdvskgmail.technoparkmessenger.services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import com.mshvdvskgmail.technoparkmessenger.Controller;
import com.mshvdvskgmail.technoparkmessenger.R;
//import com.mshvdvskgmail.technoparkmessenger.activity.ChatActivity;
//import com.mshvdvskgmail.technoparkmessenger.activity.IncomeChinsActivity;
//import com.mshvdvskgmail.technoparkmessenger.activity.PlaceActivity;
//import com.mshvdvskgmail.technoparkmessenger.activity.MainActivity;
import com.mshvdvskgmail.technoparkmessenger.activities.MainActivity;
import com.mshvdvskgmail.technoparkmessenger.events.MessageEvent;
import com.mshvdvskgmail.technoparkmessenger.helpers.ArgsBuilder;
import com.mshvdvskgmail.technoparkmessenger.network.model.Message;

/**
 * Created by andrey on 03.02.2017.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private final static String TAG = MyFirebaseMessagingService.class.toString();

    private Gson gson = new Gson();

    @Override
    public void onCreate() {
        super.onCreate();

        Log.d(TAG, "onCreate");
        FirebaseMessaging.getInstance().subscribeToTopic("production");
        FirebaseMessaging.getInstance().subscribeToTopic("test");
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        if (remoteMessage.getData().size() == 0){
            Log.w(TAG, "no data payload");
            return;
        }

        Log.d(TAG, "Message data payload: " + remoteMessage.getData());

        String json = remoteMessage.getData().get("message");
        if(json == null){
            Log.d(TAG, "Message message is null Body");
            return;
        }

        Message message = gson.fromJson(json, Message.class);

        String title;
        String body;

        EventBus.getDefault().post(new MessageEvent(message));
        Intent notificationIntent;


        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        int id;
        switch (message.getType()){
            case DIALOG:
                notificationIntent = new Intent(this, MainActivity.class);
                notificationIntent.setAction(Intent.ACTION_VIEW);
                title = "сообщение от " + message.sender.name;
                body = message.message;
                id = 10001;
                break;
/*            case CHIN_CHIN:
//                if(Controller.getInstance().messages.getByUUID(message.uuid) != null) return;   //уже получали
                notificationIntent = new Intent(this, IncomeChinsActivity.class);
                notificationIntent.setAction(Intent.ACTION_VIEW);
                title = "чин-чин от " + message.sender.name;
                body = "Пообщаемся?";
                id = 10002;
                break;
            case CHIN_ACCEPT:
//                if(Controller.getInstance().messages.getByUUID(message.uuid) != null) return;   //уже получали
                notificationIntent = new Intent(this, ChatActivity.class);
//                notificationIntent = new Intent(this, MainActivity.class);
                Controller.getInstance().putPlace(message.push_bar);
                notificationIntent.putExtras(ArgsBuilder.create().user(message.sender).bar(message.push_bar).bundle());
                notificationIntent.setAction(Intent.ACTION_VIEW);
                //@имя подтвердил/а чин-чин. Вечер становится ярче!)
                title = message.sender.name + " подтвердил/а чин-чин.";
                body = "Вечер становится ярче!)";
                id = 10003;
                break;*/
            default:
                //do nothing
                return;
        }

        PendingIntent intent = PendingIntent.getActivity(this, 0,
                notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setColor(getResources().getColor(R.color.colorPrimary))
                        .setContentTitle(title)
                        .setContentText(body)
                        .setAutoCancel(true)
                        .setContentIntent(intent)
                ;
        mBuilder.setSound(alarmSound);

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(id, mBuilder.build());

        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(300);
    }
}
