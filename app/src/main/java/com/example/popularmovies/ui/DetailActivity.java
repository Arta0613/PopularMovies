package com.example.popularmovies.ui;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.example.popularmovies.R;
import com.example.popularmovies.databinding.ActivityDetailBinding;
import com.example.popularmovies.domain.MovieItem;

import java.util.Objects;

public class DetailActivity extends AppCompatActivity {

    private MovieItem movieItem;

    @Override
    protected void onCreate(final @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loadMovieItem();
        setUpAction();

        ActivityDetailBinding binding =
                DataBindingUtil.setContentView(this, R.layout.activity_detail);

        DetailViewModel detailViewModel = new ViewModelProvider(this).get(DetailViewModel.class);
        detailViewModel.setMovieItem(movieItem);

        binding.setLifecycleOwner(this);
        binding.setViewModel(detailViewModel);
    }

    private void setUpAction() {
        try {
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        } catch (NullPointerException e) {
            Log.e(DetailActivity.class.getSimpleName(), "Error while setting UP action", e);
        }
    }

    private void loadMovieItem() {
        // TODO: detect null item
        movieItem = new MovieItem(getIntent().getParcelableExtra(MovieItem.MOVIE_KEY));
        setTitle(movieItem.getMovieTitle());
    }
}