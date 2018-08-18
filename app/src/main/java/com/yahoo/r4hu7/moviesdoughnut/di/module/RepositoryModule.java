package com.yahoo.r4hu7.moviesdoughnut.di.module;

import android.content.Context;

import com.yahoo.r4hu7.moviesdoughnut.data.MoviesRepository;
import com.yahoo.r4hu7.moviesdoughnut.data.local.LocalDataSource;
import com.yahoo.r4hu7.moviesdoughnut.data.local.MoviesDatabase;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.Endpoints;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.RemoteDataSource;

import dagger.Module;
import dagger.Provides;

@Module(includes = {RetrofitModule.class})
public class RepositoryModule {
    @Provides
    public MoviesRepository moviesRepository(LocalDataSource localDataSource, RemoteDataSource remoteDataSource) {
        return MoviesRepository.getInstance(localDataSource, remoteDataSource);
    }

    @Provides
    public LocalDataSource localDataSource(MoviesDatabase database) {
        return LocalDataSource.getInstance(database);
    }

    @Provides
    public RemoteDataSource remoteDataSource(Endpoints endpoints, LocalDataSource localDataSource) {
        return RemoteDataSource.getInstance(endpoints, localDataSource);
    }

    @Provides
    public MoviesDatabase moviesDatabase(Context context) {
        return MoviesDatabase.getInstance(context);
    }
}
