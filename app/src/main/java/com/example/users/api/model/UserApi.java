package com.example.users.api.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UserApi implements Serializable {

    @SerializedName( "name" )
    public String name;

    public String getNames() {

        return name;
    }

    public void setNames( String names ) {

        this.name = names;
    }
}
