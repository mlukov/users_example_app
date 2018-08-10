package com.example.users.presentation.configuration;

import com.example.users.presentation.users.IUsersPresenter;
import com.example.users.presentation.users.IUsersView;
import com.example.users.presentation.users.UsersActivity;

public interface IPresentationConfigurator {

    void configureUsersListView( IUsersView usersView );
}
