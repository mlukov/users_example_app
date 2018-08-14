package com.example.users.presentation.users.di;

import com.example.users.presentation.users.Presenter.IUserListPresenter;
import com.example.users.presentation.users.Presenter.UserListPresenter;
import com.example.users.presentation.users.View.IUserListView;
import com.example.users.presentation.users.View.UserListActivity;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class UserListModule {

    @ContributesAndroidInjector
    abstract UserListActivity providesUsersActivity();

    @Binds
    public abstract IUserListView bindsUserListView( UserListActivity userListActivity );

    @Binds
    public abstract IUserListPresenter bindsUserListPresenter( UserListPresenter userListPresenter );
}
