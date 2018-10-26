package com.yahoo.r4hu7.moviesdoughnut.ui.fragment;


import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yahoo.r4hu7.moviesdoughnut.R;
import com.yahoo.r4hu7.moviesdoughnut.data.MoviesRepository;
import com.yahoo.r4hu7.moviesdoughnut.data.SortOrder;
import com.yahoo.r4hu7.moviesdoughnut.databinding.FragmentGalleryLandingBinding;
import com.yahoo.r4hu7.moviesdoughnut.di.DaggerRepositoryComponent;
import com.yahoo.r4hu7.moviesdoughnut.di.module.ContextModule;
import com.yahoo.r4hu7.moviesdoughnut.ui.activity.GalleryActivity;
import com.yahoo.r4hu7.moviesdoughnut.ui.dependency.RecyclerViewMoviesPagination;
import com.yahoo.r4hu7.moviesdoughnut.ui.dependency.adapter.PosterCardAdapter;
import com.yahoo.r4hu7.moviesdoughnut.ui.viewmodel.MoviesViewModel;
import com.yahoo.r4hu7.moviesdoughnut.util.MovieNavigator;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GalleryLandingFragment extends Fragment {
    public static final String MOVIES_VM_POPULAR = "MOVIES_VM_POPULAR";
    public static final String MOVIES_VM_UPCOMING = "MOVIES_VM_UPCOMING";
    public static final String MOVIES_VM_TOPRATED = "MOVIES_VM_TOPRATED";
    public static final String MOVIES_VM_NOW_PLAYING = "MOVIES_VM_NOW_PLAYING";
    PosterCardAdapter nowPlayingMoviesAdapter;
    PosterCardAdapter popularMoviesAdapter;
    PosterCardAdapter upComingMoviesAdapter;
    PosterCardAdapter topRatedMoviesAdapter;



    @BindView(R.id.rvContainerNowPlaying)
    RecyclerView rvContainerNowPlaying;
    @BindView(R.id.rvContainerUpcoming)
    RecyclerView rvContainerUpcoming;
    @BindView(R.id.rvContainerTopRated)
    RecyclerView rvContainerTopRated;
    @BindView(R.id.rvContainerPopular)
    RecyclerView rvContainerPopular;
    private MoviesViewModel nowPlayingMoviesViewModel;
    private MoviesViewModel topRatedMoviesViewModel;
    private MoviesViewModel upcomingMoviesViewModel;
    private MoviesViewModel popularMoviesViewModel;
    private MoviesRepository repository;
    private ViewModelProvider.NewInstanceFactory newInstanceFactory;

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
        binding.setTopRatedVm(topRatedMoviesViewModel);
        binding.setNowPlayingVm(nowPlayingMoviesViewModel);
        binding.setPopularVm(popularMoviesViewModel);
        binding.setUpComingVm(upcomingMoviesViewModel);

        ButterKnife.bind(this, binding.getRoot());
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViewModels();
        initRecyclerViews();
    }

    private void initViewModels() {

        if (repository == null)
            repository = DaggerRepositoryComponent.builder().contextModule(new ContextModule(getContext())).build().getMoviesRepository();

        if (newInstanceFactory == null)
            newInstanceFactory = new ViewModelProvider.NewInstanceFactory() {
                @NonNull
                @Override
                public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                    return (T) new MoviesViewModel(repository);
                }
            };

        if (nowPlayingMoviesViewModel == null || upcomingMoviesViewModel == null || topRatedMoviesViewModel == null || popularMoviesViewModel == null) {
            nowPlayingMoviesViewModel = ViewModelProviders.of(this, newInstanceFactory).get(MOVIES_VM_NOW_PLAYING, MoviesViewModel.class);
            upcomingMoviesViewModel = ViewModelProviders.of(this, newInstanceFactory).get(MOVIES_VM_UPCOMING, MoviesViewModel.class);
            topRatedMoviesViewModel = ViewModelProviders.of(this, newInstanceFactory).get(MOVIES_VM_TOPRATED, MoviesViewModel.class);
            popularMoviesViewModel = ViewModelProviders.of(this, newInstanceFactory).get(MOVIES_VM_POPULAR, MoviesViewModel.class);
            this.nowPlayingMoviesViewModel.setSortOrder(SortOrder.NOWPLAYING);
            this.upcomingMoviesViewModel.setSortOrder(SortOrder.UPCOMING);
            this.topRatedMoviesViewModel.setSortOrder(SortOrder.TOPRATED);
            this.popularMoviesViewModel.setSortOrder(SortOrder.POPULAR);
        }

        this.nowPlayingMoviesViewModel.movies.observe(this, listResource -> {
            if (listResource == null || listResource.getData() == null)
                return;
            nowPlayingMoviesAdapter.setMovies(listResource.getData());
            nowPlayingMoviesViewModel.setNextPage(nowPlayingMoviesViewModel.getNextPage());
        });
        this.popularMoviesViewModel.movies.observe(this, listResource -> {
            if (listResource == null || listResource.getData() == null)
                return;
            popularMoviesAdapter.setMovies(listResource.getData());
            popularMoviesViewModel.setNextPage(popularMoviesViewModel.getNextPage());
        });
        this.upcomingMoviesViewModel.movies.observe(this, listResource -> {
            if (listResource == null || listResource.getData() == null)
                return;
            upComingMoviesAdapter.setMovies(listResource.getData());
            upcomingMoviesViewModel.setNextPage(upcomingMoviesViewModel.getNextPage());
        });
        this.topRatedMoviesViewModel.movies.observe(this, listResource -> {
            if (listResource == null || listResource.getData() == null)
                return;
            topRatedMoviesAdapter.setMovies(listResource.getData());
            topRatedMoviesViewModel.setNextPage(topRatedMoviesViewModel.getNextPage());
        });
    }

    private void initRecyclerViews() {
        popularMoviesAdapter = new PosterCardAdapter((MovieNavigator) getActivity(), false);
        nowPlayingMoviesAdapter = new PosterCardAdapter((MovieNavigator) getActivity(), false);
        upComingMoviesAdapter = new PosterCardAdapter((MovieNavigator) getActivity(), false);

        topRatedMoviesAdapter = new PosterCardAdapter((MovieNavigator) getActivity(), false);
        ((GalleryActivity) Objects.requireNonNull(getActivity())).hideSplash();

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
                popularMoviesViewModel.loadMoreMovies();
            }

            @Override
            protected boolean isLastItem() {
                return false;
            }

            @Override
            protected boolean isLoading() {
                return false;
            }
        });
        rvContainerNowPlaying.addOnScrollListener(new RecyclerViewMoviesPagination() {

            @Override
            protected void loadMoreItems() {
                nowPlayingMoviesViewModel.loadMoreMovies();
            }

            @Override
            protected boolean isLastItem() {
                return false;
            }

            @Override
            protected boolean isLoading() {
                return false;
            }
        });
        rvContainerTopRated.addOnScrollListener(new RecyclerViewMoviesPagination() {
            @Override
            protected void loadMoreItems() {
                topRatedMoviesViewModel.loadMoreMovies();
            }

            @Override
            protected boolean isLastItem() {
                return false;
            }

            @Override
            protected boolean isLoading() {
                return false;
            }
        });
        rvContainerUpcoming.addOnScrollListener(new RecyclerViewMoviesPagination() {
            @Override
            protected void loadMoreItems() {
                upcomingMoviesViewModel.loadMoreMovies();
            }

            @Override
            protected boolean isLastItem() {
                return false;
            }

            @Override
            protected boolean isLoading() {
                return false;
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
