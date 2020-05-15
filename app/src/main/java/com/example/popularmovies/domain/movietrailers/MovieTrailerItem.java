package com.example.popularmovies.domain.movietrailers;

import androidx.annotation.NonNull;

public class MovieTrailerItem {

    private static final String MOVIE_TRAILER_YOUTUBE_BASE_URL = "https://www.youtube.com/watch?v=";
    private static final String MOVIE_TRAILER_VIMEO_BASE_URL = "https://vimeo.com/";
    private static final String MOVIE_TRAILER_PREVIEW_BASE_URL = "https://img.youtube.com/vi/{id}/0.jpg";

    private final MovieTrailer movieTrailer;

    public MovieTrailerItem(@NonNull final MovieTrailer movieTrailer) {
        this.movieTrailer = movieTrailer;
    }

    @NonNull
    public final String getMovieTrailerId() {
        return movieTrailer.getTrailerId();
    }

    @NonNull
    public final String getMovieTrailerName() {
        return movieTrailer.getTrailerName();
    }

    @NonNull
    public String getMovieTrailerSite() {
        return movieTrailer.getTrailerSite();
    }

    @NonNull
    public String getMovieTrailerType() {
        return movieTrailer.getTrailerType();
    }

    @NonNull
    public final String getTrailerItemHeader() {
        return getMovieTrailerSite() + " : " + getMovieTrailerType();
    }

    @NonNull
    public final String getMovieTrailerUrl() {
        switch (getMovieTrailerSite()) {
            case "YouTube":
                return MOVIE_TRAILER_YOUTUBE_BASE_URL + movieTrailer.getTrailerId();
            case "Vimeo":
                return MOVIE_TRAILER_VIMEO_BASE_URL + movieTrailer.getTrailerId();
            default:
                return "Unknown";
        }
    }

    @NonNull
    public final String getMovieTrailerThumbnail() {
        if (getMovieTrailerSite().equals("YouTube")) {
            return MOVIE_TRAILER_PREVIEW_BASE_URL.replace("{id}", movieTrailer.getTrailerId());
        }

        return "No Preview";
    }
}