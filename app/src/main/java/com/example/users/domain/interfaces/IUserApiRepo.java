package com.example.users.domain.interfaces;

import android.support.annotation.Nullable;

import com.example.users.repos.IUserApiResultHandler;

public interface IUserApiRepo {

    void listUsers( @Nullable final IUserApiResultHandler resultHandler );
}
