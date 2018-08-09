package com.example.users.presentation;

import com.example.users.presentation.users.IUsersPresenter;
import com.example.users.presentation.users.UsersActivity;

public interface IPresentationConfigurator {

    IUsersPresenter configureUsersListView( UsersActivity usersActivity );
}
