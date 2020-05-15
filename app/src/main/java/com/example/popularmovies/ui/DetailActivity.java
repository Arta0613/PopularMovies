package com.example.popularmovies.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.example.popularmovies.R;
import com.example.popularmovies.databinding.ActivityDetailBinding;
import com.example.popularmovies.domain.movies.MovieItem;

import java.util.Objects;

public class DetailActivity extends AppCompatActivity implements ItemClickListener {

    private static final String LOG = DetailActivity.class.getSimpleName();

    private DetailViewModel detailViewModel;
    private MovieItem movieItem;

    @Override
    protected void onCreate(final @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getMovieItemFromIntent();
        setBackNavigationInAppBar();

        ActivityDetailBinding binding =
                DataBindingUtil.setContentView(this, R.layout.activity_detail);

        setupViewModel();

        binding.setLifecycleOwner(this);
        binding.setViewModel(detailViewModel);

        detailViewModel.loadData();
    }

    @Override
    public void onItemClicked(@NonNull final String intentUrl) {
        final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(intentUrl));
        startActivity(intent);
    }


    private void getMovieItemFromIntent() {
        try {
            movieItem = new MovieItem(
                    Objects.requireNonNull(getIntent().getParcelableExtra(MovieItem.MOVIE_KEY))
            );
            setTitle(movieItem.getMovieTitle());
        } catch (NullPointerException e) {
            Log.e(LOG, "Error while setting Movie name", e);
        }
    }

    private void setBackNavigationInAppBar() {
        try {
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        } catch (NullPointerException e) {
            Log.e(LOG, "Error while setting UP action", e);
        }
    }

    private void setupViewModel() {
        detailViewModel = new ViewModelProvider(this).get(DetailViewModel.class);
        detailViewModel.init(movieItem);
        detailViewModel.setItemClickListener(this);
    }
}