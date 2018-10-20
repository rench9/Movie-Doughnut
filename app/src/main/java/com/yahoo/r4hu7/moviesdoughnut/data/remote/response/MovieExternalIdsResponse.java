package com.yahoo.r4hu7.moviesdoughnut.data.remote.response;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.model.Movie;

@Entity(tableName = "_link",
        foreignKeys = {@ForeignKey(
                entity = Movie.class,
                parentColumns = "id",
                childColumns = "id",
                onDelete = ForeignKey.CASCADE
        )})
public class MovieExternalIdsResponse extends Response {
    @PrimaryKey
    public int id;
    public String imdb_id;
    public String facebook_id;
    public String instagram_id;
    public String twitter_id;

    public int getId() {
        return id;
    }

    public String getImdb_id() {
        return imdb_id;
    }

    public String getFacebook_id() {
        return facebook_id;
    }

    public String getInstagram_id() {
        return instagram_id;
    }

    public String getTwitter_id() {
        return twitter_id;
    }

    public boolean isDataAvailable() {
        return imdb_id != null || facebook_id != null || instagram_id != null || twitter_id != null;
    }
}
