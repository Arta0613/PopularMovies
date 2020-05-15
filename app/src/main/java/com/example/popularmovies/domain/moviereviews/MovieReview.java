package com.example.popularmovies.domain.moviereviews;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class MovieReview implements Parcelable {

    private final String reviewId;
    private final String reviewAuthor;
    private final String reviewContent;
    private final String reviewUrl;

    public MovieReview(
            @NonNull final String reviewId,
            @NonNull final String reviewAuthor,
            @NonNull final String reviewContent,
            @NonNull final String reviewUrl
    ) {
        this.reviewId = reviewId;
        this.reviewAuthor = reviewAuthor;
        this.reviewContent = reviewContent;
        this.reviewUrl = reviewUrl;
    }

    private MovieReview(final Parcel source) {
        this.reviewId = source.readString();
        this.reviewAuthor = source.readString();
        this.reviewContent = source.readString();
        this.reviewUrl = source.readString();
    }

    @NonNull
    public String getReviewId() {
        return reviewId;
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
        return "MovieReview{" +
                "reviewId='" + reviewId + '\'' +
                ", reviewAuthor='" + reviewAuthor + '\'' +
                ", reviewContent='" + reviewContent + '\'' +
                ", reviewUrl='" + reviewUrl + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.reviewId);
        dest.writeString(this.reviewAuthor);
        dest.writeString(this.reviewContent);
        dest.writeString(this.reviewUrl);
    }

    public static final Parcelable.Creator<MovieReview> CREATOR = new Parcelable.Creator<MovieReview>() {
        @Override
        public MovieReview createFromParcel(final Parcel source) {
            return new MovieReview(source);
        }

        @Override
        public MovieReview[] newArray(final int size) {
            return new MovieReview[size];
        }
    };
}