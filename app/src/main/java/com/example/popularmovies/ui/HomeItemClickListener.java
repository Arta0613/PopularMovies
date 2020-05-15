package com.example.popularmovies.ui;

import androidx.annotation.NonNull;

import com.example.popularmovies.domain.movies.MovieItem;

public interface HomeItemClickListener {
    void onItemClicked(@NonNull final MovieItem movieItem);
}