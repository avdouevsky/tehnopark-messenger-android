package com.mshvdvskgmail.technoparkmessenger;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.ContextWrapper;
import android.graphics.Typeface;
import android.location.Location;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

//import com.mshvdvskgmail.technoparkmessenger.network.model.Bar;
import com.mshvdvskgmail.technoparkmessenger.events.DataLoadEvent;
import com.mshvdvskgmail.technoparkmessenger.network.REST;
import com.mshvdvskgmail.technoparkmessenger.network.model.Chat;
import com.mshvdvskgmail.technoparkmessenger.network.model.Message;
//import com.mshvdvskgmail.technoparkmessenger.network.model.Room;
import com.mshvdvskgmail.technoparkmessenger.network.model.Settings;
import com.mshvdvskgmail.technoparkmessenger.network.model.User;


import org.greenrobot.eventbus.EventBus;

import rx.android.schedulers.AndroidSchedulers;

import static android.content.Context.MODE_PRIVATE;
import static org.droidparts.Injector.getApplicationContext;

/**
 * Created by Admin on 23.01.2017.
 */
public class Controller {

    private static Controller instance = new Controller();

    private Typeface regFont;
    private Typeface boldFont;

    private Location lastLocation = null;
    private Settings settings;

    private final static String device_type = "android";
    private final static String DEVICE_ID = "device_id";
    private String deviceId = "";
    private SharedPreferences preferences;

    private List<User> contacts = new ArrayList<>();
    private List<Chat> chats = new ArrayList<>();
    private List<Chat> group_chats = new ArrayList<>();

//    private Map<String, List<Message>> room_messages = new HashMap<>();
//    private Map<String, Message> uuid_message = new HashMap<>();

    //private List<Bar> places;
//    private Map<Integer, Bar> places = new HashMap<>();
    private Map<String, User> users = new HashMap<>();
//    private Map<Integer, List<User>> chinChins = new HashMap<>();

