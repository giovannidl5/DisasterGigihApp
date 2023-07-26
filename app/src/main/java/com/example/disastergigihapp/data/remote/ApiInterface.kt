package com.example.disastergigihapp.data.remote

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("reports?")
    fun getRecent(@Query("timeperiod") timeperiod: Int): Call<DisasterResponse>
}