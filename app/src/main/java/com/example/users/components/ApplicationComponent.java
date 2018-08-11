package com.example.users.components;


import com.example.users.ApplicationContext;
import com.example.users.domain.repositories.IRepoFactory;
import com.example.users.presentation.configuration.IPresentationConfigurator;
import com.example.users.presentation.configuration.PresentationConfigurator;
import com.example.users.presentation.users.View.UsersActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton // Constraints this component to one-per-application or unscoped bindings.
@Component(
        modules = { ApplicationModule.class }
)

public interface ApplicationComponent{

    void inject( ApplicationContext application );
    void inject( UsersActivity usersActivity );
    void inject( PresentationConfigurator presentationConfigurator );

    IRepoFactory repoFactory();
    IPresentationConfigurator presentationConfigurator();
}
