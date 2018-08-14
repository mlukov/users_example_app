package com.example.users.domain.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UserModel implements Serializable {

    private final String names;


    public UserModel( String names ) {

        this.names = names;
    }

    public String getNames() {

        return names;
    }
}
