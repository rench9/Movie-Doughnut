package com.yahoo.r4hu7.moviesdoughnut.ui.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.databinding.BindingAdapter;
import android.databinding.ObservableField;
import android.widget.ImageView;

import com.yahoo.r4hu7.moviesdoughnut.data.remote.TmdbConst;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.model.Cast;
import com.yahoo.r4hu7.moviesdoughnut.di.module.GlideApp;

public class CastViewModel extends ViewModel {
    public ObservableField<Cast> cast = new ObservableField<>();

    public CastViewModel() {
    }

    @BindingAdapter({"castImageSource"})
    public static void setCastImage(ImageView view, String image) {
        GlideApp.with(view)
                .asBitmap()
                .centerCrop()
                .circleCrop()
                .load(TmdbConst.generateImageUrl(image, TmdbConst.PROFILE_W_45))
                .into(view);
    }

    public void setCast(Cast cast) {
        this.cast.set(cast);
    }
}
