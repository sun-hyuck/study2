package com.example.myapplication

import com.google.gson.internal.GsonBuildConfig
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {

    @GET(BuildConfig.5c75c2341da662502cddf22fa9c2f599)

    fun getWeather(
        @Query("numOfRows") num_of_rows: Int,
        @Query("pageNo") page_no: Int,
        @Query("dataType") data_type: String,
        @Query("base_date") base_date: String,
        @Query("base_time") base_time: String,
        @Query("nx") nx: Int,
        @Query("ny") ny: Int
    ): Call<WEATHER>
}