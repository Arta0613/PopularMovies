package com.example.popularmovies.ui;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.popularmovies.data.MovieApiUseCases;
import com.example.popularmovies.domain.moviereviews.MovieReviewItem;
import com.example.popularmovies.domain.movies.MovieItem;
import com.example.popularmovies.domain.movietrailers.MovieTrailerItem;
import com.example.popularmovies.ui.adapters.MovieReviewAdapter;
import com.example.popularmovies.ui.adapters.MovieTrailerAdapter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;

public class DetailViewModel extends ViewModel {

    private static final String LOG = DetailViewModel.class.getSimpleName();

    private final MovieApiUseCases movieApiUseCases = new MovieApiUseCases();
    private final MovieTrailerAdapter trailerAdapter = new MovieTrailerAdapter();
    private final MovieReviewAdapter reviewAdapter = new MovieReviewAdapter();

    private final CompositeDisposable disposables = new CompositeDisposable();
    private List<MovieTrailerItem> movieTrailerItems;
    private List<MovieReviewItem> movieReviewItems;

    public final MutableLiveData<Boolean> loadingIndicator = new MutableLiveData<>(false);
    public final MutableLiveData<Boolean> errorView = new MutableLiveData<>(false);

    public MovieItem movieItem;

    public void init(final MovieItem movieItem) {
        this.movieItem = movieItem;
    }

    public MovieTrailerAdapter getTrailerAdapter() {
        return trailerAdapter;
    }

    public MovieReviewAdapter getReviewAdapter() {
        return reviewAdapter;
    }

    public void setItemClickListener(final ItemClickListener itemClickListener) {
        trailerAdapter.setItemClickListener(itemClickListener);
        reviewAdapter.setItemClickListener(itemClickListener);
    }

    public void setMovieTrailerItems(final List<MovieTrailerItem> movieTrailerItems) {
        this.movieTrailerItems = movieTrailerItems != null ? movieTrailerItems : new ArrayList<>();
    }

    public void setMovieReviewItems(final List<MovieReviewItem> movieReviewItems) {
        this.movieReviewItems = movieReviewItems != null ? movieReviewItems : new ArrayList<>();
    }

    public void loadData() {
        setLoadingIndicator(true);
        getMovieTrailers();
        getMovieReviews();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposables.dispose();
    }

    private void getMovieTrailers() {
        disposables.add(movieApiUseCases.getMovieTrailers(movieItem.getMovieId())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<MovieTrailerItem>>() {
                    @Override
                    public void onSuccess(@NonNull final List<MovieTrailerItem> movieTrailerItems) {
                        Log.d(LOG, "Success loading trailers");
                        setAndLoadTrailers(movieTrailerItems);
                        setLoadingIndicator(false);
                        setErrorView(false);
                    }

                    @Override
                    public void onError(@NonNull final Throwable e) {
                        Log.e(LOG, "Error occurred while loading trailers", e);
                        setLoadingIndicator(false);
                        setErrorView(true);
                    }
                }));
    }

    private void getMovieReviews() {
        disposables.add(movieApiUseCases.getMovieReviews(movieItem.getMovieId())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<MovieReviewItem>>() {
                    @Override
                    public void onSuccess(@NonNull final List<MovieReviewItem> movieReviewItems) {
                        Log.d(LOG, "Success loading reviews");
                        setAndLoadReviews(movieReviewItems);
                        setLoadingIndicator(false);
                        setErrorView(false);
                    }

                    @Override
                    public void onError(@NonNull final Throwable e) {
                        Log.e(LOG, "Error occurred while loading reviews", e);
                        setLoadingIndicator(false);
                        setErrorView(true);
                    }
                }));
    }

    private void setLoadingIndicator(final boolean visibility) {
        loadingIndicator.setValue(visibility);
    }

    private void setErrorView(final boolean visibility) {
        errorView.setValue(visibility);
    }

    private void setAndLoadTrailers(final List<MovieTrailerItem> movieTrailers) {
        setMovieTrailerItems(movieTrailers);
        trailerAdapter.setMovieTrailers(movieTrailers);
        trailerAdapter.notifyDataSetChanged();
    }

    private void setAndLoadReviews(final List<MovieReviewItem> movieReviews) {
        setMovieReviewItems(movieReviews);
        reviewAdapter.setMovieReviews(movieReviews);
        reviewAdapter.notifyDataSetChanged();
    }
}