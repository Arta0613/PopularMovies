package com.example.popularmovies.util;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

public class CustomBindings {

    @BindingAdapter(value = {"errorDrawable", "imageUrl"})
    public static void bindImageUrl(
            final ImageView imageView,
            final Drawable drawable,
            final String imageUrl
    ) {
        Glide.with(imageView).load(imageUrl).error(drawable).into(imageView);
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
