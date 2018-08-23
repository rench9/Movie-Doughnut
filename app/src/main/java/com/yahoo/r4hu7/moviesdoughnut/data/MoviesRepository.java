package com.yahoo.r4hu7.moviesdoughnut.data;

import android.support.annotation.NonNull;

import com.yahoo.r4hu7.moviesdoughnut.data.local.LocalDataSource;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.RemoteDataSource;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.MovieCreditsResponse;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.MovieExternalIdsResponse;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.MovieImagesResponse;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.MovieResponse;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.MovieVideosResponse;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.model.Movie;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.model.Review;

public class MoviesRepository implements MoviesDataSource {
    private static MoviesRepository INSTANCE;
    private LocalDataSource localDataSource;
    private RemoteDataSource remoteDataSource;

    public MoviesRepository(@NonNull LocalDataSource localDataSource, @NonNull RemoteDataSource remoteDataSource) {
        this.localDataSource = localDataSource;
        this.remoteDataSource = remoteDataSource;
    }

    public static MoviesRepository getInstance(LocalDataSource localDataSource, RemoteDataSource remoteDataSource) {
        if (INSTANCE == null)
            INSTANCE = new MoviesRepository(localDataSource, remoteDataSource);
        return INSTANCE;
    }

    @Override
    public void getMovies(int sortOrder, int page, LoadPagingItemCallback<Movie[], Integer, Integer> callback) {
        remoteDataSource.getMovies(sortOrder, page, callback);
    }

    @Override
    public void getMovie(int movieId, LoadItemCallback<MovieResponse> callback) {
        remoteDataSource.getMovie(movieId, callback);
    }

    @Override
    public void getImages(int movieId, LoadItemCallback<MovieImagesResponse> callback) {
        remoteDataSource.getImages(movieId, callback);
    }

    @Override
    public void getCast(int movieId, LoadItemCallback<MovieCreditsResponse> callback) {
        remoteDataSource.getCast(movieId, callback);
    }

    @Override
    public void getVideo(int movieId, LoadItemCallback<MovieVideosResponse> callback) {
        remoteDataSource.getVideo(movieId, callback);
    }

    @Override
    public void getReviews(int movieId, int page, LoadPagingItemCallback<Review[], Integer, Integer> callback) {
        remoteDataSource.getReviews(movieId, page, callback);
    }

    @Override
    public void getLinks(int movieId, LoadItemCallback<MovieExternalIdsResponse> callback) {
        remoteDataSource.getLinks(movieId, callback);
    }

    @Override
    public void markFavourite(int movieId) {
        localDataSource.markFavourite(movieId);
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
