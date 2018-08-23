package com.yahoo.r4hu7.moviesdoughnut.data.remote;

import android.support.annotation.NonNull;

import com.yahoo.r4hu7.moviesdoughnut.data.MoviesDataSource;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.MovieCreditsResponse;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.MovieExternalIdsResponse;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.MovieImagesResponse;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.MovieResponse;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.MovieReviewsResponse;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.MovieVideosResponse;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.MoviesSource;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.model.Backdrop;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.model.ImageSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.MaybeObserver;
import io.reactivex.disposables.Disposable;

public class MovieResponseObserver<T> implements MaybeObserver<T> {

    private MoviesDataSource.LoadPagingItemCallback loadPagingItemCallback;
    private MoviesDataSource.LoadItemCallback loadItemCallback;

    public MovieResponseObserver(@NonNull MoviesDataSource.LoadPagingItemCallback loadPagingItemCallback) {
        this.loadPagingItemCallback = loadPagingItemCallback;
    }

    public MovieResponseObserver(@NonNull MoviesDataSource.LoadItemCallback loadItemCallback) {
        this.loadItemCallback = loadItemCallback;
    }

    @Override
    public void onSubscribe(Disposable d) {
        if (loadItemCallback != null)
            loadItemCallback.onLoading();
        else loadPagingItemCallback.onLoading();
    }

    @Override
    public void onSuccess(T t) {
        if (loadItemCallback != null) {
            if (t instanceof MovieImagesResponse)
                loadItemCallback.onItemLoaded(getImageSources((MovieImagesResponse) t));
            else if (t instanceof MovieResponse ||
                    t instanceof MovieCreditsResponse ||
                    t instanceof MovieVideosResponse ||
                    t instanceof MovieExternalIdsResponse)
                loadItemCallback.onItemLoaded(t);
        } else if (t instanceof MoviesSource)
            loadPagingItemCallback.onItemLoaded(((MoviesSource) t).getResults(), ((MoviesSource) t).getPage(), ((MoviesSource) t).getTotal_pages());
        else if (t instanceof MovieReviewsResponse)
            loadPagingItemCallback.onItemLoaded(((MovieReviewsResponse) t).getResults(), ((MovieReviewsResponse) t).getPage(), ((MovieReviewsResponse) t).getTotal_pages());

    }

    @Override
    public void onError(Throwable e) {
        if (loadItemCallback != null)
            loadItemCallback.onDataNotAvailable(e);
        else loadPagingItemCallback.onDataNotAvailable(e);
    }

    @Override
    public void onComplete() {

    }

    private MovieImagesResponse getImageSources(MovieImagesResponse movieImagesResponse) {

        List<ImageSource> l = new ArrayList<>();
        Backdrop i = movieImagesResponse.getBackdrops()[0];
        movieImagesResponse.getBackdrops()[0] = movieImagesResponse.getBackdrops()[movieImagesResponse.getBackdrops().length - 1];
        movieImagesResponse.getBackdrops()[movieImagesResponse.getBackdrops().length - 1] = i;
        l.addAll(Arrays.asList(movieImagesResponse.getBackdrops()));
        l.addAll(Arrays.asList(movieImagesResponse.getPosters()));
//        return l.toArray(new ImageSource[0]);
        return movieImagesResponse;
    }
}
