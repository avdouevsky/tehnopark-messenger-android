package com.mshvdvskgmail.technoparkmessenger.network;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import rx.Observable;
import com.mshvdvskgmail.technoparkmessenger.network.model.*;

/**
 * Created by andrey on 19.01.2017.
 */

public interface IService {

//    @GET("?r=messages/json/u-s")
//    public Call<String> user_status(@Header("session-id") int session_id,
//                                                   @Header("token") String token,
//                                                   @Query("u") String name);

    @GET("?r=messages/json/u-e")
    public Call<String> user_ext(@Header("session-id") int session_id,
                                 @Header("token") String token,
                                 @Query("u") String name);


    @POST("?r=messages/json/settings")
    public Observable<Result<Settings>> settings();

    @POST("?r=messages/json/login")
    @FormUrlEncoded
    public Observable<Result<User>> login(@Field("name") String email,
                                          @Field("pass") String password,
                                          @Field("device") String device,
                                          @Field("device_id") String device_id,
                                          @Field("push") String push);

    @POST("?r=json/logout")
    public Observable<Result<Void>> logout(@Header("session-id") int session_id,
                                           @Header("token") String token);


    @GET("?r=messages/json/contacts")
    public Observable<Result<List<User>>> contacts(@Header("session-id") int session_id,
                                             @Header("token") String token,
                                             @Query("search") String search);

    @GET("?r=messages/json/avatar")
    public Observable<Result<User>> avatar(@Header("session-id") int session_id,
                                           @Header("token") String token,
                                           @Query("name") String name);

    @GET("?r=messages/json/chats")
    public Observable<Result<List<Chat>>> chats(@Header("session-id") int session_id,
                                          @Header("token") String token);

    @GET("?r=messages/json/groups")
    public Observable<Result<List<Chat>>> groups(@Header("session-id") int session_id,
                                           @Header("token") String token);

    @POST("?r=messages/json/chat")
    @FormUrlEncoded
    public Observable<Result<Chat>> chat(@Header("session-id") int session_id,
                                         @Header("token") String token,
                                         @Field("users") String users,
                                         @Field("name") String name);

    @POST("?r=messages/json/chat-name")
    @FormUrlEncoded
    public Observable<Result<Chat>> chatName(@Header("session-id") int session_id,
                                             @Header("token") String token,
                                             @Query("room_uuid") String room_uuid,
                                             @Field("name") String name);

    @GET("?r=messages/json/calls")
    public Observable<Result<List<SipCall>>> calls(@Header("session-id") int session_id,
                                                   @Header("token") String token,
                                                   //@Query("number") String number,
                                                   @Query("offset") int offset,
                                                   @Query("limit") int limit);

    @Multipart
    @POST("?r=messages/attach/upload")
    public Observable<Result<Attachment>> upload_attach(@Header("session-id") int session_id,
                                                    @Header("token") String token,
                                                    @Part MultipartBody.Part file);

    @GET("?r=messages/json/messages")
    public Observable<Result<List<Message>>> messages(@Header("session-id") int session_id,
                                             @Header("token") String token,
                                             @Query("room_uuid") String room_uuid,
                                             @Query("offset") int offset,
                                             @Query("limit") int limit);




    /////////////////////////////////////////////////
//    @POST("?r=json/logout")
//    public Observable<Result<Void>> logout(@Header("user-id") int id, @Header("token") String token);
//
//    @POST("?r=json/login-social")
//    @FormUrlEncoded
//    public Observable<Result<User>> loginSocial(@Header("user-id") int id,
//                                                @Header("token") String token,
//                                                @Field("device_id") String deviceId,
//                                                @Field("social") String social,
//                                                @Field("social_id") String social_id,
//                                                @Field("social_token") String social_token,
//                                                @Query("add") int add
//    );
//
//
////    @GET("?r=json/settings")
////    public Observable<Result<Settings>> settings();
//
//    @POST("?r=json/session-start")
//    @FormUrlEncoded
//    public Observable<Result<Session>> sessionStart(@Header("user-id") int id, @Header("token") String token,
//                                                    @Header("wifi") @Nullable String wifi,
//                                                    @Field("queue") String queue,
//                                                    @Field("device") String device,
//                                                    @Field("device_id") String device_id,
//                                                    @Field("push") String push,
//                                                    @Query("auto") int auto
//    );
//
//    @POST("?r=json/dialog")
//    @FormUrlEncoded
//    public Observable<Result<Room>> dialog(@Field("user-id") int id, @Header("token") String token,
//                                           @Field("users") String user);
//
//    @GET("?r=json/bar-list")
//    //@FormUrlEncoded
//    public Observable<Result<List<Bar>>> barList(@Header("user-id") int id,
//                                                 @Header("token") String token,
//                                                 @Header("wifi") String wifi,
//                                                 @Header("lng") double lng,
//                                                 @Header("lat") double lat
//    );
//
//    @GET("?r=json/chin-chin")
//    public Observable<Result<Map<Integer, List<User>>>> chinChins(@Header("user-id") int id,
//                                                                  @Header("token") String token,
//                                                                  @Header("wifi") String wifi);
//
//    @GET("?r=json/bar")
//    public Observable<Result<Bar>> bar(@Header("user-id") int id,
//                                       @Header("token") String token,
//                                       @Header("wifi") String wifi,
//                                       @Query("id") int restaurant_id);
//
//    //http://hurma.h.xsrv.ru/basic/web/?r=json/messages&room_uuid=room_d1b4a6bb6abbe5c00d196493e588ceb2&debug=1&user_id=12
//    @GET("?r=json/messages")
//    public Observable<Result<List<Message>>> messages(@Header("user-id") int id,
//                                                      @Header("token") String token,
//                                                      @Header("wifi") String wifi,
//                                                      @Query("room_uuid") String room_uuid,
//                                                      @Query("offset") int offset
//    );
//
//    @POST/*("?r=json/is-wifi")*/
//    @FormUrlEncoded
//    public Observable<Result<WifiToken>> isWifi(@Url String url,
//                                                @Header("user-id") int id,
//                                                @Header("token") String token,
//                                                //@Header("wifi") String wifi,
//                                                @Field("some_param") String post_param);
//
//    @GET("?r=json/profile")
//    public Observable<Result<User>> profile(@Header("user-id") int id,
//                                            @Header("token") String token,
//                                            @Header("wifi") String wifi,
//                                            @Query("user") String user);
//
//    @POST("?r=json/profile")
//    @FormUrlEncoded
//    public Observable<Result<Void>> set_profile(@Header("user-id") int id,
//                                                @Header("token") String token,
//                                                //@Header("wifi") String wifi,
//                                                @Field("settings") String settings, @Field("sex") String sex);
//
//    @Multipart
//    @POST("?r=avatar/upload")
//    public Observable<Result<String>> upload_avatar(@Header("user-id") int id,
//                                                    @Header("token") String token,
//                                                    @Part MultipartBody.Part file);


}
