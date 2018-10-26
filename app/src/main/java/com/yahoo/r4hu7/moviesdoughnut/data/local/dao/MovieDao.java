package com.yahoo.r4hu7.moviesdoughnut.data.local.dao;

import android.arch.lifecycle.LiveData;
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

    @Query("UPDATE _movie SET isFav=1 WHERE id = :movieId")
    void markFavourite(int movieId);

    @Query("UPDATE _movie SET isFav=0 WHERE id = :movieId")
    void unMarkFavourite(int movieId);

    @Query("SELECT isFav FROM _movie WHERE id =:movieId")
    LiveData<Boolean> isMovieFav(int movieId);

    @Query("SELECT id FROM _movie WHERE isFav=1")
    LiveData<List<Integer>> getFavMovieIds();

    @Query("SELECT * FROM _movie WHERE isFav=1")
    LiveData<List<Movie>> getFavMovies();

//    @Query("SELECT _movie.* FROM _movie INNER JOIN _sort_order ON _movie.id=_sort_order.movieId WHERE _sort_order.sortId=:sortId ORDER BY release_date DESC")
//    List<Movie> findMovies(int sortId);

    @Query("SELECT _movie.* FROM _movie INNER JOIN _sort_order ON _movie.id=_sort_order.movieId WHERE _sort_order.sortId=:sortId ORDER BY release_date DESC")
    LiveData<List<Movie>> findMovies(int sortId);
}
