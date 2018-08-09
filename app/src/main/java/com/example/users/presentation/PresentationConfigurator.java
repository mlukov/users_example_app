package com.example.users.presentation;

import com.example.users.presentation.users.IUsersPresenter;
import com.example.users.presentation.users.UsersActivity;
import com.example.users.presentation.users.UsersPresenter;

public class PresentationConfigurator implements  IPresentationConfigurator {

    @Override
    public IUsersPresenter configureUsersListView( UsersActivity usersActivity ){

        IUsersPresenter usersPresenter = new UsersPresenter( usersActivity );
        return usersPresenter;
    }
}
