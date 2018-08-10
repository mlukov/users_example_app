package com.example.users.presentation;

import android.support.annotation.StringRes;

public interface IResourceRepo extends IPresentationRepo{

    String getString( @StringRes int stringResId );
}
