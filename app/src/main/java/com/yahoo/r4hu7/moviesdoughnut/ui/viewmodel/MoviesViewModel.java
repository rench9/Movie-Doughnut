package com.yahoo.r4hu7.moviesdoughnut.ui.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.Nullable;

import com.yahoo.r4hu7.moviesdoughnut.data.MoviesRepository;
import com.yahoo.r4hu7.moviesdoughnut.data.Resource;
import com.yahoo.r4hu7.moviesdoughnut.data.SortOrder;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.AbsentLiveData;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.model.Movie;

import java.util.List;

public class MoviesViewModel extends ViewModel {
    public LiveData<Resource<List<Movie>>> movies;
    public MoviesRepository repository;
    private MutableLiveData<Integer> nextPage = new MutableLiveData<>();
    private int currentPage;
    private int sortOrder;
    private LoadMoreHandler loadMoreHandler;

    public MoviesViewModel(MoviesRepository repository) {
        this.repository = repository;
        this.sortOrder = SortOrder.NONE;
        this.loadMoreHandler = new LoadMoreHandler(repository);
        this.nextPage.setValue(0);
        this.movies = Transformations.switchMap(nextPage, input ->
                input == currentPage
                        ? AbsentLiveData.create()
                        : repository.getMovies(sortOrder, input)
        );
    }

    public void setSortOrder(int sortOrder) {
        if (this.sortOrder == sortOrder)
            return;
        loadMoreHandler.reset();
        this.sortOrder = sortOrder;
        this.nextPage.setValue(1);
    }

    public void loadMoreMovies() {
        if (sortOrder > SortOrder.NONE && this.nextPage.getValue() != null && nextPage.getValue() != currentPage) {
            loadMoreHandler.loadMoreMovies(nextPage.getValue());
        }
    }

    public void refresh() {
        nextPage.setValue(nextPage.getValue());
    }

    public LoadMoreHandler getLoadMoreHandler() {
        return loadMoreHandler;
    }

    public int getNextPage() {
        return nextPage.getValue() != null ? nextPage.getValue() : 0;
    }

    public void setNextPage(int nextPage) {
        if (this.nextPage.getValue() != null && this.nextPage.getValue() == nextPage)
            return;
        loadMoreHandler.reset();
        this.nextPage.setValue(this.nextPage.getValue() + 1);
    }

    private class LoadMoreState {
        private Boolean handledError;
        private Boolean isRunning;
        private String errorMessage;

        public LoadMoreState(Boolean isRunning, String errorMessage) {
            this.handledError = false;
            this.isRunning = isRunning;
            this.errorMessage = errorMessage;
        }

        public String errorMessageIfNotHandled() {
            if (handledError)
                return null;
            handledError = true;
            return errorMessage;
        }

        public Boolean getHandledError() {
            return handledError;
        }

        public Boolean getRunning() {
            return isRunning;
        }

        public String getErrorMessage() {
            return errorMessage;
        }
    }

    private class LoadMoreHandler implements Observer<Resource<List<Movie>>> {
        private LiveData<Resource<List<Movie>>> moreMoviesLiveData;
        private MutableLiveData<LoadMoreState> loadMoreState = new MutableLiveData<>();
        private MoviesRepository repository;
        private int nextPage;
        private boolean hasMore;

        LoadMoreHandler(MoviesRepository repository) {
            this.repository = repository;
            hasMore = false;
        }

        private void loadMoreMovies(int nextPage) {
            unregister();
            moreMoviesLiveData = repository.getMovies(sortOrder, nextPage);
        }

        private void unregister() {
            if (moreMoviesLiveData != null)
                moreMoviesLiveData.removeObserver(this);
            moreMoviesLiveData = null;
            if (hasMore)
                nextPage = -1;
        }

        private void reset() {
            unregister();
            hasMore = true;
            loadMoreState.setValue(new LoadMoreState(
                    false,
                    null
            ));
        }

        @Override
        public void onChanged(@Nullable Resource<List<Movie>> booleanResource) {
            if (booleanResource == null)
                reset();
            else {
                switch (booleanResource.getStatus()) {
                    case ERROR:
                        hasMore = true;
                        unregister();
                        loadMoreState.setValue(new LoadMoreState(
                                false,
                                booleanResource.getMessage()
                        ));
                        break;
                    case LOADING:
                        break;
                    case SUCCESS:
//                        hasMore = booleanResource.getData();
                        hasMore = true;
                        unregister();
                        loadMoreState.setValue(new LoadMoreState(
                                false,
                                null
                        ));
                        break;
                }
            }
        }
    }
}
