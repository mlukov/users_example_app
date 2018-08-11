package com.example.users.repositories;

import android.content.Context;
import android.support.annotation.Nullable;

import com.example.users.domain.repositories.IRepo;
import com.example.users.domain.repositories.IRepoFactory;
import com.example.users.domain.repositories.IUserApiRepo;
import com.example.users.domain.repositories.IDeviceRepo;
import com.example.users.domain.repositories.IResourceRepo;

public class RepoFactory implements IRepoFactory {

    private Context mContext;

    public RepoFactory( Context context ) {

        mContext = context;
    }

    @Override
    @Nullable
    public < T extends IRepo > T getRepository( Class< T > classOfT ) {

        T repository = null;

        if( IUserApiRepo.class.equals( classOfT ) )
            repository = (T) new UsersApiRepo();
        if( IDeviceRepo.class.equals( classOfT ) )
            repository = (T) new DeviceRepo(mContext);
        else if( IResourceRepo.class.equals( classOfT ) )
            repository = (T) new ResourceRepo(mContext);

        return repository;
    }
}
