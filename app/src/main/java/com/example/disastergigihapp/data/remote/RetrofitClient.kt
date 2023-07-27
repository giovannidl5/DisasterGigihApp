package com.example.disastergigihapp.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//use retrofit to get the data from petabencana.id
object RetrofitClient {
    private const val BASE_URL = "https://data.petabencana.id/"

    val instance: ApiInterface by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(ApiInterface::class.java)
    }
}