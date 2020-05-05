package com.example.popularmovies.data;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.List;

@SuppressWarnings("unused")
public class MovieApi {

    public int id;

    public String title;

    @SerializedName("original_title")
    public String originalTitle;

    @SerializedName("release_date")
    public String releaseDate;

    public String overview;

    @SerializedName("poster_path")
    public String posterPath;

    public double popularity;

    @SerializedName("vote_average")
    public double voteAverage;

    @SerializedName("vote_count")
    public int voteCount;

    public boolean video;

    public boolean adult;

    @SerializedName("backdrop_path")
    public String backdropPath;

    @SerializedName("original_language")
    public String originalLanguage;

    @SerializedName("genre_ids")
    public List<Integer> genreIds;

    @NonNull
    @Override
    public final String toString() {
        return "Movie{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", originalTitle='" + originalTitle + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", overview='" + overview + '\'' +
                ", posterPath='" + posterPath + '\'' +
                ", popularity=" + popularity +
                ", voteAverage=" + voteAverage +
                ", voteCount=" + voteCount +
                ", video=" + video +
                ", adult=" + adult +
                ", backdropPath='" + backdropPath + '\'' +
                ", originalLanguage='" + originalLanguage + '\'' +
                ", genreIds=" + genreIds +
                "}\n";
    }
}
