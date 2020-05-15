package com.example.popularmovies.data;

import androidx.annotation.NonNull;

import com.example.popularmovies.data.movies.MoviesApiResponse;
import com.example.popularmovies.domain.movies.MovieItem;
import com.example.popularmovies.domain.movies.MovieMapper;
import com.example.popularmovies.domain.movietrailers.MovieTrailerItem;
import com.example.popularmovies.domain.movietrailers.MovieTrailerMapper;
import com.example.popularmovies.network.MovieDatabaseApi;
import com.example.popularmovies.network.RetrofitServiceGenerator;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;

public class MovieApiUseCases {

    private final MovieDatabaseApi moviesDatabaseApi =
            RetrofitServiceGenerator.createMoviesApiService(MovieDatabaseApi.class);

    private final MovieTrailerMapper moviesTrailerMapper = new MovieTrailerMapper();

    private final MovieMapper movieMapper = new MovieMapper();

    public final @NonNull Single<List<MovieItem>> getPopularMovies() {
        return getMovies(moviesDatabaseApi.getPopularMovies());

    }

    public final @NonNull Single<List<MovieItem>> getTopRatedMovies() {
        return getMovies(moviesDatabaseApi.getTopRated());
    }

    @NonNull
    public final Single<List<MovieTrailerItem>> getMovieTrailers(@NonNull final String movieId) {
        return moviesDatabaseApi.getMovieTrailers(movieId)
                .flatMapIterable(moviesTrailersApiResponse -> moviesTrailersApiResponse.movieTrailers)
                .map(moviesTrailerMapper::mapMovieTrailer)
                .map(moviesTrailerMapper::mapMovieTrailerItem)
                .toList();
    }

    private @NonNull Single<List<MovieItem>> getMovies(@NonNull final Observable<MoviesApiResponse> response) {
        return response
                .flatMapIterable(moviesApiResponse -> moviesApiResponse.movies)
                .map(movieMapper::mapMovie)
                .map(movieMapper::mapMovieItem)
                .toList();
    }
}