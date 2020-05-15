package com.example.popularmovies.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.popularmovies.database.model.FavoriteMovie;
import com.example.popularmovies.database.model.MovieEntity;
import com.example.popularmovies.database.model.MovieReviewEntity;
import com.example.popularmovies.database.model.MovieTrailerEntity;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface PopularMoviesDao {

    @Transaction
    @Query("SELECT * FROM MovieEntity")
    LiveData<List<FavoriteMovie>> getFavoriteMovies();

    @Transaction
    @Query("SELECT * FROM MovieEntity")
    LiveData<List<MovieEntity>> getFavoriteMoviesAsMovieEntity();

    @Transaction
    @Query("SELECT * FROM MovieEntity WHERE id IS :id")
    LiveData<FavoriteMovie> getFavoriteMovie(final String id);

    @Transaction
    @Insert(entity = MovieEntity.class, onConflict = OnConflictStrategy.REPLACE)
    Single<Long> insertFavoriteMovie(final MovieEntity movieEntity);

    @Transaction
    @Insert(entity = MovieTrailerEntity.class, onConflict = OnConflictStrategy.REPLACE)
    Single<List<Long>> insertFavoriteMovieTrailers(final List<MovieTrailerEntity> movieTrailerEntities);

    @Transaction
    @Insert(entity = MovieReviewEntity.class, onConflict = OnConflictStrategy.REPLACE)
    Single<List<Long>> insertFavoriteMovieReviews(final List<MovieReviewEntity> movieReviewEntities);

    @Transaction
    @Delete
    Single<Integer> deleteFavoriteMovie(final MovieEntity movieEntity);
}