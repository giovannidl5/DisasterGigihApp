package com.example.disastergigihapp.data.remote

import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {
    @GET("reports")
    fun getStatus(): Call<DisasterResponse>
}