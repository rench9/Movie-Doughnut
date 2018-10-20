package com.yahoo.r4hu7.moviesdoughnut.data.remote.response;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.yahoo.r4hu7.moviesdoughnut.data.local.entity.MovieTypeConverter;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.model.Cast;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.model.Crew;

@Entity(tableName = "_cast")
@TypeConverters(MovieTypeConverter.class)
public class MovieCreditsResponse extends Response {
    @PrimaryKey
    public int id;
    public Cast[] cast;
    public Crew[] crew;

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
