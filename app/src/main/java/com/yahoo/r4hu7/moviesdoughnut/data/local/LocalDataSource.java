package com.yahoo.r4hu7.moviesdoughnut.data.local;

import android.support.annotation.NonNull;
import android.util.Log;

import com.yahoo.r4hu7.moviesdoughnut.data.MoviesDataSource;
import com.yahoo.r4hu7.moviesdoughnut.data.SortOrder;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.MovieCreditsResponse;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.MovieExternalIdsResponse;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.MovieImagesResponse;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.MovieResponse;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.MovieVideosResponse;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.model.Movie;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.model.Review;
import com.yahoo.r4hu7.moviesdoughnut.util.AppExecutors;

import static com.google.common.base.Preconditions.checkNotNull;

public class LocalDataSource implements MoviesDataSource {
    private static volatile LocalDataSource INSTANCE;
    private MoviesDatabase database;
    private AppExecutors mAppExecutors;

    private LocalDataSource(@NonNull AppExecutors appExecutors, @NonNull MoviesDatabase database) {
        this.database = database;
        this.mAppExecutors = appExecutors;
    }

    public static LocalDataSource getInstance(@NonNull AppExecutors appExecutors, MoviesDatabase moviesDatabase) {
        if (INSTANCE == null)
            INSTANCE = new LocalDataSource(appExecutors, moviesDatabase);
        return INSTANCE;
    }

    private void execute(Runnable runnable) {
        mAppExecutors.diskIO().execute(runnable);
    }

    @Override
    public void getMovies(int sortOrder, int page, LoadPagingItemCallback<Movie[], Integer, Integer> callback) {
        Runnable runnable = () -> {
            Movie[] response;
            if (sortOrder == SortOrder.FAVORITE)
                response = database.movieDao().getFavMovies().toArray(new Movie[0]);
            else
                response = database.movieDao().findMovies(sortOrder).toArray(new Movie[0]);
            mAppExecutors.mainThread().execute(() -> {
                if (response.length == 0)
                    callback.onDataNotAvailable(new Exception());
                else
                    callback.onItemLoaded(response, 1, 1);
            });
        };
        execute(runnable);
    }


    @Override
    public void getMovie(int movieId, LoadItemCallback<MovieResponse> callback) {
        Runnable runnable = () -> {
            MovieResponse response = database.movieDetailDao().findMovieDetailById(movieId);
            mAppExecutors.mainThread().execute(() -> {
                if (response == null)
                    callback.onDataNotAvailable(new Exception());
                else
                    callback.onItemLoaded(response);
            });
        };
        execute(runnable);
    }

    @Override
    public void getImages(int movieId, LoadItemCallback<MovieImagesResponse> callback) {
        Runnable runnable = () -> {
            MovieImagesResponse response = database.imageDao().getResponse(movieId);
            mAppExecutors.mainThread().execute(() -> {
                if (response == null)
                    callback.onDataNotAvailable(new Exception());
                else
                    callback.onItemLoaded(response);
            });
        };
        execute(runnable);
    }

    @Override
    public void getCast(int movieId, LoadItemCallback<MovieCreditsResponse> callback) {
        Runnable runnable = () -> {
            MovieCreditsResponse response = database.castDao().getResponse(movieId);
            mAppExecutors.mainThread().execute(() -> {
                if (response == null)
                    callback.onDataNotAvailable(new Exception());
                else
                    callback.onItemLoaded(response);
            });
        };
        execute(runnable);
    }

    @Override
    public void getVideo(int movieId, LoadItemCallback<MovieVideosResponse> callback) {
        Runnable runnable = () -> {
            MovieVideosResponse response = database.videoDao().getResponse(movieId);
            mAppExecutors.mainThread().execute(() -> {
                if (response == null)
                    callback.onDataNotAvailable(new Exception());
                else
                    callback.onItemLoaded(response);
            });
        };
        execute(runnable);
    }

    @Override
    public void getReviews(int movieId, int page, LoadPagingItemCallback<Review[], Integer, Integer> callback) {
        return;
    }

    @Override
    public void getLinks(int movieId, LoadItemCallback<MovieExternalIdsResponse> callback) {
        Runnable runnable = () -> {
            MovieExternalIdsResponse response = database.externalLinkDao().getResponse(movieId);
            mAppExecutors.mainThread().execute(() -> {
                if (response == null)
                    callback.onDataNotAvailable(new Exception());
                else
                    callback.onItemLoaded(response);
            });
        };
        execute(runnable);
    }

    @Override
    public void isMovieFav(int movieId, LoadItemCallback<Boolean> callback) {
        Runnable runnable = () -> {
            boolean response = database.movieDao().isMovieFav(movieId);
            mAppExecutors.mainThread().execute(() -> callback.onItemLoaded(response));
        };
        execute(runnable);
    }

    @Override
    public void markFavourite(int movieId) {
        Runnable runnable = () -> database.movieDao().markFavourite(movieId);
        execute(runnable);
    }

    @Override
    public void unMarkFavourite(int movieId) {
        Runnable runnable = () -> database.movieDao().unMarkFavourite(movieId);
        execute(runnable);
    }

    @Override
    public void saveMovies(Movie[] movies) {
        checkNotNull(movies);
        Runnable runnable = () -> database.movieDao().insert(movies);
        execute(runnable);
    }

    @Override
    public void saveMovie(MovieResponse movie) {
        checkNotNull(movie);
        Runnable runnable = () -> database.movieDetailDao().insert(movie);
        execute(runnable);
    }

    @Override
    public void saveImages(MovieImagesResponse movieImagesResponse) {
        Log.e("asdasdasdasfd00", String.valueOf(movieImagesResponse.getPosters().length));
        checkNotNull(movieImagesResponse);
        Runnable runnable = () -> database.imageDao().insert(movieImagesResponse);
        execute(runnable);
    }

    @Override
    public void saveCast(MovieCreditsResponse movieCreditsResponse) {
        checkNotNull(movieCreditsResponse);
        Runnable runnable = () -> database.castDao().insert(movieCreditsResponse);
        execute(runnable);
    }

    @Override
    public void saveVideo(MovieVideosResponse movieVideosResponse) {
        checkNotNull(movieVideosResponse);
        Runnable runnable = () -> database.videoDao().insert(movieVideosResponse);
        execute(runnable);
    }

    @Override
    public void saveSortOrder(int sortId, Movie[] movies) {
        checkNotNull(movies);
        SortOrder[] sortOrders = new SortOrder[movies.length];
        for (int i = 0; i < movies.length; i++) {
            sortOrders[i] = new SortOrder(sortId, movies[i].getId());
        }

        Runnable runnable = () -> database.sortOrderDao().insert(sortOrders);
        execute(runnable);
    }

    @Override
    public void saveLinks(MovieExternalIdsResponse movieExternalIdsResponse) {
        checkNotNull(movieExternalIdsResponse);
        Runnable runnable = () -> database.externalLinkDao().insert(movieExternalIdsResponse);
        execute(runnable);
    }
}
