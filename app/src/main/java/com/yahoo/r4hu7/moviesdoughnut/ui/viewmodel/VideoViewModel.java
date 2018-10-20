package com.yahoo.r4hu7.moviesdoughnut.ui.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.databinding.BindingAdapter;
import android.databinding.ObservableField;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.yahoo.r4hu7.moviesdoughnut.R;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.TmdbConst;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.model.Video;
import com.yahoo.r4hu7.moviesdoughnut.di.module.GlideApp;
import com.yahoo.r4hu7.moviesdoughnut.util.MovieNavigator;

import java.lang.ref.WeakReference;

public class VideoViewModel extends ViewModel {
    public ObservableField<Video> video = new ObservableField<>();
    private WeakReference<MovieNavigator> mNavigator;

    public VideoViewModel() {
    }

    @BindingAdapter({"videoImageSource"})
    public static void setVideoImage(ImageView view, String image) {
        GlideApp.with(view)
                .asBitmap()
                .centerCrop()
                .placeholder(R.drawable.placeholder_banner)
                .load(TmdbConst.generateYoutubeTubmnail(image))
                .into(view);
    }

    public void setVideo(Video video) {
        this.video.set(video);
    }

    public void setNavigator(@Nullable MovieNavigator mNavigator) {
        this.mNavigator = new WeakReference<>(mNavigator);
    }

    public void openVideo(String url) {
        mNavigator.get().openLink(String.format("https://youtu.be/%s", url));
    }
}
