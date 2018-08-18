package com.yahoo.r4hu7.moviesdoughnut.data.remote;

import android.support.annotation.NonNull;

import com.yahoo.r4hu7.moviesdoughnut.data.MoviesDataSource;
import com.yahoo.r4hu7.moviesdoughnut.data.SortOrder;
import com.yahoo.r4hu7.moviesdoughnut.data.local.LocalDataSource;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.request.Movies;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.MovieCreditsResponse;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.MovieExternalIdsResponse;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.MovieImagesResponse;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.MovieResponse;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.model.Movie;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.model.Review;

public class RemoteDataSource implements MoviesDataSource {

    private static RemoteDataSource INSTANCE;
    private Movies movies;
    private LocalDataSource localDataSource;

    private RemoteDataSource(@NonNull Endpoints endpoints, @NonNull LocalDataSource localDataSource) {
        this.movies = new Movies(endpoints);
        this.localDataSource = localDataSource;
    }

    public static RemoteDataSource getInstance(Endpoints endpoints, LocalDataSource localDataSource) {
        if (INSTANCE == null)
            INSTANCE = new RemoteDataSource(endpoints, localDataSource);
        return INSTANCE;
    }

    @Override
    public void getMovies(int sortOrder, int page, LoadPagingItemCallback<Movie[], Integer, Integer> callback) {

        switch (sortOrder) {
            case SortOrder.POPULAR:
                movies.getPopularMovies(page).subscribe(new MovieResponseObserver<>(callback));
                break;
            case SortOrder.NOWPLAYING:
                movies.getNowPlayingMovies(page).subscribe(new MovieResponseObserver<>(callback));
                break;
            case SortOrder.TOPRATED:
                movies.getTopRatedMovies(page).subscribe(new MovieResponseObserver<>(callback));
                break;
            case SortOrder.UPCOMING:
                movies.getUpcomingMovies(page).subscribe(new MovieResponseObserver<>(callback));
                break;
            default:
                movies.getPopularMovies(page).subscribe(new MovieResponseObserver<>(callback));
                break;
        }
    }

    @Override
    public void getMovie(int movieId, LoadItemCallback<MovieResponse> callback) {
        movies.getMovieById(movieId).subscribe(new MovieResponseObserver<>(callback));
    }

    @Override
    public void getImages(int movieId, LoadItemCallback<MovieImagesResponse> callback) {
        movies.getMovieImages(movieId).subscribe(new MovieResponseObserver<>(callback));
    }

    @Override
    public void getCast(int movieId, LoadItemCallback<MovieCreditsResponse> callback) {
        movies.getMovieCredits(movieId).subscribe(new MovieResponseObserver<>(callback));
    }

    @Override
    public void getReviews(int movieId, int page, LoadPagingItemCallback<Review[], Integer, Integer> callback) {
        movies.getMovieReviews(movieId, page).subscribe(new MovieResponseObserver<>(callback));
    }

    @Override
    public void getLinks(int movieId, LoadItemCallback<MovieExternalIdsResponse> callback) {
        movies.getMovieExternalIds(movieId).subscribe(new MovieResponseObserver<>(callback));
    }

    @Override
    public void markFavourite(int movieId) {
        localDataSource.markFavourite(movieId);
    }

    @Override
    public void saveMovies(Movie[] movies) {
        localDataSource.saveMovies(movies);
    }

    @Override
    public void saveMovie(MovieResponse movie) {
        localDataSource.saveMovie(movie);
    }

    @Override
    public void saveImages(MovieImagesResponse movieImagesResponse) {
        localDataSource.saveImages(movieImagesResponse);
    }

    @Override
    public void saveCast(MovieCreditsResponse movieCreditsResponse) {
        localDataSource.saveCast(movieCreditsResponse);
    }

    @Override
    public void saveSortOrder(SortOrder[] sortOrders) {
        localDataSource.saveSortOrder(sortOrders);
    }
}
