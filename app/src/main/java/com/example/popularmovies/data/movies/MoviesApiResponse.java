package com.example.popularmovies.data.movies;

import com.google.gson.annotations.SerializedName;

import java.util.List;

@SuppressWarnings("unused")
public class MoviesApiResponse {

    public int page;

    @SerializedName("total_results")
    public int totalResults;

    @SerializedName("total_pages")
    public int totalPages;

    @SerializedName("results")
    public List<MoviesApi> movies;
}