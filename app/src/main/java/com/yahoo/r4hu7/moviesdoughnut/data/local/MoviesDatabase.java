package com.yahoo.r4hu7.moviesdoughnut.data.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.yahoo.r4hu7.moviesdoughnut.data.local.dao.MovieDao;
import com.yahoo.r4hu7.moviesdoughnut.data.local.dao.MovieDetailDao;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.MovieResponse;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.model.Movie;

@Database(entities = {Movie.class,
        MovieResponse.class}, version = 1, exportSchema = true)
public abstract class MoviesDatabase extends RoomDatabase {
    private static MoviesDatabase INSTANCE;

    public abstract MovieDao movieDao();

    public abstract MovieDetailDao movieDetailDao();

    private static final Object sLock = new Object();

    public static MoviesDatabase getInstance(Context context) {
        if (INSTANCE == null)
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), MoviesDatabase.class, "_main").build();
        return INSTANCE;
    }
}
