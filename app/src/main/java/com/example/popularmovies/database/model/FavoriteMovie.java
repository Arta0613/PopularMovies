package com.example.popularmovies.database.model;

import androidx.annotation.NonNull;
import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class FavoriteMovie {

    @Embedded
    private final MovieEntity movieEntity;

    @Relation(parentColumn = "id", entityColumn = "movieId", entity = MovieTrailerEntity.class)
    private final List<MovieTrailerEntity> movieTrailerEntities;

    @Relation(parentColumn = "id", entityColumn = "movieId", entity = MovieReviewEntity.class)
    private final List<MovieReviewEntity> movieReviewEntities;

    public FavoriteMovie(
            @NonNull final MovieEntity movieEntity,
            @NonNull final List<MovieTrailerEntity> movieTrailerEntities,
            @NonNull final List<MovieReviewEntity> movieReviewEntities
    ) {
        this.movieEntity = movieEntity;
        this.movieTrailerEntities = movieTrailerEntities;
        this.movieReviewEntities = movieReviewEntities;
    }

    @NonNull
    public MovieEntity getMovieEntity() {
        return movieEntity;
    }

    @NonNull
    public List<MovieTrailerEntity> getMovieTrailerEntities() {
        return movieTrailerEntities;
    }

    @NonNull
    public List<MovieReviewEntity> getMovieReviewEntities() {
        return movieReviewEntities;
    }

    @NonNull
    @Override
    public final String toString() {
        return "FavoriteMovie{" +
                "movieEntity=" + movieEntity +
                ", movieTrailerEntities=" + movieTrailerEntities +
                ", movieReviewEntities=" + movieReviewEntities +
                '}';
    }
}