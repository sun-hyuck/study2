package com.example.myapplication

import com.google.gson.internal.GsonBuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClass {
    private fun getRetrofit(): Retrofit{
        return Retrofit.Builder()
            .baseUrl("https://www.data.go.kr/index.do")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    fun getRetrofitService(): RetrofitService{
        return getRetrofit().create(RetrofitService::class.java)
    }
}