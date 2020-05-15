package com.example.popularmovies.network;

import androidx.annotation.NonNull;

import com.example.popularmovies.data.moviereviews.MoviesReviewsApiResponse;
import com.example.popularmovies.data.movies.MoviesApiResponse;
import com.example.popularmovies.data.movietrailers.MoviesTrailersApiResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MovieDatabaseApi {

    @GET("movie/popular")
    Observable<MoviesApiResponse> getPopularMovies();

    @GET("movie/top_rated")
    Observable<MoviesApiResponse> getTopRated();

    @GET("movie/{id}/videos")
    Observable<MoviesTrailersApiResponse> getMovieTrailers(@NonNull final @Path("id") String movieId);

    @GET("movie/{id}/reviews")
    Observable<MoviesReviewsApiResponse> getMovieReviews(@NonNull final @Path("id") String movieId);
}