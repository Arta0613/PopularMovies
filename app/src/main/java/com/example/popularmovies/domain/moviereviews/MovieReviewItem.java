package com.example.popularmovies.domain.moviereviews;

import androidx.annotation.NonNull;

public class MovieReviewItem {

    private final MovieReview movieReview;

    public MovieReviewItem(@NonNull final MovieReview movieReview) {
        this.movieReview = movieReview;
    }

    @NonNull
    public final String getMovieReview() {
        return movieReview.getReviewContent();
    }

    @NonNull
    public final String getMovieReviewId() {
        return movieReview.getReviewId();
    }

    @NonNull
    public final String getMovieReviewContent() {
        return movieReview.getReviewContent();
    }

    @NonNull
    public final String getMovieReviewAuthor() {
        return "- " + movieReview.getReviewAuthor();
    }

    @NonNull
    public final String getFullReviewUrl() {
        return movieReview.getReviewUrl();
    }
}