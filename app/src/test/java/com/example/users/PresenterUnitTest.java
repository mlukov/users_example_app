package com.example.users;

import com.example.users.domain.models.UserModel;
import com.example.users.domain.repositories.IDeviceRepo;
import com.example.users.domain.repositories.IRepoFactory;
import com.example.users.domain.repositories.IResourceRepo;
import com.example.users.domain.repositories.IUserApiRepo;
import com.example.users.domain.repositories.IUserListResponseHandler;
import com.example.users.presentation.users.Presenter.UsersPresenter;
import com.example.users.presentation.users.View.IUsersView;
import com.example.users.presentation.users.model.UsersListViewModel;

import org.junit.*;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class PresenterUnitTest {

    private IRepoFactory    repoFactory;
    private IUsersView      usersView;
    private IUserApiRepo    userApiRepo;
    private IDeviceRepo     deviceRepo;
    private IResourceRepo   resourceRepo;

    private String          errorMessage = "Error";


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

    @Test
    public void test_OnViewCreated__Api_OnNext_One_onCompleteLoading(){

        initInterfaces();

        final String userNames = "John Smith";

        doAnswer( new Answer() {
            @Override
            public Object answer( InvocationOnMock invocation ) throws Throwable {

                // After finish loading users, error should not be displayed
                IUserListResponseHandler  callback = (IUserListResponseHandler) invocation.getArguments()[0];

                List<UserModel> usersList = new ArrayList<>(  );
                UserModel user = new UserModel(userNames );
                usersList.add( user );

                callback.onNextList( usersList );
                callback.onCompleteLoading();
                return null;
            }
        } ).when( userApiRepo ).getUserList( any( IUserListResponseHandler.class ) );

        UsersPresenter presenter = new UsersPresenter( usersView, repoFactory );

        ArgumentCaptor<UsersListViewModel> viewModelCaptor = ArgumentCaptor.forClass( UsersListViewModel.class );

        presenter.onViewCreated();
        presenter.onViewShown();

        Mockito.verify( usersView ).onUsersLoaded( viewModelCaptor.capture() );

        // List should with one user
        assertEquals( 1, viewModelCaptor.getValue().getUsersList().size() );
        // Names should be as
        assertEquals( userNames, viewModelCaptor.getValue().getUsersList().get( 0 ).getUserNames() );

        // After finish loading, errors should not be displayed
        verify( usersView, times( 0 ) ).onUserLoadError( anyString() );
    }

    @Test
    public void test_OnViewCreated__Api_OnError_OnNext_One_onCompleteLoading(){

        initInterfaces();

        doAnswer( new Answer() {
            @Override
            public Object answer( InvocationOnMock invocation ) throws Throwable {

                // After finish loading users, error should not be displayed
                IUserListResponseHandler  callback = (IUserListResponseHandler) invocation.getArguments()[0];

                List<UserModel> usersList = new ArrayList<>(  );
                UserModel user = new UserModel( "John Smith" );
                usersList.add( user );

                callback.onError();
                callback.onNextList( usersList );
                callback.onCompleteLoading();

                return null;
            }
        } ).when( userApiRepo ).getUserList( any( IUserListResponseHandler.class ) );

        UsersPresenter presenter = new UsersPresenter( usersView, repoFactory );

        ArgumentCaptor<String> errorMessageCaptor = ArgumentCaptor.forClass( String.class );

        presenter.onViewCreated();
        presenter.onViewShown();

        verify( usersView ).onUserLoadError( errorMessageCaptor.capture() );

        // One error message should be send to interface
        assertEquals( 1, errorMessageCaptor.getAllValues().size() );
        assertEquals( errorMessage, errorMessageCaptor.getValue() );

        // After error, results from api repo should not be send to view until next load
        verify( usersView, times( 0 ) ).onStartLoadingUsers();
        verify( usersView, times( 0 ) ).onUsersLoaded( any( UsersListViewModel.class ));
    }

    @Test
    public void test_OnViewCreated__Api_OnNext_Null_onCompleteLoading(){

        initInterfaces();

        doAnswer( new Answer() {
            @Override
            public Object answer( InvocationOnMock invocation ) throws Throwable {

                // After finish loading users, error should not be displayed
                IUserListResponseHandler  callback = (IUserListResponseHandler) invocation.getArguments()[0];
                callback.onNextList( null );
                callback.onCompleteLoading();
                callback.onError();
                return null;
            }
        } ).when( userApiRepo ).getUserList( any( IUserListResponseHandler.class ) );

        UsersPresenter presenter = new UsersPresenter( usersView, repoFactory );

        ArgumentCaptor<UsersListViewModel> viewModelCaptor = ArgumentCaptor.forClass( UsersListViewModel.class );

        ArgumentCaptor<String> errorMessageCaptor = ArgumentCaptor.forClass( String.class );

        presenter.onViewCreated();
        presenter.onViewShown();

        Mockito.verify( usersView ).onUsersLoaded( viewModelCaptor.capture() );

        // List should be empty, since null result is passed from repo
        assertEquals( 0, viewModelCaptor.getValue().getUsersList().size() );

        // After finish loading, errors should not be displayed
        verify( usersView, times( 0 ) ).onUserLoadError( anyString() );
    }

    @Test
    public void test_OnViewCreated__Api_OnComplete_OnNext_OnError(){

        initInterfaces();

        doAnswer( new Answer() {
            @Override
            public Object answer( InvocationOnMock invocation ) throws Throwable {

                IUserListResponseHandler  callback = (IUserListResponseHandler) invocation.getArguments()[0];
                callback.onCompleteLoading();

                List<UserModel> usersList = new ArrayList<>(  );
                UserModel user = new UserModel("John Smith");
                usersList.add( user );

                callback.onNextList( usersList );
                callback.onError();
                return null;
            }
        } ).when( userApiRepo ).getUserList( any( IUserListResponseHandler.class ) );

        UsersPresenter presenter = new UsersPresenter( usersView, repoFactory );

        presenter.onViewCreated();

        // View is not shown, all of the functions below should not be called
        verify( usersView, times( 0 ) ).onUserLoadError( anyString() );
        verify( usersView, times( 0 ) ).onUsersLoaded( any( UsersListViewModel.class ) );
        verify( usersView, times( 0 ) ).onStartLoadingUsers();
    }

    private void initInterfaces(){

        repoFactory     = mock( IRepoFactory.class );
        usersView       = mock( IUsersView.class);
        userApiRepo     = mock( IUserApiRepo.class );
        deviceRepo      = mock( IDeviceRepo.class );
        resourceRepo    = mock( IResourceRepo.class );

        when(repoFactory.getRepository( IUserApiRepo.class )).thenReturn( userApiRepo );
        when(repoFactory.getRepository( IDeviceRepo.class )).thenReturn( deviceRepo );
        when(repoFactory.getRepository( IResourceRepo.class )).thenReturn( resourceRepo );

        when( deviceRepo.isNetworkConnected()).thenReturn( false );
        when( resourceRepo.getString( any( int.class ) ) ).thenReturn( errorMessage );
    }

}
