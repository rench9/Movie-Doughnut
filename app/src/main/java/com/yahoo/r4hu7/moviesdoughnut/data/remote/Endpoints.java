package com.yahoo.r4hu7.moviesdoughnut.data.remote;

import android.arch.lifecycle.LiveData;

import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.DiscoverMovieResponse;
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

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Endpoints {

    @GET("discover/movie")
    LiveData<ApiResponse<DiscoverMovieResponse>> discoverMovies(
            @Query("sort_by") String sort_by,
            @Query("page") int page);

    @GET("discover/movie")
    LiveData<ApiResponse<DiscoverMovieResponse>> discoverMovies(
            @Query("page") int page);

    @GET("movie/{movie_id}")
    LiveData<ApiResponse<MovieResponse>> getMovieById(
            @Path("movie_id") int movie_id);

    @GET("movie/latest")
    LiveData<ApiResponse<LatestMovieResponse>> getLatestMovie();

    @GET("movie/now_playing")
    LiveData<ApiResponse<NowPlayingMovieResponse>> getNowPlayingMovies(
            @Query("page") int page);

    @GET("movie/now_playing")
    LiveData<ApiResponse<NowPlayingMovieResponse>> getNowPlayingMovies(
            @Query("page") int page,
            @Query("region") String region);

    @GET("movie/popular")
    LiveData<ApiResponse<PopularMovieResponse>> getPopularMovies(
            @Query("page") int page);

    @GET("movie/popular")
    LiveData<ApiResponse<PopularMovieResponse>> getPopularMovie(
            @Query("page") int page);

    @GET("movie/popular")
    LiveData<ApiResponse<PopularMovieResponse>> getPopularMovies(
            @Query("page") int page,
            @Query("region") String region);

    @GET("movie/top_rated")
    LiveData<ApiResponse<TopRatedMovieResponse>> getTopRatedMovies(
            @Query("page") int page);

    @GET("movie/top_rated")
    LiveData<ApiResponse<TopRatedMovieResponse>> getTopRatedMovies(
            @Query("page") int page,
            @Query("region") String region);

    @GET("movie/upcoming")
    LiveData<ApiResponse<UpcomingMovieResponse>> getUpcomingMovies(
            @Query("page") int page);

    @GET("movie/upcoming")
    LiveData<ApiResponse<UpcomingMovieResponse>> getUpcomingMovies(
            @Query("page") int page,
            @Query("region") String region);

    @GET("movie/{movie_id}/similar")
    LiveData<ApiResponse<RecommendedMoviesResponse>> getSimilarMovies(
            @Path("movie_id") int movie_id,
            @Query("page") int page);

    @GET("movie/{movie_id}/recommendations")
    LiveData<ApiResponse<RecommendedMoviesResponse>> getRecommendedMovies(
            @Path("movie_id") int movie_id,
            @Query("page") int page);

    @GET("movie/{movie_id}/reviews")
    LiveData<ApiResponse<MovieReviewsResponse>> getMovieReviews(
            @Path("movie_id") int movie_id,
            @Query("page") int page);

    @GET("movie/{movie_id}/images")
    LiveData<ApiResponse<MovieImagesResponse>> getMovieImages(
            @Path("movie_id") int movie_id);

    @GET("movie/{movie_id}/videos")
    LiveData<ApiResponse<MovieVideosResponse>> getMovieVideos(
            @Path("movie_id") int movie_id);

    @GET("movie/{movie_id}/credits")
    LiveData<ApiResponse<MovieCreditsResponse>> getMovieCredits(
            @Path("movie_id") int movie_id);

    @GET("movie/{movie_id}/external_ids")
    LiveData<ApiResponse<MovieExternalIdsResponse>> getMovieExternalIds(
            @Path("movie_id") int movie_id);
}
