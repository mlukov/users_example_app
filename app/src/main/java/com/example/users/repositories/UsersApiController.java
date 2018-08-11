package com.example.users.repositories;

import com.example.users.domain.models.UserModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface UsersApiController {

    @GET("/users")
    Call<List<UserModel > > getUsers();
}
