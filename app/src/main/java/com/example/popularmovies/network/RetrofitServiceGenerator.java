package com.example.popularmovies.network;

import retrofit2.Retrofit;

public class RetrofitServiceGenerator {

    private final static Retrofit.Builder movieApiBuilder = MoviesServiceGenerator.buildMoviesServiceGenerator();

    public static <S> S createMoviesApiService(final Class<S> serviceClass) {
        return build().create(serviceClass);
    }

    private static Retrofit build() {
        return RetrofitServiceGenerator.movieApiBuilder.build();
    }
}