package com.yahoo.r4hu7.moviesdoughnut.ui.fragment;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yahoo.r4hu7.moviesdoughnut.R;
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

    public static GridMoviesFragment newInstance() {
        return new GridMoviesFragment();
    }

    public GridMoviesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_grid_movies, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initRecyclerViews();
    }

    private void initRecyclerViews() {
        PosterCardAdapter adapter = new PosterCardAdapter(moviesViewModel.movies, (MovieNavigator) getActivity(), true);
        rvContainerMovies.setAdapter(adapter);

        rvContainerMovies.addItemDecoration(getItemDecoration());

        rvContainerMovies.addOnScrollListener(new RecyclerViewMoviesPagination() {
            @Override
            protected void loadMoreItems() {
                moviesViewModel.loadMovies();
            }

            @Override
            protected boolean isLastItem() {
                return moviesViewModel.isLastPage();
            }

            @Override
            protected boolean isLoading() {
                return moviesViewModel.isDataLoading();
            }
        });
    }

    public void setMoviesViewModel(MoviesViewModel moviesViewModel) {
        this.moviesViewModel = moviesViewModel;
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

}
