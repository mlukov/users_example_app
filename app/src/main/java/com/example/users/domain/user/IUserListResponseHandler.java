package com.example.users.domain.user;

import android.support.annotation.Nullable;

import com.example.users.data.UserData;

import java.util.List;

public interface IUserListResponseHandler {

    void onNextList( @Nullable List<UserData> userDataList );

    void onCompleteLoading();

    void onError( @Nullable Throwable throwable );
}
