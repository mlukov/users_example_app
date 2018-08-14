package com.example.users.api;

import com.example.users.api.model.UserApi;
import com.example.users.domain.models.UserModel;

import java.util.List;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface UsersApiController {

    @Headers("Cache-Control: max-age=300")
    @GET("/users")
    Single< List<UserApi> > getUsers();
}
