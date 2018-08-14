package com.example.users.api.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UserApi implements Serializable {

    @SerializedName( "name" )
    private String names;

    public String getNames() {

        return names;
    }
}
