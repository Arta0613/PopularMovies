package com.example.popularmovies.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.example.popularmovies.R;
import com.example.popularmovies.databinding.ActivityHomeBinding;
import com.example.popularmovies.domain.MovieItem;
import com.example.popularmovies.domain.MovieSortOrder;
import com.example.popularmovies.util.UserPreferenceStorage;

public class HomeActivity extends AppCompatActivity implements HomeItemClickListener {

    private HomeViewModel homeViewModel;
    private UserPreferenceStorage userPreferenceStorage;

    @Override
    protected void onCreate(final @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityHomeBinding binding =
                DataBindingUtil.setContentView(this, R.layout.activity_home);

        userPreferenceStorage = UserPreferenceStorage.getInstance(this);
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        binding.setLifecycleOwner(this);
        binding.setViewModel(homeViewModel);
        homeViewModel.setItemClickListener(this);

        if (!userPreferenceStorage.contains(MovieSortOrder.MOVIE_SORT_ORDER_KEY)) {
            setInitialSortOrder();
        }
        loadMovies();
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        menu.getItem(0).setTitle(getPreferenceTitle());
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final @NonNull MenuItem item) {
        if (item.getItemId() == R.id.sort_order) {
            switchMoviesPreference();
            item.setTitle(getPreferenceTitle());
            loadMovies();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClicked(final MovieItem movieItem) {
        // TODO: detect null movie item (if couldn't load) and display
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(MovieItem.MOVIE_KEY, movieItem.getMovie());
        startActivity(intent);
    }

    private void loadMovies() {
        homeViewModel.loadMovies(getMoviePreference());
    }

    private void setInitialSortOrder() {
        saveMoviesPreference(MovieSortOrder.POPULAR.name());
    }

    private void saveMoviesPreference(final String value) {
        userPreferenceStorage.savePreference(MovieSortOrder.MOVIE_SORT_ORDER_KEY, value);
    }

    private String getMoviePreference() {
        return userPreferenceStorage.getPreference(MovieSortOrder.MOVIE_SORT_ORDER_KEY);
    }

    private String getPreferenceTitle() {
        switch (MovieSortOrder.getMovieSortOrder(getMoviePreference())) {
            case POPULAR:
                return getResources().getString(R.string.popular);
            case TOP_RATED:
                return getResources().getString(R.string.top_rated);
            default:
                return "Unknown";
        }
    }

    private void switchMoviesPreference() {
        switch (MovieSortOrder.getMovieSortOrder(getMoviePreference())) {
            case POPULAR:
                saveMoviesPreference(MovieSortOrder.TOP_RATED.name());
                break;
            case TOP_RATED:
                saveMoviesPreference(MovieSortOrder.POPULAR.name());
                break;
        }
    }
}