package com.example.users;

import android.app.Activity;
import android.app.Application;

import com.example.users.di.component.DaggerApplicationComponent;
import com.example.users.di.module.DeviceModule;
import com.example.users.di.module.NetworkModule;

import java.io.File;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

public class UsersApp extends Application implements HasActivityInjector {

    private static final String TAG = UsersApp.class.getSimpleName();

    @Inject
    public DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

    @Override
    public void onCreate() {

        super.onCreate();

        DaggerApplicationComponent
                .builder()
                .application( this )
                .device( new DeviceModule( this ))
                .network( new NetworkModule( new File( getCacheDir(), "responses") ) )
                .build()
                .inject( this );

    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }

}
