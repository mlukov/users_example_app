package com.example.users.presentation;


public interface IPresentationRepoFactory {

    <T extends IPresentationRepo > T getRepository( Class< T > classOfT );
}
