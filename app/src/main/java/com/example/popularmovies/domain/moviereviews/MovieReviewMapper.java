package com.example.popularmovies.domain.moviereviews;

import androidx.annotation.NonNull;

import com.example.popularmovies.data.moviereviews.MoviesReviewsApi;

public class MovieReviewMapper {

    @NonNull
    public MovieReview mapMovieReview(@NonNull final MoviesReviewsApi moviesReviewsApi) {
        return new MovieReview(
                moviesReviewsApi.id,
                moviesReviewsApi.author,
                moviesReviewsApi.content,
                moviesReviewsApi.url
        );
    }

    @NonNull
    public MovieReviewItem mapMovieReviewItem(@NonNull final MovieReview movieReview) {
        return new MovieReviewItem(movieReview);
    }
}