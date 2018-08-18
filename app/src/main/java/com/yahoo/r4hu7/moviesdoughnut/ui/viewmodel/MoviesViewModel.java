package com.yahoo.r4hu7.moviesdoughnut.ui.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableList;

import com.yahoo.r4hu7.moviesdoughnut.data.MoviesDataSource;
import com.yahoo.r4hu7.moviesdoughnut.data.MoviesRepository;
import com.yahoo.r4hu7.moviesdoughnut.data.SortOrder;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.model.Movie;

import java.util.Arrays;

public class MoviesViewModel extends ViewModel {
    public final ObservableList<Movie> movies = new ObservableArrayList<>();
    private final ObservableBoolean dataLoading = new ObservableBoolean(false);
    private MoviesRepository repository;
    private int currentPage;
    private int sortOrder;
    private int totalPages;

    public MoviesViewModel(MoviesRepository repository) {
        this.repository = repository;
        currentPage = 0;
        sortOrder = SortOrder.POPULAR;
    }

    public MoviesViewModel(MoviesRepository repository, int sortOrder) {
        this.repository = repository;
        currentPage = 0;
        this.sortOrder = sortOrder;
    }

    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
    }

    public void loadMovies() {
        repository.getMovies(sortOrder, currentPage + 1, new MoviesDataSource.LoadPagingItemCallback<Movie[], Integer, Integer>() {
            @Override
            public void onLoading() {
                dataLoading.set(true);
            }

            @Override
            public void onItemLoaded(Movie[] movies, Integer page, Integer totalPages) {
                MoviesViewModel.this.movies.addAll(Arrays.asList(movies));
                MoviesViewModel.this.totalPages = totalPages;
                dataLoading.set(false);
                currentPage++;
            }

            @Override
            public void onDataNotAvailable(Throwable e) {
                dataLoading.set(false);
            }
        });
    }

    public boolean isLastPage() {
        return currentPage >= totalPages;
    }

    public boolean isDataLoading() {
        return dataLoading.get();
    }
}
