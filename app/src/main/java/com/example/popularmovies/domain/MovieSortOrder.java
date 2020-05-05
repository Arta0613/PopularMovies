package com.example.popularmovies.domain;

public enum MovieSortOrder {
    POPULAR(),
    TOP_RATED();

    public static final String MOVIE_SORT_ORDER_KEY = "movie_sort_order_key";

    public static MovieSortOrder getMovieSortOrder(final String movieSortOrder) {
        if (movieSortOrder.equals(POPULAR.name())) return POPULAR;
        else return TOP_RATED;
    }
}
