package com.example.users.domain.repositories;


public interface IRepoFactory {

    <T extends IRepo > T getRepository( Class<T> classOfT );
}
