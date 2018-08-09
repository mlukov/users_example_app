package com.example.users.presentation.users;

import android.content.Context;

import com.example.users.data.UserData;
import com.example.users.domain.user.IUserLogic;

import java.util.ArrayList;
import java.util.List;

public class UsersPresenter implements IUsersPresenter {

    private final static String TAG = UsersPresenter.class.getSimpleName();

    private List<UserData> mUserDataList = new ArrayList();

    private IUsersView mUsersView;
    private IUserLogic mUserLogic;

    public UsersPresenter( IUsersView usersView, IUserLogic userLogic ){

        mUsersView = usersView;
        mUserLogic = userLogic;
    }

    @Override
    public void onViewCreated() {


    }

    @Override
    public void onViewShown() {

    }

    @Override
    public void onViewHidden() {

    }

    @Override
    public void onViewDestroyed() {

    }
}
