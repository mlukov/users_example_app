package com.example.users.components;

import com.example.users.ApplicationContext;
import com.example.users.domain.repositories.IRepoFactory;
import com.example.users.presentation.configuration.IPresentationConfigurator;
import com.example.users.presentation.configuration.PresentationConfigurator;
import com.example.users.repositories.RepoFactory;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    private final ApplicationContext mApplication;

    public ApplicationModule(ApplicationContext application) {

        this.mApplication = application;
    }

    @Provides
    @Singleton
    IRepoFactory repoFactory(){

        return new RepoFactory( mApplication );
    }

    @Provides
    @Singleton
    IPresentationConfigurator presentationConfigurator(){

        return new PresentationConfigurator();
    }
}
