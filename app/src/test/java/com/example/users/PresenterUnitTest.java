package com.example.users;

import com.example.users.domain.repositories.IDeviceRepo;
import com.example.users.domain.repositories.IRepo;
import com.example.users.domain.repositories.IRepoFactory;
import com.example.users.domain.repositories.IResourceRepo;
import com.example.users.domain.repositories.IUserApiRepo;
import com.example.users.domain.repositories.IUserListResponseHandler;
import com.example.users.presentation.users.Presenter.UsersPresenter;
import com.example.users.presentation.users.View.IUsersView;

import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.mockito.Mockito.*;

public class PresenterUnitTest {

    private IRepoFactory    repoFactory;
    private IUsersView      usersView;
    private IUserApiRepo    userApiRepo;
    private IDeviceRepo     deviceRepo;
    private IResourceRepo   resourceRepo;

    @Test
    public void testOnViewCreatedOnNextNull(){

        initInterfaces();

        doAnswer( new Answer() {
            @Override
            public Object answer( InvocationOnMock invocation ) throws Throwable {

                IUserListResponseHandler  callback = (IUserListResponseHandler) invocation.getArguments()[0];
                callback.onNextList( null );
                return null;
            }
        } ).when( userApiRepo ).getUserList( any( IUserListResponseHandler.class ) );

        UsersPresenter presenter = new UsersPresenter( usersView, repoFactory );

        presenter.onViewCreated();
    }


    @Test
    public void test_OnViewCreated__Api_OnComplete_OnNext_OnError(){

        initInterfaces();

        doAnswer( new Answer() {
            @Override
            public Object answer( InvocationOnMock invocation ) throws Throwable {

                IUserListResponseHandler  callback = (IUserListResponseHandler) invocation.getArguments()[0];
                callback.onCompleteLoading();
                callback.onNextList( null );
                callback.onCompleteLoading();
                return null;
            }
        } ).when( userApiRepo ).getUserList( any( IUserListResponseHandler.class ) );

        UsersPresenter presenter = new UsersPresenter( usersView, repoFactory );

        presenter.onViewCreated();
    }

    @Test
    public void test_onViewDestroyed_onViewCreated(){

        initInterfaces();

        UsersPresenter presenter = new UsersPresenter( usersView, repoFactory );

        presenter.onViewDestroyed();
        presenter.onViewCreated();
    }

    @Test
    public void test_onViewDestroyed_onViewHidden(){

        initInterfaces();

        UsersPresenter presenter = new UsersPresenter( usersView, repoFactory );

        presenter.onViewDestroyed();
        presenter.onViewHidden();
    }

    @Test
    public void test_onViewDestroyed_onViewShown(){

        initInterfaces();

        UsersPresenter presenter = new UsersPresenter( usersView, repoFactory );

        presenter.onViewDestroyed();
        presenter.onViewShown();
    }


    private void initInterfaces(){

        repoFactory    = mock( IRepoFactory.class );
        usersView        = mock( IUsersView.class);
        userApiRepo    = mock( IUserApiRepo.class );
        deviceRepo      = mock( IDeviceRepo.class );
        resourceRepo  = mock( IResourceRepo.class );

        when(repoFactory.getRepository( IUserApiRepo.class )).thenReturn( userApiRepo );
        when(repoFactory.getRepository( IDeviceRepo.class )).thenReturn( deviceRepo );
        when(repoFactory.getRepository( IResourceRepo.class )).thenReturn( resourceRepo );
    }

}
