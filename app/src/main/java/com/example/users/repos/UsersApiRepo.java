package com.example.users.repos;


import android.support.annotation.Nullable;
import android.util.Log;

import com.example.users.BuildConfig;
import com.example.users.data.UserData;
import com.example.users.domain.user.IUserApiResultHandler;
import com.example.users.domain.user.IUserApiRepo;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class UsersApiRepo implements IUserApiRepo {

    private final static String TAG = UsersApiRepo.class.getSimpleName();

    private Retrofit mRetrofit;
    private String mApiEndpoint;

    public UsersApiRepo(){

        mApiEndpoint = BuildConfig.API_ENDPOINT;
        mRetrofit = buildRetrofit();
    }

    public void listUsers( @Nullable final IUserApiResultHandler resultHandler ) {

        if( mRetrofit == null ){

            String message = " listUsers() mRetrofit is null";
            Log.e( TAG, message );

            if( resultHandler != null )
                resultHandler.onError( new RuntimeException( TAG + message ) );

            return;
        }

        UsersApiController usersController = this.mRetrofit.create( UsersApiController.class );

        if( usersController == null ){

            String message = " listUsers() usersController is null";
            Log.e( TAG, message );

            if( resultHandler != null )
                resultHandler.onError( new RuntimeException( TAG + message ) );
        }

        Call call = usersController.getUsers();
        call.enqueue( new Callback<List<UserData>>() {

            public void onFailure( @Nullable Call call, @Nullable Throwable throwable) {

                if( resultHandler == null )
                    return;

                resultHandler.onError(throwable);
            }

            public void onResponse(@Nullable Call<List<UserData>> call, @Nullable Response<List<UserData>> response) {

                if( resultHandler == null )
                    return;

                resultHandler.onResult( response.body() );
            }
        });
    }

    private final Retrofit buildRetrofit() {

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setFieldNamingPolicy( FieldNamingPolicy.UPPER_CAMEL_CASE);
        Gson gson = gsonBuilder.create();

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(2L, TimeUnit.MINUTES)
                .readTimeout(2L, TimeUnit.MINUTES)
                .writeTimeout(2L, TimeUnit.MINUTES)
                .build();

        return new Retrofit.Builder().baseUrl(mApiEndpoint)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client).build();

    }
}
