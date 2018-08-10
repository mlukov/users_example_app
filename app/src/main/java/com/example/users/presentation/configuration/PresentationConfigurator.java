package com.example.users.presentation.configuration;


import com.example.users.ApplicationContext;
import com.example.users.domain.IRepoFactory;
import com.example.users.domain.user.IUserLogic;
import com.example.users.domain.user.UserLogic;
import com.example.users.presentation.IPresentationRepoFactory;
import com.example.users.presentation.users.IUsersPresenter;
import com.example.users.presentation.users.IUsersView;
import com.example.users.presentation.users.UsersPresenter;

import javax.inject.Inject;

public class PresentationConfigurator implements IPresentationConfigurator {

    @Inject
    IRepoFactory repoFactory;

    @Inject
    IPresentationRepoFactory presentationRepoFactory;


    public PresentationConfigurator() {

        ApplicationContext.getAppContext().component().inject( this );
    }

    @Override
    public void configureUsersListView( IUsersView usersView ){

        IUserLogic userLogic = new UserLogic( repoFactory );
        IUsersPresenter usersPresenter = new UsersPresenter( usersView, userLogic, presentationRepoFactory );
        usersView.setPresenter( usersPresenter );
    }
}
