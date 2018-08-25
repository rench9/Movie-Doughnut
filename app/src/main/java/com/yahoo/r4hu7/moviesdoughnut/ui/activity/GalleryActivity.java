package com.yahoo.r4hu7.moviesdoughnut.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.databinding.Observable;
import android.databinding.ObservableBoolean;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.FrameLayout;

import com.yahoo.r4hu7.moviesdoughnut.R;
import com.yahoo.r4hu7.moviesdoughnut.data.SortOrder;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.model.Movie;
import com.yahoo.r4hu7.moviesdoughnut.di.DaggerRepositoryComponent;
import com.yahoo.r4hu7.moviesdoughnut.di.module.ContextModule;
import com.yahoo.r4hu7.moviesdoughnut.ui.dependency.SearchTextInputLayout;
import com.yahoo.r4hu7.moviesdoughnut.ui.dependency.SortOrderSpinner;
import com.yahoo.r4hu7.moviesdoughnut.ui.dependency.adapter.Filter;
import com.yahoo.r4hu7.moviesdoughnut.ui.dependency.adapter.SortSpinnerAdapter;
import com.yahoo.r4hu7.moviesdoughnut.ui.fragment.GalleryLandingFragment;
import com.yahoo.r4hu7.moviesdoughnut.ui.fragment.GridMoviesFragment;
import com.yahoo.r4hu7.moviesdoughnut.ui.viewmodel.GalleryActivityViewModel;
import com.yahoo.r4hu7.moviesdoughnut.ui.viewmodel.MoviesViewModel;
import com.yahoo.r4hu7.moviesdoughnut.ui.viewmodel.ViewModelHolder;
import com.yahoo.r4hu7.moviesdoughnut.util.ActivityUtils;
import com.yahoo.r4hu7.moviesdoughnut.util.MovieNavigator;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GalleryActivity extends AppCompatActivity implements MovieNavigator, AdapterView.OnItemSelectedListener {

    public static final String ACTIVITY_GALLERY_VM = "ACTIVITY_GALLERY_VM";
    public static final String MOVIES_VM = "MOVIES_VM";
    public static final String MOVIES_VM_POPULAR = "MOVIES_VM_POPULAR";
    public static final String MOVIES_VM_UPCOMING = "MOVIES_VM_UPCOMING";
    public static final String MOVIES_VM_TOPRATED = "MOVIES_VM_TOPRATED";
    public static final String MOVIES_VM_NOW_PLAYING = "MOVIES_VM_NOW_PLAYING";
    public static final String GRID_MOVIES_FRAGMENT = "GRID_MOVIES_FRAGMENT";
    public static final String LANDING_FRAGMENT = "LANDING_FRAGMENT";

    private GalleryActivityViewModel activityViewModel;

    private MoviesViewModel gridMoviesViewModel;

    private Observable.OnPropertyChangedCallback fragmentSwitcherCallback;

    private Menu mMenu;

    @BindView(R.id.tbPrimary)
    Toolbar toolbar;
    @BindView(R.id.tilSearch)
    SearchTextInputLayout searchInputLayout;
    @BindView(R.id.abPrimary)
    AppBarLayout abPrimary;
    @BindView(R.id.spnContainer)
    SortOrderSpinner spnContainer;
    @BindView(R.id.flContainer)
    FrameLayout flContainer;
    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        initFilterSpinner();

        if (fragmentSwitcherCallback == null)
            fragmentSwitcherCallback = new Observable.OnPropertyChangedCallback() {
                @Override
                public void onPropertyChanged(Observable sender, int propertyId) {
                    if (((ObservableBoolean) sender).get()) {
                        showSearchOption();
                        showGridFragment();
                        hideSearchBar();
                    } else {
                        hideSearchOption();
                        showLandingFragment();
                        showSearchBar();
                    }
                }
            };

        activityViewModel = findOrCreateGalleryActivityViewModel();
        activityViewModel.gridView.addOnPropertyChangedCallback(fragmentSwitcherCallback);

        activityViewModel.moviesFilter.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                showGridFragment();
            }
        });

        if (activityViewModel.gridView.get()) {
            showGridFragment();
            hideSearchBar();
        } else {
            showLandingFragment();
            showSearchBar();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.actionSearch:
                activityViewModel.gridView.set(false);
                break;
            case R.id.actionFilter:
                activityViewModel.gridView.set(true);
                break;
            case R.id.actionFav:
                activityViewModel.gridView.set(true);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initFilterSpinner() {
        Filter[] filters = new Filter[]{
                new Filter(SortOrder.POPULAR, getResources().getString(R.string.popular_movies)),
                new Filter(SortOrder.TOPRATED, getResources().getString(R.string.top_rated_movies)),
                new Filter(SortOrder.NOWPLAYING, getResources().getString(R.string.now_playing_movies)),
                new Filter(SortOrder.UPCOMING, getResources().getString(R.string.upcoming_movies)),
                new Filter(SortOrder.FAVORITE, getResources().getString(R.string.fav_movies))
        };

        spnContainer.setAdapter(new SortSpinnerAdapter(this, R.layout.adapter_spinner, filters));
        spnContainer.setOnItemSelectedListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.gallery, menu);
        mMenu = menu;
        if (activityViewModel.gridView.get()) {
            menu.findItem(R.id.actionSearch).setVisible(true);
            menu.findItem(R.id.actionFilter).setVisible(false);
        } else {
            menu.findItem(R.id.actionSearch).setVisible(false);
            menu.findItem(R.id.actionFilter).setVisible(true);
        }
        return super.onCreateOptionsMenu(menu);
    }

    protected void hideSearchOption() {
        mMenu.findItem(R.id.actionSearch).setVisible(false);
        mMenu.findItem(R.id.actionFilter).setVisible(true);
    }

    private void hideSearchBar() {

        ValueAnimator anim = ValueAnimator.ofInt(searchInputLayout.getMeasuredWidth(), getResources().getDimensionPixelSize(R.dimen.filter_w));
        anim.addUpdateListener(valueAnimator -> {
            int val = (Integer) valueAnimator.getAnimatedValue();
            ViewGroup.LayoutParams layoutParams = searchInputLayout.getLayoutParams();
            layoutParams.width = val;
            searchInputLayout.setLayoutParams(layoutParams);
        });

        ValueAnimator alphaAnimation = ValueAnimator.ofFloat(searchInputLayout.getChildAt(0).getAlpha(), 0f);
        alphaAnimation.addUpdateListener(valueAnimator -> {
            float val = (float) valueAnimator.getAnimatedValue();
            searchInputLayout.getChildAt(0).setAlpha(val);
        });


        ValueAnimator colorAnimation = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), searchInputLayout.getBackgroundTintList().getDefaultColor(), ContextCompat.getColor(getBaseContext(), R.color.shade0));
        }
        colorAnimation.addUpdateListener(valueAnimator -> {
            int val = (Integer) valueAnimator.getAnimatedValue();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                searchInputLayout.setBackgroundTintList(ColorStateList.valueOf(val));
            }
        });

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setInterpolator(new DecelerateInterpolator());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            animatorSet.playTogether(anim, colorAnimation, alphaAnimation);
        else
            animatorSet.playTogether(anim, alphaAnimation);
        animatorSet.setDuration(500);

        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                searchInputLayout.forceHide();
                spnContainer.forceVisible();
            }
        });
        animatorSet.start();
    }

    private void showSearchOption() {
        mMenu.findItem(R.id.actionSearch).setVisible(true);
        mMenu.findItem(R.id.actionFilter).setVisible(false);
    }

    private void showSearchBar() {
        ValueAnimator anim = ValueAnimator.ofInt(searchInputLayout.getMeasuredWidth(), getResources().getDimensionPixelSize(R.dimen.search_bar_w));
        anim.addUpdateListener(valueAnimator -> {
            int val = (Integer) valueAnimator.getAnimatedValue();
            ViewGroup.LayoutParams layoutParams = searchInputLayout.getLayoutParams();
            layoutParams.width = val;
            searchInputLayout.setLayoutParams(layoutParams);
        });

        ValueAnimator colorAnimation = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), searchInputLayout.getBackgroundTintList().getDefaultColor(), ContextCompat.getColor(getBaseContext(), R.color.sky_blue_s2));
        }
        colorAnimation.addUpdateListener(valueAnimator -> {
            int val = (Integer) valueAnimator.getAnimatedValue();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                searchInputLayout.setBackgroundTintList(ColorStateList.valueOf(val));
            }
        });

        ValueAnimator alphaAnimation = ValueAnimator.ofFloat(searchInputLayout.getChildAt(0).getAlpha(), 1.0f);
        alphaAnimation.addUpdateListener(valueAnimator -> {
            float val = (float) valueAnimator.getAnimatedValue();
            searchInputLayout.getChildAt(0).setAlpha(val);
        });


        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setInterpolator(new DecelerateInterpolator());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            animatorSet.playTogether(anim, colorAnimation, alphaAnimation);
        else
            animatorSet.playTogether(anim, alphaAnimation);
        animatorSet.setDuration(500);

        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                searchInputLayout.forceVisible();
                spnContainer.forceHide();
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                searchInputLayout.forceVisible();
                spnContainer.forceHide();
            }
        });
        animatorSet.start();
    }

    private void showLandingFragment() {
        GalleryLandingFragment fragment = findOrCreateLandingFragment();
        fragment.setNowPlayingViewModel(findOrCreateMoviesViewModel(MOVIES_VM_NOW_PLAYING));
        fragment.setUpComingViewModel(findOrCreateMoviesViewModel(MOVIES_VM_UPCOMING));
        fragment.setTopRatedViewModel(findOrCreateMoviesViewModel(MOVIES_VM_TOPRATED));
        fragment.setPopularViewModel(findOrCreateMoviesViewModel(MOVIES_VM_POPULAR));
    }

    private void showGridFragment() {
        if (gridMoviesViewModel == null)
            gridMoviesViewModel = findOrCreateMoviesViewModel(MOVIES_VM);
        GridMoviesFragment fragment = findOrCreateGridFragment();
        fragment.setMoviesViewModel(gridMoviesViewModel);
    }

    @NonNull
    private GridMoviesFragment findOrCreateGridFragment() {
        GridMoviesFragment fragment =
                (GridMoviesFragment) getSupportFragmentManager().findFragmentByTag(GRID_MOVIES_FRAGMENT);
        if (fragment == null) {
            fragment = GridMoviesFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), fragment, R.id.flContainer, GRID_MOVIES_FRAGMENT);
        }
        return fragment;
    }

    @NonNull
    private GalleryActivityViewModel findOrCreateGalleryActivityViewModel() {
        // In a configuration change we might have a ViewModel present. It's retained using the
        // Fragment Manager.
        ViewModelHolder<GalleryActivityViewModel> retainedViewModel =
                (ViewModelHolder<GalleryActivityViewModel>) getSupportFragmentManager()
                        .findFragmentByTag(ACTIVITY_GALLERY_VM);

        if (retainedViewModel != null && retainedViewModel.getViewModel() != null) {
            // If the model was retained, return it.
            return retainedViewModel.getViewModel();
        } else {
            // There is no ViewModel yet, create it.
            GalleryActivityViewModel viewModel = new GalleryActivityViewModel();
            // and bind it to this Activity's lifecycle using the Fragment Manager.
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(),
                    ViewModelHolder.createContainer(viewModel),
                    ACTIVITY_GALLERY_VM);
            return viewModel;
        }
    }

    @NonNull
    private MoviesViewModel findOrCreateMoviesViewModel(String tag) {
        // In a configuration change we might have a ViewModel present. It's retained using the
        // Fragment Manager.
        @SuppressWarnings("unchecked")
        ViewModelHolder<MoviesViewModel> retainedViewModel =
                (ViewModelHolder<MoviesViewModel>) getSupportFragmentManager()
                        .findFragmentByTag(tag);

        if (retainedViewModel != null && retainedViewModel.getViewModel() != null) {
            // If the model was retained, return it.
            return retainedViewModel.getViewModel();
        } else {
            // There is no ViewModel yet, create it.
            MoviesViewModel viewModel = new MoviesViewModel(
                    DaggerRepositoryComponent.builder().contextModule(new ContextModule(getApplicationContext())).build().getMoviesRepository()
            );
            // and bind it to this Activity's lifecycle using the Fragment Manager.
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(),
                    ViewModelHolder.createContainer(viewModel),
                    tag);
            return viewModel;
        }
    }

    @NonNull
    private GalleryLandingFragment findOrCreateLandingFragment() {
        GalleryLandingFragment fragment =
                (GalleryLandingFragment) getSupportFragmentManager().findFragmentByTag(LANDING_FRAGMENT);
        if (fragment == null) {
            fragment = GalleryLandingFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), fragment, R.id.flContainer, LANDING_FRAGMENT);
        }
        return fragment;
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void openMovieDetails(Movie movie) {
        Bundle bundle = new Bundle();
        Intent intent = new Intent(getApplicationContext(), DetailsActivity.class);
        bundle.putParcelable(DetailsActivity.MOVIE_KEY, movie);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void openLink(String url) {

    }

    @Override
    public void goBack() {

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (adapterView.getSelectedItem() instanceof Filter) {
            Filter f = (Filter) adapterView.getSelectedItem();
            gridMoviesViewModel.setSortOrder(f.getSortOrder());
            gridMoviesViewModel.loadMovies();
        }
    }
}
