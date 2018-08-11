package com.example.users.repositories;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.users.BuildConfig;
import com.example.users.domain.models.UserModel;
import com.example.users.domain.repositories.IUserListResponseHandler;
import com.example.users.domain.repositories.IUserApiRepo;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
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

    //region IUserApiRepo implementation
    // callbacks executed on main thread
    @Override
    public Disposable getUserList( @NonNull final IUserListResponseHandler responseHandler ) {

        assert responseHandler != null;

        if( mRetrofit == null ){

            String message = " listUsers() mRetrofit is null";
            Log.e( TAG, message );

            if( responseHandler != null )
                responseHandler.onError();

            return null;
        }

        UsersApiController usersController = this.mRetrofit.create( UsersApiController.class );

        if( usersController == null ){

            Log.e( TAG," listUsers() usersController is null" );


            if( responseHandler != null )
                responseHandler.onError();
        }

        Call call = null;
        try{
            call = usersController.getUsers();
        }
        catch ( Exception ex ){

            Log.e( TAG, ex.getMessage(), ex );
        }

        if( call == null ){

            Log.e( TAG," listUsers() call is null" );

            if( responseHandler != null )
                responseHandler.onError();
        }

        final Call callFinal = call;


        Observable observable = Observable.create( new ObservableOnSubscribe<List<UserModel >>() {

            @Override
            public void subscribe( final ObservableEmitter< List< UserModel > > e ) throws Exception {

                callFinal.enqueue( new Callback<List<UserModel >>() {

                    public void onFailure( @Nullable Call call, @Nullable Throwable throwable) {

                        e.onError( throwable );
                    }

                    public void onResponse( @Nullable Call<List<UserModel >> call, @Nullable Response<List<UserModel >> response) {

                        e.onNext( response.body() );
                        e.onComplete();
                    }
                });
            }
        } ) ;

        return observable.subscribeOn( Schedulers.io() )
                .observeOn( AndroidSchedulers.mainThread() )
                .subscribe( new Consumer< List< UserModel > >() {
                                @Override
                                public void accept( List< UserModel > data ) throws Exception {

                                    responseHandler.onNextList( data );
                                }
                            },
                        new Consumer< Throwable >() {
                            @Override
                            public void accept( Throwable throwable ) throws Exception {


                                Log.e( TAG, throwable.getMessage(), throwable );
                                responseHandler.onError();
                            }
                        },
                        new Action() {
                            @Override
                            public void run() throws Exception {

                                responseHandler.onCompleteLoading();
                            }
                        } );
    }
    //endregion IUserApiRepo implementation


    private final Retrofit buildRetrofit() {

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setFieldNamingPolicy( FieldNamingPolicy.IDENTITY);
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
