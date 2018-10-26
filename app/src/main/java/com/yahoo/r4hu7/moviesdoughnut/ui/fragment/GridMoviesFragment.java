package com.yahoo.r4hu7.moviesdoughnut.ui.fragment;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
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
import com.yahoo.r4hu7.moviesdoughnut.di.DaggerRepositoryComponent;
import com.yahoo.r4hu7.moviesdoughnut.di.module.ContextModule;
import com.yahoo.r4hu7.moviesdoughnut.ui.dependency.RecyclerViewMoviesPagination;
import com.yahoo.r4hu7.moviesdoughnut.ui.dependency.adapter.PosterCardAdapter;
import com.yahoo.r4hu7.moviesdoughnut.ui.viewmodel.MoviesViewModel;
import com.yahoo.r4hu7.moviesdoughnut.util.ActivityUtils;
import com.yahoo.r4hu7.moviesdoughnut.util.MovieNavigator;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GridMoviesFragment extends Fragment {

    @BindView(R.id.rvContainerMovies)
    RecyclerView rvContainerMovies;
    private MoviesViewModel moviesViewModel;
    public static final String MOVIES_VM = "MOVIES_VM";
    PosterCardAdapter adapter;
    private MoviesRepository repository;
    private ViewModelProvider.NewInstanceFactory newInstanceFactory;

    public static GridMoviesFragment newInstance() {
        return new GridMoviesFragment();
    }

    public GridMoviesFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_grid_movies, container, false);
        ButterKnife.bind(this, v);
        return v;
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
        if (moviesViewModel == null) {
            moviesViewModel = ViewModelProviders.of(this, newInstanceFactory).get(MOVIES_VM, MoviesViewModel.class);
        }

        this.moviesViewModel.movies.observe(this, listResource -> {
            if (listResource != null && listResource.getData() != null) {
                adapter.setMovies(listResource.getData());
                moviesViewModel.setNextPage(moviesViewModel.getNextPage());
            }
        });
    }

    private void initRecyclerViews() {
        adapter = new PosterCardAdapter((MovieNavigator) getActivity(), true);
        rvContainerMovies.setAdapter(adapter);

        rvContainerMovies.addItemDecoration(getItemDecoration());

        rvContainerMovies.addOnScrollListener(new RecyclerViewMoviesPagination() {
            @Override
            protected void loadMoreItems() {
                moviesViewModel.loadMoreMovies();
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
            private int spanCount = ActivityUtils.isLandscape(getResources()) ? 6 : 3;
            private int spacing = 28;

            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                int position = parent.getChildAdapterPosition(view);
                int column = position % spanCount;
                outRect.left = spacing - column * spacing / spanCount;

                outRect.right = (column + 1) * spacing / spanCount;

                if (position < spanCount) {
                    outRect.top = spacing;
                }
                outRect.bottom = spacing * 2;
            }
        };
    }

    public void setSortOrder(int sortOrder) {
        moviesViewModel.setSortOrder(sortOrder);
    }

}
