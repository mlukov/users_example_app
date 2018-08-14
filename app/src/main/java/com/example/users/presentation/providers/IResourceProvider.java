package com.example.users.presentation.providers;

import android.support.annotation.StringRes;

public interface IResourceProvider {

    String getString( @StringRes int stringResId );
}
