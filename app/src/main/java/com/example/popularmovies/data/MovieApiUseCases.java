package com.example.popularmovies.data;

import com.example.popularmovies.data.movies.MoviesApiResponse;
import com.example.popularmovies.domain.moviereviews.MovieReviewItem;
import com.example.popularmovies.domain.moviereviews.MovieReviewMapper;
import com.example.popularmovies.domain.movies.MovieItem;
import com.example.popularmovies.domain.movies.MovieMapper;
import com.example.popularmovies.domain.movietrailers.MovieTrailerItem;
import com.example.popularmovies.domain.movietrailers.MovieTrailerMapper;
import com.example.popularmovies.network.MovieDatabaseApi;
import com.example.popularmovies.network.RetrofitServiceGenerator;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.annotations.NonNull;

public class MovieApiUseCases {

    private final MovieDatabaseApi moviesDatabaseApi =
            RetrofitServiceGenerator.createMoviesApiService(MovieDatabaseApi.class);

    private final MovieMapper movieMapper = new MovieMapper();
    private final MovieTrailerMapper moviesTrailerMapper = new MovieTrailerMapper();
    private final MovieReviewMapper moviesReviewMapper = new MovieReviewMapper();

    @NonNull
    public final Single<List<MovieItem>> getPopularMovies() {
        return getMovies(moviesDatabaseApi.getPopularMovies());
    }

    @NonNull
    public final Single<List<MovieItem>> getTopRatedMovies() {
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

    @NonNull
    public final Single<List<MovieReviewItem>> getMovieReviews(@NonNull final String movieId) {
        return moviesDatabaseApi.getMovieReviews(movieId)
                .flatMapIterable(moviesReviewsApiResponse -> moviesReviewsApiResponse.moviesReviews)
                .map(moviesReviewMapper::mapMovieReview)
                .map(moviesReviewMapper::mapMovieReviewItem)
                .toList();
    }

    @NonNull
    private Single<List<MovieItem>> getMovies(@NonNull final Observable<MoviesApiResponse> response) {
        return response
                .flatMapIterable(moviesApiResponse -> moviesApiResponse.movies)
                .map(movieMapper::mapMovie)
                .map(movieMapper::mapMovieItem)
                .toList();
    }
}