package com.yahoo.r4hu7.moviesdoughnut.di.module;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.Endpoints;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.LiveDataCallAdapterFactory;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.TmdbConst;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(includes = {OkHttpClientModule.class})
public class RetrofitModule {

    @Provides
    public Endpoints endpoints(Retrofit retrofit) {
        return retrofit.create(Endpoints.class);
    }

    @Provides
    public Retrofit retrofit(OkHttpClient okHttpClient, GsonConverterFactory gsonConverterFactory) {
        return new Retrofit.Builder()
                .baseUrl(TmdbConst.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(gsonConverterFactory)
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                .build();
    }

    @Provides
    public Gson gson() {
        return new GsonBuilder().create();
    }

    @Provides
    public GsonConverterFactory gsonConverterFactory(Gson gson) {
        return GsonConverterFactory.create(gson);
    }
}
