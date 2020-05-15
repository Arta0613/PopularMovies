package com.example.popularmovies.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.example.popularmovies.R;
import com.example.popularmovies.database.PopularMoviesDatabase;
import com.example.popularmovies.databinding.ActivityHomeBinding;
import com.example.popularmovies.domain.MovieSortOrder;
import com.example.popularmovies.domain.movies.MovieItem;
import com.example.popularmovies.util.UserPreferenceStorage;

import java.util.ArrayList;

import static com.example.popularmovies.util.NetworkState.isConnectedToNetwork;

public class HomeActivity extends AppCompatActivity implements HomeItemClickListener {

    private static final String IS_FAVORITES_SELECTED = "isFavoritesSelected";
    private static final int SORT_ORDER = 0;
    private static final int FAVORITES_MODE = 1;

    private HomeViewModel homeViewModel;
    private UserPreferenceStorage userPreferenceStorage;
    private boolean isFavoritesSelected = false;

    @Override
    protected void onCreate(final @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityHomeBinding binding =
                DataBindingUtil.setContentView(this, R.layout.activity_home);

        userPreferenceStorage = UserPreferenceStorage.getInstance(this);
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        if (savedInstanceState != null && savedInstanceState.containsKey(IS_FAVORITES_SELECTED)) {
            isFavoritesSelected = savedInstanceState.getBoolean(IS_FAVORITES_SELECTED, false);
        }

        setupViewModel();

        binding.setLifecycleOwner(this);
        binding.setViewModel(homeViewModel);

        if (!userPreferenceStorage.contains(MovieSortOrder.MOVIE_SORT_ORDER_KEY)) {
            setInitialSortOrder();
        }

        if (noNetwork() && homeViewModel.isCurrentMoviesLoaded()) {
            homeViewModel.setCurrentLoadedMovies();
        } else {
            loadMovies();
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull final Bundle outState) {
        outState.putBoolean(IS_FAVORITES_SELECTED, isFavoritesSelected);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);

        menu.getItem(SORT_ORDER).setTitle(getPreferenceTitle());
        menu.getItem(SORT_ORDER).setVisible(!isFavoritesSelected);
        menu.getItem(FAVORITES_MODE).setChecked(isFavoritesSelected);
        menu.getItem(FAVORITES_MODE).setIcon(
                isFavoritesSelected ? android.R.drawable.star_big_on : android.R.drawable.star_big_off
        );

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull final MenuItem item) {
        if (item.getItemId() == R.id.sort_order) {
            if (noNetwork()) {
                Toast.makeText(this, R.string.check_connection, Toast.LENGTH_SHORT).show();
                return true;
            }

            changeSortOrderAndLoadMovies(item);
            return true;
        } else if (item.getItemId() == R.id.offline_favorite) {
            isFavoritesSelected = !item.isChecked();
            showOfflineFavorites();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClicked(@NonNull final MovieItem movieItem) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(MovieItem.MOVIE_KEY, movieItem.getMovie());
        startActivity(intent);
    }

    private void setupViewModel() {
        homeViewModel.setDatabase(PopularMoviesDatabase.getInstance(getApplicationContext()));
        homeViewModel.getFavoriteMoviesLiveData().observe(this, favoriteMovies ->
                homeViewModel.setFavoriteMovieMovieItems(
                        favoriteMovies != null ? favoriteMovies : new ArrayList<>()
                )
        );
        homeViewModel.setItemClickListener(this);
    }

    private void changeSortOrderAndLoadMovies(@NonNull final MenuItem item) {
        switchMoviesPreference();
        item.setTitle(getPreferenceTitle());
        loadMovies();
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
                return getString(R.string.popular);
            case TOP_RATED:
                return getString(R.string.top_rated);
            default:
                return getString(R.string.unknown);
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

    private void showOfflineFavorites() {
        homeViewModel.setFavoritesEnabled(isFavoritesSelected);
        invalidateOptionsMenu();
    }

    private boolean noNetwork() {
        return !isConnectedToNetwork(this);
    }
}