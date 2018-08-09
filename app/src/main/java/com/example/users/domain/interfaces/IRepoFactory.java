package com.example.users.domain.interfaces;

public interface IRepoFactory {

    <T extends IRepo > T getRepository( Class<T> classOfT );
}
