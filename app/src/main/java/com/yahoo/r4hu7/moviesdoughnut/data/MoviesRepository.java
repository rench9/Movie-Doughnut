package com.yahoo.r4hu7.moviesdoughnut.data;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.yahoo.r4hu7.moviesdoughnut.data.local.LocalDataSource;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.ApiResponse;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.RemoteDataSource;
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
import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.model.Movie;
import com.yahoo.r4hu7.moviesdoughnut.util.AppExecutors;

import java.util.List;

public class MoviesRepository {
    private static MoviesRepository INSTANCE;
    private LocalDataSource localDataSource;
    private RemoteDataSource remoteDataSource;
    private AppExecutors appExecutors;

    private MoviesRepository(@NonNull LocalDataSource localDataSource, @NonNull RemoteDataSource remoteDataSource, @NonNull AppExecutors appExecutors) {
        this.localDataSource = localDataSource;
        this.remoteDataSource = remoteDataSource;
        this.appExecutors = appExecutors;
    }

    public static MoviesRepository getInstance(LocalDataSource localDataSource, RemoteDataSource remoteDataSource, AppExecutors appExecutors) {
        if (INSTANCE == null)
            INSTANCE = new MoviesRepository(localDataSource, remoteDataSource, appExecutors);
        return INSTANCE;
    }

    public LiveData<Resource<List<Movie>>> getMovies(int sortOrder, int page) {
        switch (sortOrder) {
            case SortOrder.POPULAR:
                return getPopularMovies(page);
            case SortOrder.NOWPLAYING:
                return getNowPlayingMovies(page);
            case SortOrder.UPCOMING:
                return getUpcomingMovies(page);
            case SortOrder.TOPRATED:
                return getTopRatedMovies(page);
            case SortOrder.FAVORITE:
                return getFavMovies();
            default:
                return new MutableLiveData<>();
        }
    }

    public LiveData<Resource<List<Movie>>> getMovies(int sortOrder) {
        switch (sortOrder) {
            case SortOrder.POPULAR:
                return getPopularMovies(1);
            case SortOrder.NOWPLAYING:
                return getNowPlayingMovies(1);
            case SortOrder.UPCOMING:
                return getUpcomingMovies(1);
            case SortOrder.TOPRATED:
                return getTopRatedMovies(1);
            case SortOrder.FAVORITE:
                return getFavMovies();
            default:
                return new MutableLiveData<>();
        }
    }

    public LiveData<Resource<List<Movie>>> getNowPlayingMovies(int page) {
        return new NetworkBoundResource<List<Movie>, NowPlayingMovieResponse>(appExecutors) {

            @Override
            protected void saveCallResult(NowPlayingMovieResponse item) {
                Log.e("NetworkBoundResource", "saveCallResult");
                localDataSource.saveMovies(item.getResults());
                localDataSource.saveSortOrder(SortOrder.NOWPLAYING, item.getResults());
            }

            @Override
            protected boolean shouldFetch(List<Movie> data) {
                Log.e("NetworkBoundResource", String.format("shouldFetch - %s", String.valueOf(data == null || data.isEmpty())));
//                return data == null || data.isEmpty();
                return true;
            }

            @Override
            protected LiveData<List<Movie>> loadFromDb() {
                Log.e("NetworkBoundResource", "loadFromDb");
                return localDataSource.getNowPlayingMovies();
            }

            @Override
            protected LiveData<ApiResponse<NowPlayingMovieResponse>> createCall() {
                Log.e("NetworkBoundResource", "createCall");
                return remoteDataSource.getNowPlayingMovies(page);
            }

            @Override
            protected void onFetchFailed() {
                Log.e("NetworkBoundResource", "onFetchFailed");
                super.onFetchFailed();
            }
        }.asLiveDate();
    }

    public LiveData<Resource<List<Movie>>> getPopularMovies(int page) {
        return new NetworkBoundResource<List<Movie>, PopularMovieResponse>(appExecutors) {
            @Override
            protected void saveCallResult(PopularMovieResponse item) {
                localDataSource.saveMovies(item.getResults());
                localDataSource.saveSortOrder(SortOrder.POPULAR, item.getResults());
            }

            @Override
            protected boolean shouldFetch(List<Movie> data) {
                return data == null || data.isEmpty();
            }

            @Override
            protected LiveData<List<Movie>> loadFromDb() {
                return localDataSource.getPopularMovies();
            }

            @Override
            protected LiveData<ApiResponse<PopularMovieResponse>> createCall() {
                return remoteDataSource.getPopularMovies(page);
            }
        }.asLiveDate();
    }

