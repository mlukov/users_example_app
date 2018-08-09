package com.example.users.domain.user;

import com.example.users.data.UserData;

import java.util.List;

public interface IUserLogic {

   void getUserList( IUserListResponseHandler responseHandler );
}
