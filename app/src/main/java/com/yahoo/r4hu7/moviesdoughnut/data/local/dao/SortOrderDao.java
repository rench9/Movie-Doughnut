package com.yahoo.r4hu7.moviesdoughnut.data.local.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;

import com.yahoo.r4hu7.moviesdoughnut.data.SortOrder;

@Dao
public interface SortOrderDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(SortOrder... sortOrder);
}
