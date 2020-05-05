package com.example.popularmovies.domain;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Movie implements Parcelable {

    private final String name;
    private final String posterUrl;
    private final String backdropUrl;
    private final String releaseDate;
    private final String synopsis;
    private final double userRating;

    public Movie(final String name, final String posterUrl, final String backdropUrl, final String releaseDate, final String synopsis, final double userRating) {
        this.name = name;
        this.posterUrl = posterUrl;
        this.backdropUrl = backdropUrl;
        this.releaseDate = releaseDate;
        this.synopsis = synopsis;
        this.userRating = userRating;
    }

    protected Movie(final Parcel source) {
        this.name = source.readString();
        this.posterUrl = source.readString();
        this.backdropUrl = source.readString();
        this.releaseDate = source.readString();
        this.synopsis = source.readString();
        this.userRating = source.readDouble();
    }

    public final String getName() {
        return name;
    }

    public final String getPosterUrl() {
        return posterUrl;
    }

    public final String getBackdropUrl() {
        return backdropUrl;
    }

    public final String getReleaseDate() {
        return releaseDate;
    }

    public final String getSynopsis() {
        return synopsis;
    }

    public final double getUserRating() {
        return userRating;
    }

    @NonNull
    @Override
    public final String toString() {
        return "Movie{" +
                "name='" + name + '\'' +
                ", posterUrl='" + posterUrl + '\'' +
                ", backdropUrl='" + backdropUrl + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", synopsis='" + synopsis + '\'' +
                ", userRating='" + userRating + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeString(this.name);
        dest.writeString(this.posterUrl);
        dest.writeString(this.backdropUrl);
        dest.writeString(this.releaseDate);
        dest.writeString(this.synopsis);
        dest.writeDouble(this.userRating);
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(final Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(final int size) {
            return new Movie[size];
        }
    };
}