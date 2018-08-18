package com.yahoo.r4hu7.moviesdoughnut.data.remote.response;

import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.model.Dates;

public class NowPlayingMovieResponse extends MoviesSource {
    Dates dates;

    public Dates getDates() {
        return dates;
    }
}
