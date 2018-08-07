package com.yahoo.r4hu7.moviesdoughnut.data;

import android.support.annotation.NonNull;

import com.yahoo.r4hu7.moviesdoughnut.data.local.LocalDataSource;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.RemoteDataSource;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.MovieExternalIdsResponse;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.MovieResponse;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.model.Cast;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.model.ImageSource;
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
    public void getMovies(LoadItemCallback<Movie[]> callback) {

    }

    @Override
    public void getMovie(LoadItemCallback<MovieResponse> callback) {

    }

    @Override
    public void getImages(LoadItemCallback<ImageSource[]> callback) {

    }

    @Override
    public void getCast(LoadItemCallback<Cast[]> callback) {

    }

    @Override
    public void getReviews(LoadItemCallback<Review[]> callback) {

    }

    @Override
    public void getLinks(LoadItemCallback<MovieExternalIdsResponse> callback) {

    }

    @Override
    public void favouriteMovie(int movieId) {

    }

    @Override
    public void saveMovies(Movie[] movies) {

    }

    @Override
    public void saveMovie(MovieResponse movie) {

    }

    @Override
    public void saveImages(ImageSource source) {

    }

    @Override
    public void saveCast(Cast[] casts) {

    }

    @Override
    public void saveSortOrder(SortOrder[] sortOrders) {

    }
}
