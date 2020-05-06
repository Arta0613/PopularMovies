package com.example.popularmovies.domain;

public class MovieItem {

    private final String MOVIE_IMAGE_BASE_URL = "https://image.tmdb.org/t/p/";
    public static final String MOVIE_KEY = "movie_key";

    private final Movie movie;

    public MovieItem(final Movie movie) {
        this.movie = movie;
    }

    public final Movie getMovie() {
        return movie;
    }

    public final String getMovieTitle() {
        return movie.getName();
    }

    public final String getMoviePosterUrl() {
        return MOVIE_IMAGE_BASE_URL + "w342" + movie.getPosterUrl();
    }

    public final String getMovieDetailImageUrl() {
        return MOVIE_IMAGE_BASE_URL + "original" + movie.getBackdropUrl();
    }

    public final String getMovieReleaseDate() {
        return movie.getReleaseDate();
    }

    public final String getSynopsis() {
        return movie.getSynopsis();
    }

    public final String getMovieUserRating() {
        return movie.getUserRating() + "";
    }
}