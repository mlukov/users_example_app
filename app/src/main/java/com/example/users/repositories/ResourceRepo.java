package com.example.users.repositories;

import android.content.Context;

import com.example.users.domain.repositories.IResourceRepo;

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