    public LiveData<Resource<List<Movie>>> getTopRatedMovies(int page) {
        return new NetworkBoundResource<List<Movie>, TopRatedMovieResponse>(appExecutors) {
            @Override
            protected void saveCallResult(TopRatedMovieResponse item) {
                localDataSource.saveMovies(item.getResults());
                localDataSource.saveSortOrder(SortOrder.TOPRATED, item.getResults());
            }

            @Override
            protected boolean shouldFetch(List<Movie> data) {
                return data == null || data.isEmpty();
            }

            @Override
            protected LiveData<List<Movie>> loadFromDb() {
                return localDataSource.getTopRatedMovies();
            }

            @Override
            protected LiveData<ApiResponse<TopRatedMovieResponse>> createCall() {
                return remoteDataSource.getTopRatedMovies(page);
            }
        }.asLiveDate();
    }

    public LiveData<Resource<List<Movie>>> getUpcomingMovies(int page) {
        return new NetworkBoundResource<List<Movie>, UpcomingMovieResponse>(appExecutors) {
            @Override
            protected void saveCallResult(UpcomingMovieResponse item) {

                localDataSource.saveMovies(item.getResults());
                localDataSource.saveSortOrder(SortOrder.UPCOMING, item.getResults());
            }

            @Override
            protected boolean shouldFetch(List<Movie> data) {
                return data == null || data.isEmpty();
            }

            @Override
            protected LiveData<List<Movie>> loadFromDb() {
                return localDataSource.getUpcomingMovies();
            }

            @Override
            protected LiveData<ApiResponse<UpcomingMovieResponse>> createCall() {
                return remoteDataSource.getUpcomingMovies(page);
            }
        }.asLiveDate();
    }

    public LiveData<Resource<List<Movie>>> getFavMovies() {
        return new NetworkBoundResource<List<Movie>, List<Movie>>(appExecutors) {
            @Override
            protected void saveCallResult(List<Movie> item) {
            }

            @Override
            protected boolean shouldFetch(List<Movie> data) {
                return false;
            }

            @Override
            protected LiveData<List<Movie>> loadFromDb() {
                return localDataSource.getFavMovies();
            }

            @Override
            protected LiveData<ApiResponse<List<Movie>>> createCall() {
                return null;
            }

        }.asLiveDate();
    }

    public LiveData<Resource<MovieResponse>> getMovie(int movieId) {
        return new NetworkBoundResource<MovieResponse, MovieResponse>(appExecutors) {
            @Override
            protected void saveCallResult(MovieResponse item) {
                localDataSource.saveMovie(item);
            }

            @Override
            protected boolean shouldFetch(MovieResponse data) {
                return data == null;
            }

            @Override
            protected LiveData<MovieResponse> loadFromDb() {
                return localDataSource.getMovie(movieId);
            }

            @Override
            protected LiveData<ApiResponse<MovieResponse>> createCall() {
                return remoteDataSource.getMovie(movieId);
            }
        }.asLiveDate();
    }

    public LiveData<Resource<MovieImagesResponse>> getImages(int movieId) {
        return new NetworkBoundResource<MovieImagesResponse, MovieImagesResponse>(appExecutors) {
            @Override
            protected void saveCallResult(MovieImagesResponse item) {
                localDataSource.saveImages(item);
            }

            @Override
            protected boolean shouldFetch(MovieImagesResponse data) {
                return data == null;
            }

            @Override
            protected LiveData<MovieImagesResponse> loadFromDb() {
                return localDataSource.getImages(movieId);
            }

            @Override
            protected LiveData<ApiResponse<MovieImagesResponse>> createCall() {
                return remoteDataSource.getImages(movieId);
            }
        }.asLiveDate();
    }

