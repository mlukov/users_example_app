package com.example.users.di.component;



import com.example.users.UsersApp;
import com.example.users.di.module.ApplicationModule;
import com.example.users.di.module.DeviceModule;
import com.example.users.di.module.NetworkModule;
import com.example.users.presentation.users.di.UserListModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;

@Singleton // Constraints this component to one-per-application or unscoped bindings.
@Component(
        modules = { ApplicationModule.class,
                UserListModule.class,
                NetworkModule.class,
                DeviceModule.class
        }
)
public interface ApplicationComponent{

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(UsersApp app);
        Builder device( DeviceModule deviceModule );
        Builder network( NetworkModule networkModule );
        ApplicationComponent build();
    }

    void inject( UsersApp usersApp );
}
