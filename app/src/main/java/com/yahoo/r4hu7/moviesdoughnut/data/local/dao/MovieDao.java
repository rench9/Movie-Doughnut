package com.yahoo.r4hu7.moviesdoughnut.data.local.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.model.Movie;

import java.util.List;

@Dao
public interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Movie... movies);

//    @Query("SELECT * FROM _movie")
//    List<Movie> findMovies(int sortId);
}
