package com.example.users.presentation.users.View;


import com.example.users.presentation.users.model.UserViewData;

import java.util.List;

public interface IUserListView {

    void loading( boolean isLoading );

    void onUsersLoaded( List<UserViewData> usersViewModel );

    void onUserLoadError( String errorMessage );
}
