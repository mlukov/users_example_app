package com.example.users.di.module;

import android.content.Context;

import com.example.users.presentation.providers.INetworkInfoProvider;
import com.example.users.presentation.providers.IResourceProvider;
import com.example.users.presentation.providers.NetworkInfoProvider;
import com.example.users.presentation.providers.ResourceProvider;

import dagger.Module;
import dagger.Provides;

@Module
public class DeviceModule {

    private Context mContext;

    public DeviceModule( Context context ) {

        mContext = context;
    }

    @Provides
    INetworkInfoProvider networkInfoProvider(){

        return new NetworkInfoProvider( mContext );
    }

    @Provides
    IResourceProvider resourceProvider(){

        return new ResourceProvider( mContext );
    }
}
