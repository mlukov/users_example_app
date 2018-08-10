package com.example.users.presentation.users;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.users.R;
import com.example.users.data.UserData;


import java.util.List;


public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UserViewHolder> {

    private List<UserViewData> mUserDataList;

    public UsersAdapter( List<UserViewData> userDataList ){

        mUserDataList = userDataList;
    }


    public static final class UserViewHolder extends RecyclerView.ViewHolder {

        @Nullable
        private TextView mUserNamesTextView;

        void bind( @Nullable UserViewData userData ) {

            if( this.mUserNamesTextView == null )
                return;

            if( userData == null )
                mUserNamesTextView.setText( userData.getUserNames() );
            else
                mUserNamesTextView.setText( "" );
        }

        public UserViewHolder(@Nullable ViewGroup itemView) {

            super(itemView);

            if( itemView == null )
                return;

            this.mUserNamesTextView = itemView.findViewById( R.id.userNamesTextView );
        }
    }

    @NonNull
    public UserViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {

        return new UserViewHolder( parent );
    }

    public int getItemCount() {

        return mUserDataList == null ? 0 : mUserDataList.size();
    }

    public void onBindViewHolder( @NonNull UserViewHolder holder, int position ) {

        if( holder == null )
            return;

        if( mUserDataList == null || mUserDataList.size() <= position )
            holder.bind( null );
        else
            holder.bind( mUserDataList.get( position ) );
    }
}
