package com.example.popularmovies.database.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(
        foreignKeys = {
                @ForeignKey(
                        entity = MovieEntity.class,
                        parentColumns = "id",
                        childColumns = "movieId",
                        onDelete = ForeignKey.CASCADE)
        }
)
public class MovieTrailerEntity {

    @NonNull
    @PrimaryKey
    private final String trailerId;

    @ColumnInfo(index = true)
    private final String movieId;

    private final String trailerName;
    private final String trailerSite;
    private final String trailerType;

    public MovieTrailerEntity(
            @NonNull final String trailerId,
            @NonNull final String movieId,
            @NonNull final String trailerName,
            @NonNull final String trailerSite,
            @NonNull final String trailerType
    ) {
        this.trailerId = trailerId;
        this.movieId = movieId;
        this.trailerName = trailerName;
        this.trailerSite = trailerSite;
        this.trailerType = trailerType;
    }

    @NonNull
    public String getTrailerId() {
        return trailerId;
    }

    @NonNull
    public String getMovieId() {
        return movieId;
    }

    @NonNull
    public String getTrailerName() {
        return trailerName;
    }

    @NonNull
    public String getTrailerSite() {
        return trailerSite;
    }

    @NonNull
    public String getTrailerType() {
        return trailerType;
    }

    @NonNull
    @Override
    public String toString() {
        return "MovieTrailerEntity{" +
                "trailerId='" + trailerId + '\'' +
                ", movieId='" + movieId + '\'' +
                ", trailerName='" + trailerName + '\'' +
                ", trailerSite='" + trailerSite + '\'' +
                ", trailerType='" + trailerType + '\'' +
                '}';
    }
}