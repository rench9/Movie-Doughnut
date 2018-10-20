package com.yahoo.r4hu7.moviesdoughnut.ui.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;

import com.yahoo.r4hu7.moviesdoughnut.ui.activity.GalleryActivity;

public class GalleryActivityViewModel extends ViewModel {
    public ObservableBoolean gridView = new ObservableBoolean();
    public ObservableField<String> moviesFilter = new ObservableField<>(GalleryActivity.MOVIES_VM_POPULAR);
    public ObservableBoolean isSplashGone = new ObservableBoolean();
}
