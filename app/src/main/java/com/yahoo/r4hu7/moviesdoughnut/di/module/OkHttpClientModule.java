package com.yahoo.r4hu7.moviesdoughnut.di.module;

import android.content.Context;

import com.yahoo.r4hu7.moviesdoughnut.R;

import java.io.File;
import java.util.concurrent.TimeUnit;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;

@Module(includes = {ContextModule.class})
public class OkHttpClientModule {

    @Provides
    public OkHttpClient okHttpClient(Cache cache, Interceptor interceptor) {
        return new OkHttpClient.Builder()
                .connectTimeout(7, TimeUnit.SECONDS)
                .readTimeout(12, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .addInterceptor(interceptor)
                .cache(cache)
                .build();
    }

    @Provides
    public Cache cache(File cacheFile) {
        return new Cache(cacheFile, 100 * 1024 * 1024);
    }

    @Provides
    public File file(Context context) {
        File file = new File(context.getCacheDir(), "WebCache");
        file.mkdirs();
        return file;
    }

    @Provides
    public Interceptor interceptor(String api_key) {
        return chain -> {
            Request request = chain.request();
            HttpUrl url = request.url();

            Headers headers = request.headers().newBuilder()

                    .add("Connection", "keep-alive")
                    .add("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                    .add("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.99 Safari/537.36")
                    .add("Accept-Language", "en-US,en;q=0.9,en-GB;q=0.8")
                    .build();

            HttpUrl newUrl = url.newBuilder().addQueryParameter("api_key", api_key).build();

            request = request.newBuilder().build();

            request = request.newBuilder().url(newUrl).headers(headers).build();
            return chain.proceed(request);
        };
    }

    @Provides
    public String getApiKey(Context context) {
        return context.getString(R.string.api_key);
    }
}
