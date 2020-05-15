package com.example.popularmovies.data.moviereviews;

import com.google.gson.annotations.SerializedName;

import java.util.List;

@SuppressWarnings("unused")
public class MoviesReviewsApiResponse {

    public int id;
    public int page;

    @SerializedName("results")
    public List<MoviesReviewsApi> moviesReviews;

    @SerializedName("total_pages")
    public int totalPages;

    @SerializedName("total_results")
    public int totalReviews;
}