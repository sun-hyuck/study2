package com.example.coillib

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coillib.adapter.ImageListAdapter
import com.example.coillib.`interface`.UnsplashApiService
import com.example.coillib.`interface`.UnsplashPhoto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ImageListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.unsplash.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val unsplashApiService = retrofit.create(UnsplashApiService::class.java)

        val call = unsplashApiService.getRandomPhoto(count = 20)

        call.enqueue(object: Callback<List<UnsplashPhoto>> {
            override fun onResponse(call: Call<List<UnsplashPhoto>>, response: Response<List<UnsplashPhoto>>) {
                if (response.isSuccessful) {
                    val photos = response.body()
                    val dataList = photos?.mapNotNull { it.urls.regular } ?: emptyList()

                    val layoutManager = GridLayoutManager(this@MainActivity, 3)
                    adapter = ImageListAdapter(dataList)
                    recyclerView.layoutManager = layoutManager
                    recyclerView.adapter = adapter
                    }
                }

            override fun onFailure(call: Call<List<UnsplashPhoto>>, t: Throwable) {

            }

        })
    }
}