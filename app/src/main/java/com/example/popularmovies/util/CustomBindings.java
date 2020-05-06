package com.example.popularmovies.util;

import android.view.View;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

public class CustomBindings {

    @BindingAdapter("imageUrl")
    public static void bindImageUrl(final ImageView imageView, final String imageUrl) {
        // TODO: simple model of 'backdropPath' url can be empty; pass in object to determine how to load or different load options pre-loaded
        Glide.with(imageView).load(imageUrl).into(imageView);
    }

    @BindingAdapter("setAdapter")
    public static void bindRecyclerViewAdapter(final RecyclerView recyclerView, final RecyclerView.Adapter<?> adapter) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(20);
        recyclerView.setAdapter(adapter);
    }

    @BindingAdapter("visibleOrGone")
    public static void setVisibleOrGone(final View view, final Boolean visible) {
        if (visible != null) {
            view.setVisibility(visible ? View.VISIBLE : View.GONE);
        }
    }
}
