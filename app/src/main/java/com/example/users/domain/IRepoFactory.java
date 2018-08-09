package com.example.users.domain;

public interface IRepoFactory {

    <T extends IRepo > T getRepository( Class<T> classOfT );
}
