package com.example.users.domain.repositories.user;

import android.support.annotation.Nullable;

import com.example.users.api.model.UserApi;
import com.example.users.domain.models.UserModel;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.disposables.Disposable;

public interface IUserApiRepository {

    // callback executed on main thread
    Single<List<UserApi> > getUserList();
}
