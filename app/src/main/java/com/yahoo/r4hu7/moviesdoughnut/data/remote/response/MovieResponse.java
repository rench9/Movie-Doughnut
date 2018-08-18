package com.yahoo.r4hu7.moviesdoughnut.data.remote.response;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.yahoo.r4hu7.moviesdoughnut.data.local.entity.MovieTypeConverter;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.model.Item;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.model.ProductionCompanies;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.model.ProductionCountries;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.model.SpokenLanguages;

@Entity(tableName = "_detail")
@TypeConverters(MovieTypeConverter.class)
public class MovieResponse extends Response {
    @Ignore
    public boolean adult;
    @Ignore
    public String backdrop_path;
    @Ignore
    public Object belongs_to_collection;
    public int budget;
    public Item[] genres;
    public String homepage;
    @PrimaryKey
    public int id;
    public String imdb_id; // minLength: 9, maxLength: 9, pattern: ^tt[0-9]{7}
    @Ignore
    public String original_language;
    @Ignore
    public String original_title;
    @Ignore
    public String overview;
    @Ignore
    public double popularity;
    @Ignore
    public String poster_path;
    @Ignore
    public ProductionCompanies[] production_companies;
    @Ignore
    public ProductionCountries[] production_countries;
    @Ignore
    public String release_date;
    public int revenue;
    public int runtime;
    @Ignore
    public SpokenLanguages[] spoken_languages;
    public String status; //Rumored, Planned, In Production, Post Production, Released, Canceled
    public String tagline;
    @Ignore
    public String title;
    @Ignore
    public boolean video;
    @Ignore
    public double vote_average;
    @Ignore
    public int vote_count;

    public boolean isAdult() {
        return adult;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public Object getBelongs_to_collection() {
        return belongs_to_collection;
    }

    public int getBudget() {
        return budget;
    }

    public Item[] getGenres() {
        return genres;
    }

    public String getHomepage() {
        return homepage;
    }

    public int getId() {
        return id;
    }

    public String getImdb_id() {
        return imdb_id;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public String getOverview() {
        return overview;
    }

    public double getPopularity() {
        return popularity;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public ProductionCompanies[] getProduction_companies() {
        return production_companies;
    }

    public ProductionCountries[] getProduction_countries() {
        return production_countries;
    }

    public String getRelease_date() {
        return release_date;
    }

    public int getRevenue() {
        return revenue;
    }

    public int getRuntime() {
        return runtime;
    }

    public SpokenLanguages[] getSpoken_languages() {
        return spoken_languages;
    }

    public String getStatus() {
        return status;
    }

    public String getTagline() {
        return tagline;
    }

    public String getTitle() {
        return title;
    }

    public boolean isVideo() {
        return video;
    }

    public double getVote_average() {
        return vote_average;
    }

    public int getVote_count() {
        return vote_count;
    }
}
