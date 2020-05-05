package com.example.popularmovies.network;

import com.example.popularmovies.data.MoviesApiResponse;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;

public interface MovieDatabaseApi {

    @GET("movie/popular")
    Observable<MoviesApiResponse> getPopularMovies();

    @GET("movie/top_rated")
    Observable<MoviesApiResponse> getTopRated();

}
