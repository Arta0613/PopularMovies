package com.example.popularmovies.domain.movietrailers;

import androidx.annotation.NonNull;

import com.example.popularmovies.data.movietrailers.MoviesTrailersApi;

public class MovieTrailerMapper {

    @NonNull
    public MovieTrailer mapMovieTrailer(@NonNull final MoviesTrailersApi moviesTrailersApi) {
        return new MovieTrailer(
                moviesTrailersApi.key,
                moviesTrailersApi.name,
                moviesTrailersApi.site,
                moviesTrailersApi.type
        );
    }

    @NonNull
    public MovieTrailerItem mapMovieTrailerItem(@NonNull final MovieTrailer movieTrailer) {
        return new MovieTrailerItem(movieTrailer);
    }
}