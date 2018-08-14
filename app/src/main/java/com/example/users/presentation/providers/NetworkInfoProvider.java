package com.example.users.presentation.providers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import javax.inject.Inject;

public class NetworkInfoProvider implements INetworkInfoProvider {

    private static final String LOG_TAG = NetworkInfoProvider.class.getSimpleName();

    private final Context mContext;

    @Inject
    public NetworkInfoProvider( Context context ) {

        assert context != null;
        mContext = context;
    }

    @Override
    public boolean isNetworkConnected() {

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
