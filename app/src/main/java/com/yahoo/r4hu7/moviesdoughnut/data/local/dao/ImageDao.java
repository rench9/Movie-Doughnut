package com.yahoo.r4hu7.moviesdoughnut.data.local.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.MovieImagesResponse;

@Dao
public interface ImageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(MovieImagesResponse movieImagesResponse);

    @Query("SELECT * FROM _image WHERE id =:movieId")
    LiveData<MovieImagesResponse> getResponse(int movieId);
}
