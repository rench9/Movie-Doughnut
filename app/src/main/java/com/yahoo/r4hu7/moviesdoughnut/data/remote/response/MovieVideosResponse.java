package com.yahoo.r4hu7.moviesdoughnut.data.remote.response;

import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.model.Video;

public class MovieVideosResponse extends Response {
    int id;
    Video[] results;

    public int getId() {
        return id;
    }

    public Video[] getResults() {
        return results;
    }
}
