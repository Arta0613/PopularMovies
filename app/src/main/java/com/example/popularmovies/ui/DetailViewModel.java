package com.example.popularmovies.ui;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.popularmovies.data.MovieApiUseCases;
import com.example.popularmovies.database.MovieEntityMappers;
import com.example.popularmovies.database.PopularMoviesDao;
import com.example.popularmovies.database.PopularMoviesDatabase;
import com.example.popularmovies.database.model.FavoriteMovie;
import com.example.popularmovies.domain.moviereviews.MovieReviewItem;
import com.example.popularmovies.domain.movies.MovieItem;
import com.example.popularmovies.domain.movietrailers.MovieTrailerItem;
import com.example.popularmovies.ui.adapters.MovieReviewAdapter;
import com.example.popularmovies.ui.adapters.MovieTrailerAdapter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class DetailViewModel extends ViewModel {

    private static final String LOG = DetailViewModel.class.getSimpleName();

    private final MovieApiUseCases movieApiUseCases = new MovieApiUseCases();
    private final MovieTrailerAdapter trailerAdapter = new MovieTrailerAdapter();
    private final MovieReviewAdapter reviewAdapter = new MovieReviewAdapter();

    private final CompositeDisposable disposables = new CompositeDisposable();
    private List<MovieTrailerItem> movieTrailerItems;
    private List<MovieReviewItem> movieReviewItems;
    private LiveData<FavoriteMovie> favoriteLiveData;
    private PopularMoviesDatabase database;
    private boolean extraMovieDataLoaded = false;

    public final MutableLiveData<Boolean> loadingIndicator = new MutableLiveData<>(false);
    public final MutableLiveData<Boolean> errorView = new MutableLiveData<>(false);

    public MovieItem movieItem;

    public void init(final PopularMoviesDatabase database, final MovieItem movieItem) {
        this.database = database;
        this.movieItem = movieItem;
        favoriteLiveData = getDao().getFavoriteMovie(movieItem.getMovieId());
    }

    public MovieTrailerAdapter getTrailerAdapter() {
        return trailerAdapter;
    }

    public MovieReviewAdapter getReviewAdapter() {
        return reviewAdapter;
    }

    public LiveData<FavoriteMovie> getFavoriteLiveData() {
        return favoriteLiveData;
    }

    public void updateFavoriteStatus(final boolean isFavorite) {
        if (isFavorite) {
            insertFavoriteMovie();
        } else {
            deleteFavoriteMovie();
        }
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

    public void setOfflineDataLoaded() {
        extraMovieDataLoaded = true;
    }

    public void loadData() {
        setLoadingIndicator(true);
        getMovieTrailers();
        getMovieReviews();
    }

    public void updateMovieTrailersAndReviewsWithOfflineData() {
        trailerAdapter.setMovieTrailers(movieTrailerItems);
        reviewAdapter.setMovieReviews(movieReviewItems);

        trailerAdapter.notifyDataSetChanged();
        reviewAdapter.notifyDataSetChanged();
    }

    public final boolean extraMovieDataLoaded() {
        return extraMovieDataLoaded;
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
                        extraMovieDataLoaded = true;
                        setLoadingIndicator(false);
                        setErrorView(false);
                    }

                    @Override
                    public void onError(@NonNull final Throwable e) {
                        Log.e(LOG, "Error occurred while loading trailers", e);
                        extraMovieDataLoaded = false;
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
                        extraMovieDataLoaded = true;
                        setLoadingIndicator(false);
                        setErrorView(false);
                    }

                    @Override
                    public void onError(@NonNull final Throwable e) {
                        Log.e(LOG, "Error occurred while loading reviews", e);
                        extraMovieDataLoaded = false;
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

    // Database actions ------------
    private PopularMoviesDao getDao() {
        return database.popularMoviesDao();
    }

    private void insertFavoriteMovie() {
        getDao()
                .insertFavoriteMovie(MovieEntityMappers.mapMovieEntityItem(movieItem))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Long>() {
                    @Override
                    public void onSubscribe(final Disposable d) {
                        disposables.add(d);
                    }

                    @Override
                    public void onSuccess(final Long id) {
                        Log.d(LOG, "Inserted MovieEntity Success -- ID: " + id);
                        insertMovieTrailers();
                        insertMovieReviews();
                    }

                    @Override
                    public void onError(final Throwable e) {
                        Log.e(LOG, "Error inserting", e);
                    }
                });
    }

    private void insertMovieTrailers() {
        getDao()
                .insertFavoriteMovieTrailers(MovieEntityMappers.mapMovieTrailerEntityItem(movieItem.getMovieId(), movieTrailerItems))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(listObserver);
    }

    private void insertMovieReviews() {
        getDao()
                .insertFavoriteMovieReviews(MovieEntityMappers.mapMovieReviewEntityItem(movieItem.getMovieId(), movieReviewItems))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(listObserver);
    }

    private void deleteFavoriteMovie() {
        getDao()
                .deleteFavoriteMovie(MovieEntityMappers.mapMovieEntityItem(movieItem))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Integer>() {
                    @Override
                    public void onSubscribe(final Disposable d) {
                        disposables.add(d);
                    }

                    @Override
                    public void onSuccess(final Integer id) {
                        Log.d(LOG, "Success deleting from DB -- ID: " + id);
                    }

                    @Override
                    public void onError(final Throwable e) {
                        Log.d(LOG, "Error deleting from DB", e);
                    }
                });
    }

    private final SingleObserver<List<Long>> listObserver = new SingleObserver<List<Long>>() {
        @Override
        public void onSubscribe(Disposable d) {
            disposables.add(d);
        }

        @Override
        public void onSuccess(List<Long> longs) {
            Log.d(LOG, "onSuccess -- Adding Trailers/Reviews to DB");
        }

        @Override
        public void onError(Throwable e) {
            Log.e(LOG, "onError -- Adding Trailers/Reviews to DB", e);
        }
    };
    // Database actions --------------
}