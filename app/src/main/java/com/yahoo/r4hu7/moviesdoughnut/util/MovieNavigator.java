package com.yahoo.r4hu7.moviesdoughnut.util;

import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.model.Movie;

public interface MovieNavigator {
    void openMovieDetails(Movie movie);

    void openLink(String url);

    void goBack();

}

