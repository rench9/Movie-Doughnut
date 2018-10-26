package com.yahoo.r4hu7.moviesdoughnut.data.local.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.MovieExternalIdsResponse;

@Dao
public interface ExternalLinkDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(MovieExternalIdsResponse... externalIdsResponses);

    @Query("SELECT * FROM _link WHERE id=:movieId")
    LiveData<MovieExternalIdsResponse> getResponse(int movieId);
}
