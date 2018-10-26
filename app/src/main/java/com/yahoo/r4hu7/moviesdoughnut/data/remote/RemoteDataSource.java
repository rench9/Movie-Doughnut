package com.yahoo.r4hu7.moviesdoughnut.data.remote;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.yahoo.r4hu7.moviesdoughnut.data.remote.request.Movies;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.MovieCreditsResponse;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.MovieExternalIdsResponse;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.MovieImagesResponse;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.MovieResponse;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.MovieReviewsResponse;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.MovieVideosResponse;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.NowPlayingMovieResponse;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.PopularMovieResponse;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.TopRatedMovieResponse;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.UpcomingMovieResponse;

public class RemoteDataSource {

    private static RemoteDataSource INSTANCE;
    private Movies movies;

    private RemoteDataSource(@NonNull Endpoints endpoints) {
        this.movies = new Movies(endpoints);
    }

    public static RemoteDataSource getInstance(Endpoints endpoints) {
        if (INSTANCE == null)
            INSTANCE = new RemoteDataSource(endpoints);
        return INSTANCE;
    }


    public LiveData<ApiResponse<NowPlayingMovieResponse>> getNowPlayingMovies(int page) {
        return movies.getNowPlayingMovies(page);
    }


    public LiveData<ApiResponse<PopularMovieResponse>> getPopularMovies(int page) {
        return movies.getPopularMovies(page);
    }


    public LiveData<ApiResponse<TopRatedMovieResponse>> getTopRatedMovies(int page) {
        return movies.getTopRatedMovies(page);
    }


    public LiveData<ApiResponse<UpcomingMovieResponse>> getUpcomingMovies(int page) {
        return movies.getUpcomingMovies(page);
    }


    public LiveData<ApiResponse<MovieResponse>> getMovie(int movieId) {
        return movies.getMovieById(movieId);
    }


    public LiveData<ApiResponse<MovieImagesResponse>> getImages(int movieId) {
        return movies.getMovieImages(movieId);
    }


    public LiveData<ApiResponse<MovieCreditsResponse>> getCast(int movieId) {
        return movies.getMovieCredits(movieId);
    }


    public LiveData<ApiResponse<MovieVideosResponse>> getVideo(int movieId) {
        return movies.getMovieVideos(movieId);
    }


    public LiveData<ApiResponse<MovieReviewsResponse>> getReviews(int movieId, int page) {
        return movies.getMovieReviews(movieId, page);
    }


    public LiveData<ApiResponse<MovieExternalIdsResponse>> getLinks(int movieId) {
        return movies.getMovieExternalIds(movieId);
    }
}
