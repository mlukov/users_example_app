package com.example.users.ui.users;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.users.R;
import com.example.users.data.UserData;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class UsersActivity extends AppCompatActivity implements IUsersView {

    private RecyclerView mUsersRecyclerView = null;
    private UsersAdapter mUsersAdapter = null;
    private List<UserData> mUserDataList = new ArrayList();

    private IUsersPresenter mUsersPresenter;

    //region AppCompatActivity overrides
    @Override
    public void onCreate( @Nullable Bundle savedInstanceState ) {

        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        mUsersPresenter =  new UsersPresenter( this );

        mUsersRecyclerView = findViewById( R.id.usersRecyclerView );
        mUsersAdapter = new UsersAdapter( mUserDataList );
        mUsersRecyclerView.setLayoutManager(  new LinearLayoutManager( this, RecyclerView.VERTICAL, false  ) );
    }

    @Override
    protected void onResume() {

        super.onResume();
        mUsersPresenter.onViewShown();
    }

    @Override
    public void onConfigurationChanged( Configuration newConfig ) {

        super.onConfigurationChanged( newConfig );
    }

    @Override
    protected void onPause() {

        mUsersPresenter.onViewHidden();
        super.onPause();
    }

    @Override
    protected void onDestroy() {

        mUsersPresenter.onViewDestroyed();
        super.onDestroy();
    }
    //region AppCompatActivity overrides


    //region IUsersView implementation
    @Override
    public void onStartLoadingUsers() {

    }

    @Override
    public void onUsersLoaded( List< UserData > userDataList ) {


    }

    @Override
    public void onUserLoadError( String errorMessage ) {

    }
    //endregion IUsersView implementation
}
