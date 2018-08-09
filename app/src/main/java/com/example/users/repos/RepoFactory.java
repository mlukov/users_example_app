package com.example.users.repos;

import android.support.annotation.Nullable;

import com.example.users.domain.IRepo;
import com.example.users.domain.IRepoFactory;
import com.example.users.domain.user.IUserApiRepo;

public class RepoFactory implements IRepoFactory {


    public RepoFactory() {

    }

    @Override
    @Nullable
    public < T extends IRepo > T getRepository( Class< T > classOfT ) {

        T repository = null;

        if( IUserApiRepo.class.equals( classOfT ) )
            repository = (T) new UsersApiRepo();

        return repository;
    }
}
