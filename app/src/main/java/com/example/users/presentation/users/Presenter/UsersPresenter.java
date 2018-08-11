package com.example.users.presentation.users.Presenter;

import android.support.annotation.Nullable;

import com.example.users.R;
import com.example.users.domain.repositories.IRepoFactory;
import com.example.users.domain.models.UserModel;
import com.example.users.domain.repositories.IUserApiRepo;
import com.example.users.domain.repositories.IUserListResponseHandler;
import com.example.users.domain.repositories.IDeviceRepo;
import com.example.users.domain.repositories.IResourceRepo;
import com.example.users.presentation.users.View.IUsersView;
import com.example.users.presentation.users.model.UserViewData;
import com.example.users.presentation.users.model.UsersListViewModel;

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

    private UsersListViewModel mUsersViewModel = new UsersListViewModel(  new ArrayList() );

    private IUsersView mUsersView;
    private IRepoFactory mRepoFactory;

    private boolean mViewShown = false;
    private LoadState mLoadState;
    private String mErrorMessage;

    @Nullable
    private Disposable mLoadUsersDisposable = null;

    public UsersPresenter( IUsersView usersView, IRepoFactory repoFactory ){

        assert usersView != null && repoFactory != null;

        mUsersView = usersView;
        mRepoFactory = repoFactory;

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
                mUsersView.onUsersLoaded( mUsersViewModel );
                break;

            case Error:
                mUsersView.onUserLoadError( mErrorMessage );
                break;
        }
    }

    @Override
    public void onRefresh() {

        startLoadList();
    }

    @Override
    public void onViewHidden() {

        mViewShown = false;
    }

    @Override
    public void onViewDestroyed() {

        if( mLoadUsersDisposable != null )
            mLoadUsersDisposable.dispose();

        if( mUsersViewModel != null )
            mUsersViewModel.getUsersList().clear();

        mLoadUsersDisposable = null;
        mUsersViewModel = null;
    }

    private void startLoadList(){

        mLoadState = LoadState.Loading;

        if( mUsersViewModel == null )
            mUsersViewModel = new UsersListViewModel( new ArrayList() );

        mUsersViewModel.getUsersList().clear();

        IUserApiRepo userApiRepo = mRepoFactory.getRepository( IUserApiRepo.class );

        mLoadUsersDisposable = userApiRepo.getUserList( new IUserListResponseHandler() {
            @Override
            public void onNextList( @Nullable List< UserModel > userDataList ) {

                if( userDataList == null || mLoadState != LoadState.Loading )
                    return;

                for( UserModel userData: userDataList )
                    mUsersViewModel.getUsersList().add(new UserViewData( userData.name ));
            }

            @Override
            public void onCompleteLoading() {

                mLoadState = LoadState.Loaded;
                if( mViewShown ){
                    mUsersView.onUsersLoaded( mUsersViewModel );
                }
            }

            @Override
            public void onError() {

                if( mLoadState != LoadState.Loading )

                mLoadState = LoadState.Error;

                IDeviceRepo deviceRepo = mRepoFactory.getRepository( IDeviceRepo.class );
                IResourceRepo resourceRepo = mRepoFactory.getRepository( IResourceRepo.class );

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
