package com.mshvdvskgmail.technoparkmessenger.network;

import android.util.Log;

import com.google.gson.Gson;

import java.io.File;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
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
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import com.mshvdvskgmail.technoparkmessenger.Consts;
import com.mshvdvskgmail.technoparkmessenger.Controller;
import com.mshvdvskgmail.technoparkmessenger.network.model.*;

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
                                            .timeout(timeout, TimeUnit.SECONDS)
                                            .retry(retries);
                                }
                            });
                }else
                    return observable
                        .subscribeOn(Schedulers.io())
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

    @Override
    public Call<String> user_status(@Header("session-id") int session_id,
                                                   @Header("token") String token,
                                                   @Query("u") String name){
        return service.user_status(session_id, token, name);
    }

    @Override
    public Call<String> user_ext(@Header("session-id") int session_id,
                                 @Header("token") String token,
                                 @Query("u") String name){
        return service.user_ext(session_id, token, name);
    }


//    @Override
//    public Observable<Result<Settings>> settings(){
//        return service.settings().compose(this.<Result<Settings>>setup());
//    }

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

    @Override
    public Observable<Result<List<Chat>>> groups(@Header("session-id") int session_id,
                                           @Header("token") String token){
        return service.groups(session_id, token).compose(this.<Result<List<Chat>>>setup());
    }

    @Override
    public Observable<Result<Chat>> chat(@Header("session-id") int session_id,
                                         @Header("token") String token,
                                         @Field("users") String users,
                                         @Field("name") String name){
        return service.chat(session_id, token, users, name).compose(this.<Result<Chat>>setup());
    }

    @Override
    public Observable<Result<Chat>> chatName(@Header("session-id") int session_id,
                                             @Header("token") String token,
                                             @Query("room_uuid") String room_uuid,
                                             @Field("name") String name){
        return service.chatName(session_id, token, room_uuid, name).compose(this.<Result<Chat>>setup());
    }

    @Override
    public Observable<Result<List<SipCall>>> calls(@Header("session-id") int session_id,
                                                   @Header("token") String token,
                                                   @Query("number") String number,
                                                   @Query("offset") String offset,
                                                   @ Query("limit") String limit){
        return service.calls(session_id, token, number, offset, limit).compose(this.<Result<List<SipCall>>>setup());
    }

    @Override
    public Observable<Result<String>> upload_attach(@Header("session-id") int session_id,
                                                    @Header("token") String token,
                                                    @Part MultipartBody.Part file){
        return service.upload_attach(session_id, token, file).compose(this.<Result<String>>setup());
    }

    public Observable<Result<String>> upload_file(int session_id, String token, File file, String mime) {
        Log.w(TAG, "file "+ file);
        RequestBody requestFile =
                RequestBody.create(
                        MediaType.parse(mime),
                        file
                );
        Log.w(TAG, "requestFile "+ requestFile);
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("file", file.getName(), requestFile);
        Log.w(TAG, "body "+ body);
        return upload_attach(session_id, token, body);
    }

    @Override
    public Observable<Result<List<Message>>> messages(@Header("session-id") int session_id,
                                             @Header("token") String token,
                                             @Query("room_uuid") String room_uuid,
                                             @Query("offset") String offset,
                                             @ Query("limit") String limit){
        return service.messages(session_id, token, room_uuid, offset, limit).compose(this.<Result<List<Message>>>setup());
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
        }
    }
}
