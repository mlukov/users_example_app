package com.example.users.domain.repositories;

import android.support.annotation.Nullable;

import io.reactivex.disposables.Disposable;

public interface IUserApiRepo extends IRepo {

    // callback executed on main thread
    Disposable getUserList( @Nullable final IUserListResponseHandler resultHandler );
}
