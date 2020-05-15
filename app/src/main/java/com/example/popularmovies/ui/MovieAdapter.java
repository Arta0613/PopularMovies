package com.example.popularmovies.ui;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.example.popularmovies.BR;
import com.example.popularmovies.R;
import com.example.popularmovies.domain.movies.MovieItem;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private List<MovieItem> movies;
    private HomeItemClickListener homeItemClickListener;

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.movie_poster, parent, false);

        return new MovieViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull final MovieViewHolder holder, final int position) {
        holder.bind(position);
    }

    @Override
    public final int getItemCount() {
        return movies == null ? 0 : movies.size();
    }

    public void setMovies(final List<MovieItem> movies) {
        this.movies = movies;
    }

    public void setItemClickListener(final HomeItemClickListener homeItemClickListener) {
        this.homeItemClickListener = homeItemClickListener;
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {

        public final ViewDataBinding binding;

        public MovieViewHolder(final ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(final int position) {
            binding.getRoot().setOnClickListener(l ->
                    homeItemClickListener.onItemClicked(movies.get(position))
            );
            binding.setVariable(BR.movieItem, movies.get(position));
            binding.executePendingBindings();
        }
    }
}