package com.yahoo.r4hu7.moviesdoughnut.data.local;

import com.yahoo.r4hu7.moviesdoughnut.data.MoviesDataSource;
import com.yahoo.r4hu7.moviesdoughnut.data.SortOrder;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.MovieExternalIdsResponse;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.MovieResponse;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.model.Cast;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.model.ImageSource;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.model.Movie;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.model.Review;

public class LocalDataSource implements MoviesDataSource {
    private static volatile LocalDataSource INSTANCE;

    private LocalDataSource() {

    }

    public static LocalDataSource getInstance() {
        if (INSTANCE == null)
            INSTANCE = new LocalDataSource();
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
