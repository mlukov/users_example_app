package com.example.users

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class UsersAdapter constructor ( ): RecyclerView.Adapter<UsersAdapter.UserViewHolder>() {

    class UserViewHolder( itemView: View? ): RecyclerView.ViewHolder( itemView ){

        var userNamesTextView: TextView? = null

        init {
            userNamesTextView = itemView?.findViewById( R.id.userNamesTextView)
        }

        fun bindViewHolder()
    }


    override fun onCreateViewHolder(parent : ViewGroup, viewType : Int) : UserViewHolder {


    }

    override fun getItemCount() : Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(holder : UserViewHolder, position : Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}