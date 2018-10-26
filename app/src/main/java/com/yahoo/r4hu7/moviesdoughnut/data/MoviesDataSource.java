package com.yahoo.r4hu7.moviesdoughnut.data;

import android.arch.lifecycle.LiveData;

import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.MovieCreditsResponse;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.MovieExternalIdsResponse;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.MovieImagesResponse;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.MovieResponse;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.MovieVideosResponse;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.model.Movie;

public interface MoviesDataSource {
    <T> T getNowPlayingMovies(int page);

    <T> T getPopularMovies(int page);

    <T> T getTopRatedMovies(int page);

    <T> T getUpcomingMovies(int page);

    <T> LiveData<MovieResponse> getMovie(int movieId);

    <T> LiveData<MovieImagesResponse> getImages(int movieId);

    <T> LiveData<MovieCreditsResponse> getCast(int movieId);

    <T> LiveData<MovieVideosResponse> getVideo(int movieId);

    <T> T getReviews(int movieId, int page);

    <T> T getLinks(int movieId);

    <T> T isMovieFav(int movieId);

    void markFavourite(int movieId);

    void unMarkFavourite(int movieId);

    void saveMovies(Movie[] movies);

    void saveMovie(MovieResponse movie);

    void saveImages(MovieImagesResponse movieImagesResponse);

    void saveCast(MovieCreditsResponse movieCreditsResponse);

    void saveVideo(MovieVideosResponse movieVideosResponse);

    void saveSortOrder(int sortOrder, Movie[] movies);

    void saveLinks(MovieExternalIdsResponse movieExternalIdsResponse);

/*    interface LoadItemCallback<T> {
        void onLoading();

        void onItemLoaded(T t);

        void onDataNotAvailable(Throwable e);
    }

    interface LoadPagingItemCallback<T, T2, T3> {
        void onLoading();

        void onItemLoaded(T t, T2 page, T3 totalPages);

        void onDataNotAvailable(Throwable e);
    }*/
}