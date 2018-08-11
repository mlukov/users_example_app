package com.example.users.domain.repositories;

import android.support.annotation.Nullable;

import com.example.users.domain.models.UserModel;

import java.util.List;

public interface IUserListResponseHandler {

    void onNextList( @Nullable List<UserModel > userDataList );

    void onCompleteLoading();

    void onError();
}
