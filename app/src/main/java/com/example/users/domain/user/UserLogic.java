package com.example.users.domain.user;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.users.data.UserData;
import com.example.users.domain.IRepoFactory;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class UserLogic implements IUserLogic {

    private final static String TAG = UserLogic.class.getSimpleName();

    @Nullable
    IRepoFactory mRepoFactory;


    public UserLogic( IRepoFactory repoFactory ) {

        mRepoFactory = repoFactory;
    }

    @Override
    public Disposable getUserList( @NonNull final IUserListResponseHandler responseHandler ) {

        assert responseHandler != null;

        if ( mRepoFactory == null ) {

            String message = " getUserList() mRepoFactory is null";
            Log.e( TAG, message );
            responseHandler.onError( new RuntimeException( message ) );
        }

        final IUserApiRepo userApiRepo = mRepoFactory.getRepository( IUserApiRepo.class );

        if ( userApiRepo == null ) {

            String message = "getUserList() userApiRepo is null";
            Log.e( TAG, message );
            responseHandler.onError( new RuntimeException( message ) );
        }

        Observable observable = Observable.create( new ObservableOnSubscribe<List<UserData>> () {

            @Override
            public void subscribe( final ObservableEmitter< List< UserData > > e ) throws Exception {

                userApiRepo.listUsers( new IUserApiResultHandler() {
                    @Override
                    public void onResult( @Nullable List< UserData > userDataList ) {

                        e.onNext( userDataList );
                        e.onComplete();
                    }

                    @Override
                    public void onError( @Nullable Throwable throwable ) {

                        e.onError( throwable );
                    }
                } );
            }
        } ) ;

        return observable.subscribeOn( Schedulers.io() )
                .observeOn( AndroidSchedulers.mainThread() )
                .subscribe( new Consumer< List< UserData > >() {
                                @Override
                                public void accept( List< UserData > data ) throws Exception {

                                    responseHandler.onNextList( data );
                                }
                            },
                        new Consumer< Throwable >() {
                            @Override
                            public void accept( Throwable throwable ) throws Exception {

                                responseHandler.onError( throwable );
                            }
                        },
                        new Action() {
                            @Override
                            public void run() throws Exception {

                                responseHandler.onCompleteLoading();
                            }
                        } );
    }
}
