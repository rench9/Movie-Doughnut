package com.yahoo.r4hu7.moviesdoughnut.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.yahoo.r4hu7.moviesdoughnut.R;
import com.yahoo.r4hu7.moviesdoughnut.di.DaggerRepositoryComponent;
import com.yahoo.r4hu7.moviesdoughnut.di.module.ContextModule;
import com.yahoo.r4hu7.moviesdoughnut.ui.fragment.MovieDetailFragment;
import com.yahoo.r4hu7.moviesdoughnut.ui.viewmodel.MovieDetailViewModel;
import com.yahoo.r4hu7.moviesdoughnut.ui.viewmodel.ViewModelHolder;
import com.yahoo.r4hu7.moviesdoughnut.util.ActivityUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsActivity extends AppCompatActivity {
    public final static String MOVIE_KEY = "M_KEY";
    public final static String DETAIL_VM = "DETAIL_VM";
    public final static String DETAIL_FRAGMENT = "DETAIL_FRAGMENT";

    @BindView(R.id.tbPrimary)
    Toolbar tbPrimary;

    @BindView(R.id.abPrimary)
    AppBarLayout abPrimary;

    MovieDetailViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);
        setSupportActionBar(tbPrimary);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        viewModel = findOrCreateMovieDetailViewModel();
        viewModel.movie.set(getIntent().getExtras().getParcelable(MOVIE_KEY));
        viewModel.loadMovieDetail();
        viewModel.loadCast();
        viewModel.loadImages();
        viewModel.loadVideos();
        viewModel.loadReviews();
        viewModel.loadExternalIds();
        MovieDetailFragment fragment = findOrCreateDetailFragment();
        fragment.setViewModel(viewModel);
    }

    @NonNull
    private MovieDetailViewModel findOrCreateMovieDetailViewModel() {
        // In a configuration change we might have a ViewModel present. It's retained using the
        // Fragment Manager.
        @SuppressWarnings("unchecked")
        ViewModelHolder<MovieDetailViewModel> retainedViewModel =
                (ViewModelHolder<MovieDetailViewModel>) getSupportFragmentManager()
                        .findFragmentByTag(DETAIL_VM);

        if (retainedViewModel != null && retainedViewModel.getViewModel() != null) {
            // If the model was retained, return it.
            return retainedViewModel.getViewModel();
        } else {
            // There is no ViewModel yet, create it.
            MovieDetailViewModel viewModel = new MovieDetailViewModel(DaggerRepositoryComponent.builder().contextModule(new ContextModule(getApplicationContext())).build().getMoviesRepository());
            // and bind it to this Activity's lifecycle using the Fragment Manager.
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(),
                    ViewModelHolder.createContainer(viewModel),
                    DETAIL_VM);
            return viewModel;
        }
    }


    @NonNull
    private MovieDetailFragment findOrCreateDetailFragment() {
        MovieDetailFragment fragment =
                (MovieDetailFragment) getSupportFragmentManager().findFragmentByTag(DETAIL_FRAGMENT);
        if (fragment == null) {
            fragment = MovieDetailFragment.newInstance();
//            getSupportFragmentManager().beginTransaction().replace(R.id.flContainer, fragment).commit();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), fragment, R.id.flContainer);
        }
        return fragment;
    }
}
