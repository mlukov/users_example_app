package com.example.users.presentation.users;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.users.ApplicationContext;
import com.example.users.R;
import com.example.users.presentation.configuration.IPresentationConfigurator;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class UsersActivity extends AppCompatActivity implements IUsersView {

    // Views
    private RecyclerView mUsersRecyclerView = null;
    private ProgressBar mProgressBar;

    private UsersAdapter mUsersAdapter = null;
    private List<UserViewData> mUserDataList = new ArrayList();

    private IUsersPresenter mUsersPresenter;

    @Inject
    IPresentationConfigurator mPresentationConfigurator;

    //region AppCompatActivity overrides
    @Override
    public void onCreate( @Nullable Bundle savedInstanceState ) {

        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        ApplicationContext.getAppContext().component().inject( this );

        mUsersRecyclerView = findViewById( R.id.usersRecyclerView );
        mProgressBar = findViewById( R.id.usersLoadProgressBar );

        mUsersAdapter = new UsersAdapter( mUserDataList );
        mUsersRecyclerView.setLayoutManager(  new LinearLayoutManager( this, RecyclerView.VERTICAL, false  ) );

        mPresentationConfigurator.configureUsersListView(this );

        mUsersPresenter.onViewCreated();
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
    public void setPresenter( IUsersPresenter usersPresenter ) {

        mUsersPresenter = usersPresenter;
    }

    @Override
    public void onStartLoadingUsers() {

        showProgressBar();
    }

    @Override
    public void onUsersLoaded( List< UserViewData > userDataList ) {

        mUserDataList.clear();

        if( userDataList == null ){

            mUsersAdapter.notifyDataSetChanged();
            return;
        }

        mUserDataList.addAll( userDataList );
        mUsersAdapter.notifyDataSetChanged();

        hideProgressBar();
    }

    @Override
    public void onUserLoadError( String errorMessage ) {

        hideProgressBar();
        Toast.makeText( this, errorMessage, Toast.LENGTH_LONG ).show();
    }
    //endregion IUsersView implementation

    private void showProgressBar(){

        mProgressBar.setVisibility( View.VISIBLE );
    }

    private void hideProgressBar(){

        mProgressBar.setVisibility( View.GONE );
    }
}
