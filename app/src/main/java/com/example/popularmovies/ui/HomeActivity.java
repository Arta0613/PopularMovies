package com.example.popularmovies.ui;

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

public class HomeActivity extends AppCompatActivity implements HomeItemClickListener {

    private HomeViewModel homeViewModel;

    @Override
    protected void onCreate(final @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityHomeBinding binding =
                DataBindingUtil.setContentView(this, R.layout.activity_home);

        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        binding.setLifecycleOwner(this);
        binding.setViewModel(homeViewModel);
        homeViewModel.setItemClickListener(this);

        loadMovies();
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final @NonNull MenuItem item) {
        if (item.getItemId() == R.id.sort_order) {
            // TODO: change sort_order
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClicked(final MovieItem movieItem) {
        // TODO: detect null movie item (if couldn't load) and display
    }

    private void loadMovies() {
        homeViewModel.loadMovies();
    }
}