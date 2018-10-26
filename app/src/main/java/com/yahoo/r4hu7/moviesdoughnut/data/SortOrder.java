package com.yahoo.r4hu7.moviesdoughnut.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.model.Movie;

@Entity(tableName = "_sort_order",
        indices = {@Index(value = {"movieId", "sortId"}, unique = true)},
        foreignKeys = {@ForeignKey(
                entity = Movie.class,
                parentColumns = "id",
                childColumns = "movieId",
                onDelete = ForeignKey.CASCADE)})
public class SortOrder {
    @Ignore
    public static final int NONE = -1;
    @Ignore
    public static final int POPULAR = 1;
    @Ignore
    public static final int TOPRATED = 2;
    @Ignore
    public static final int NOWPLAYING = 3;
    @Ignore
    public static final int UPCOMING = 4;
    @Ignore
    public static final int FAVORITE = 5;

    @PrimaryKey(autoGenerate = true)
    public int id;
    public int movieId;
    public int sortId;

    public SortOrder(int sortId, int movieId) {
        this.movieId = movieId;
        this.sortId = sortId;
    }
}
