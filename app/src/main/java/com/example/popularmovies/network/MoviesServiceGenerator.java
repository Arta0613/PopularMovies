package com.example.popularmovies.network;

import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MoviesServiceGenerator {

    private static final String BASE_URL = "https://api.themoviedb.org/3/";

    public static Retrofit.Builder buildMoviesServiceGenerator() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .client(InterceptorClient.createInterceptor());

    }
}