    public void fillAll(){
        REST.getInstance().contacts(Controller.getInstance().getAuth().getUser().token.session_id, Controller.getInstance().getAuth().getUser().token.token, "")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new REST.DataSubscriber<List<User>>(){
                    @Override
                    public void onData(List<User> data){
                        contacts = data;
                    }

                    @Override
                    public void onCompleted(){
                        REST.getInstance().chats(Controller.getInstance().getAuth().getUser().token.session_id, Controller.getInstance().getAuth().getUser().token.token)
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new REST.DataSubscriber<List<Chat>>(){
                                    @Override
                                    public void onData(List<Chat> data){
                                        Log.w("Chats", "data:" + data);
                                        chats = data;
                                    }

                                    @Override
                                    public void onCompleted(){
                                        EventBus.getDefault().post(new DataLoadEvent("Chats"));
                                    }
                                });
                        REST.getInstance().groups(Controller.getInstance().getAuth().getUser().token.session_id, Controller.getInstance().getAuth().getUser().token.token)
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new REST.DataSubscriber<List<Chat>>(){
                                    @Override
                                    public void onData(List<Chat> data){
                                        Log.w("Groups", "data:" + data);
                                        group_chats = data;
                                    }

                                    @Override
                                    public void onCompleted(){
                                        EventBus.getDefault().post(new DataLoadEvent("Groups"));
                                    }
                                });
                    }
                });
    }

    public List<User>getContacts(){
        return contacts;
    }

    public User getContactWithName(String name){
        if(name.equals("testme1_mc64_ru")){
            name = "testme1";
        }
        for(User user : contacts){
            Log.w("User", "passing "+user+" user.name: "+user.name+" searching "+name);
            if(user.id != null && user.id.equals(name)){
                Log.w("User", "got "+user);
                return user;
            }
        }
        return null;
    }

    public void fillContacts(){
        REST.getInstance().contacts(Controller.getInstance().getAuth().getUser().token.session_id, Controller.getInstance().getAuth().getUser().token.token, "")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new REST.DataSubscriber<List<User>>(){
                    @Override
                    public void onData(List<User> data){
                        contacts = data;
                    }

                    @Override
                    public void onCompleted(){


                    }
                });
    }

    public List<Chat>getChats(){
        return chats;
    }

    public void fillChats(){
        REST.getInstance().chats(Controller.getInstance().getAuth().getUser().token.session_id, Controller.getInstance().getAuth().getUser().token.token)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new REST.DataSubscriber<List<Chat>>(){
                    @Override
                    public void onData(List<Chat> data){
                        Log.w("Chats", "data:" + data);
                        chats = data;
                    }

                    @Override
                    public void onCompleted(){
                        EventBus.getDefault().postSticky(new DataLoadEvent("Chats"));
                    }
                });
    }


    public List<Chat>getGroupChats(){
        return group_chats;
    }

    public void fillGroupChats(){
        REST.getInstance().groups(Controller.getInstance().getAuth().getUser().token.session_id, Controller.getInstance().getAuth().getUser().token.token)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new REST.DataSubscriber<List<Chat>>(){
                    @Override
                    public void onData(List<Chat> data){
                        Log.w("Groups", "data:" + data);
                        group_chats = data;
                    }

                    @Override
                    public void onCompleted(){
                        EventBus.getDefault().postSticky(new DataLoadEvent("Groups"));
                    }
                });
    }

    public final UI ui = new UI();

    private final Auth auth = new Auth();

    public String deviceType(){
        return device_type;
    }

    public String deviceId(){
        preferences = PreferenceManager.getDefaultSharedPreferences(TechnoparkApp.getContext());
        deviceId = preferences.getString(DEVICE_ID, null);
        if(deviceId == null){
            deviceId = UUID.randomUUID().toString();
            preferences.edit().putString(DEVICE_ID, deviceId).commit();
        }
        return deviceId;
    }
    
    public final Messages messages = new Messages();

    public static Controller getInstance() {
//        Log.w("Technopark", "instance "+instance);

        return instance;
    }


    public void setLastLocation(Location lastLocation) {
        this.lastLocation = lastLocation;
    }

    @Nullable
    public User getUser(String unique_id) {
        return users.get(unique_id);
    }

    public void putUsers(List<User> users){
        for(User user : users) putUser(user);
    }

    public void putUser(User user){
        users.put(user.unique_id, user);
    }

    private Controller() {}

    public Settings getSettings() {
        return settings;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
        settings.time();
    }

    public boolean isAuth() {
        if(getAuth().user == null) return false;
        return true;
    }

    public Typeface getRegFont() {
        return regFont;
    }

    public void setRegFont(Typeface regFont) {
        this.regFont = regFont;
    }

    public Typeface getBoldFont() {
        return boldFont;
    }

    public void setBoldFont(Typeface boldFont) {
        this.boldFont = boldFont;
    }

//    @Nullable
//    public Bar getPlaceById(int place_id){
//        return places.get(place_id);
//    }

//    @Nullable
//    public Bar getPlaceById(String placeId){
//        try {
//            int id = Integer.parseInt(placeId);
//            return getPlaceById(id);
//        } catch (NumberFormatException e) {
//            return null;
//        }
//    }

