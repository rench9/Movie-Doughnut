package com.yahoo.r4hu7.moviesdoughnut.data.local;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.yahoo.r4hu7.moviesdoughnut.data.SortOrder;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.MovieCreditsResponse;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.MovieExternalIdsResponse;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.MovieImagesResponse;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.MovieResponse;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.MovieVideosResponse;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.model.Movie;
import com.yahoo.r4hu7.moviesdoughnut.util.AppExecutors;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class LocalDataSource {
    private static volatile LocalDataSource INSTANCE;
    private MoviesDatabase database;
    private AppExecutors mAppExecutors;

    private LocalDataSource(@NonNull AppExecutors appExecutors, @NonNull MoviesDatabase database) {
        this.database = database;
        this.mAppExecutors = appExecutors;
    }

    public static LocalDataSource getInstance(@NonNull AppExecutors appExecutors, MoviesDatabase moviesDatabase) {
        if (INSTANCE == null)
            INSTANCE = new LocalDataSource(appExecutors, moviesDatabase);
        return INSTANCE;
    }

    private void execute(Runnable runnable) {
        mAppExecutors.diskIO().execute(runnable);
    }


    public LiveData<List<Movie>> getNowPlayingMovies() {
        return database.movieDao().findMovies(SortOrder.NOWPLAYING);
    }

    public LiveData<List<Movie>> getPopularMovies() {
        return database.movieDao().findMovies(SortOrder.POPULAR);
    }

    public LiveData<List<Movie>> getTopRatedMovies() {
        return database.movieDao().findMovies(SortOrder.TOPRATED);
    }

    public LiveData<List<Movie>> getUpcomingMovies() {
        return database.movieDao().findMovies(SortOrder.UPCOMING);
    }

    public LiveData<List<Movie>> getFavMovies() {
        return database.movieDao().getFavMovies();
    }


    public LiveData<MovieResponse> getMovie(int movieId) {
        return database.movieDetailDao().findMovieDetailById(movieId);
    }


    public LiveData<MovieImagesResponse> getImages(int movieId) {
        return database.imageDao().getResponse(movieId);
    }


    public LiveData<MovieCreditsResponse> getCast(int movieId) {
        return database.castDao().getResponse(movieId);
    }


    public LiveData<MovieVideosResponse> getVideo(int movieId) {
        return database.videoDao().getResponse(movieId);
    }


    public void getReviews(int movieId, int page) {

    }


    public LiveData<MovieExternalIdsResponse> getLinks(int movieId) {
        return database.externalLinkDao().getResponse(movieId);
    }


    public LiveData<Boolean> isMovieFav(int movieId) {
        return database.movieDao().isMovieFav(movieId);
    }


    public void markFavourite(int movieId) {
        Runnable runnable = () -> database.movieDao().markFavourite(movieId);
        execute(runnable);
    }


    public void unMarkFavourite(int movieId) {
        Runnable runnable = () -> database.movieDao().unMarkFavourite(movieId);
        execute(runnable);
    }


    public void saveMovies(Movie[] movies) {
        checkNotNull(movies);
        Runnable runnable = () -> database.movieDao().insert(movies);
        execute(runnable);
    }


    public void saveMovie(MovieResponse movie) {
        checkNotNull(movie);
        Runnable runnable = () -> database.movieDetailDao().insert(movie);
        execute(runnable);
    }


    public void saveImages(MovieImagesResponse movieImagesResponse) {
        checkNotNull(movieImagesResponse);
        Runnable runnable = () -> database.imageDao().insert(movieImagesResponse);
        execute(runnable);
    }


    public void saveCast(MovieCreditsResponse movieCreditsResponse) {
        checkNotNull(movieCreditsResponse);
        Runnable runnable = () -> database.castDao().insert(movieCreditsResponse);
        execute(runnable);
    }


    public void saveVideo(MovieVideosResponse movieVideosResponse) {
        checkNotNull(movieVideosResponse);
        Runnable runnable = () -> database.videoDao().insert(movieVideosResponse);
        execute(runnable);
    }


    public void saveSortOrder(int sortId, Movie[] movies) {
        checkNotNull(movies);
        SortOrder[] sortOrders = new SortOrder[movies.length];
        for (int i = 0; i < movies.length; i++) {
            sortOrders[i] = new SortOrder(sortId, movies[i].getId());
        }

        Runnable runnable = () -> database.sortOrderDao().insert(sortOrders);
        execute(runnable);
    }


    public void saveLinks(MovieExternalIdsResponse movieExternalIdsResponse) {
        checkNotNull(movieExternalIdsResponse);
        Runnable runnable = () -> database.externalLinkDao().insert(movieExternalIdsResponse);
        execute(runnable);
    }
}
