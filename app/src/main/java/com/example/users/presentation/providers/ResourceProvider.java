package com.example.users.presentation.providers;

import android.content.Context;

import javax.inject.Inject;

public class ResourceProvider implements IResourceProvider {

    private final Context mContext;

    @Inject
    public ResourceProvider( Context context ) {

        assert context != null;
        mContext = context;
    }

    @Override
    public String getString( int stringResId ) {

        return mContext.getString( stringResId );
    }
}
