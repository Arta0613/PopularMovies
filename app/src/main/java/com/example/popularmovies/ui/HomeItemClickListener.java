package com.example.popularmovies.ui;

import com.example.popularmovies.domain.movies.MovieItem;

public interface HomeItemClickListener {
    void onItemClicked(final MovieItem movieItem);
}