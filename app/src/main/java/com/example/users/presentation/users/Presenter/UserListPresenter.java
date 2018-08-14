package com.example.users.presentation.users.Presenter;

import android.support.annotation.Nullable;
import android.util.Log;

import com.example.users.R;
import com.example.users.domain.models.UserModel;
import com.example.users.presentation.providers.INetworkInfoProvider;
import com.example.users.presentation.providers.IResourceProvider;
import com.example.users.domain.interactor.user.IUserInteractor;
import com.example.users.mvp.BasePresenter;
import com.example.users.presentation.users.View.IUserListView;
import com.example.users.presentation.users.model.UserViewData;
import com.example.users.utils.ISchedulersProvider;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

public class UserListPresenter extends BasePresenter implements IUserListPresenter {

    private final static String TAG = UserListPresenter.class.getSimpleName();


    private final IUserListView         mUsersView;
    private final IUserInteractor       mUserInteractor;
    private final ISchedulersProvider   mSchedulersProvider;
    private final INetworkInfoProvider  mNetworkInfoProvider;
    private final IResourceProvider     mResourceProvider;

    @Inject
    public UserListPresenter( IUserListView usersView,
                              IUserInteractor userInteractor,
                              ISchedulersProvider schedulersProvider,
                              INetworkInfoProvider  networkInfoProvider,
                              IResourceProvider resourceProvider){

        assert usersView != null && userInteractor != null && schedulersProvider != null;

        mUsersView = usersView;
        mUserInteractor = userInteractor;
        mSchedulersProvider = schedulersProvider;
        mNetworkInfoProvider = networkInfoProvider;
        mResourceProvider = resourceProvider;
    }

    @Override
    public void loadUserList(){

        final Disposable disposable = mUserInteractor.getUserList()
                .map(
                        new Function< List<UserModel>, List<UserViewData> >() {

                            @Override
                            public List<UserViewData> apply( List<UserModel> userList ) {

                                final List<UserViewData> userViewDataList = new ArrayList<>();

                                if( userList == null )
                                    return userViewDataList;

                                for( UserModel userModel : userList ) {

                                    if( userModel != null)
                                        userViewDataList.add( new UserViewData( userModel.getNames() ) );
                                }

                                return userViewDataList;
                            }
                        }
                )
                .subscribeOn(  mSchedulersProvider.ioScheduler() )
                .observeOn( mSchedulersProvider.uiScheduler() )
                .subscribe( new Consumer< List< UserViewData > >() {

                                @Override
                                public void accept( List< UserViewData > userViewData ) throws Exception {

                                    mUsersView.onUsersLoaded( userViewData );
                                    mUsersView.loading( false );
                                }
                            },
                        new Consumer< Throwable >() {
                            @Override
                            public void accept( Throwable throwable ) throws Exception {

                                Log.e( TAG, throwable.getMessage(), throwable );
                                String errorMessage = mResourceProvider.getString( R.string.oops_went_wrong_try );
                                if( mNetworkInfoProvider.isNetworkConnected() == false )
                                    errorMessage = mResourceProvider.getString( R.string.internet_connection_offline );

                                mUsersView.onUserLoadError( errorMessage );
                                mUsersView.loading( false );
                            }
                        } );

        addDisposable( disposable );
    }
}
