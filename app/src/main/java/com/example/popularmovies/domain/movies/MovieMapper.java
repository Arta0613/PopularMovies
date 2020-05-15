package com.example.popularmovies.domain.movies;

import androidx.annotation.NonNull;

import com.example.popularmovies.data.MoviesApi;

public class MovieMapper {

    @NonNull
    public Movie mapMovie(@NonNull final MoviesApi moviesApi) {
        return new Movie(
                moviesApi.id + "",
                moviesApi.originalTitle,
                moviesApi.posterPath,
                moviesApi.backdropPath,
                moviesApi.releaseDate,
                moviesApi.overview,
                moviesApi.voteAverage
        );
    }

    @NonNull
    public MovieItem mapMovieItem(@NonNull final Movie movie) {
        return new MovieItem(movie);
    }
}