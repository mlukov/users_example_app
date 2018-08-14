package com.example.users.domain.interactor.user;

import com.example.users.api.model.UserApi;
import com.example.users.domain.models.UserModel;
import com.example.users.domain.repositories.user.IUserApiRepository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;

public class UserInteractor implements IUserInteractor {

    private final static String TAG = UserInteractor.class.getSimpleName();

    private IUserApiRepository mUsersApiRepo;

    @Inject
    public UserInteractor( IUserApiRepository usersApiRepo ){

        mUsersApiRepo = usersApiRepo;
    }

    @Override
    public Single< List<UserModel> > getUserList() {

        return mUsersApiRepo.getUserList().map( new Function< List< UserApi >,  List<UserModel>  >() {
            @Override
            public List<UserModel> apply( List< UserApi > userApis ) throws Exception {

                final List<UserModel> userList = new ArrayList<>();
                if( userApis == null )
                    return userList;

                for( UserApi userApi : userApis ){

                    if( userApi != null )
                        userList.add( new UserModel( userApi.getNames() ) );
                }

                return userList;
            }
        } );
    }
}
