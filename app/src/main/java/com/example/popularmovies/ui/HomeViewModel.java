package com.example.popularmovies.ui;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.popularmovies.data.MovieApiUseCases;
import com.example.popularmovies.database.MovieEntityMappers;
import com.example.popularmovies.database.PopularMoviesDatabase;
import com.example.popularmovies.database.model.MovieEntity;
import com.example.popularmovies.domain.MovieSortOrder;
import com.example.popularmovies.domain.movies.MovieItem;
import com.example.popularmovies.ui.adapters.MovieAdapter;
import com.example.popularmovies.util.AppExecutors;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;

public class HomeViewModel extends ViewModel {

    private final MovieApiUseCases movieApiUseCases = new MovieApiUseCases();
    private final MovieAdapter adapter = new MovieAdapter();
    private final CompositeDisposable disposables = new CompositeDisposable();
    private String moviePreference;
    private boolean favoritesEnabled;
    private List<MovieItem> favoriteMovieMovieItems;
    private List<MovieItem> currentMovies;
    private LiveData<List<MovieEntity>> favoriteMoviesLiveData;

    public final MutableLiveData<Boolean> loadingIndicator = new MutableLiveData<>(false);
    public final MutableLiveData<Boolean> errorView = new MutableLiveData<>(false);

    @Override
    protected void onCleared() {
        disposables.dispose();
        super.onCleared();
    }

    public MovieAdapter getAdapter() {
        return adapter;
    }

    public void loadMovies(final String preference) {
        moviePreference = preference;

        switch (MovieSortOrder.getMovieSortOrder(moviePreference)) {
            case POPULAR:
                getPopularMovies();
                break;
            case TOP_RATED:
                getTopRatedMovies();
                break;
        }
    }

    public void retryLoadingMovies() {
        loadMovies(moviePreference);
    }

    public LiveData<List<MovieEntity>> getFavoriteMoviesLiveData() {
        return favoriteMoviesLiveData;
    }

    public void setItemClickListener(final HomeItemClickListener homeItemClickListener) {
        adapter.setItemClickListener(homeItemClickListener);
    }

    public void setDatabase(final PopularMoviesDatabase database) {
        favoriteMoviesLiveData = database.popularMoviesDao().getFavoriteMoviesAsMovieEntity();
    }

    public void setFavoriteMovieMovieItems(final List<MovieEntity> favoriteMovieMovieItems) {
        final List<MovieItem> movieItems = new ArrayList<>();
        AppExecutors.getInstance().getDiskIO().execute(() -> {
            for (MovieEntity movieEntity : favoriteMovieMovieItems) {
                movieItems.add(MovieEntityMappers.mapMovieItem(movieEntity));
            }
            this.favoriteMovieMovieItems = movieItems;

            AppExecutors.getInstance().getMainThread().execute(this::setMoviesToAdapter);
        });
    }

    public void setFavoritesEnabled(final boolean favoritesEnabled) {
        this.favoritesEnabled = favoritesEnabled;
        setMoviesToAdapter();

        if (favoritesEnabled) toggleErrorView(false);
        else if (currentMovies != null && !currentMovies.isEmpty()) toggleErrorView(false);
        else toggleErrorView(true);
    }

    public boolean isCurrentMoviesLoaded() {
        return currentMovies != null && !currentMovies.isEmpty();
    }

    public void setCurrentLoadedMovies() {
        adapter.setMovies(currentMovies);
        adapter.notifyDataSetChanged();
    }

    private void getPopularMovies() {
        getMovies(movieApiUseCases.getPopularMovies());
    }

    private void getTopRatedMovies() {
        getMovies(movieApiUseCases.getTopRatedMovies());
    }

    private void getMovies(final Single<List<MovieItem>> moviesSingle) {
        toggleLoadingView(true);
        disposables.add(moviesSingle
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<MovieItem>>() {
                    @Override
                    public void onSuccess(final @NonNull List<MovieItem> movies) {
                        toggleErrorView(false);
                        toggleLoadingView(false);
                        currentMovies = movies;
                        setMoviesToAdapter();

                    }

                    @Override
                    public void onError(final @NonNull Throwable e) {
                        if (!favoritesEnabled) {
                            toggleErrorView(true);
                        }
                        toggleLoadingView(false);
                        Log.e(HomeViewModel.class.getSimpleName(), "Error occurred while loading data", e);
                    }
                }));
    }

    private void setMoviesToAdapter() {
        adapter.setMovies(favoritesEnabled ? favoriteMovieMovieItems : currentMovies);
        adapter.notifyDataSetChanged();
    }

    private void toggleLoadingView(final Boolean visibility) {
        loadingIndicator.setValue(visibility);
    }

    private void toggleErrorView(final Boolean visibility) {
        errorView.setValue(visibility);
    }
}