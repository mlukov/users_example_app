package com.example.users.domain.user;

import com.example.users.data.UserData;

import java.util.List;

import io.reactivex.disposables.Disposable;

public interface IUserLogic {

   Disposable getUserList( IUserListResponseHandler responseHandler );
}
