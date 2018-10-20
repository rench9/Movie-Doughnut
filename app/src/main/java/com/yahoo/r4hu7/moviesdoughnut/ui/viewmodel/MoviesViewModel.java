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
    public ObservableBoolean dataLoaded = new ObservableBoolean();
    private MoviesRepository repository;
    private int currentPage;
    private int sortOrder;
    private int totalPages;

    public MoviesViewModel(MoviesRepository repository) {
        this.repository = repository;
        currentPage = 0;
        sortOrder = SortOrder.POPULAR;
    }

    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
        if (!repository.isOffline())
            currentPage = 0;
        movies.clear();
    }

    public void loadMovies() {
        if (sortOrder == 0)
            throw new IllegalArgumentException("First set Movies Sort order, using MoviesViewModel.setSortOrder(<SortOrder>)");
        repository.getMovies(sortOrder, currentPage + 1, new MoviesDataSource.LoadPagingItemCallback<Movie[], Integer, Integer>() {
            @Override
            public void onLoading() {
                dataLoading.set(true);
            }

            @Override
            public void onItemLoaded(Movie[] movies, Integer page, Integer totalPages) {
                MoviesViewModel.this.movies.addAll(Arrays.asList(movies));
                MoviesViewModel.this.totalPages = totalPages;
                currentPage++;
                dataLoading.set(false);
                repository.saveMovies(movies);
                repository.saveSortOrder(sortOrder, movies);
                dataLoaded.set(true);
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
