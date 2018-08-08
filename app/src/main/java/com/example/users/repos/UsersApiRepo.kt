package com.example.users.repos

import android.content.Context
import com.example.users.BuildConfig
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class UsersApiRepo private constructor( context:Context? ) {

    companion object {

        private var instance: UsersApiRepo? =null

        fun getInstance( context : Context? ) : UsersApiRepo{

            if( instance == null)
                instance = UsersApiRepo( context)

            return instance!!
        }
    }

    private var retrofit : Retrofit? = null
    private var apiEndpoint :String

    init {
        apiEndpoint= BuildConfig.API_ENDPOINT
        retrofit = buildRetrofit()
    }


    private fun buildRetrofit() : Retrofit {

        val gsonBuilder = GsonBuilder()

        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
        //gsonBuilder.registerTypeAdapter( ApiErrorResponse.ApiErrorKind.class, ApiErrorResponse.ApiErrorKind.getDeserializer() );

        val gson = gsonBuilder.create()

        val client = OkHttpClient.Builder().addInterceptor { chain ->

            val newRequest = chain.request().newBuilder().build()
            chain.proceed(newRequest)
        }
                .connectTimeout( 2 , TimeUnit.MINUTES )
                .readTimeout( 2 , TimeUnit.MINUTES )
                .writeTimeout( 2 , TimeUnit.MINUTES )
                .build()

        return Retrofit.Builder()
                .baseUrl(ApiEndpoint)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build()
    }
}