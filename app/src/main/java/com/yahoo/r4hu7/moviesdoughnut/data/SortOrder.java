package com.yahoo.r4hu7.moviesdoughnut.data;

import android.arch.persistence.room.Ignore;

public class SortOrder {
    @Ignore
    public static final int POPULAR = 1;
    @Ignore
    public static final int TOPRATED = 2;
    @Ignore
    public static final int NOWPLAYING = 3;
    @Ignore
    public static final int UPCOMING = 4;

    int id;
    int movieId;
    int sortId;


}
