package com.example.popularmovies.data.moviereviews;

import androidx.annotation.NonNull;

@SuppressWarnings("unused")
public class MoviesReviewsApi {

    public String id;
    public String author;
    public String content;
    public String url;

    @NonNull
    @Override
    public final String toString() {
        return "MoviesReviewsApi{" +
                "id='" + id + '\'' +
                ", author='" + author + '\'' +
                ", content='" + content + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}