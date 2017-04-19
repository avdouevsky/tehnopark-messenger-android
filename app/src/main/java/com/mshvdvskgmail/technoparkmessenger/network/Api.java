package com.mshvdvskgmail.technoparkmessenger.network;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import com.mshvdvskgmail.technoparkmessenger.Consts;

/**
 * Created by Admin on 30.01.2017.
 */
public class Api {
/*
    private static Api ourInstance = new Api();
    private RouteService routeService;


    private Api() {
        HttpLoggingInterceptor routeInterceptor = new HttpLoggingInterceptor();
        routeInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        Retrofit routeAdapter = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(Consts.MAPS_API_BASE_URL)
                .client(new OkHttpClient.Builder().addInterceptor(routeInterceptor).build())
                .build();
        routeService = routeAdapter.create(RouteService.class);

    }

    public RouteService getRouteService() {
        return routeService;
    }

    public static Api getInstance() {
        return ourInstance;
    }
    */
}
