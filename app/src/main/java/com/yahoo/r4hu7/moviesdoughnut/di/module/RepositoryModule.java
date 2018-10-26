package com.yahoo.r4hu7.moviesdoughnut.di.module;

import android.content.Context;

import com.yahoo.r4hu7.moviesdoughnut.data.MoviesRepository;
import com.yahoo.r4hu7.moviesdoughnut.data.local.LocalDataSource;
import com.yahoo.r4hu7.moviesdoughnut.data.local.MoviesDatabase;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.Endpoints;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.RemoteDataSource;
import com.yahoo.r4hu7.moviesdoughnut.util.AppExecutors;

import dagger.Module;
import dagger.Provides;

@Module(includes = {RetrofitModule.class})
public class RepositoryModule {
    @Provides
    public MoviesRepository moviesRepository(LocalDataSource localDataSource, RemoteDataSource remoteDataSource, AppExecutors appExecutors) {
        return MoviesRepository.getInstance(localDataSource, remoteDataSource, appExecutors);
    }

    @Provides
    public LocalDataSource localDataSource(AppExecutors appExecutors, MoviesDatabase database) {
        return LocalDataSource.getInstance(appExecutors, database);
    }

    @Provides
    public RemoteDataSource remoteDataSource(Endpoints endpoints) {
        return RemoteDataSource.getInstance(endpoints);
    }

    @Provides
    public MoviesDatabase moviesDatabase(Context context) {
        return MoviesDatabase.getInstance(context);
    }

    @Provides
    public AppExecutors appExecutors() {
        return new AppExecutors();
    }
}
