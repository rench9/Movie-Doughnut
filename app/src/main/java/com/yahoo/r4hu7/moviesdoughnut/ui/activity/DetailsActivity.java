package com.yahoo.r4hu7.moviesdoughnut.ui.activity;

import android.content.Intent;
import android.databinding.Observable;
import android.databinding.ObservableField;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.yahoo.r4hu7.moviesdoughnut.R;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.MovieResponse;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.model.Movie;
import com.yahoo.r4hu7.moviesdoughnut.di.DaggerRepositoryComponent;
import com.yahoo.r4hu7.moviesdoughnut.di.module.ContextModule;
import com.yahoo.r4hu7.moviesdoughnut.ui.fragment.MovieDetailFragment;
import com.yahoo.r4hu7.moviesdoughnut.ui.viewmodel.MovieDetailViewModel;
import com.yahoo.r4hu7.moviesdoughnut.ui.viewmodel.ViewModelHolder;
import com.yahoo.r4hu7.moviesdoughnut.util.ActivityUtils;
import com.yahoo.r4hu7.moviesdoughnut.util.MovieNavigator;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsActivity extends AppCompatActivity implements MovieNavigator {
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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        viewModel = findOrCreateMovieDetailViewModel();
        viewModel.setMovie(getIntent().getExtras().getParcelable(MOVIE_KEY));
        viewModel.setNavigator(this);
        viewModel.loadMovieDetail();
        viewModel.loadCast();
        viewModel.loadImages();
        viewModel.loadVideos();
        viewModel.loadReviews();
        viewModel.loadExternalIds();
        MovieDetailFragment fragment = findOrCreateDetailFragment();
        fragment.setViewModel(viewModel);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        viewModel.movieDetail.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                int h = (int) Math.floor(((ObservableField<MovieResponse>) sender).get().getRuntime() / 60);
                int m = ((ObservableField<MovieResponse>) sender).get().getRuntime() % 60;
                menu.add(String.format("%dh%dm", h, m)).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
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
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), fragment, R.id.flContainer, DETAIL_FRAGMENT);
        }
        return fragment;
    }

    @Override
    public void openMovieDetails(Movie movie) {

    }

    @Override
    public void openLink(String url) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
    }

    @Override
    public void goBack() {

    }
}
