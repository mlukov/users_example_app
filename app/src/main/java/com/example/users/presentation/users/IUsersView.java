package com.example.users.presentation.users;

import com.example.users.data.UserData;

import java.util.List;

public interface IUsersView {

    void onStartLoadingUsers();

    void onUsersLoaded( List<UserViewData> userDataList );

    void onUserLoadError( String errorMessage );

    void setPresenter( IUsersPresenter usersPresenter );

}
