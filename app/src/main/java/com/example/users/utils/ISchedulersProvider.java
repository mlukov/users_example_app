package com.example.users.utils;

import io.reactivex.Scheduler;

public interface ISchedulersProvider {

    Scheduler ioScheduler();

    Scheduler uiScheduler();

    Scheduler computationScheduler();
}