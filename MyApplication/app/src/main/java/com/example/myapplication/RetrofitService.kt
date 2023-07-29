package com.example.myapplication

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import com.example.myapplication.BuildConfig


interface RetrofitService {
    @GET(BuildConfig.API_KEY)
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