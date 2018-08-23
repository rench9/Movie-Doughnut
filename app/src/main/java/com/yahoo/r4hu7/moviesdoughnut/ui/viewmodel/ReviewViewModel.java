package com.yahoo.r4hu7.moviesdoughnut.ui.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableField;

import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.model.Review;

public class ReviewViewModel extends ViewModel {
    public ObservableField<Review> review = new ObservableField<>();

    public ReviewViewModel() {
    }

    public void setReview(Review review) {
        this.review.set(review);
    }
}
