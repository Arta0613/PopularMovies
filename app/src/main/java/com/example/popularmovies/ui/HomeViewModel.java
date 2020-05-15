package com.example.popularmovies.ui;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.popularmovies.data.MovieApiUseCases;
import com.example.popularmovies.domain.MovieSortOrder;
import com.example.popularmovies.domain.movies.MovieItem;
import com.example.popularmovies.ui.adapters.MovieAdapter;

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
    private List<MovieItem> currentMovies;

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

    public void setItemClickListener(final HomeItemClickListener homeItemClickListener) {
        adapter.setItemClickListener(homeItemClickListener);
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
                        toggleErrorView(true);
                        toggleLoadingView(false);
                        Log.e(HomeViewModel.class.getSimpleName(), "Error occurred while loading data", e);
                    }
                }));
    }

    private void setMoviesToAdapter() {
        adapter.setMovies(currentMovies);
        adapter.notifyDataSetChanged();
    }

    private void toggleLoadingView(final Boolean visibility) {
        loadingIndicator.setValue(visibility);
    }

    private void toggleErrorView(final Boolean visibility) {
        errorView.setValue(visibility);
    }
}
