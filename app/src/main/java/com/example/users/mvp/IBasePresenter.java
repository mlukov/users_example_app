package com.example.users.mvp;

import io.reactivex.disposables.Disposable;

public interface IBasePresenter {

    void addDisposable( Disposable disposable );

    void dispose();

}
