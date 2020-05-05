package com.example.popularmovies.domain;

import com.example.popularmovies.data.MovieApi;

public class MovieMapper {

    public Movie mapMovie(final MovieApi movieApi) {
        return new Movie(
                movieApi.originalTitle,
                movieApi.posterPath,
                movieApi.backdropPath,
                movieApi.releaseDate,
                movieApi.overview,
                movieApi.voteAverage
        );
    }

    public MovieItem mapMovieItem(final Movie movie) {
        return new MovieItem(movie);
    }
}