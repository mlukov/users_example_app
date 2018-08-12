package com.example.users.domain.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UserModel implements Serializable {

    @SerializedName( "name" )
    public String name;

    public UserModel( String name ) {

        this.name = name;
    }
}