    public LiveData<Resource<MovieCreditsResponse>> getCast(int movieId) {
        return new NetworkBoundResource<MovieCreditsResponse, MovieCreditsResponse>(appExecutors) {
            @Override
            protected void saveCallResult(MovieCreditsResponse item) {
                localDataSource.saveCast(item);
            }

            @Override
            protected boolean shouldFetch(MovieCreditsResponse data) {
                return data == null;
            }

            @Override
            protected LiveData<MovieCreditsResponse> loadFromDb() {
                return localDataSource.getCast(movieId);
            }

            @Override
            protected LiveData<ApiResponse<MovieCreditsResponse>> createCall() {
                return remoteDataSource.getCast(movieId);
            }
        }.asLiveDate();
    }

    public LiveData<Resource<MovieVideosResponse>> getVideo(int movieId) {
        return new NetworkBoundResource<MovieVideosResponse, MovieVideosResponse>(appExecutors) {
            @Override
            protected void saveCallResult(MovieVideosResponse item) {
                localDataSource.saveVideo(item);
            }

            @Override
            protected boolean shouldFetch(MovieVideosResponse data) {
                return data == null;
            }

            @Override
            protected LiveData<MovieVideosResponse> loadFromDb() {
                return localDataSource.getVideo(movieId);
            }

            @Override
            protected LiveData<ApiResponse<MovieVideosResponse>> createCall() {
                return remoteDataSource.getVideo(movieId);
            }
        }.asLiveDate();
    }

    public LiveData<Resource<MovieReviewsResponse>> getReviews(int movieId, int page) {
        return new NetworkBoundResource<MovieReviewsResponse, MovieReviewsResponse>(appExecutors) {
            @Override
            protected void saveCallResult(MovieReviewsResponse item) {
            }

            @Override
            protected boolean shouldFetch(MovieReviewsResponse data) {
                return true;
            }

            @Override
            protected LiveData<MovieReviewsResponse> loadFromDb() {
                return new MutableLiveData<>();
            }

            @Override
            protected LiveData<ApiResponse<MovieReviewsResponse>> createCall() {
                return remoteDataSource.getReviews(movieId, page);
            }
        }.asLiveDate();
    }

    public LiveData<Resource<MovieExternalIdsResponse>> getLinks(int movieId) {
        return new NetworkBoundResource<MovieExternalIdsResponse, MovieExternalIdsResponse>(appExecutors) {
            @Override
            protected void saveCallResult(MovieExternalIdsResponse item) {
                localDataSource.saveLinks(item);
            }

            @Override
            protected boolean shouldFetch(MovieExternalIdsResponse data) {
                return data == null;
            }

            @Override
            protected LiveData<MovieExternalIdsResponse> loadFromDb() {
                return localDataSource.getLinks(movieId);
            }

            @Override
            protected LiveData<ApiResponse<MovieExternalIdsResponse>> createCall() {
                return remoteDataSource.getLinks(movieId);
            }
        }.asLiveDate();
    }

    public LiveData<Resource<Boolean>> isMovieFav(int movieId) {
        return new NetworkBoundResource<Boolean, Void>(appExecutors) {
            @Override
            protected void saveCallResult(Void item) {

            }

            @Override
            protected boolean shouldFetch(Boolean data) {
                return true;
            }

            @Override
            protected LiveData<Boolean> loadFromDb() {
                return localDataSource.isMovieFav(movieId);
            }

            @Override
            protected LiveData<ApiResponse<Void>> createCall() {
                return null;
            }
        }.asLiveDate();
    }

    public void markFavourite(int movieId) {
        localDataSource.markFavourite(movieId);
    }

    public void unMarkFavourite(int movieId) {
        localDataSource.unMarkFavourite(movieId);
    }

    public void saveMovies(Movie[] movies) {
        localDataSource.saveMovies(movies);
    }

    public void saveMovie(MovieResponse movie) {
        localDataSource.saveMovie(movie);
    }

    public void saveImages(MovieImagesResponse movieImagesResponse) {
        localDataSource.saveImages(movieImagesResponse);
    }

    public void saveCast(MovieCreditsResponse movieCreditsResponse) {
        localDataSource.saveCast(movieCreditsResponse);
    }

    public void saveVideo(MovieVideosResponse movieVideosResponse) {
        localDataSource.saveVideo(movieVideosResponse);
    }

    public void saveSortOrder(int sortOrder, Movie[] movies) {
        localDataSource.saveSortOrder(sortOrder, movies);
    }

    public void saveLinks(MovieExternalIdsResponse movieExternalIdsResponse) {
        localDataSource.saveLinks(movieExternalIdsResponse);
    }
}
