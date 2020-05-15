package com.example.popularmovies.domain.movies;

import androidx.annotation.NonNull;

public class MovieItem {

    private final String MOVIE_IMAGE_BASE_URL = "https://image.tmdb.org/t/p/";
    public static final String MOVIE_KEY = "movie_key";

    private final Movie movie;

    public MovieItem(@NonNull final Movie movie) {
        this.movie = movie;
    }

    @NonNull
    public final Movie getMovie() {
        return movie;
    }

    @NonNull
    public final String getMovieId() {
        return movie.getId();
    }

    @NonNull
    public final String getMovieTitle() {
        return movie.getName();
    }

    @NonNull
    public final String getMoviePosterUrl() {
        return movie.getPosterUrl();
    }

    @NonNull
    public final String getMovieBackdropUrl() {
        return movie.getBackdropUrl();
    }

    @NonNull
    public final String getMoviePosterFullUrl() {
        return MOVIE_IMAGE_BASE_URL + "w342" + getMoviePosterUrl();
    }

    @NonNull
    public final String getMovieDetailImageUrl() {
        return MOVIE_IMAGE_BASE_URL + "original" + getMovieBackdropUrl();
    }

    @NonNull
    public final String getMovieReleaseDate() {
        return movie.getReleaseDate();
    }

    @NonNull
    public final String getSynopsis() {
        return movie.getSynopsis();
    }

    @NonNull
    public final String getMovieUserRating() {
        return movie.getUserRating() + "";
    }
}