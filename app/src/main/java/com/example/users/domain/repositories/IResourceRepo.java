package com.example.users.domain.repositories;

import android.support.annotation.StringRes;

public interface IResourceRepo extends IRepo {

    String getString( @StringRes int stringResId );
}
