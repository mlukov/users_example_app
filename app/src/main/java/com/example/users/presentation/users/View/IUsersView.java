package com.example.users.presentation.users.View;

import com.example.users.presentation.users.Presenter.IUsersPresenter;
import com.example.users.presentation.users.model.UsersListViewModel;


public interface IUsersView {

    void onStartLoadingUsers();

    void onUsersLoaded( UsersListViewModel usersViewModel );

    void onUserLoadError( String errorMessage );

    void setPresenter( IUsersPresenter usersPresenter );

}
