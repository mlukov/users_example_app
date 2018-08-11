package com.example.users.presentation.users.model;

import java.util.List;

public class UsersListViewModel {

    private List<UserViewData> mUsersList;

    public UsersListViewModel( List< UserViewData > usersList ) {

        mUsersList = usersList;
    }

    public List< UserViewData > getUsersList() {

        return mUsersList;
    }
}
