package com.mshvdvskgmail.technoparkmessenger.network;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Admin on 30.01.2017.
 */

public interface RouteService {
    @GET("/maps/api/directions/json")
    Observable<RouteResponse> getRoute(
            @Query(value = "origin", encoded = false) String position,
            @Query(value = "destination", encoded = false) String destination,
            @Query("sensor") boolean sensor,
            @Query("language") String language);
}
