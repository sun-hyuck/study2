package com.example.coillib.`interface`

import com.example.coillib.BuildConfig
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface UnsplashApiService {
   @GET("photos/random?" + "client_id=${BuildConfig.UNSPLASH_ACCESS_KEY}"
    )
   fun getRandomPhoto(@Query("count") count: Int): Call<List<UnsplashPhoto>>

}

data class UnsplashPhoto(val urls: UnsplashPhotoUrls)

data class UnsplashPhotoUrls(val regular: String)

