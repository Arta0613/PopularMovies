package com.example.popularmovies.ui.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.example.popularmovies.BR;
import com.example.popularmovies.R;
import com.example.popularmovies.domain.moviereviews.MovieReviewItem;
import com.example.popularmovies.ui.ItemClickListener;

import java.util.List;

public class MovieReviewAdapter extends RecyclerView.Adapter<MovieReviewAdapter.MovieReviewViewHolder> {

    private List<MovieReviewItem> movieReviews;
    private ItemClickListener itemClickListener;

    @NonNull
    @Override
    public MovieReviewViewHolder onCreateViewHolder(
            @NonNull final ViewGroup parent,
            final int viewType
    ) {
        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(
                parent.getContext()), R.layout.movie_review_item, parent, false
        );

        return new MovieReviewViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull final MovieReviewViewHolder holder, final int position) {
        holder.bind(position);
    }

    @Override
    public final int getItemCount() {
        return movieReviews == null ? 0 : movieReviews.size();
    }

    public void setMovieReviews(final List<MovieReviewItem> movieReviews) {
        this.movieReviews = movieReviews;
    }

    public void setItemClickListener(final ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    class MovieReviewViewHolder extends RecyclerView.ViewHolder {

        public final ViewDataBinding binding;

        public MovieReviewViewHolder(final ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(final int position) {
            final MovieReviewItem reviewItem = movieReviews.get(position);

            binding.getRoot().setOnClickListener(l ->
                    itemClickListener.onItemClicked(reviewItem.getFullReviewUrl())
            );
            binding.setVariable(BR.movieReview, reviewItem);
            binding.executePendingBindings();
        }
    }
}