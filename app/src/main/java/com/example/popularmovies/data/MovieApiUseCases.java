package com.example.popularmovies.data;

import com.example.popularmovies.domain.MovieItem;
import com.example.popularmovies.domain.MovieMapper;
import com.example.popularmovies.network.MovieDatabaseApi;
import com.example.popularmovies.network.RetrofitServiceGenerator;

import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public class MovieApiUseCases {

    private final MovieDatabaseApi moviesDatabaseApi =
            RetrofitServiceGenerator.createMoviesApiService(MovieDatabaseApi.class);

    private final MovieMapper movieMapper = new MovieMapper();

    public final @NonNull Single<List<MovieItem>> getPopularMovies() {
        return getMovies(moviesDatabaseApi.getPopularMovies());

    }

    public final @NonNull Single<List<MovieItem>> getTopRatedMovies() {
        return getMovies(moviesDatabaseApi.getTopRated());
    }

    private @NonNull Single<List<MovieItem>> getMovies(final Observable<MoviesApiResponse> response) {
        return response
                .flatMapIterable(moviesApiResponse -> moviesApiResponse.movies)
                .map(movieMapper::mapMovie)
                .map(movieMapper::mapMovieItem)
                .toList();
    }
}