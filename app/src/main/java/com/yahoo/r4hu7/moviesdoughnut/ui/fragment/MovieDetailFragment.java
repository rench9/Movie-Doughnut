package com.yahoo.r4hu7.moviesdoughnut.ui.fragment;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.yahoo.r4hu7.moviesdoughnut.R;
import com.yahoo.r4hu7.moviesdoughnut.databinding.FragmentMovieDetailBinding;
import com.yahoo.r4hu7.moviesdoughnut.ui.activity.DetailsActivity;
import com.yahoo.r4hu7.moviesdoughnut.ui.dependency.adapter.CastAdapter;
import com.yahoo.r4hu7.moviesdoughnut.ui.dependency.adapter.ReviewAdapter;
import com.yahoo.r4hu7.moviesdoughnut.ui.dependency.adapter.VideoAdapter;
import com.yahoo.r4hu7.moviesdoughnut.ui.viewmodel.MovieDetailViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieDetailFragment extends Fragment {

    @BindView(R.id.rvCastContainer)
    RecyclerView rvCastContainer;
    @BindView(R.id.rvVideoContainer)
    RecyclerView rvVideoContainer;
    @BindView(R.id.rvReviewContainer)
    RecyclerView rvReviewContainer;
    @BindView(R.id.tvShare)
    TextView tvShare;
    private MovieDetailViewModel viewModel;

    public static MovieDetailFragment newInstance() {
        return new MovieDetailFragment();
    }

    public void setViewModel(MovieDetailViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentMovieDetailBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie_detail, container, false);
        binding.setVm(viewModel);
        ButterKnife.bind(this, binding.getRoot());
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initRecyclerViews();
        tvShare.setOnClickListener(view -> shareVideo());
    }

    private void initRecyclerViews() {
        CastAdapter castAdapter = new CastAdapter(viewModel.casts);
        VideoAdapter videoAdapter = new VideoAdapter(viewModel.videos, (DetailsActivity) getActivity());
        ReviewAdapter reviewAdapter = new ReviewAdapter(viewModel.reviews);

        rvCastContainer.setAdapter(castAdapter);
        rvVideoContainer.setAdapter(videoAdapter);
        rvReviewContainer.setAdapter(reviewAdapter);

        rvCastContainer.addItemDecoration(getItemDecoration());
        rvVideoContainer.addItemDecoration(getItemDecoration());

        LinearSnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(rvReviewContainer);
        rvReviewContainer.setOnFlingListener(snapHelper);

    }

    private RecyclerView.ItemDecoration getItemDecoration() {
        return new RecyclerView.ItemDecoration() {
            private int spacing = 32;

            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                int position = parent.getChildAdapterPosition(view); // item position
                if (position == 0) {
                    return;
                }
                outRect.left = spacing;
            }
        };
    }

    public void shareVideo() {
        try {
            Intent share = new Intent(android.content.Intent.ACTION_SEND);
            share.setType("text/plain");
            share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
            share.putExtra(Intent.EXTRA_SUBJECT, String.format("Trailer of %s", viewModel.movie.get().title));
            share.putExtra(Intent.EXTRA_TEXT, String.format("https://youtu.be/%s", viewModel.videos.get(0).getKey()));

            startActivity(Intent.createChooser(share, String.format("Share %s", viewModel.movie.get().title)));
        } catch (Exception e) {
            Toast.makeText(getContext(), R.string.try_later, Toast.LENGTH_SHORT).show();
        }

    }

}
