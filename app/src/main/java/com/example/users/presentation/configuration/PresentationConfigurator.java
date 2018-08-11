package com.example.users.presentation.configuration;


import com.example.users.ApplicationContext;
import com.example.users.domain.repositories.IRepoFactory;
import com.example.users.presentation.users.Presenter.IUsersPresenter;
import com.example.users.presentation.users.View.IUsersView;
import com.example.users.presentation.users.Presenter.UsersPresenter;

import javax.inject.Inject;

public class PresentationConfigurator implements IPresentationConfigurator {

    @Inject
    IRepoFactory repoFactory;


    public PresentationConfigurator() {

        ApplicationContext.getAppContext().component().inject( this );
    }

    @Override
    public void configureUsersListView( IUsersView usersView ){

        IUsersPresenter usersPresenter = new UsersPresenter( usersView, repoFactory );
        usersView.setPresenter( usersPresenter );
    }
}
