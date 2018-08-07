package com.yahoo.r4hu7.moviesdoughnut.di.module;

import com.yahoo.r4hu7.moviesdoughnut.data.MoviesRepository;
import com.yahoo.r4hu7.moviesdoughnut.data.local.LocalDataSource;
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
    public LocalDataSource localDataSource() {
        return LocalDataSource.getInstance();
    }

    @Provides
    public RemoteDataSource remoteDataSource(Endpoints endpoints) {
        return RemoteDataSource.getInstance(endpoints);
    }
}
