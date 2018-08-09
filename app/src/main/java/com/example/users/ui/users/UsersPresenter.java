package com.example.users.ui.users;

import com.example.users.data.UserData;

import java.util.ArrayList;
import java.util.List;

public class UsersPresenter implements IUsersPresenter {

    private List<UserData> mUserDataList = new ArrayList();
    private IUsersView mUsersView;

    public UsersPresenter( IUsersView usersView ){

        mUsersView = usersView;

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
