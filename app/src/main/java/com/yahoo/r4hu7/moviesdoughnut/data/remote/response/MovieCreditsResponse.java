package com.yahoo.r4hu7.moviesdoughnut.data.remote.response;

import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.model.Cast;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.model.Crew;

public class MovieCreditsResponse extends Response {
    int id;
    Cast[] cast;
    Crew[] crew;

    public int getId() {
        return id;
    }

    public Cast[] getCast() {
        return cast;
    }

    public Crew[] getCrew() {
        return crew;
    }
}
