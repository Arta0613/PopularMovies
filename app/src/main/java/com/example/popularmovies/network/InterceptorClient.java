package com.example.popularmovies.network;

import com.example.popularmovies.BuildConfig;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class InterceptorClient {

    public static OkHttpClient createInterceptor() {

        Interceptor interceptor = chain -> {
            Request original = chain.request();
            HttpUrl originalUrl = original.url();

            HttpUrl newUrl = originalUrl.newBuilder().addQueryParameter(
                    BuildConfig.API_KEY, BuildConfig.API_KEY_VALUE
            ).build();

            Request.Builder requestBuilder = original.newBuilder()
                    .url(newUrl)
                    .method(original.method(), original.body());

            Request request = requestBuilder.build();
            return chain.proceed(request);
        };

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.interceptors().add(interceptor);

        return builder.build();
    }
}