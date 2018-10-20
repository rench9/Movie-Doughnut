package com.yahoo.r4hu7.moviesdoughnut.data.local;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;

import com.yahoo.r4hu7.moviesdoughnut.data.SortOrder;
import com.yahoo.r4hu7.moviesdoughnut.data.local.dao.CastDao;
import com.yahoo.r4hu7.moviesdoughnut.data.local.dao.ExternalLinkDao;
import com.yahoo.r4hu7.moviesdoughnut.data.local.dao.ImageDao;
import com.yahoo.r4hu7.moviesdoughnut.data.local.dao.MovieDao;
import com.yahoo.r4hu7.moviesdoughnut.data.local.dao.MovieDetailDao;
import com.yahoo.r4hu7.moviesdoughnut.data.local.dao.SortOrderDao;
import com.yahoo.r4hu7.moviesdoughnut.data.local.dao.VideoDao;
import com.yahoo.r4hu7.moviesdoughnut.data.local.entity.FavouriteEntity;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.MovieCreditsResponse;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.MovieExternalIdsResponse;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.MovieImagesResponse;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.MovieResponse;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.MovieVideosResponse;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.model.Movie;

@Database(entities = {
        Movie.class,
        MovieResponse.class,
        SortOrder.class,
        MovieImagesResponse.class,
        MovieCreditsResponse.class,
        MovieExternalIdsResponse.class,
        MovieVideosResponse.class,
        FavouriteEntity.class
}, version = 1, exportSchema = false)
public abstract class MoviesDatabase extends RoomDatabase {
    private static MoviesDatabase INSTANCE;

    public abstract MovieDao movieDao();

    public abstract MovieDetailDao movieDetailDao();

    private static final Object sLock = new Object();

    public static MoviesDatabase getInstance(Context context) {
        if (INSTANCE == null)
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), MoviesDatabase.class, "_main")
                    .addCallback(new Callback() {
                        @Override
                        public void onCreate(@NonNull SupportSQLiteDatabase db) {

                            db.execSQL("CREATE TRIGGER IF NOT EXISTS update_fav_trigger AFTER INSERT ON _movie\n" +
                                    "WHEN EXISTS (SELECT movieId FROM _favourite WHERE movieId=new.id)\n" +
                                    "BEGIN\n" +
                                    "UPDATE _movie\n" +
                                    "SET isFav = 1\n" +
                                    "WHERE id = new.id;\n" +
                                    "END;"
                            );

                            db.execSQL("CREATE TRIGGER IF NOT EXISTS insert_fav_trigger AFTER UPDATE ON _movie\n" +
                                    "WHEN new.isFav=1\n" +
                                    "BEGIN\n" +
                                    "INSERT OR IGNORE INTO _favourite VALUES(new.id);\n" +
                                    "END;");

                            db.execSQL("CREATE TRIGGER IF NOT EXISTS insert_fav_trigger AFTER UPDATE ON _movie\n" +
                                    "WHEN new.isFav=0\n" +
                                    "BEGIN\n" +
                                    "DELETE FROM _favourite WHERE movieId=new.id;\n" +
                                    "END;");

                            super.onCreate(db);
                        }
                    })
                    .build();
        return INSTANCE;
    }

    public abstract SortOrderDao sortOrderDao();

    public abstract ImageDao imageDao();

    public abstract CastDao castDao();

    public abstract ExternalLinkDao externalLinkDao();

    public abstract VideoDao videoDao();
}
