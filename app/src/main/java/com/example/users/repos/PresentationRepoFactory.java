package com.example.users.repos;

import android.content.Context;
import android.support.annotation.Nullable;

import com.example.users.presentation.IDeviceRepo;
import com.example.users.presentation.IPresentationRepo;
import com.example.users.presentation.IPresentationRepoFactory;
import com.example.users.presentation.IResourceRepo;

public class PresentationRepoFactory implements IPresentationRepoFactory {

    private Context mContext;

    public PresentationRepoFactory( Context context ) {

        mContext = context;
    }

    @Nullable
    @Override
    public < T extends IPresentationRepo > T getRepository( Class< T > classOfT ) {

        T repository = null;

        if( IDeviceRepo.class.equals( classOfT ) )
            repository = (T) new DeviceRepo(mContext);
        else if( IResourceRepo.class.equals( classOfT ) )
            repository = (T) new ResourceRepo(mContext);

        return repository;
    }
}
