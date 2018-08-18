package com.yahoo.r4hu7.moviesdoughnut.ui.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.databinding.BindingAdapter;
import android.databinding.ObservableField;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;

import com.yahoo.r4hu7.moviesdoughnut.R;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.TmdbConst;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.model.Movie;
import com.yahoo.r4hu7.moviesdoughnut.di.module.GlideApp;
import com.yahoo.r4hu7.moviesdoughnut.util.MovieNavigator;

import java.lang.ref.WeakReference;

public class PosterViewModel extends ViewModel {
    public ObservableField<Movie> movie = new ObservableField<>();
    @Nullable
    private WeakReference<MovieNavigator> mNavigator;

    public PosterViewModel() {
    }

    public void setMovie(Movie movie) {
        this.movie.set(movie);
    }

    public void setNavigator(@Nullable MovieNavigator mNavigator) {
        this.mNavigator = new WeakReference<>(mNavigator);
    }

    public void posterClicked() {
        if (movie.get() != null && mNavigator != null && mNavigator.get() != null)
            mNavigator.get().openMovieDetails(movie.get().getId());
    }

    @BindingAdapter({"ratingColor"})
    public static void setRatingColor(View view, double rating) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            view.setBackgroundTintList(ContextCompat.getColorStateList(view.getContext(),
                    rating < 5.5 ?
                            R.color.rating_low :
                            rating >= 8 ?
                                    R.color.rating_high :
                                    R.color.rating_normal
            ));
        }
    }

    @BindingAdapter({"imageSource"})
    public static void setPoster(ImageView view, String image) {
        GlideApp.with(view)
                .asBitmap()
                .centerCrop()
                .load(TmdbConst.generateImageUrl(image, TmdbConst.POSTER_W_185))
                .into(view);
    }
}
