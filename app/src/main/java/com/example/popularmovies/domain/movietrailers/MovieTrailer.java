package com.example.popularmovies.domain.movietrailers;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class MovieTrailer implements Parcelable {

    private final String trailerId;
    private final String trailerName;
    private final String trailerSite;
    private final String trailerType;

    public MovieTrailer(
            @NonNull final String trailerId,
            @NonNull final String trailerName,
            @NonNull final String trailerSite,
            @NonNull final String trailerType) {
        this.trailerId = trailerId;
        this.trailerName = trailerName;
        this.trailerSite = trailerSite;
        this.trailerType = trailerType;
    }

    protected MovieTrailer(final Parcel source) {
        this.trailerId = source.readString();
        this.trailerName = source.readString();
        this.trailerSite = source.readString();
        this.trailerType = source.readString();
    }

    @NonNull
    public String getTrailerId() {
        return trailerId;
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
        return "MovieTrailer{" +
                "trailerId='" + trailerId + '\'' +
                ", trailerName='" + trailerName + '\'' +
                ", trailerSite='" + trailerSite + '\'' +
                ", trailerType='" + trailerType + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.trailerId);
        dest.writeString(this.trailerName);
        dest.writeString(this.trailerSite);
        dest.writeString(this.trailerType);
    }

    public static final Parcelable.Creator<MovieTrailer> CREATOR = new Parcelable.Creator<MovieTrailer>() {
        @Override
        public MovieTrailer createFromParcel(final Parcel source) {
            return new MovieTrailer(source);
        }

        @Override
        public MovieTrailer[] newArray(final int size) {
            return new MovieTrailer[size];
        }
    };
}