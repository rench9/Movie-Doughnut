package com.yahoo.r4hu7.moviesdoughnut.data;

import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.MovieCreditsResponse;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.MovieExternalIdsResponse;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.MovieImagesResponse;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.MovieResponse;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.MovieVideosResponse;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.model.Movie;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.model.Review;

public interface MoviesDataSource {
    void getMovies(int sortOrder, int page, LoadPagingItemCallback<Movie[], Integer, Integer> callback);

    void getMovie(int movieId, LoadItemCallback<MovieResponse> callback);

    void getImages(int movieId, LoadItemCallback<MovieImagesResponse> callback);

    void getCast(int movieId, LoadItemCallback<MovieCreditsResponse> callback);

    void getVideo(int movieId, LoadItemCallback<MovieVideosResponse> callback);

    void getReviews(int movieId, int page, LoadPagingItemCallback<Review[], Integer, Integer> callback);

    void getLinks(int movieId, LoadItemCallback<MovieExternalIdsResponse> callback);

    void isMovieFav(int movieId, LoadItemCallback<Boolean> callback);

    void markFavourite(int movieId);

    void unMarkFavourite(int movieId);

    void saveMovies(Movie[] movies);

    void saveMovie(MovieResponse movie);

    void saveImages(MovieImagesResponse movieImagesResponse);

    void saveCast(MovieCreditsResponse movieCreditsResponse);

    void saveVideo(MovieVideosResponse movieVideosResponse);

    void saveSortOrder(int sortOrder, Movie[] movies);

    void saveLinks(MovieExternalIdsResponse movieExternalIdsResponse);

    interface LoadItemCallback<T> {
        void onLoading();

        void onItemLoaded(T t);

        void onDataNotAvailable(Throwable e);
    }

    interface LoadPagingItemCallback<T, T2, T3> {
        void onLoading();

        void onItemLoaded(T t, T2 page, T3 totalPages);

        void onDataNotAvailable(Throwable e);
    }
}