package com.example.popularmovies.database.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class MovieEntity {

    @NonNull
    @PrimaryKey
    private final String id;

    private final String name;
    private final String posterUrl;
    private final String backdropUrl;
    private final String releaseDate;
    private final String synopsis;
    private final Double userRating;

    public MovieEntity(
            @NonNull final String id,
            @NonNull final String name,
            @NonNull final String posterUrl,
            @NonNull final String backdropUrl,
            @NonNull final String releaseDate,
            @NonNull final String synopsis,
            @NonNull final Double userRating
    ) {
        this.id = id;
        this.name = name;
        this.posterUrl = posterUrl;
        this.backdropUrl = backdropUrl;
        this.releaseDate = releaseDate;
        this.synopsis = synopsis;
        this.userRating = userRating;
    }

    @NonNull
    public String getId() {
        return id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    @NonNull
    public String getPosterUrl() {
        return posterUrl;
    }

    @NonNull
    public String getBackdropUrl() {
        return backdropUrl;
    }

    @NonNull
    public String getReleaseDate() {
        return releaseDate;
    }

    @NonNull
    public String getSynopsis() {
        return synopsis;
    }

    @NonNull
    public Double getUserRating() {
        return userRating;
    }

    @NonNull
    @Override
    public String toString() {
        return "MovieEntity{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", posterUrl='" + posterUrl + '\'' +
                ", backdropUrl='" + backdropUrl + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", synopsis='" + synopsis + '\'' +
                ", userRating=" + userRating +
                '}';
    }
}