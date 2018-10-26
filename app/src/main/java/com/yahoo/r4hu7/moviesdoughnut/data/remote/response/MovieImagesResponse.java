package com.yahoo.r4hu7.moviesdoughnut.data.remote.response;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.yahoo.r4hu7.moviesdoughnut.data.local.entity.MovieTypeConverter;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.model.Backdrop;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.model.Movie;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.model.Poster;

@Entity(tableName = "_image",
        foreignKeys = {@ForeignKey(
                entity = Movie.class,
                parentColumns = "id",
                childColumns = "id",
                onDelete = ForeignKey.CASCADE
        )})
@TypeConverters(MovieTypeConverter.class)
public class MovieImagesResponse extends Response {
    @PrimaryKey
    public int id;
    public Backdrop[] backdrops;
    public Poster[] posters;

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
