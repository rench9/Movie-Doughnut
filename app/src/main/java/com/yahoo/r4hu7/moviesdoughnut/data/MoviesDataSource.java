package com.yahoo.r4hu7.moviesdoughnut.data;

import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.MovieExternalIdsResponse;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.MovieResponse;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.model.Cast;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.model.ImageSource;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.model.Movie;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.model.Review;

public interface MoviesDataSource {
    void getMovies(LoadItemCallback<Movie[]> callback);

    void getMovie(LoadItemCallback<MovieResponse> callback);

    void getImages(LoadItemCallback<ImageSource[]> callback);

    void getCast(LoadItemCallback<Cast[]> callback);

    void getReviews(LoadItemCallback<Review[]> callback);

    void getLinks(LoadItemCallback<MovieExternalIdsResponse> callback);

    void favouriteMovie(int movieId);

    void saveMovies(Movie[] movies);

    void saveMovie(MovieResponse movie);

    void saveImages(ImageSource source);

    void saveCast(Cast[] casts);

    void saveSortOrder(SortOrder[] sortOrders);

    interface LoadItemCallback<T> {
        void onMoviesLoaded(T t);

        void onDataNotAvailable();
    }
}
