package com.yahoo.r4hu7.moviesdoughnut.di;

import com.yahoo.r4hu7.moviesdoughnut.data.MoviesRepository;
import com.yahoo.r4hu7.moviesdoughnut.di.module.RepositoryModule;

import dagger.Component;

@Component(modules = {RepositoryModule.class})
public interface RepositoryComponent {
    MoviesRepository getMoviesRepository();
}
