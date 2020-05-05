package com.example.popularmovies.network;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MoviesServiceGenerator {

    private static final String BASE_URL = "https://api.themoviedb.org/3/";

    public static Retrofit.Builder buildMoviesServiceGenerator() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .client(InterceptorClient.createInterceptor());

    }
}