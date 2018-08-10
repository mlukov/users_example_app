package com.example.users.repos;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.example.users.presentation.IDeviceRepo;

public class DeviceRepo implements IDeviceRepo {

    private static final String LOG_TAG = DeviceRepo.class.getSimpleName();
    private Context mContext;

    public DeviceRepo( Context context ) {

        mContext = context;
    }

    @Override
    public boolean isNetworkConnected( ) {

        boolean isConnected = false;

        try {

            ConnectivityManager cm = ( ConnectivityManager ) mContext.getSystemService( Context.CONNECTIVITY_SERVICE );
            NetworkInfo networkInfo = cm.getActiveNetworkInfo();
            isConnected = networkInfo != null && networkInfo.isConnected();
        }
        catch ( Exception ex ) {

            Log.e( LOG_TAG, ex.getMessage(), ex );
        }

        return isConnected;
    }
}
