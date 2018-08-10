package com.example.users.repos;

import android.content.Context;

import com.example.users.presentation.IResourceRepo;

public class ResourceRepo implements IResourceRepo {

    private Context mContext;

    public ResourceRepo( Context context ) {

        assert context != null;
        mContext = context;
    }

    @Override
    public String getString( int stringResId ) {

        return mContext.getString( stringResId );
    }
}
