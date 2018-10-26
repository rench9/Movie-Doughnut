package com.yahoo.r4hu7.moviesdoughnut.ui.dependency.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.yahoo.r4hu7.moviesdoughnut.R;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.model.Movie;
import com.yahoo.r4hu7.moviesdoughnut.databinding.AdapterPosterBinding;
import com.yahoo.r4hu7.moviesdoughnut.databinding.AdapterPosterGridBinding;
import com.yahoo.r4hu7.moviesdoughnut.ui.viewmodel.PosterViewModel;
import com.yahoo.r4hu7.moviesdoughnut.util.MovieNavigator;

import java.util.ArrayList;
import java.util.List;

public class PosterCardAdapter extends RecyclerView.Adapter<PosterCardAdapter.ViewHolder> {

    private List<Movie> movies;
    private MovieNavigator movieNavigator;

    private boolean isGrid;

    public PosterCardAdapter(MovieNavigator navigator, boolean isGrid) {
        this.movies = new ArrayList<>();
        this.movieNavigator = navigator;
        this.isGrid = isGrid;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (isGrid) {
            AdapterPosterGridBinding binding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.getContext()),
                    R.layout.adapter_poster_grid,
                    parent,
                    false);
            return new ViewHolder(binding);
        } else {
            AdapterPosterBinding binding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.getContext()),
                    R.layout.adapter_poster,
                    parent,
                    false);
            return new ViewHolder(binding);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.viewModel.setNavigator(movieNavigator);
        holder.viewModel.setMovie(movies.get(position));
    }

    @Override
    public int getItemCount() {
        if (movies == null)
            return 0;
        return movies.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        PosterViewModel viewModel;

        ViewHolder(AdapterPosterBinding binding) {
            super(binding.getRoot());
            viewModel = new PosterViewModel();
            binding.setVm(viewModel);
        }

        ViewHolder(AdapterPosterGridBinding binding) {
            super(binding.getRoot());
            viewModel = new PosterViewModel();
            binding.setVm(viewModel);
        }
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }
}
