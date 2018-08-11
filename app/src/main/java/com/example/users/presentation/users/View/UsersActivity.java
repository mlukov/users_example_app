package com.example.users.presentation.users.View;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.users.ApplicationContext;
import com.example.users.R;
import com.example.users.presentation.configuration.IPresentationConfigurator;
import com.example.users.presentation.users.Presenter.IUsersPresenter;
import com.example.users.presentation.users.model.UserViewData;
import com.example.users.presentation.users.model.UsersListViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class UsersActivity extends AppCompatActivity implements IUsersView {

    // Views
    private RecyclerView mUsersRecyclerView = null;
    private SwipeRefreshLayout mUsersSwipeRefreshLayout;

    private UsersAdapter mUsersAdapter = null;
    private List<UserViewData > mUserDataList = new ArrayList();

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
        mUsersSwipeRefreshLayout = findViewById( R.id.usersSwipeRefreshLayout );

        mUsersAdapter = new UsersAdapter( mUserDataList );

        initUsersListView();

        mPresentationConfigurator.configureUsersListView(this );

        mUsersPresenter.onViewCreated();

        mUsersSwipeRefreshLayout.setColorSchemeResources( android.R.color.holo_orange_dark );

        mUsersSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                if( mUsersPresenter != null )
                    mUsersPresenter.onRefresh();
            }
        });
    }

    @Override
    protected void onResume() {

        super.onResume();
        mUsersPresenter.onViewShown();
    }

    @Override
    public void onConfigurationChanged( Configuration newConfig ) {

        super.onConfigurationChanged( newConfig );

        initUsersListView();
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
    public void onUsersLoaded( UsersListViewModel usersListViewModel ) {

        mUserDataList.clear();

        if( usersListViewModel == null ){

            mUsersAdapter.notifyDataSetChanged();
            return;
        }

        mUserDataList.addAll( usersListViewModel.getUsersList() );
        mUsersAdapter.notifyDataSetChanged();

        hideProgressBar();
    }

    @Override
    public void onUserLoadError( String errorMessage ) {

        hideProgressBar();
        Toast.makeText( this, errorMessage, Toast.LENGTH_LONG ).show();
    }
    //endregion IUsersView implementation

    private void initUsersListView() {

        boolean landscape = this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
        int columns = getResources().getInteger( R.integer.users_portrait_columns );
        if( landscape)
            columns = getResources().getInteger( R.integer.users_landscape_columns );

        mUsersRecyclerView.setLayoutManager(  new GridLayoutManager( this, columns, RecyclerView.VERTICAL, false  ) );
        mUsersRecyclerView.setAdapter( mUsersAdapter );

    }

    private void showProgressBar(){

        mUsersSwipeRefreshLayout.setRefreshing( true );
    }

    private void hideProgressBar(){

        mUsersSwipeRefreshLayout.setRefreshing( false );
    }
}
