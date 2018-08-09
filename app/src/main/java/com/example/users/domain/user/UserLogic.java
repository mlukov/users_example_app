package com.example.users.domain.user;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.users.domain.IRepoFactory;
import java.util.List;

public class UserLogic implements IUserLogic {

    private final static String TAG = UserLogic.class.getSimpleName();

    @Nullable
    IRepoFactory mRepoFactory;


    public UserLogic( IRepoFactory repoFactory ) {

        mRepoFactory = repoFactory;
    }

    @Override
    public void getUserList( @NonNull IUserListResponseHandler responseHandler ) {

        assert responseHandler != null;

        if ( mRepoFactory == null ) {

            String message = " getUserList() mRepoFactory is null";
            Log.e( TAG, message );
            responseHandler.onError( new RuntimeException( message ) );
        }

        IUserApiRepo userApiRepo = mRepoFactory.getRepository( IUserApiRepo.class );

        if ( userApiRepo == null ) {

            String message = "getUserList() userApiRepo is null";
            Log.e( TAG, message );
            responseHandler.onError( new RuntimeException( message ) );
        }

        

    }
}
