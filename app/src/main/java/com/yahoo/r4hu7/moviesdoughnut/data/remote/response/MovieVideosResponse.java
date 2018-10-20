package com.yahoo.r4hu7.moviesdoughnut.data.remote.response;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.yahoo.r4hu7.moviesdoughnut.data.local.entity.MovieTypeConverter;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.model.Movie;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.model.Video;

@Entity(tableName = "_video",
        foreignKeys = {@ForeignKey(
                entity = Movie.class,
                parentColumns = "id",
                childColumns = "id",
                onDelete = ForeignKey.CASCADE
        )})
@TypeConverters(MovieTypeConverter.class)
public class MovieVideosResponse extends Response {
    @PrimaryKey
    public int id;
    public Video[] results;

    public int getId() {
        return id;
    }

    public Video[] getResults() {
        return results;
    }
}
