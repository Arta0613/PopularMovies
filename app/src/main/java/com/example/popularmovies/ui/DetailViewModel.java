package com.example.popularmovies.ui;

import androidx.lifecycle.ViewModel;

import com.example.popularmovies.domain.MovieItem;

public class DetailViewModel extends ViewModel {
    public MovieItem movieItem;

    public void setMovieItem(MovieItem movieItem) {
        this.movieItem = movieItem;
    }
}
