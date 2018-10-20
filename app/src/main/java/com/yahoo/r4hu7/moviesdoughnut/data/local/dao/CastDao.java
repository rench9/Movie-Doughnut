package com.yahoo.r4hu7.moviesdoughnut.data.local.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.MovieCreditsResponse;

@Dao
public interface CastDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(MovieCreditsResponse... creditsResponses);

    @Query("SELECT * FROM _cast WHERE id=:movieId")
    MovieCreditsResponse getResponse(int movieId);
}
