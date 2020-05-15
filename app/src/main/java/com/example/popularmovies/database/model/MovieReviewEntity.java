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
public class MovieReviewEntity {

    @NonNull
    @PrimaryKey
    private final String reviewId;

    @ColumnInfo(index = true)
    private final String movieId;

    private final String reviewAuthor;
    private final String reviewContent;
    private final String reviewUrl;

    public MovieReviewEntity(
            @NonNull final String reviewId,
            @NonNull final String movieId,
            @NonNull final String reviewAuthor,
            @NonNull final String reviewContent,
            @NonNull final String reviewUrl
    ) {
        this.reviewId = reviewId;
        this.movieId = movieId;
        this.reviewAuthor = reviewAuthor;
        this.reviewContent = reviewContent;
        this.reviewUrl = reviewUrl;
    }

    @NonNull
    public String getReviewId() {
        return reviewId;
    }

    @NonNull
    public String getMovieId() {
        return movieId;
    }

    @NonNull
    public String getReviewAuthor() {
        return reviewAuthor;
    }

    @NonNull
    public String getReviewContent() {
        return reviewContent;
    }

    @NonNull
    public String getReviewUrl() {
        return reviewUrl;
    }

    @NonNull
    @Override
    public String toString() {
        return "MovieReviewEntity{" +
                "reviewId='" + reviewId + '\'' +
                ", movieId='" + movieId + '\'' +
                ", reviewAuthor='" + reviewAuthor + '\'' +
                ", reviewContent='" + reviewContent + '\'' +
                ", reviewUrl='" + reviewUrl + '\'' +
                '}';
    }
}