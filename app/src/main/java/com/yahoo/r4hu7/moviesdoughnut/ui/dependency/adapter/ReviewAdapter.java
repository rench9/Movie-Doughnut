package com.yahoo.r4hu7.moviesdoughnut.ui.dependency.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.yahoo.r4hu7.moviesdoughnut.R;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.model.Review;
import com.yahoo.r4hu7.moviesdoughnut.databinding.AdapterReviewBinding;
import com.yahoo.r4hu7.moviesdoughnut.ui.viewmodel.ReviewViewModel;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {

    private ObservableList<Review> reviews;

    public ReviewAdapter(ObservableList<Review> reviews) {
        this.reviews = reviews;
        reviews.addOnListChangedCallback(new ObservableList.OnListChangedCallback<ObservableList<Review>>() {
            @Override
            public void onChanged(ObservableList<Review> sender) {
                notifyDataSetChanged();
            }

            @Override
            public void onItemRangeChanged(ObservableList<Review> sender, int positionStart, int itemCount) {
                notifyItemRangeChanged(positionStart, itemCount);
            }

            @Override
            public void onItemRangeInserted(ObservableList<Review> sender, int positionStart, int itemCount) {
                notifyItemRangeInserted(positionStart, itemCount);
            }

            @Override
            public void onItemRangeMoved(ObservableList<Review> sender, int fromPosition, int toPosition, int itemCount) {
                notifyItemRangeChanged(fromPosition, itemCount);
            }

            @Override
            public void onItemRangeRemoved(ObservableList<Review> sender, int positionStart, int itemCount) {
                notifyItemRangeRemoved(positionStart, itemCount);
            }
        });
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        AdapterReviewBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.adapter_review, parent, false);
        return new ViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.viewModel.setReview(reviews.get(position));
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ReviewViewModel viewModel;

        ViewHolder(AdapterReviewBinding binding) {
            super(binding.getRoot());
            viewModel = new ReviewViewModel();
            binding.setVm(viewModel);
        }

    }
}
