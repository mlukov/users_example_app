package com.example.users.di.module;

import android.content.Context;

import com.example.users.UsersApp;
import com.example.users.di.anotation.AppContext;
import com.example.users.domain.interactor.user.IUserInteractor;
import com.example.users.domain.interactor.user.UserInteractor;
import com.example.users.domain.repositories.user.IUserApiRepository;
import com.example.users.domain.repositories.user.UsersApiRepository;
import com.example.users.presentation.providers.IResourceProvider;
import com.example.users.presentation.providers.ResourceProvider;
import com.example.users.utils.ISchedulersProvider;
import com.example.users.utils.SchedulersProvider;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    @Provides
    @Singleton
    @AppContext
    Context providesAppContext( UsersApp repoBrowserApp ) {

        return repoBrowserApp;
    }

    @Provides
    ISchedulersProvider providesSchedulersProvider( SchedulersProvider schedulersProvider) {

        return schedulersProvider;
    }

    @Provides
    @Singleton
    Gson providesGson() {
        return new GsonBuilder().create();
    }

    @Provides
    IUserInteractor providesUserInteractor( UserInteractor userInteractor) {

        return userInteractor;
    }

    @Provides
    IUserApiRepository providesUserApiRepository( UsersApiRepository usersApiRepository) {

        return usersApiRepository;
    }
}
