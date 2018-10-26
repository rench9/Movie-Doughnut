package com.yahoo.r4hu7.moviesdoughnut.data.remote.request;

import android.arch.lifecycle.LiveData;

import com.yahoo.r4hu7.moviesdoughnut.data.remote.ApiResponse;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.Endpoints;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.LatestMovieResponse;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.MovieCreditsResponse;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.MovieExternalIdsResponse;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.MovieImagesResponse;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.MovieResponse;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.MovieReviewsResponse;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.MovieVideosResponse;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.NowPlayingMovieResponse;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.PopularMovieResponse;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.RecommendedMoviesResponse;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.TopRatedMovieResponse;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.UpcomingMovieResponse;

public class Movies {
    private Endpoints endpoints;

    public Movies(Endpoints e) {
        endpoints = e;
    }

    public LiveData<ApiResponse<MovieResponse>> getMovieById(int movieId) {
        return endpoints.getMovieById(movieId);
    }

    public LiveData<ApiResponse<LatestMovieResponse>> getLatestMovie() {
        return endpoints.getLatestMovie();
    }

    public LiveData<ApiResponse<NowPlayingMovieResponse>> getNowPlayingMovies(int page) {
        return endpoints.getNowPlayingMovies(page);
    }

    public LiveData<ApiResponse<NowPlayingMovieResponse>> getNowPlayingMovies(int page, String region) {
        return endpoints.getNowPlayingMovies(page, region);
    }

    public LiveData<ApiResponse<PopularMovieResponse>> getPopularMovies(int page) {
        return endpoints.getPopularMovies(page);
    }

    public LiveData<ApiResponse<PopularMovieResponse>> getPopularMovie(int page) {
        return endpoints.getPopularMovie(page);
    }

    public LiveData<ApiResponse<PopularMovieResponse>> getPopularMovies(int page, String region) {
        return endpoints.getPopularMovies(page, region);
    }

    public LiveData<ApiResponse<TopRatedMovieResponse>> getTopRatedMovies(int page) {
        return endpoints.getTopRatedMovies(page);
    }

    public LiveData<ApiResponse<TopRatedMovieResponse>> getTopRatedMovies(int page, String region) {
        return endpoints.getTopRatedMovies(page, region);
    }

    public LiveData<ApiResponse<UpcomingMovieResponse>> getUpcomingMovies(int page) {
        return endpoints.getUpcomingMovies(page);
    }

    public LiveData<ApiResponse<UpcomingMovieResponse>> getUpcomingMovies(int page, String region) {
        return endpoints.getUpcomingMovies(page, region);
    }

    public LiveData<ApiResponse<RecommendedMoviesResponse>> getSimilarMovies(int movieId, int page) {
        return endpoints.getSimilarMovies(movieId, page);
    }

    public LiveData<ApiResponse<RecommendedMoviesResponse>> getRecommendedMovies(int movieId, int page) {
        return endpoints.getRecommendedMovies(movieId, page);
    }

    public LiveData<ApiResponse<MovieReviewsResponse>> getMovieReviews(int movieId, int page) {
        return endpoints.getMovieReviews(movieId, page);
    }

    public LiveData<ApiResponse<MovieImagesResponse>> getMovieImages(int movieId) {
        return endpoints.getMovieImages(movieId);
    }

    public LiveData<ApiResponse<MovieVideosResponse>> getMovieVideos(int movieId) {
        return endpoints.getMovieVideos(movieId);
    }

    public LiveData<ApiResponse<MovieCreditsResponse>> getMovieCredits(int movieId) {
        return endpoints.getMovieCredits(movieId);
    }

    public LiveData<ApiResponse<MovieExternalIdsResponse>> getMovieExternalIds(int movieId) {
        return endpoints.getMovieExternalIds(movieId);
    }

}
