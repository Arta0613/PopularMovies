package com.example.popularmovies.data.movietrailers;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class MoviesTrailersApi {

    public String id;

    @SerializedName("iso_639_1")
    public String language;
    @SerializedName("iso_3166_1")
    public String countryCode;

    public String key;
    public String name;
    public String site;
    public int size;
    public String type;

    @NonNull
    @Override
    public final String toString() {
        return "MovieTrailersApi{" +
                "id='" + id + '\'' +
                ", language='" + language + '\'' +
                ", countryCode='" + countryCode + '\'' +
                ", key='" + key + '\'' +
                ", name='" + name + '\'' +
                ", site='" + site + '\'' +
                ", size=" + size +
                ", type='" + type + '\'' +
                '}';
    }
}