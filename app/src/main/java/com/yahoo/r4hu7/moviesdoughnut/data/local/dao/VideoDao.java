package com.yahoo.r4hu7.moviesdoughnut.data.local.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.MovieVideosResponse;

@Dao
public interface VideoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(MovieVideosResponse... videosResponses);

    @Query("SELECT * FROM _video WHERE id=:movieId")
    LiveData<MovieVideosResponse> getResponse(int movieId);
}
