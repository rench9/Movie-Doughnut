package com.yahoo.r4hu7.moviesdoughnut.ui.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.databinding.BindingAdapter;
import android.databinding.ObservableField;
import android.widget.ImageView;

import com.yahoo.r4hu7.moviesdoughnut.R;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.TmdbConst;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.model.Video;
import com.yahoo.r4hu7.moviesdoughnut.di.module.GlideApp;

public class VideoViewModel extends ViewModel {
    public ObservableField<Video> video = new ObservableField<>();

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
}
