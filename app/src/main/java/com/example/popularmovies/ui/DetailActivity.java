package com.example.popularmovies.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.popularmovies.R;
import com.example.popularmovies.database.MovieEntityMappers;
import com.example.popularmovies.database.PopularMoviesDatabase;
import com.example.popularmovies.database.model.FavoriteMovie;
import com.example.popularmovies.databinding.ActivityDetailBinding;
import com.example.popularmovies.domain.movies.MovieItem;
import com.example.popularmovies.util.AppExecutors;
import com.example.popularmovies.util.NetworkState;

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
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        updateFavoriteStatus(menu.getItem(0));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull final MenuItem item) {
        if (item.getItemId() == R.id.favorite) {
            final boolean isFavorite = !item.isChecked();

            // when saving to database while offline; stopping attempt if not all is data loaded
            if (isFavorite && noNetwork() && !detailViewModel.extraMovieDataLoaded()) {
                Toast.makeText(this, R.string.try_saving_to_db_again, Toast.LENGTH_SHORT).show();
                return true;
            }

            setFavorite(item, isFavorite);
            detailViewModel.updateFavoriteStatus(isFavorite);
            return true;
        } else if (item.getItemId() == R.id.share) {
            shareFirstVideo();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClicked(@NonNull final String intentUrl) {
        final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(intentUrl));
        startActivity(intent);
    }

    private void shareFirstVideo() {
        final String shareUrl = detailViewModel.getFirstVideoUrl();

        if (shareUrl.isEmpty()) {
            Toast.makeText(this, R.string.no_videos_found, Toast.LENGTH_SHORT).show();
            return;
        }

        sendShareIntent(shareUrl);
    }

    private void sendShareIntent(final String shareUrl) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, shareUrl);
        sendIntent.setType("text/plain");

        Intent shareIntent = Intent.createChooser(sendIntent, null);
        startActivity(shareIntent);
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
        detailViewModel.init(PopularMoviesDatabase.getInstance(getApplicationContext()), movieItem);
        detailViewModel.setItemClickListener(this);
    }

    private void updateFavoriteStatus(@NonNull final MenuItem item) {
        detailViewModel.getFavoriteLiveData().observe(this, new Observer<FavoriteMovie>() {
            @Override
            public void onChanged(final FavoriteMovie favoriteMovie) {
                detailViewModel.getFavoriteLiveData().removeObserver(this);

                final boolean isFavorite = favoriteMovie != null;
                setFavorite(item, isFavorite);

                if (noNetwork() && isFavorite) {
                    loadOfflineData(favoriteMovie);
                } else {
                    detailViewModel.loadData();
                }
            }
        });
    }

    private void loadOfflineData(@NonNull final FavoriteMovie favoriteMovie) {
        AppExecutors.getInstance().getDiskIO().execute(() -> {
            // We are offline & this is a favorite item (offline), set saved trailers/reviews to load in VM
            detailViewModel.setMovieTrailerItems(
                    MovieEntityMappers.mapMovieTrailerItems(favoriteMovie.getMovieTrailerEntities())
            );
            detailViewModel.setMovieReviewItems(
                    MovieEntityMappers.mapMovieReviewItems(favoriteMovie.getMovieReviewEntities())
            );

            detailViewModel.setOfflineDataLoaded();

            AppExecutors.getInstance().getMainThread().execute(() ->
                    detailViewModel.updateMovieTrailersAndReviewsWithOfflineData());
        });
    }

    private void setFavorite(@NonNull final MenuItem menuItem, final boolean isFavorite) {
        menuItem.setChecked(isFavorite);
        menuItem.setIcon(
                isFavorite ? android.R.drawable.star_big_on : android.R.drawable.star_big_off
        );
    }

    private boolean noNetwork() {
        return !NetworkState.isConnectedToNetwork(DetailActivity.this);
    }
}