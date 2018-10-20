package com.yahoo.r4hu7.moviesdoughnut.data.local.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "_favourite")
public class FavouriteEntity {
    @PrimaryKey
    public int movieId;
}
