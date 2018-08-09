package com.example.users.repos;

import android.support.annotation.Nullable;

import com.example.users.data.UserData;



import java.util.List;

public interface IUserApiResultHandler {

    void onResult( @Nullable List<UserData> userDataList );

    void onError( @Nullable Throwable throwable );
}
