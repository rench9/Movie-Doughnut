package com.yahoo.r4hu7.moviesdoughnut.data.local;

import android.support.annotation.NonNull;

import com.yahoo.r4hu7.moviesdoughnut.data.MoviesDataSource;
import com.yahoo.r4hu7.moviesdoughnut.data.SortOrder;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.MovieCreditsResponse;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.MovieExternalIdsResponse;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.MovieImagesResponse;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.MovieResponse;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.MovieVideosResponse;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.model.Movie;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.model.Review;

public class LocalDataSource implements MoviesDataSource {
    private static volatile LocalDataSource INSTANCE;
    MoviesDatabase database;

    private LocalDataSource(@NonNull MoviesDatabase database) {
        this.database = database;
    }

    public static LocalDataSource getInstance(MoviesDatabase moviesDatabase) {
        if (INSTANCE == null)
            INSTANCE = new LocalDataSource(moviesDatabase);
        return INSTANCE;
    }


    @Override
    public void getMovies(int sortOrder, int page, LoadPagingItemCallback<Movie[], Integer, Integer> callback) {

    }

    @Override
    public void getMovie(int movieId, LoadItemCallback<MovieResponse> callback) {

    }

    @Override
    public void getImages(int movieId, LoadItemCallback<MovieImagesResponse> callback) {

    }

    @Override
    public void getCast(int movieId, LoadItemCallback<MovieCreditsResponse> callback) {

    }

    @Override
    public void getVideo(int movieId, LoadItemCallback<MovieVideosResponse> callback) {

    }

    @Override
    public void getReviews(int movieId, int page, LoadPagingItemCallback<Review[], Integer, Integer> callback) {

    }

    @Override
    public void getLinks(int movieId, LoadItemCallback<MovieExternalIdsResponse> callback) {

    }

    @Override
    public void markFavourite(int movieId) {

    }

    @Override
    public void saveMovies(Movie[] movies) {

    }

    @Override
    public void saveMovie(MovieResponse movie) {

    }

    @Override
    public void saveImages(MovieImagesResponse movieImagesResponse) {

    }

    @Override
    public void saveCast(MovieCreditsResponse movieCreditsResponse) {

    }

    @Override
    public void saveVideo(MovieVideosResponse movieVideosResponse) {

    }

    @Override
    public void saveSortOrder(SortOrder[] sortOrders) {

    }
}
