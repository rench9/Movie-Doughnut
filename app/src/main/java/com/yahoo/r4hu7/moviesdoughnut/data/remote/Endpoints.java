package com.yahoo.r4hu7.moviesdoughnut.data.remote;

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

import io.reactivex.Maybe;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Endpoints {

    @GET("discover/movie")
    Maybe<DiscoverMovieResponse> discoverMovies(
            @Query("sort_by") String sort_by,
            @Query("page") int page);

    @GET("discover/movie")
    Maybe<DiscoverMovieResponse> discoverMovies(
            @Query("page") int page);

    @GET("movie/{movie_id}")
    Maybe<MovieResponse> getMovieById(
            @Path("movie_id") int movie_id);

    @GET("movie/latest")
    Maybe<LatestMovieResponse> getLatestMovie();

    @GET("movie/now_playing")
    Maybe<NowPlayingMovieResponse> getNowPlayingMovies(
            @Query("page") int page);

    @GET("movie/now_playing")
    Maybe<NowPlayingMovieResponse> getNowPlayingMovies(
            @Query("page") int page,
            @Query("region") String region);

    @GET("movie/popular")
    Maybe<PopularMovieResponse> getPopularMovies(
            @Query("page") int page);

    @GET("movie/popular")
    Maybe<PopularMovieResponse> getPopularMovies(
            @Query("page") int page,
            @Query("region") String region);

    @GET("movie/top_rated")
    Maybe<TopRatedMovieResponse> getTopRatedMovies(
            @Query("page") int page);

    @GET("movie/top_rated")
    Maybe<TopRatedMovieResponse> getTopRatedMovies(
            @Query("page") int page,
            @Query("region") String region);

    @GET("movie/upcoming")
    Maybe<UpcomingMovieResponse> getUpcomingMovies(
            @Query("page") int page);

    @GET("movie/upcoming")
    Maybe<UpcomingMovieResponse> getUpcomingMovies(
            @Query("page") int page,
            @Query("region") String region);

    @GET("movie/{movie_id}/similar")
    Maybe<RecommendedMoviesResponse> getSimilarMovies(
            @Path("movie_id") int movie_id,
            @Query("page") int page);

    @GET("movie/{movie_id}/recommendations")
    Maybe<RecommendedMoviesResponse> getRecommendedMovies(
            @Path("movie_id") int movie_id,
            @Query("page") int page);

    @GET("movie/{movie_id}/reviews")
    Maybe<MovieReviewsResponse> getMovieReviews(
            @Path("movie_id") int movie_id,
            @Query("page") int page);

    @GET("movie/{movie_id}/images")
    Maybe<MovieImagesResponse> getMovieImages(
            @Path("movie_id") int movie_id);

    @GET("movie/{movie_id}/videos")
    Maybe<MovieVideosResponse> getMovieVideos(
            @Path("movie_id") int movie_id);

    @GET("movie/{movie_id}/credits")
    Maybe<MovieCreditsResponse> getMovieCredits(
            @Path("movie_id") int movie_id);

    @GET("movie/{movie_id}/external_ids")
    Maybe<MovieExternalIdsResponse> getMovieExternalIds(
            @Path("movie_id") int movie_id);
}
