package com.yahoo.r4hu7.moviesdoughnut.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ArgbEvaluator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.ColorStateList;
import android.databinding.Observable;
import android.databinding.ObservableBoolean;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkRequest;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.AppBarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.yahoo.r4hu7.moviesdoughnut.R;
import com.yahoo.r4hu7.moviesdoughnut.data.MoviesRepository;
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
import com.yahoo.r4hu7.moviesdoughnut.ui.fragment.SplashScreenFragment;
import com.yahoo.r4hu7.moviesdoughnut.ui.viewmodel.GalleryActivityViewModel;
import com.yahoo.r4hu7.moviesdoughnut.ui.viewmodel.MoviesViewModel;
import com.yahoo.r4hu7.moviesdoughnut.ui.viewmodel.ViewModelHolder;
import com.yahoo.r4hu7.moviesdoughnut.util.ActivityUtils;
import com.yahoo.r4hu7.moviesdoughnut.util.ConnectivityChangeListener;
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
    public static final String PROP_BAR_L = "PROP_BAR_L";
    public static final String PROP_ALPHA = "PROP_ALPHA";
    public static final String PROP_COLOR = "PROP_COLOR";
    private static final String CONNECTIVITY_CHANGE = "CONNECTIVITY_CHANGE";

    private ConnectivityChangeListener connectivityChangeListener;
    private IntentFilter intentFilter;

    private MoviesRepository repository;
    private SplashScreenFragment splashScreenFragment;

    private GalleryActivityViewModel activityViewModel;
    private MoviesViewModel nowPlayingMoviesViewModel;
    private MoviesViewModel upcomingMoviesViewModel;
    private MoviesViewModel topRatedMoviesViewModel;
    private MoviesViewModel popularMoviesViewModel;
    private MoviesViewModel gridMoviesViewModel;

    private Observable.OnPropertyChangedCallback fragmentSwitcherCallback;
    private Observable.OnPropertyChangedCallback filterChangeCallback;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        ButterKnife.bind(this);
        showSplash();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        initFilterSpinner();
        initAnimator();
        initNetworkListener();
        if (repository == null)
            repository = DaggerRepositoryComponent.builder().contextModule(new ContextModule(getApplicationContext())).build().getMoviesRepository();
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
                        initAnimator();
                    }
                }
            };

        if (filterChangeCallback == null)
            filterChangeCallback = new Observable.OnPropertyChangedCallback() {
                @Override
                public void onPropertyChanged(Observable sender, int propertyId) {
                    showGridFragment();
                }
            };

        activityViewModel = findOrCreateGalleryActivityViewModel();
        activityViewModel.gridView.addOnPropertyChangedCallback(fragmentSwitcherCallback);

        activityViewModel.moviesFilter.addOnPropertyChangedCallback(filterChangeCallback);

        if (activityViewModel.gridView.get()) {
            showGridFragment();
            hideSearchBar();
        } else {
            showLandingFragment();
            initAnimator();
        }
    }

    private void showSplash() {
        if (ActivityUtils.splashGone)
            return;
        splashScreenFragment = new SplashScreenFragment();
        splashScreenFragment.setCancelable(false);
        splashScreenFragment.show(getSupportFragmentManager(), "splash");
        ActivityUtils.splashGone = true;
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
                spnContainer.setSelection(4, true);
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

    private void initNetworkListener() {
        connectivityChangeListener = new ConnectivityChangeListener() {

            @Override
            public void connected() {
                if (ActivityUtils.splashGone) ;
                Toast.makeText(
                        getApplicationContext(),
                        "You are connected now",
                        Toast.LENGTH_SHORT).show();
                if (repository != null)
                    repository.setOffline(false);
            }

            @Override
            public void disConnected() {
                Toast.makeText(
                        getApplicationContext(),
                        "No internet connectivity",
                        Toast.LENGTH_SHORT).show();
                if (repository != null)
                    repository.setOffline(true);
            }
        };
        intentFilter = new IntentFilter();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            createChangeConnectivityMonitor();
            intentFilter.addAction(CONNECTIVITY_CHANGE);
        } else {
            intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        }

        registerReceiver(connectivityChangeListener, intentFilter);
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private void createChangeConnectivityMonitor() {
        final Intent intent = new Intent(CONNECTIVITY_CHANGE);
        ConnectivityManager connectivityManager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            connectivityManager.registerNetworkCallback(
                    new NetworkRequest.Builder().build(),
                    new ConnectivityManager.NetworkCallback() {
                        @Override
                        public void onAvailable(Network network) {
                            sendBroadcast(intent);
                        }

                        @Override
                        public void onLost(Network network) {
                            sendBroadcast(intent);
                        }
                    });
        }
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

    private void showSearchOption() {
        mMenu.findItem(R.id.actionSearch).setVisible(true);
        mMenu.findItem(R.id.actionFilter).setVisible(false);
    }

    private void hideSearchBar() {

        PropertyValuesHolder lengthAnimator = PropertyValuesHolder.ofInt(PROP_BAR_L, searchInputLayout.getMeasuredWidth(), getResources().getDimensionPixelSize(R.dimen.filter_w));
        PropertyValuesHolder alphaAnimator = PropertyValuesHolder.ofFloat(PROP_ALPHA, searchInputLayout.getChildAt(0).getAlpha(), 0f);

        ValueAnimator animator = new ValueAnimator();
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.setDuration(300);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            PropertyValuesHolder colorAnimator = PropertyValuesHolder.ofObject(PROP_COLOR, new ArgbEvaluator(), searchInputLayout.getBackgroundTintList().getDefaultColor(), ContextCompat.getColor(getBaseContext(), R.color.shade0));
            animator.setValues(lengthAnimator, alphaAnimator, colorAnimator);
        } else animator.setValues(lengthAnimator, alphaAnimator);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                searchInputLayout.forceHide();
                spnContainer.forceVisible();
            }
        });

        animator.addUpdateListener(valueAnimator -> {
            int val = (Integer) valueAnimator.getAnimatedValue(PROP_COLOR);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                searchInputLayout.setBackgroundTintList(ColorStateList.valueOf(val));
            }

            float val2 = (float) valueAnimator.getAnimatedValue(PROP_ALPHA);
            searchInputLayout.getChildAt(0).setAlpha(val2);

            int val3 = (Integer) valueAnimator.getAnimatedValue(PROP_BAR_L);
            ViewGroup.LayoutParams layoutParams = searchInputLayout.getLayoutParams();
            layoutParams.width = val3;
            searchInputLayout.setLayoutParams(layoutParams);
        });

        animator.start();
    }

    private void initAnimator() {
        PropertyValuesHolder lengthAnimator = PropertyValuesHolder.ofInt(PROP_BAR_L, getResources().getDimensionPixelSize(R.dimen.filter_w), getResources().getDimensionPixelSize(R.dimen.search_bar_w));
        PropertyValuesHolder alphaAnimator = PropertyValuesHolder.ofFloat(PROP_ALPHA, searchInputLayout.getAlpha(), 1.0f);

        ValueAnimator animator = new ValueAnimator();
        animator.setInterpolator(new DecelerateInterpolator());
        animator.setDuration(250);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            PropertyValuesHolder colorAnimator = PropertyValuesHolder.ofObject(PROP_COLOR, new ArgbEvaluator(), searchInputLayout.getBackgroundTintList().getDefaultColor(), ContextCompat.getColor(getBaseContext(), R.color.sky_blue_s2));
            animator.setValues(lengthAnimator, alphaAnimator, colorAnimator);
        } else animator.setValues(lengthAnimator, alphaAnimator);
        animator.addListener(new AnimatorListenerAdapter() {
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

        animator.addUpdateListener(valueAnimator -> {
            float val = (float) valueAnimator.getAnimatedValue(PROP_ALPHA);
            searchInputLayout.getChildAt(0).setAlpha(val);

            int val2 = (Integer) valueAnimator.getAnimatedValue(PROP_COLOR);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                searchInputLayout.setBackgroundTintList(ColorStateList.valueOf(val2));
            }

            int val3 = (Integer) valueAnimator.getAnimatedValue(PROP_BAR_L);
            ViewGroup.LayoutParams layoutParams = searchInputLayout.getLayoutParams();
            layoutParams.width = val3;
            searchInputLayout.setLayoutParams(layoutParams);
        });

        animator.start();

    }

    private void showLandingFragment() {
        GalleryLandingFragment fragment = findOrCreateLandingFragment();
        if (nowPlayingMoviesViewModel == null || upcomingMoviesViewModel == null || topRatedMoviesViewModel == null || popularMoviesViewModel == null) {
            nowPlayingMoviesViewModel = findOrCreateMoviesViewModel(MOVIES_VM_NOW_PLAYING);
            upcomingMoviesViewModel = findOrCreateMoviesViewModel(MOVIES_VM_UPCOMING);
            topRatedMoviesViewModel = findOrCreateMoviesViewModel(MOVIES_VM_TOPRATED);
            popularMoviesViewModel = findOrCreateMoviesViewModel(MOVIES_VM_POPULAR);
        }
        fragment.setNowPlayingViewModel(nowPlayingMoviesViewModel);
        fragment.setUpComingViewModel(upcomingMoviesViewModel);
        fragment.setTopRatedViewModel(topRatedMoviesViewModel);
        fragment.setPopularViewModel(popularMoviesViewModel);
        nowPlayingMoviesViewModel.dataLoaded.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                hideSplash();
            }
        });
    }

    private void showGridFragment() {
        gridMoviesViewModel = findOrCreateMoviesViewModel(MOVIES_VM);
        GridMoviesFragment fragment = findOrCreateGridFragment();
        fragment.setMoviesViewModel(gridMoviesViewModel);
    }

    @NonNull
    private GalleryActivityViewModel findOrCreateGalleryActivityViewModel() {
        ViewModelHolder<GalleryActivityViewModel> retainedViewModel = (ViewModelHolder<GalleryActivityViewModel>) getSupportFragmentManager().findFragmentByTag(ACTIVITY_GALLERY_VM);

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
        ViewModelHolder<MoviesViewModel> retainedViewModel =
                (ViewModelHolder<MoviesViewModel>) getSupportFragmentManager()
                        .findFragmentByTag(tag);

        if (retainedViewModel != null && retainedViewModel.getViewModel() != null) {
            // If the model was retained, return it.
            return retainedViewModel.getViewModel();
        } else {
            // There is no ViewModel yet, create it.
            MoviesViewModel viewModel = new MoviesViewModel(repository);
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
            if (gridMoviesViewModel != null) {
                gridMoviesViewModel.setSortOrder(f.getSortOrder());
                gridMoviesViewModel.loadMovies();
            }
        }
    }

    public void hideSplash() {
        if (splashScreenFragment != null && splashScreenFragment.isVisible()) {
            splashScreenFragment.dismissAllowingStateLoss();
            splashScreenFragment.dismiss();
            activityViewModel.isSplashGone.set(true);
        }
    }

}
