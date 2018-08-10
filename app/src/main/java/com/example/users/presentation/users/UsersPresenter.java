package com.example.users.presentation.users;

import android.support.annotation.Nullable;

import com.example.users.R;
import com.example.users.data.UserData;
import com.example.users.domain.user.IUserListResponseHandler;
import com.example.users.domain.user.IUserLogic;
import com.example.users.presentation.IDeviceRepo;
import com.example.users.presentation.IPresentationRepoFactory;
import com.example.users.presentation.IResourceRepo;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;

public class UsersPresenter implements IUsersPresenter {

    enum LoadState{
        Idle,
        Loading,
        Error,
        Loaded
    }

    private final static String TAG = UsersPresenter.class.getSimpleName();

    private List<UserViewData> mUserDataList = new ArrayList();

    private IUsersView mUsersView;
    private IUserLogic mUserLogic;
    private IPresentationRepoFactory mPresentationRepoFactory;

    private boolean mViewShown = false;
    private LoadState mLoadState;
    private String mErrorMessage;

    @Nullable
    private Disposable mLoadUsersDisposable = null;

    public UsersPresenter( IUsersView usersView, IUserLogic userLogic, IPresentationRepoFactory presentationRepoFactory ){

        assert userLogic != null && usersView != null && presentationRepoFactory != null;

        mUsersView = usersView;
        mUserLogic = userLogic;
        mPresentationRepoFactory = presentationRepoFactory;

        mLoadState = LoadState.Idle;
    }

    @Override
    public void onViewCreated() {

        startLoadList();
    }

    @Override
    public void onViewShown() {

        mViewShown = true;
        switch ( mLoadState ){

            case Loading:
                mUsersView.onStartLoadingUsers();
                break;

            case Loaded:
                mUsersView.onUsersLoaded( mUserDataList );
                break;

            case Error:
                mUsersView.onUserLoadError( mErrorMessage );
                break;
        }
    }

    @Override
    public void onViewHidden() {

        mViewShown = false;
    }

    @Override
    public void onViewDestroyed() {

        if( mLoadUsersDisposable != null )
            mLoadUsersDisposable.dispose();

        if( mUserDataList != null )
            mUserDataList.clear();

        mLoadUsersDisposable = null;
        mUserLogic = null;
        mUsersView = null;
        mUserDataList = null;
    }

    private void startLoadList(){

        mLoadState = LoadState.Loading;

        if( mUserDataList == null )
            mUserDataList = new ArrayList();

        mUserDataList.clear();

        mLoadUsersDisposable = mUserLogic.getUserList( new IUserListResponseHandler() {
            @Override
            public void onNextList( @Nullable List< UserData > userDataList ) {

                for( UserData userData: userDataList )
                    mUserDataList.add(new UserViewData( userData.Name ));
            }

            @Override
            public void onCompleteLoading() {

                mLoadState = LoadState.Loaded;
                if( mViewShown ){
                    mUsersView.onUsersLoaded( mUserDataList );
                }
            }

            @Override
            public void onError( @Nullable Throwable throwable ) {

                mLoadState = LoadState.Error;

                IDeviceRepo deviceRepo = mPresentationRepoFactory.getRepository( IDeviceRepo.class );
                IResourceRepo resourceRepo = mPresentationRepoFactory.getRepository( IResourceRepo.class );

                if( deviceRepo.isNetworkConnected() == false )
                    mErrorMessage = resourceRepo.getString( R.string.internet_connection_offline );
                else
                    mErrorMessage = resourceRepo.getString( R.string.oops_went_wrong_try );

                if( mViewShown ){

                    mUsersView.onUserLoadError( mErrorMessage );
                }
            }
        } );
    }
}
