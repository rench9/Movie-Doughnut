package com.yahoo.r4hu7.moviesdoughnut.data.remote.response;

import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.model.Backdrop;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.model.Poster;

public class MovieImagesResponse extends Response {
    int id;

    Backdrop[] backdrops;

    Poster[] posters;

    public int getId() {
        return id;
    }

    public Backdrop[] getBackdrops() {
        return backdrops;
    }

    public Poster[] getPosters() {
        return posters;
    }
}
