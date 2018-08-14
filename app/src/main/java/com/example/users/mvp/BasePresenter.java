package com.example.users.mvp;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class BasePresenter implements IBasePresenter {

    private final CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    @Override
    public void addDisposable( Disposable disposable ) {

        mCompositeDisposable.add( disposable );
    }

    @Override
    public void dispose() {

        mCompositeDisposable.clear();
    }
}
