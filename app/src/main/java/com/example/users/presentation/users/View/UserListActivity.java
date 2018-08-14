package com.example.users.presentation.users.View;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.users.R;
import com.example.users.presentation.users.Presenter.IUserListPresenter;
import com.example.users.presentation.users.model.UserViewData;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class UserListActivity extends AppCompatActivity implements IUserListView {

    // Views
    private RecyclerView mUsersRecyclerView = null;
    private SwipeRefreshLayout mUsersSwipeRefreshLayout;
    private TextView mEmptyListText;

    private UsersAdapter mUsersAdapter = null;
    private List<UserViewData > mUserDataList = new ArrayList();

    @Inject
    IUserListPresenter mUsersPresenter;

    //region AppCompatActivity overrides
    @Override
    public void onCreate( @Nullable Bundle savedInstanceState ) {

        AndroidInjection.inject(this);
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        mUsersRecyclerView = findViewById( R.id.usersRecyclerView );
        mUsersSwipeRefreshLayout = findViewById( R.id.usersSwipeRefreshLayout );
        mEmptyListText = findViewById( R.id.emptyListText );

        mUsersAdapter = new UsersAdapter( mUserDataList );
        initUsersListView();

        mEmptyListText.setVisibility( View.GONE );

        AndroidInjection.inject(this);

        mUsersSwipeRefreshLayout.setColorSchemeResources( android.R.color.holo_orange_dark );
        mUsersSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                mUsersPresenter.loadUserList();
            }
        });
    }

    @Override
    protected void onResume() {

        super.onResume();
        mUsersPresenter.loadUserList();
    }

    @Override
    public void onConfigurationChanged( Configuration newConfig ) {

        super.onConfigurationChanged( newConfig );

        initUsersListView();
    }

    @Override
    protected void onPause() {

        mUsersPresenter.dispose();
        super.onPause();
    }
    //region AppCompatActivity overrides


    //region IUserListView implementation

    @Override
    public void loading( boolean isLoading ) {

        mUsersSwipeRefreshLayout.setRefreshing( isLoading );
    }

    @Override
    public void onUsersLoaded( List<UserViewData> userViewDataList ) {

        mUserDataList.clear();

        if( userViewDataList == null ){

            mUsersAdapter.notifyDataSetChanged();
            return;
        }

        mUserDataList.addAll( userViewDataList );
        mUsersAdapter.notifyDataSetChanged();

        boolean listIsEmpty = userViewDataList.size() == 0;
        mEmptyListText.setVisibility( listIsEmpty ? View.VISIBLE : View.GONE );
    }

    @Override
    public void onUserLoadError( String errorMessage ) {

        mUserDataList.clear();
        mUsersAdapter.notifyDataSetChanged();
        mEmptyListText.setVisibility( View.VISIBLE );

        Toast.makeText( this, errorMessage, Toast.LENGTH_LONG ).show();
    }
    //endregion IUserListView implementation

    private void initUsersListView() {

        boolean landscape = this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
        int columns = getResources().getInteger( R.integer.users_portrait_columns );
        if( landscape)
            columns = getResources().getInteger( R.integer.users_landscape_columns );

        mUsersRecyclerView.setLayoutManager(  new GridLayoutManager( this, columns, RecyclerView.VERTICAL, false  ) );
        mUsersRecyclerView.setAdapter( mUsersAdapter );

    }

}