//    public List<Bar> getPlaces() {
//        List<Bar> list = new ArrayList<>(places.values());
//        Collections.sort(list, new Comparator<Bar>() {
//            @Override
//            public int compare(Bar bar, Bar t1) {
//                if(bar.presence) return -1;
//                if(t1.presence) return 1;
//                if(lastLocation == null && bar.distance != null && t1.distance != null) return t1.distance.compareTo(bar.distance);
//                if(lastLocation == null) return 0;
//                Float d1 = bar.getLocation().distanceTo(lastLocation);
//                Float d2 = t1.getLocation().distanceTo(lastLocation);
//                return d1.compareTo(d2);
//            }
//        });
//        return list;
//    }
//
//    public void putPlace(Bar bar){
//        places.put(bar.id, bar);
//    }
//
//    public void putPlaces(@Nullable List<Bar> places) {
//        if(places == null) return;
//        for(Bar bar : places) putPlace(bar);
//    }
//
//    public Map<Integer, List<User>> getChinChins() {
//        return chinChins;
//    }
//
//    public void setChinChins(@Nullable Map<Integer, List<User>> chinChins) {
//        if(chinChins == null) return;
//        this.chinChins = chinChins;
//    }

    /**
     * Возвращает текущий профиль пользователя
     * @return
     */
    public User getMyProfile() {
        return auth.getUser();
    }

    //====== MESSAGES ========

    public class Messages{
        private Map<String, List<Message>> room = new HashMap<>();
        private Map<String, Message> uuid = new HashMap<>();

        // Входящие, непрочитанные сообщения
        private Map<String, Message> income_uuid = new HashMap<>();

        public void read(Message message){
            income_uuid.remove(message.uuid);
        }

        public void income(@Nullable List<Message> messages){
            if(messages == null) return;
            for (Message message : messages) income(message, true);
        }

        public void setStatus(Message messageStatus){
            Message m = uuid.get(messageStatus.uuid);
            if(m != null) m.setStatus(messageStatus.getStatus());
            m = income_uuid.get(messageStatus.uuid);
            if(m != null) m.setStatus(messageStatus.getStatus());
        }

        public void income(Message message, boolean read){
            switch (message.getType()){
                case ERROR:
                    break;
                case DIALOG:
                    List<Message> messages = room.get(message.room);
                    if(messages == null){
                        messages = new ArrayList<>();
                        room.put(message.room, messages);
                    }
                    if(uuid.get(message.uuid) == null) messages.add(message);   //проверка на уникальность
                    uuid.put(message.uuid, message);
                    if(!read) income_uuid.put(message.uuid, message);
                    break;
                case CHIN_CHIN:
                case CHIN_ACCEPT:
                    uuid.put(message.uuid, message);
                    uuid.put(message.uuid, message);
                    break;
                case MESSAGE_STATUS:
                    //do nothing
                    break;
                case UNKNOWN:
                default:
                    //do nothing
            }
        }

//        public int incomeCountBy(User user){
//            int c = 0;
//            for(Message message : income_uuid.values()){
//                if(message.room.equals(user.room_uuid)) c++;
//            }
//            return c;
//        }
//
//        public int incomeCountBy(Bar bar){
//            int c = 0;
//            for(Message message : income_uuid.values()){
//                if(message.bar_id == bar.id) c++;
//            }
//            return c;
//        }
//
//        public int incomeCount(){
//            return income_uuid.size();
//        }

        public Message getByUUID(String uuid){
            return this.uuid.get(uuid);
        }

        public List<Message> get(String room) {
            //todo sort by date
            List<Message> ms = this.room.get(room);
            if(ms == null) ms = new ArrayList<>();
            //sort
            Collections.sort(ms, new Comparator<Message>() {
                @Override
                public int compare(Message message, Message t1) {
                    if(message.date == t1.date) return 0;
                    return message.date < t1.date ? -1 : 1;
                }
            });
            return ms;
        }
    }

    //====== AUTH =======

    public Auth getAuth() {
        return auth;
    }

    public static class Auth{
        public User user;
//        public WifiToken wifiToken = new WifiToken();

        public void setUser(User input){
            user = input;
        }

        public User getUser() {
            return user;
        }

        public void logout() {
            user = null;
//            wifiToken = null;
        }
    }

    public class UI{
//        public int getChinChinCount(){
//            return chinChins.size();
//        }
//
//        public boolean isWifi() {
//            return auth.wifiToken != null && !auth.wifiToken.expired();
//        }
    }
}
