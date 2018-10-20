package com.yahoo.r4hu7.moviesdoughnut.data;

import android.support.annotation.NonNull;
import android.util.Log;

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
    private boolean isOffline = false;

    private MoviesRepository(@NonNull LocalDataSource localDataSource, @NonNull RemoteDataSource remoteDataSource) {
        this.localDataSource = localDataSource;
        this.remoteDataSource = remoteDataSource;
    }

    public static MoviesRepository getInstance(LocalDataSource localDataSource, RemoteDataSource remoteDataSource) {
        if (INSTANCE == null)
            INSTANCE = new MoviesRepository(localDataSource, remoteDataSource);
        return INSTANCE;
    }

    public boolean isOffline() {
        return isOffline;
    }

    public void setOffline(boolean offline) {
        this.isOffline = offline;
    }

    @Override
    public void getMovies(int sortOrder, int page, LoadPagingItemCallback<Movie[], Integer, Integer> callback) {
        if (isOffline || sortOrder == SortOrder.FAVORITE)
            localDataSource.getMovies(sortOrder, page, callback);
        else
            remoteDataSource.getMovies(sortOrder, page, callback);
        Log.e("ASDSADS", "SSSSSSSS" + isOffline);

    }

    @Override
    public void getMovie(int movieId, LoadItemCallback<MovieResponse> callback) {
        if (isOffline)
            localDataSource.getMovie(movieId, callback);
        else
            remoteDataSource.getMovie(movieId, callback);
    }

    @Override
    public void getImages(int movieId, LoadItemCallback<MovieImagesResponse> callback) {
        if (isOffline)
            localDataSource.getImages(movieId, callback);
        else
            remoteDataSource.getImages(movieId, callback);
    }

    @Override
    public void getCast(int movieId, LoadItemCallback<MovieCreditsResponse> callback) {
        if (isOffline)
            localDataSource.getCast(movieId, callback);
        else
            remoteDataSource.getCast(movieId, callback);

    }

    @Override
    public void getVideo(int movieId, LoadItemCallback<MovieVideosResponse> callback) {
        if (isOffline)
            localDataSource.getVideo(movieId, callback);
        else
            remoteDataSource.getVideo(movieId, callback);
    }

    @Override
    public void getReviews(int movieId, int page, LoadPagingItemCallback<Review[], Integer, Integer> callback) {
        remoteDataSource.getReviews(movieId, page, callback);
    }

    @Override
    public void getLinks(int movieId, LoadItemCallback<MovieExternalIdsResponse> callback) {
        if (isOffline)
            localDataSource.getLinks(movieId, callback);
        else
            remoteDataSource.getLinks(movieId, callback);
    }

    @Override
    public void isMovieFav(int movieId, LoadItemCallback<Boolean> callback) {
        localDataSource.isMovieFav(movieId, callback);
    }

    @Override
    public void markFavourite(int movieId) {
        localDataSource.markFavourite(movieId);
    }

    @Override
    public void unMarkFavourite(int movieId) {
        localDataSource.unMarkFavourite(movieId);
    }

    @Override
    public void saveMovies(Movie[] movies) {
        if (!isOffline)
            localDataSource.saveMovies(movies);
    }

    @Override
    public void saveMovie(MovieResponse movie) {
        if (!isOffline)
            localDataSource.saveMovie(movie);
    }

    @Override
    public void saveImages(MovieImagesResponse movieImagesResponse) {
        if (!isOffline)
            localDataSource.saveImages(movieImagesResponse);
    }

    @Override
    public void saveCast(MovieCreditsResponse movieCreditsResponse) {
        if (!isOffline)
            localDataSource.saveCast(movieCreditsResponse);
    }

    @Override
    public void saveVideo(MovieVideosResponse movieVideosResponse) {
        if (!isOffline)
            localDataSource.saveVideo(movieVideosResponse);
    }

    @Override
    public void saveSortOrder(int sortId, Movie[] movies) {
        if (!isOffline)
            localDataSource.saveSortOrder(sortId, movies);
    }

    @Override
    public void saveLinks(MovieExternalIdsResponse movieExternalIdsResponse) {
        if (!isOffline)
            localDataSource.saveLinks(movieExternalIdsResponse);
    }
}
