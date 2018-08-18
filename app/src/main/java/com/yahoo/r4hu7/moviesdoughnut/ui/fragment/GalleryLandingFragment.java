package com.yahoo.r4hu7.moviesdoughnut.ui.fragment;


import android.databinding.DataBindingUtil;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yahoo.r4hu7.moviesdoughnut.R;
import com.yahoo.r4hu7.moviesdoughnut.data.SortOrder;
import com.yahoo.r4hu7.moviesdoughnut.databinding.FragmentGalleryLandingBinding;
import com.yahoo.r4hu7.moviesdoughnut.ui.dependency.RecyclerViewMoviesPagination;
import com.yahoo.r4hu7.moviesdoughnut.ui.dependency.adapter.PosterCardAdapter;
import com.yahoo.r4hu7.moviesdoughnut.ui.viewmodel.MoviesViewModel;
import com.yahoo.r4hu7.moviesdoughnut.util.MovieNavigator;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GalleryLandingFragment extends Fragment {

    private MoviesViewModel nowPlayingViewModel;
    private MoviesViewModel topRatedViewModel;
    private MoviesViewModel upComingViewModel;
    private MoviesViewModel popularViewModel;

    @BindView(R.id.rvContainerNowPlaying)
    RecyclerView rvContainerNowPlaying;
    @BindView(R.id.rvContainerUpcoming)
    RecyclerView rvContainerUpcoming;
    @BindView(R.id.rvContainerTopRated)
    RecyclerView rvContainerTopRated;
    @BindView(R.id.rvContainerPopular)
    RecyclerView rvContainerPopular;

    public static GalleryLandingFragment newInstance() {
        return new GalleryLandingFragment();
    }

    public GalleryLandingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FragmentGalleryLandingBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_gallery_landing, container, false);

        ButterKnife.bind(this, binding.getRoot());
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initRecyclerViews();
    }

    public void setNowPlayingViewModel(MoviesViewModel nowPlayingViewModel) {
        this.nowPlayingViewModel = nowPlayingViewModel;
        this.nowPlayingViewModel.setSortOrder(SortOrder.NOWPLAYING);
        this.nowPlayingViewModel.loadMovies();
    }

    public void setTopRatedViewModel(MoviesViewModel topRatedViewModel) {
        this.topRatedViewModel = topRatedViewModel;
        this.topRatedViewModel.setSortOrder(SortOrder.TOPRATED);
        this.topRatedViewModel.loadMovies();
    }

    public void setUpComingViewModel(MoviesViewModel upComingViewModel) {
        this.upComingViewModel = upComingViewModel;
        this.upComingViewModel.setSortOrder(SortOrder.UPCOMING);
        this.upComingViewModel.loadMovies();
    }

    public void setPopularViewModel(MoviesViewModel popularViewModel) {
        this.popularViewModel = popularViewModel;
        this.popularViewModel.setSortOrder(SortOrder.POPULAR);
        this.popularViewModel.loadMovies();
    }

    private void initRecyclerViews() {

        PosterCardAdapter popularMoviesAdapter = new PosterCardAdapter(popularViewModel.movies, (MovieNavigator) getActivity(), false);
        PosterCardAdapter nowPlayingMoviesAdapter = new PosterCardAdapter(nowPlayingViewModel.movies, (MovieNavigator) getActivity(), false);
        PosterCardAdapter upComingMoviesAdapter = new PosterCardAdapter(upComingViewModel.movies, (MovieNavigator) getActivity(), false);
        PosterCardAdapter topRatedMoviesAdapter = new PosterCardAdapter(topRatedViewModel.movies, (MovieNavigator) getActivity(), false);

        rvContainerPopular.setAdapter(popularMoviesAdapter);
        rvContainerNowPlaying.setAdapter(nowPlayingMoviesAdapter);
        rvContainerTopRated.setAdapter(topRatedMoviesAdapter);
        rvContainerUpcoming.setAdapter(upComingMoviesAdapter);

        rvContainerPopular.addItemDecoration(getItemDecoration());
        rvContainerNowPlaying.addItemDecoration(getItemDecoration());
        rvContainerTopRated.addItemDecoration(getItemDecoration());
        rvContainerUpcoming.addItemDecoration(getItemDecoration());

        rvContainerPopular.addOnScrollListener(new RecyclerViewMoviesPagination() {
            @Override
            protected void loadMoreItems() {
                popularViewModel.loadMovies();
            }

            @Override
            protected boolean isLastItem() {
                return popularViewModel.isLastPage();
            }

            @Override
            protected boolean isLoading() {
                return popularViewModel.isDataLoading();
            }
        });
        rvContainerNowPlaying.addOnScrollListener(new RecyclerViewMoviesPagination() {
            @Override
            protected void loadMoreItems() {
                nowPlayingViewModel.loadMovies();
            }

            @Override
            protected boolean isLastItem() {
                return nowPlayingViewModel.isLastPage();
            }

            @Override
            protected boolean isLoading() {
                return nowPlayingViewModel.isDataLoading();
            }
        });
        rvContainerTopRated.addOnScrollListener(new RecyclerViewMoviesPagination() {
            @Override
            protected void loadMoreItems() {
                topRatedViewModel.loadMovies();
            }

            @Override
            protected boolean isLastItem() {
                return topRatedViewModel.isLastPage();
            }

            @Override
            protected boolean isLoading() {
                return topRatedViewModel.isDataLoading();
            }
        });
        rvContainerUpcoming.addOnScrollListener(new RecyclerViewMoviesPagination() {
            @Override
            protected void loadMoreItems() {
                upComingViewModel.loadMovies();
            }

            @Override
            protected boolean isLastItem() {
                return upComingViewModel.isLastPage();
            }

            @Override
            protected boolean isLoading() {
                return upComingViewModel.isDataLoading();
            }
        });

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
}
