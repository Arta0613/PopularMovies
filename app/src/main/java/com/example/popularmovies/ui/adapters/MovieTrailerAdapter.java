package com.example.popularmovies.ui.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.example.popularmovies.BR;
import com.example.popularmovies.R;
import com.example.popularmovies.domain.movietrailers.MovieTrailerItem;
import com.example.popularmovies.ui.ItemClickListener;

import java.util.List;

public class MovieTrailerAdapter extends RecyclerView.Adapter<MovieTrailerAdapter.MovieTrailerViewHolder> {

    private List<MovieTrailerItem> movieTrailers;
    private ItemClickListener itemClickListener;

    @NonNull
    @Override
    public MovieTrailerViewHolder onCreateViewHolder(
            @NonNull final ViewGroup parent,
            final int viewType
    ) {
        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(
                parent.getContext()), R.layout.movie_trailer_item, parent, false
        );

        return new MovieTrailerViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull final MovieTrailerViewHolder holder, final int position) {
        holder.bind(position);
    }

    @Override
    public final int getItemCount() {
        return movieTrailers == null ? 0 : movieTrailers.size();
    }

    public void setMovieTrailers(final List<MovieTrailerItem> movieTrailers) {
        this.movieTrailers = movieTrailers;
    }

    public void setItemClickListener(final ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    class MovieTrailerViewHolder extends RecyclerView.ViewHolder {

        public final ViewDataBinding binding;

        public MovieTrailerViewHolder(final ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(final int position) {
            final MovieTrailerItem trailerItem = movieTrailers.get(position);

            binding.getRoot().setOnClickListener(l ->
                    itemClickListener.onItemClicked(trailerItem.getMovieTrailerUrl())
            );
            binding.setVariable(BR.movieTrailer, trailerItem);
            binding.executePendingBindings();
        }
    }
}