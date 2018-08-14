package com.example.users.utils;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SchedulersProvider implements ISchedulersProvider {

    @Inject
    public SchedulersProvider() {

    }

    @Override
    public Scheduler ioScheduler() {
        return Schedulers.io();
    }

    @Override
    public Scheduler uiScheduler() {
        return AndroidSchedulers.mainThread();
    }

    @Override
    public Scheduler computationScheduler() {
        return Schedulers.computation();
    }

}
