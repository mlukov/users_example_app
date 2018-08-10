package com.example.users.components;

import com.example.users.ApplicationContext;
import com.example.users.domain.IRepoFactory;
import com.example.users.presentation.IPresentationRepoFactory;
import com.example.users.presentation.configuration.IPresentationConfigurator;
import com.example.users.presentation.configuration.PresentationConfigurator;
import com.example.users.repos.PresentationRepoFactory;
import com.example.users.repos.RepoFactory;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    private final ApplicationContext application;

    public ApplicationModule(ApplicationContext application) {

        this.application = application;
    }

    @Provides
    @Singleton
    IRepoFactory repoFactory(){

        return new RepoFactory();
    }

    @Provides
    @Singleton
    IPresentationRepoFactory presentationRepoFactory() {

        return new PresentationRepoFactory( application );
    }

    @Provides
    @Singleton
    IPresentationConfigurator presentationConfigurator(){

        return new PresentationConfigurator();
    }


}
