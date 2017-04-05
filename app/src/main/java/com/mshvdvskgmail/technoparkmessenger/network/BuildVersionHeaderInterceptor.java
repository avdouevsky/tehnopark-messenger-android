package com.mshvdvskgmail.technoparkmessenger.network;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import com.mshvdvskgmail.technoparkmessenger.BuildConfig;

/**
 * Created by andrey on 17.08.2016.
 */
public class BuildVersionHeaderInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();

        Request request = original.newBuilder()
                .header("x-version-code", String.format("%d", BuildConfig.VERSION_CODE))
                .header("x-version-name", BuildConfig.VERSION_NAME)
                .header("x-system", "Android")
                .header("x-package", BuildConfig.APPLICATION_ID)
                .header("x-flavor", BuildConfig.FLAVOR)
                .header("x-debug", (BuildConfig.DEBUG) ? "true" : "false")
                .header("x-amqp-auto", "1")
//                .header("Content-Type", "application/json;charset=utf-8")
                .header("Accept", "application/json;q=1")
                .header("accept-encoding", "identity")
                .method(original.method(), original.body())
                .build();

        return chain.proceed(request);
    }
}
