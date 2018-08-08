package com.example.users

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    var usersRecyclerView : RecyclerView? = null

    override fun onCreate(savedInstanceState : Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        usersRecyclerView = findViewById(R.id.usersRecyclerView)
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}
