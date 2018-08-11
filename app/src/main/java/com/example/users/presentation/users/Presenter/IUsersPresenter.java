package com.example.users.presentation.users.Presenter;

public interface IUsersPresenter {

    void onViewCreated();

    void onViewShown();

    void onViewHidden();

    void onViewDestroyed();

    void onRefresh();
}
