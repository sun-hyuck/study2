package com.example.myapplication

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClass {
    private fun getRetrofit(): Retrofit{
        return Retrofit.Builder()
            .baseUrl(BuildConfig.URL_WEATHER)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    fun getRetrofitService(): RetrofitService{
        return getRetrofit().create(RetrofitService::class.java)
    }
}