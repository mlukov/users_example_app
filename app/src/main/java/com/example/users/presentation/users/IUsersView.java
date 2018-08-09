package com.example.users.presentation.users;

import com.example.users.data.UserData;

import java.util.List;

public interface IUsersView {

    void onStartLoadingUsers();

    void onUsersLoaded( List<UserData> userDataList );

    void onUserLoadError( String errorMessage );

}
