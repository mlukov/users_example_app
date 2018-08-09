package com.example.users.repos;

import com.example.users.data.UserData;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface UsersApiController {

    @GET("/Users")
    Call<List<UserData > > getUsers();
}
