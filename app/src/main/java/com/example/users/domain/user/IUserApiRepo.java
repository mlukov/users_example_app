package com.example.users.domain.user;

import android.support.annotation.Nullable;

import com.example.users.domain.IRepo;
import com.example.users.repos.IUserApiResultHandler;

public interface IUserApiRepo extends IRepo {

    void listUsers( @Nullable final IUserApiResultHandler resultHandler );
}
