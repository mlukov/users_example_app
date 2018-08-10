package com.example.users;

import android.app.Application;

import com.example.users.components.ApplicationComponent;
import com.example.users.components.ApplicationModule;
import com.example.users.components.DaggerApplicationComponent;

public class ApplicationContext extends Application {

    private static final String TAG = ApplicationContext.class.getSimpleName();

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {

        super.onCreate();

        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule( new ApplicationModule( this ) )
                .build();

        this.applicationComponent.inject( this );

    }
}
