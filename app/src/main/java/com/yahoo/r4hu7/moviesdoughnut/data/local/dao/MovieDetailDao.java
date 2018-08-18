package com.yahoo.r4hu7.moviesdoughnut.data.local.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.MovieResponse;
@Dao
public interface MovieDetailDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(MovieResponse... movieResponses);

    @Query("SELECT * FROM _detail WHERE id=:movieId")
    MovieResponse findMovieDetailById(int movieId);
}
