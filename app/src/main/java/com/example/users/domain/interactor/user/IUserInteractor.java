package com.example.users.domain.interactor.user;

import com.example.users.domain.models.UserModel;

import java.util.List;

import io.reactivex.Single;

public interface IUserInteractor {

    Single<List<UserModel > > getUserList();
}
