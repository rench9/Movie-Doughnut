package com.yahoo.r4hu7.moviesdoughnut.ui.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.databinding.BindingAdapter;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableList;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.yahoo.r4hu7.moviesdoughnut.R;
import com.yahoo.r4hu7.moviesdoughnut.data.MoviesDataSource;
import com.yahoo.r4hu7.moviesdoughnut.data.MoviesRepository;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.TmdbConst;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.MovieCreditsResponse;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.MovieExternalIdsResponse;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.MovieImagesResponse;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.MovieResponse;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.MovieVideosResponse;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.model.Cast;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.model.ImageSource;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.model.Item;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.model.Movie;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.model.Review;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.model.Video;
import com.yahoo.r4hu7.moviesdoughnut.di.module.GlideApp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MovieDetailViewModel extends ViewModel {
    private final ObservableBoolean dataLoading = new ObservableBoolean(false);
    public ObservableField<Movie> movie = new ObservableField<>();
    public ObservableField<MovieResponse> movieDetail = new ObservableField<>();
    public ObservableField<MovieExternalIdsResponse> externalIds = new ObservableField<>();
    public ObservableList<Video> videos = new ObservableArrayList<>();
    public ObservableList<Review> reviews = new ObservableArrayList<>();
    public ObservableList<Cast> casts = new ObservableArrayList<>();
    public ObservableList<ImageSource> imageSources = new ObservableArrayList<>();
    public ObservableList<Item> genres = new ObservableArrayList<>();
    private MoviesRepository moviesRepository;
    private int currentReviewPage;
    private int totalReviewPages;

    public MovieDetailViewModel(MoviesRepository moviesRepository) {
        this.moviesRepository = moviesRepository;
        this.currentReviewPage = 0;
    }

    @BindingAdapter({"posterSource"})
    public static void setPosterImage(ImageView view, String image) {
        GlideApp.with(view)
                .asBitmap()
                .fitCenter()
                .placeholder(R.drawable.placeholder_poster)
                .load(TmdbConst.generateImageUrl(image, TmdbConst.POSTER_W_185))
                .into(view);
    }

    @BindingAdapter({"backdropSource"})
    public static void setbackdropImage(ImageView view, String image) {
        GlideApp.with(view)
                .asBitmap()
                .centerCrop()
                .placeholder(R.drawable.placeholder_banner)
                .load(TmdbConst.generateImageUrl(image, TmdbConst.BACKDROP_W_300))
                .into(view);
    }

    @BindingAdapter({"imageSource"})
    public static void setGalleryImage(ImageView view, String image) {
        GlideApp.with(view)
                .asBitmap()
                .centerCrop()
                .placeholder(R.drawable.placeholder_square)
                .load(TmdbConst.generateImageUrl(image, TmdbConst.BACKDROP_W_300))
                .into(view);
    }

    public void loadMovieDetail() {
        moviesRepository.getMovie(movie.get().getId(), new MoviesDataSource.LoadItemCallback<MovieResponse>() {
            @Override
            public void onLoading() {

            }

            @Override
            public void onItemLoaded(MovieResponse movieResponse) {
                movieDetail.set(movieResponse);
            }

            @Override
            public void onDataNotAvailable(Throwable e) {

            }
        });
    }

    public void loadExternalIds() {
        moviesRepository.getLinks(movie.get().getId(), new MoviesDataSource.LoadItemCallback<MovieExternalIdsResponse>() {
            @Override
            public void onLoading() {

            }

            @Override
            public void onItemLoaded(MovieExternalIdsResponse movieExternalIdsResponse) {
                externalIds.set(movieExternalIdsResponse);
            }

            @Override
            public void onDataNotAvailable(Throwable e) {

            }
        });
    }

    public void loadVideos() {
        moviesRepository.getVideo(movie.get().getId(), new MoviesDataSource.LoadItemCallback<MovieVideosResponse>() {
            @Override
            public void onLoading() {

            }

            @Override
            public void onItemLoaded(MovieVideosResponse movieVideosResponse) {
                videos.addAll(Arrays.asList(movieVideosResponse.getResults()));
            }

            @Override
            public void onDataNotAvailable(Throwable e) {

            }
        });
    }

    public void loadCast() {
        moviesRepository.getCast(movie.get().getId(), new MoviesDataSource.LoadItemCallback<MovieCreditsResponse>() {
            @Override
            public void onLoading() {

            }

            @Override
            public void onItemLoaded(MovieCreditsResponse movieCreditsResponse) {
                casts.addAll(Arrays.asList(movieCreditsResponse.getCast()));
            }

            @Override
            public void onDataNotAvailable(Throwable e) {

            }
        });
    }

    public void loadImages() {
        moviesRepository.getImages(movie.get().getId(), new MoviesDataSource.LoadItemCallback<MovieImagesResponse>() {
            @Override
            public void onLoading() {

            }

            @Override
            public void onItemLoaded(MovieImagesResponse movieImagesResponse) {
                imageSources.addAll(Arrays.asList(movieImagesResponse.getPosters()));
                imageSources.addAll(Arrays.asList(movieImagesResponse.getBackdrops()));
            }

            @Override
            public void onDataNotAvailable(Throwable e) {

            }
        });
    }

    public void loadReviews() {
        moviesRepository.getReviews(movie.get().getId(), currentReviewPage + 1, new MoviesDataSource.LoadPagingItemCallback<Review[], Integer, Integer>() {
            @Override
            public void onLoading() {
                dataLoading.set(true);
            }

            @Override
            public void onItemLoaded(Review[] reviews, Integer page, Integer totalPages) {
                MovieDetailViewModel.this.reviews.addAll(Arrays.asList(reviews));
                MovieDetailViewModel.this.totalReviewPages = totalPages;
                currentReviewPage++;
                dataLoading.set(false);

            }

            @Override
            public void onDataNotAvailable(Throwable e) {
                dataLoading.set(false);
            }
        });
    }

    public String getGenreString() {
        List<String> l = new ArrayList<>();
        for (Item i : movieDetail.get().genres) {
            l.add(i.getName());
        }
        return TextUtils.join("  \u25CF  ", l);
    }

    public void linkClicked(View v, String url) {
        Log.e(String.valueOf(v.getId()), url);

    }

    public boolean isLastReviewPage() {
        return currentReviewPage >= totalReviewPages;
    }

    public boolean isDataLoading() {
        return dataLoading.get();
    }


}
