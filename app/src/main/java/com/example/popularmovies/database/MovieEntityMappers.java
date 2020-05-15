package com.example.popularmovies.database;

import androidx.annotation.NonNull;

import com.example.popularmovies.database.model.MovieEntity;
import com.example.popularmovies.database.model.MovieReviewEntity;
import com.example.popularmovies.database.model.MovieTrailerEntity;
import com.example.popularmovies.domain.moviereviews.MovieReview;
import com.example.popularmovies.domain.moviereviews.MovieReviewItem;
import com.example.popularmovies.domain.movies.Movie;
import com.example.popularmovies.domain.movies.MovieItem;
import com.example.popularmovies.domain.movietrailers.MovieTrailer;
import com.example.popularmovies.domain.movietrailers.MovieTrailerItem;

import java.util.ArrayList;
import java.util.List;

public class MovieEntityMappers {

    @NonNull
    public static MovieEntity mapMovieEntityItem(@NonNull final MovieItem movieItem) {
        return new MovieEntity(
                movieItem.getMovieId(),
                movieItem.getMovieTitle(),
                movieItem.getMoviePosterUrl(),
                movieItem.getMovieBackdropUrl(),
                movieItem.getMovieReleaseDate(),
                movieItem.getSynopsis(),
                Double.parseDouble(movieItem.getMovieUserRating())
        );
    }

    @NonNull
    public static MovieItem mapMovieItem(@NonNull final MovieEntity movieEntity) {
        return new MovieItem(new Movie(
                movieEntity.getId(),
                movieEntity.getName(),
                movieEntity.getPosterUrl(),
                movieEntity.getBackdropUrl(),
                movieEntity.getReleaseDate(),
                movieEntity.getSynopsis(),
                movieEntity.getUserRating()
        ));
    }

    @NonNull
    public static List<MovieTrailerEntity> mapMovieTrailerEntityItem(@NonNull final String movieId, @NonNull final List<MovieTrailerItem> movieTrailerItems) {
        final List<MovieTrailerEntity> movieTrailerEntities = new ArrayList<>();

        for (final MovieTrailerItem movieTrailerItem : movieTrailerItems) {
            movieTrailerEntities.add(new MovieTrailerEntity(
                    movieTrailerItem.getMovieTrailerId(),
                    movieId,
                    movieTrailerItem.getMovieTrailerName(),
                    movieTrailerItem.getMovieTrailerSite(),
                    movieTrailerItem.getMovieTrailerType()
            ));
        }

        return movieTrailerEntities;
    }

    @NonNull
    public static List<MovieTrailerItem> mapMovieTrailerItems(@NonNull final List<MovieTrailerEntity> movieTrailerEntities) {
        final List<MovieTrailerItem> movieTrailerItems = new ArrayList<>();

        for (final MovieTrailerEntity movieTrailerEntity : movieTrailerEntities) {
            movieTrailerItems.add(new MovieTrailerItem(new MovieTrailer(
                    movieTrailerEntity.getTrailerId(),
                    movieTrailerEntity.getTrailerName(),
                    movieTrailerEntity.getTrailerSite(),
                    movieTrailerEntity.getTrailerType()
            )));
        }

        return movieTrailerItems;
    }

    @NonNull
    public static List<MovieReviewEntity> mapMovieReviewEntityItem(@NonNull final String movieId, @NonNull final List<MovieReviewItem> movieReviewItems) {
        final List<MovieReviewEntity> movieReviewEntities = new ArrayList<>();

        for (final MovieReviewItem movieReviewItem : movieReviewItems) {
            movieReviewEntities.add(new MovieReviewEntity(
                    movieReviewItem.getMovieReviewId(),
                    movieId,
                    movieReviewItem.getMovieReviewAuthor(),
                    movieReviewItem.getMovieReviewContent(),
                    movieReviewItem.getFullReviewUrl()
            ));
        }

        return movieReviewEntities;
    }

    @NonNull
    public static List<MovieReviewItem> mapMovieReviewItems(@NonNull final List<MovieReviewEntity> movieReviewEntities) {
        final List<MovieReviewItem> movieReviewItems = new ArrayList<>();

        for (final MovieReviewEntity movieReviewEntity : movieReviewEntities) {
            movieReviewItems.add(new MovieReviewItem(new MovieReview(
                    movieReviewEntity.getReviewId(),
                    movieReviewEntity.getReviewAuthor(),
                    movieReviewEntity.getReviewContent(),
                    movieReviewEntity.getReviewUrl()
            )));
        }

        return movieReviewItems;
    }
}