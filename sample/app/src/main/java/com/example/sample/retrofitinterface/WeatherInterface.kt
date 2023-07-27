package com.example.sample.retrofitinterface

import android.support.v4.app.INotificationSideChannel
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherInterface {
    @GET("getUltraSrtFcst?serviceKey=4scTVD28holis07VzuglmIvLbvK3WwZDWZP4LO%2F3bkOtILz9zYNk%2FDd7T1fd69Y%2BfHqTJKqsG3E%2Bc6rvAZ37Bw%3D%3D")

    fun GetWeather(@Query("numOfRows")num_of_rows : Int,
                   @Query("pageNo") page_no : Int,
                   @Query("dataType") data_type : String,
                   @Query("base_date") base_date : String,
                   @Query("base_time") base_time : String,
                   @Query("nx") nx : String,
                   @Query("ny") ny : String)
        :Call<WEATHER>
}

data class WEATHER (val response : RESPONSE)
data class RESPONSE (val header : HEADER, val body : BODY)
data class HEADER (val resultCode : Int, val resultMsg : String)
data class BODY (val dataType : String, val items : ITEMS, val totalCount : Int)
data class ITEMS(val item : List<ITEM>)
data class ITEM(val category : String, val fcstDate : String, val fcstTime : String, val fcstValue : String)

private val retrofit = Retrofit.Builder()
    .baseUrl("http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

object ApiObject {
    val retrofitService: WeatherInterface by lazy{
        retrofit.create(WeatherInterface::class.java)
    }
}