package com.mshvdvskgmail.technoparkmessenger.network;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.*;
import retrofit2.http.Header;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import com.mshvdvskgmail.technoparkmessenger.BuildConfig;
import com.mshvdvskgmail.technoparkmessenger.Consts;
import com.mshvdvskgmail.technoparkmessenger.Controller;
import com.mshvdvskgmail.technoparkmessenger.events.ErrorEvent;
import com.mshvdvskgmail.technoparkmessenger.network.model.*;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by andrey on 23.01.2017.
 */
public class REST implements IService {
    private final static String TAG = REST.class.toString();
    private final static REST instance = new REST();

    public final static int OK = 1;
    public final static int FAIL = 0;

    private Retrofit retrofit;
    private IService service;
    private IService service2;
    private Converter<ResponseBody, Error> errorConverter;

    public static REST getInstance() {
        return instance;
    }

    public REST() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .followRedirects(true)
                .addInterceptor(new BuildVersionHeaderInterceptor())
                .addInterceptor(interceptor)
                .connectTimeout(Consts.Network.TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(Consts.Network.TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(Consts.Network.TIMEOUT, TimeUnit.SECONDS)
                .build();

        String url = Consts.Network.WEB_API;

        retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        service = retrofit.create(IService.class);

        errorConverter = retrofit.responseBodyConverter(Error.class, new Annotation[0]);

        // для загрузки больших данных
        interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);
        client = new OkHttpClient.Builder()
                .followRedirects(true)
                .addInterceptor(new BuildVersionHeaderInterceptor())
                .addInterceptor(interceptor)
                .connectTimeout(Consts.Network.TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(Consts.Network.TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(Consts.Network.TIMEOUT, TimeUnit.SECONDS)
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        service2 = retrofit.create(IService.class);
    }

    private <T> Observable.Transformer<T, T> setup() {
        return setup(Consts.Network.TIMEOUT, Consts.Network.RETRIES);
    }

    private <T> Observable.Transformer<T, T> setup(final int timeout, final int retries) {
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(final Observable<T> observable) {
                if(Controller.getInstance().getSettings() == null){
                    return settings()
                            .flatMap(new Func1<Result<Settings>, Observable<T>>() {
                                @Override
                                public Observable<T> call(Result<Settings> settingsResult) {
                                    Controller.getInstance().setSettings(settingsResult.data);
                                    return observable
                                            .subscribeOn(Schedulers.io())
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .timeout(timeout, TimeUnit.SECONDS)
                                            .retry(retries);
                                }
                            });
                }else
                    return observable
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .timeout(timeout, TimeUnit.SECONDS)
                        .retry(retries);
            }
        };
    }

//    private Observable.Transformer<Result<WifiToken>, Result<WifiToken>> follow307(final User user) {
//        Log.w(TAG, "follow307");
//        return new Observable.Transformer<Result<WifiToken>, Result<WifiToken>>() {
//            @Override
//            public Observable<Result<WifiToken>> call(final Observable<Result<WifiToken>> observable) {
//                Log.w(TAG, "follow307 call");
//                return observable
//                        .onErrorResumeNext(new Func1<Throwable, Observable<Result<WifiToken>>>() {
//                            @Override
//                            public Observable<Result<WifiToken>> call(Throwable throwable){
//                                Log.w(TAG, "follow307 call onErrorResumeNext ");
//                                if(throwable instanceof HttpException){
//                                    Log.w(TAG, "follow307 call onErrorResumeNext HttpException");
//                                    if(((HttpException) throwable).code() == 307){
//                                        Log.w(TAG, "follow307 call onErrorResumeNext HttpException 307");
//                                        //перегенерировать токен, вернуться в цепочку
//                                        Log.w(TAG, throwable.getMessage());
//                                        throwable.printStackTrace();
//                                        HttpException he = (HttpException) throwable;
//                                        String location = he.response().headers().get("Location");
//
//                                        return REST.getInstance().isWifi(location, user.id, user.token.token, "444");
//                                    }
//                                }
//                                Log.w(TAG, "checkAuth call onErrorResumeNext return", throwable);
//                                //throw (RuntimeException)throwable;
//                                return observable;
//                            }
//                        });
//            }
//        };
//    }

    //=========== API ==================================================================================================

    /*@Override
    public Observable<Result<User>> login(@Field("email") String email, @Field("password") String password, @Field("device") String device, @Field("device_id") String device_id, @Field("push") String push) {
        return service.login(email, password, device, device_id, push).compose(this.<Result<User>>setup());
    }

    @Override
    public Observable<Result<Void>> logout(@Header("user-id") int id, @Header("token") String token) {
        return service.logout(id, token).compose(this.<Result<Void>>setup());
    }

    @Override
    public Observable<Result<User>> loginSocial(int id, String token, @Field("device_id") String deviceId, @Field("social") String social,
                                                @Field("social_id") String social_id, @Field("social_token") String social_token, int add) {
        return service.loginSocial(id, token, deviceId, social, social_id, social_token, add).compose(this.<Result<User>>setup());
    }*/

//    @Override
//    public Call<String> user_status(@Header("session-id") int session_id,
//                                                   @Header("token") String token,
//                                                   @Query("u") String name){
//        return service.user_status(session_id, token, name);
//    }

    @Override
    public Call<String> user_ext(@Header("session-id") int session_id,
                                 @Header("token") String token,
                                 @Query("u") String name){
        return service.user_ext(session_id, token, name);
    }


    @Override
    public Observable<Result<Settings>> settings() {
        return service.settings().subscribeOn(Schedulers.io())
                .timeout(15, TimeUnit.SECONDS)
                .retry(3);
    }

    @Override
    public Observable<Result<User>> login(@Field("name") String email,
                                          @Field("pass") String password,
                                          @Field("device") String device,
                                          @Field("device_id") String device_id,
                                          @Field("push") String push){
        return service.login(email, password, device, device_id, push).compose(this.<Result<User>>setup());
    }

    @Override
    public Observable<Result<Void>> logout(@Header("session-id") int session_id,
            @Header("token") String token) {
        return service.logout(session_id, token).compose(this.<Result<Void>>setup());
    }

    @Override
    public Observable<Result<List<User>>> contacts(@Header("session-id") int session_id,
                                                   @Header("token") String token,
                                                   @Query("search") String search){
        return service.contacts(session_id, token, search).compose(this.<Result<List<User>>>setup());
    }

    public Observable<Result<List<User>>> contacts(Token token,
                                                   @Nullable String search){
        return contacts(token.session_id, token.token, search);
    }

    @Override
    public Observable<Result<List<User>>> get_by_sip(@Header("session-id") int session_id, @Header("token") String token, @Query("search") String sip) {
        return service.get_by_sip(session_id, token, sip).compose(this.<Result<List<User>>>setup());
    }

    public Observable<Result<List<User>>> get_by_sip(Token token,
                                                   @Nullable String sip){
        return get_by_sip(token.session_id, token.token, sip);
    }



    @Override
    public Observable<Result<User>> avatar(@Header("session-id") int session_id,
                                           @Header("token") String token,
                                           @Query("name") String name){
        return service.avatar(session_id, token, name).compose(this.<Result<User>>setup());
    }

    @Override
    public Observable<Result<List<Chat>>> chats(@Header("session-id") int session_id,
                                          @Header("token") String token){
        return service.chats(session_id, token).compose(this.<Result<List<Chat>>>setup());
    }

    public Observable<Result<List<Chat>>> chats(Token token){
        return chats(token.session_id, token.token);
    }

    @Override
    public Observable<Result<List<Chat>>> groups(@Header("session-id") int session_id,
                                           @Header("token") String token){
        return service.groups(session_id, token).compose(this.<Result<List<Chat>>>setup());
    }

    public Observable<Result<List<Chat>>> groups(Token token){
        return groups(token.session_id, token.token);
    }

    @Override
    @Deprecated
    public Observable<Result<Chat>> groups_leave(@Header("session-id") int session_id,
                                                       @Header("token") String token,
                                                       @Query("room_uuid") String room_uuid){
        return service.groups_leave(session_id, token, room_uuid).compose(this.<Result<Chat>>setup());
    }

    public Observable<Result<Chat>> groups_leave(Token token, Chat chat){
        return groups_leave(token.session_id, token.token, chat.uuid);
    }


    @Override
    @Deprecated
    public Observable<Result<Chat>> groups_mute(@Header("session-id") int session_id,
                                                 @Header("token") String token,
                                                 @Query("room_uuid") String room_uuid,
                                                @Header("token") int isMuted){
        return service.groups_mute(session_id, token, room_uuid, isMuted).compose(this.<Result<Chat>>setup());
    }

    public Observable<Result<Chat>> groups_mute(Token token, Chat chat, boolean isMuted){
        int intIsMuted;
        if(isMuted)intIsMuted = 1; else intIsMuted = 0;
        return groups_mute(token.session_id, token.token, chat.uuid, intIsMuted);
    }



    @Override
    public Observable<Result<Chat>> chat(@Header("session-id") int session_id,
                                         @Header("token") String token,
                                         @Field("users") String users,
                                         @Field("name") String name){
        return service.chat(session_id, token, users, name).compose(this.<Result<Chat>>setup());
    }

    public Observable<Result<Chat>> chat(Token token,
                                         List<User> users,
                                         String name){
        String uu = TextUtils.join(",", users);
        String chatName;
        StringBuilder sb = new StringBuilder();
        for (User a : users){
            sb.append(a.name).append(", ");
        }
        chatName = sb.toString().substring(0, sb.toString().length()-2);

//        String tempChatName = TextUtils.join(", ", users);
        return chat(token.session_id, token.token, uu, chatName).compose(this.<Result<Chat>>setup());
    }

    @Override
    @Deprecated
    public Observable<Result<Chat>> group_add(@Header("session-id") int session_id,
                                              @Header("token") String token,
                                              @Query("room_uuid") String room_uuid,
                                              @Field("users") String users) {
        return service.group_add(session_id, token, room_uuid, users).compose(this.<Result<Chat>>setup());
    }

    public Observable<Result<Chat>> group_add(Token token,
                                              Chat chat,
                                              List<User> users) {
        List<User> inChat = chat.getUsers();
        List<User> users2 = new ArrayList<>(users);
        users2.removeAll(inChat);
        if(users2.size() == 0){
            Result<Chat> r = new Result<>();
            r.data = chat;
            r.status = OK;
            return Observable.just(r);
        }else
            return group_add(token.session_id, token.token, chat.uuid, TextUtils.join(",", users2));
    }

    @Override
    public Observable<Result<Chat>> group_remove(@Header("session-id") int session_id,
                                                 @Header("token") String token,
                                                 @Query("room_uuid") String room_uuid,
                                                 @Field("users") String users) {
        return service.group_remove(session_id, token, room_uuid, users).compose(this.<Result<Chat>>setup());
    }

    public Observable<Result<Chat>> group_remove(Token token, Chat chat, List<User> users){
        List<User> inChat = chat.getUsers();
        inChat.removeAll(users);
        if(inChat.size() == 0){
            Result<Chat> r = new Result<>();
            r.data = chat;
            r.status = OK;
            return Observable.just(r);
        }else
            return group_remove(token.session_id, token.token, chat.uuid, TextUtils.join(",", inChat));
    }

    @Override
    @Deprecated
    public Observable<Result<Chat>> chatName(@Header("session-id") int session_id,
                                             @Header("token") String token,
                                             @Query("room_uuid") String room_uuid,
                                             @Field("name") String name){
        return service.chatName(session_id, token, room_uuid, name).compose(this.<Result<Chat>>setup());
    }

    public Observable<Result<Chat>> chatName(Token token,
                                             @Query("room_uuid") String room_uuid,
                                             @Field("name") String name){
        return chatName(token.session_id, token.token, room_uuid, name);
    }

    @Override
    public Observable<Result<List<SipCall>>> calls(@Header("session-id") int session_id,
                                                   @Header("token") String token,
                                                   //@Query("number") String number,
                                                   @Query("offset") int offset,
                                                   @ Query("limit") int limit){
        return service.calls(session_id, token, offset, limit).compose(this.<Result<List<SipCall>>>setup());
    }

    public Observable<Result<List<SipCall>>> calls(Token token,
                                                   @Query("offset") int offset,
                                                   @ Query("limit") int limit){
        return calls(token.session_id, token.token, offset, limit);
    }

    @Override
    public Observable<Result<Attachment>> upload_attach(@Header("session-id") int session_id,
                                                        @Header("token") String token,
                                                        @Header("time") String time,
                                                        @Part MultipartBody.Part file){
        return service2.upload_attach(session_id, token, time, file).compose(this.<Result<Attachment>>setup());
    }

    public Observable<Result<Attachment>> upload_attach(Token token, File file, String time, String mime) {
        RequestBody requestFile = RequestBody.create(MediaType.parse(mime), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
        return upload_attach(token.session_id, token.token, time, body);
    }

    @Override
    public Observable<Result<Attachment>> upload_link(@Header("session-id") int session_id,
                                                        @Header("token") String token,
                                                        @Header("text") String text,
                                                        @Header("name") String name,
                                                        @Header("time") String time,
                                                        @Header("longurl") String url,
                                                        @Part MultipartBody.Part file){
        return service2.upload_link(session_id, token, text, name, time, url, file).compose(this.<Result<Attachment>>setup());
    }

    public Observable<Result<Attachment>> upload_link(Token token, String linkAdress, String text, String name, String time, String mime) {
        RequestBody requestLink = RequestBody.create(MediaType.parse(mime), linkAdress);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", linkAdress, requestLink);
        return upload_link(token.session_id, token.token, text, name, time, linkAdress , body);
    }

    @Override
    public Observable<Result<List<Attachment>>> get_room_attachments(@Header("session-id") int session_id, @Header("token") String token, @Query("room_uuid") String room_uuid) {
        return service.get_room_attachments(session_id, token, room_uuid).compose(this.<Result<List<Attachment>>>setup());
    }

    public Observable<Result<List<Attachment>>> get_room_attachments(Token token, String room_uuid) {
        return get_room_attachments(token.session_id, token.token, room_uuid);
    }

    @Override
    @Deprecated
    public Observable<Result<List<Attachment>>> get_user_attachments(@Header("session-id") int session_id, @Header("token") String token, @Query("user_id") String user_id) {
        return service.get_user_attachments(session_id, token, user_id).compose(this.<Result<List<Attachment>>>setup());
    }

    public Observable<Result<List<Attachment>>> get_user_attachments(Token token, User user) {
        return get_user_attachments(token.session_id, token.token, user.id);
    }

    @Override
    @Deprecated
    public Observable<Result<List<Attachment>>> get_user_pictures(@Header("session-id") int session_id, @Header("token") String token, @Query("user_id") String user_id) {
        return service.get_user_pictures(session_id, token, user_id).compose(this.<Result<List<Attachment>>>setup());
    }


    public Observable<Result<List<Attachment>>> get_user_pictures(Token token, User user) {
        return get_user_pictures(token.session_id, token.token, user.id);
    }

    @Override
    @Deprecated
    public Observable<Result<Attachment>> get_attachment(@Header("session-id") int session_id, @Header("token") String token, @Query("uuid") String uuid) {
        return service.get_attachment(session_id, token, uuid).compose(this.<Result<Attachment>>setup());
    }

    public Observable<Result<Attachment>> get_attachment(Token token, Attachment attachment) {
        return get_attachment(token.session_id, token.token, attachment.uuid);
    }

    @Override
    @Deprecated
    public void push_test(@Header("session-id") int session_id, @Header("token") String token, @Query("uuid") String uuid) {
//        return service.push_test(session_id, token, uuid).compose(this.<Result<Attachment>>setup());
        service.push_test(session_id, token, uuid);
    }

    @Override
    @Deprecated
    public Observable<Result<Void>> register_voip(int session_id, String token, String push) {
        return service.register_voip(session_id, token, push).compose(this.<Result<Void>>setup());
    }

    public Observable<Result<Void>> register_voip(Token token, String push) {
        return register_voip(token.session_id, token.token, push);
    }

    private Picasso picasso;

    public void createSecurePicasso(Context context, final Token token){
        if (context!=null){
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(new HttpLoggingInterceptor())
                    .build();

            picasso = new Picasso.Builder(context)
                    .downloader(new OkHttp3Downloader(client))
                    .indicatorsEnabled(BuildConfig.DEBUG)
                    .build();
        }
    }

    public Picasso getPicasso() {
        return picasso;
    }

    @Override
    public Observable<Result<List<Message>>> messages(@Header("session-id") int session_id,
                                             @Header("token") String token,
                                             @Query("room_uuid") String room_uuid,
                                             @Query("offset") int offset,
                                             @Query("limit") int limit){
        return service.messages(session_id, token, room_uuid, offset, limit).compose(this.<Result<List<Message>>>setup());
    }


    public Observable<Result<List<Message>>> messages(Token token,
                                                      String room_uuid,
                                                      int offset,
                                                      int limit){
        return messages(token.session_id, token.token, room_uuid, offset, limit);
    }


/*
    public Observable<Result<User>> loginSocial(@Nullable User user, @Field("device_id") String deviceId, @Field("social") String social,

                                                @Field("social_id") String social_id, @Field("social_token") String social_token, int add) {
        int id = user == null ? 0 : user.id;
        String token  = user == null ? "" : user.token.token;

        return loginSocial(id, token, deviceId, social, social_id, social_token, add).compose(this.<Result<User>>setup());
    }

    @Override
    public Observable<Result<Settings>> settings() {
        return service.settings().subscribeOn(Schedulers.io())
                .timeout(15, TimeUnit.SECONDS)
                .retry(3);
    }

    @Override
    public Observable<Result<Session>> sessionStart(int id, String token, @Nullable String wifi, String queue, String device, String device_id, String push, int auto) {
        return service.sessionStart(id, token, wifi, queue, device, device_id, push, auto).compose(this.<Result<Session>>setup());
    }

    public Observable<Result<Session>> sessionStart(User user, @Nullable WifiToken wifi, String queue, String device, String device_id, String push, int auto) {
        String wifiTiken = wifi == null ? "" : wifi.token;
        return service.sessionStart(user.id, user.token.token, wifiTiken, queue, device, device_id, push, auto).compose(this.<Result<Session>>setup());
    }

    @Override
    public Observable<Result<Room>> dialog(int id, String token, @Field("users") String user) {
        return service.dialog(id, token, user).compose(this.<Result<Room>>setup());
    }

    @Override
    public Observable<Result<List<Bar>>> barList(int id, String token, String wifi, double lat, double lng) {
        return service.barList(id, token, wifi, lat, lng).compose(this.<Result<List<Bar>>>setup());
    }

    public Observable<Result<List<Bar>>> barList(User user, @Nullable WifiToken wifiToken, @Nullable Location location) {
        String wifi = wifiToken == null || wifiToken.token == null ? "" : wifiToken.token;

        if(location == null){
            location = new Location("mock");
            location.setLatitude(0);
            location.setLongitude(0);
        }

        return barList(user.id, user.token.token, wifi, location.getLatitude(), location.getLongitude());
    }

    @Override
    public Observable<Result<Map<Integer, List<User>>>> chinChins(@Header("user-id") int id, @Header("token") String token, @Header("wifi") String wifi) {
        return service.chinChins(id, token, wifi).compose(this.<Result<Map<Integer,List<User>>>>setup());
    }

    public Observable<Result<Map<Integer, List<User>>>> chinChins(User user, WifiToken wifiToken) {
        String wifi = wifiToken == null || wifiToken.token == null ? "" : wifiToken.token;
        return chinChins(user.id, user.token.token, wifi);
    }

    @Override
    public Observable<Result<Bar>> bar(@Header("user-id") int id, @Header("token") String token, @Header("wifi") String wifi, @Query("id") int restaurant_id) {
        return service.bar(id, token, wifi, restaurant_id).compose(this.<Result<Bar>>setup());
    }

    public Observable<Result<Bar>> bar(User user, @Nullable WifiToken wifi, int restaurant_id) {
        String wifiToken = wifi == null ? "" : wifi.token;
        return bar(user.id, user.token.token, wifiToken, restaurant_id);
    }

    @Override
    public Observable<Result<List<Message>>> messages(int id, String token, String wifi, String room_uuid, int offset) {
        return service.messages(id, token, wifi, room_uuid, offset).compose(this.<Result<List<Message>>>setup());
    }

    public Observable<Result<List<Message>>> messages(User user, @Nullable WifiToken wifi, String room_uuid, int offset) {
        String wifiToken = wifi == null ? "" : wifi.token;
        return messages(user.id, user.token.token, wifiToken, room_uuid, offset);
    }


    @Override
    public Observable<Result<WifiToken>> isWifi(String url, @Header("user_id") int id, @Header("token") String token, String param) {
        if(BuildConfig.PERMANENT)
            return service.isWifi("?r=json/is-wifi&wifi=6fb22881-67d5-4420-b5ed-5ea1fb196a7c", id, token, param);
        else
            return service.isWifi(url, id, token, param).compose(this.<Result<WifiToken>>setup());
    }

    public Observable<Result<WifiToken>> isWifi(User user) {
        return isWifi("?r=json/is-wifi", user.id, user.token.token, "asljoind").compose(follow307(user));
    }

    @Override
    public Observable<Result<User>> profile(int id, String token, String wifi, String user) {
        return service.profile(id, token, wifi, user).compose(this.<Result<User>>setup());
    }

    public Observable<Result<User>> profile(User user, WifiToken wifi, String user_uid) {
        String wifiToken = wifi == null ? "" : wifi.token;
        return profile(user.id, user.token.token, wifiToken, user_uid);
    }

    @Override
    public Observable<Result<Void>> set_profile( int id, String token, String settings, String sex) {
        return service.set_profile(id, token, settings, sex).compose(this.<Result<Void>>setup());
    }

    public Observable<Result<Void>> set_profile(User user, @Field("settings") String settings, String sex) {
        return set_profile(user.id, user.token.token, settings, sex);
    }

    @Override
    public Observable<Result<String>> upload_avatar(int id, String token, MultipartBody.Part file) {
        return service2.upload_avatar(id, token, file).compose(this.<Result<String>>setup());
    }

    public Observable<Result<String>> upload_avatar(User user, File file) {
        RequestBody requestFile =
                RequestBody.create(
                        MediaType.parse("image/jpeg"),
                        file
                );
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("file", file.getName(), requestFile);
        return upload_avatar(user.id, user.token.token, body);
    }*/

    public abstract static class DataSubscriber<T> extends Subscriber<Result<T>> {
        @Override
        public void onCompleted() {
            Log.v(TAG, "DataSubscriber.onCompleted");
        }

        abstract public void onData(T data);

        @Override
        final public void onNext(Result<T> t) {
            Gson gson = new Gson();
            Log.d(TAG, gson.toJson(t));

            if(t.status != OK){
                Log.w(TAG, t.error);
                onError(new Throwable(t.error));
            }else{
                onData(t.data);
            }
        }

        @Override
        public void onError(Throwable e) {
            Log.w(TAG, "DataSubscriber.onError", e);

            EventBus.getDefault().postSticky(new ErrorEvent(e));
        }
    }
}
