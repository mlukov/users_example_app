package com.example.users.di.module;

import com.example.users.BuildConfig;
import com.example.users.api.UsersApiController;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.annotations.NonNull;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {

    private final File mCacheFile;

    public NetworkModule( @NonNull File cacheFile ) {

        mCacheFile = cacheFile;
    }

    @Provides
    @Singleton
    OkHttpClient providesOkHttpClient(){

        Cache cache = null;

        try {

            cache = new Cache( mCacheFile, 10 * 1024 * 1024);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return new OkHttpClient.Builder()
                .connectTimeout(2L, TimeUnit.MINUTES)
                .readTimeout(1L, TimeUnit.MINUTES)
                .writeTimeout(1L, TimeUnit.MINUTES)
                .cache( cache )
                .build();
    }

    @Provides
    @Singleton
    UsersApiController providesUsersApiController( OkHttpClient okHttpClient ){

        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl( BuildConfig.API_ENDPOINT )
                .addConverterFactory( GsonConverterFactory.create() )
                .addCallAdapterFactory( RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();

        return retrofit.create( UsersApiController.class );
    }
}
