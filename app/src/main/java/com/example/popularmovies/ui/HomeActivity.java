package com.example.popularmovies.ui;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.example.popularmovies.R;
import com.example.popularmovies.databinding.ActivityHomeBinding;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(final @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityHomeBinding binding =
                DataBindingUtil.setContentView(this, R.layout.activity_home);

        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        binding.setLifecycleOwner(this);
        binding.setViewModel(homeViewModel);
    }
}