package com.yahoo.r4hu7.moviesdoughnut.data.remote.request;

import android.arch.lifecycle.LiveData;

import com.yahoo.r4hu7.moviesdoughnut.data.remote.ApiResponse;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.Endpoints;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.DiscoverMovieResponse;

public class Discover {
    private Endpoints endpoints;

    public Discover(Endpoints e) {
        endpoints = e;
    }

    public LiveData<ApiResponse<DiscoverMovieResponse>> discoverMovies() {
        return endpoints.discoverMovies(1);
    }

    public LiveData<ApiResponse<DiscoverMovieResponse>> discoverMovies(int page) {
        return endpoints.discoverMovies(page);
    }

    /**
     * String sortBy
     * Allowed Values: , popularity.asc, popularity.desc, release_date.asc, release_date.desc, revenue.asc, revenue.desc, primary_release_date.asc, primary_release_date.desc, original_title.asc, original_title.desc, vote_average.asc, vote_average.desc, vote_count.asc, vote_count.desc
     * Default: popularity.desc
     */
    public LiveData<ApiResponse<DiscoverMovieResponse>> discoverMovies(String sortBy, int page) {
        return endpoints.discoverMovies(sortBy, page);
    }

}
