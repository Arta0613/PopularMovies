package com.example.popularmovies.data.movietrailers;

import com.google.gson.annotations.SerializedName;

import java.util.List;

@SuppressWarnings("unused")
public class MoviesTrailersApiResponse {

    public int id;
    @SerializedName("results")
    public List<MoviesTrailersApi> movieTrailers;
}