package com.example.popularmovies.database;

import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.popularmovies.database.model.MovieEntity;
import com.example.popularmovies.database.model.MovieReviewEntity;
import com.example.popularmovies.database.model.MovieTrailerEntity;

@Database(entities = {MovieEntity.class, MovieTrailerEntity.class, MovieReviewEntity.class}, version = 1, exportSchema = false)
public abstract class PopularMoviesDatabase extends RoomDatabase {

    private static final String LOG = PopularMoviesDatabase.class.getSimpleName();

    public static final Object LOCK = new Object();
    public static final String DATABASE_NAME = "popular_movies_database";

    public static volatile PopularMoviesDatabase INSTANCE;

    public static PopularMoviesDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (LOCK) {
                Log.d(LOG, "Creating a new Popular Movies Database");

                INSTANCE = Room.databaseBuilder(
                        context.getApplicationContext(),
                        PopularMoviesDatabase.class,
                        PopularMoviesDatabase.DATABASE_NAME
                ).build();
            }
        }

        Log.d(LOG, "Getting the database instance");
        return INSTANCE;
    }

    public abstract PopularMoviesDao popularMoviesDao();
}