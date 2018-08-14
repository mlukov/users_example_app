package com.example.users.domain.repositories.user;


import com.example.users.api.UsersApiController;
import com.example.users.api.model.UserApi;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;


public class UsersApiRepository implements IUserApiRepository {

    private final static String TAG = UsersApiRepository.class.getSimpleName();

    private UsersApiController mUsersApiController;

    @Inject
    public UsersApiRepository( UsersApiController usersApiController ){

        mUsersApiController = usersApiController;
    }

    @Override
    public Single<List<UserApi>> getUserList() {

        return mUsersApiController.getUsers();
    }
}
