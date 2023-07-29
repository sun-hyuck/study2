package com.example.myapplication

import android.Manifest
import android.annotation.SuppressLint
import android.graphics.Point
import java.util.*
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import com.example.myapplication.databinding.ActivityWeatherBinding
import androidx.databinding.DataBindingUtil.setContentView
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import retrofit2.Call
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import retrofit2.Callback

class WeatherActivity : AppCompatActivity() {

    private var baseDate = "20230729"
    private var baseTime = "2330"
    private var curPoint : Point? = null


    lateinit var  binding: ActivityWeatherBinding
    @SuppressLint("SetTextI18n", "MissingPermission")
    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding = setContentView(this, R.layout.activity_weather)
        binding.weatherActivity = this

        val permissionList = arrayOf<String>(

            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
        )

        ActivityCompat.requestPermissions(this@WeatherActivity, permissionList, 1)

        binding.tvDate.text = SimpleDateFormat("MM월 dd일",Locale.getDefault()).format(Calendar.getInstance().time) + "날씨"

        requestLocation()

        binding.btnRefresh.setOnClickListener {
            requestLocation()

        }
    }

    private fun setWeather(nx : Int, ny: Int) {
        val cal = Calendar.getInstance()
        baseDate = SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(cal.time)
        val timeH = SimpleDateFormat("HH", Locale.getDefault()).format(cal.time)
        val timeM = SimpleDateFormat("HH", Locale.getDefault()).format(cal.time)

        baseTime = Common().getBaseTime(timeH, timeM)

        if (timeH == "00"&& baseTime == "2330") {
            cal.add(Calendar.DATE, -1).toString()
            baseDate = SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(cal.time)
        }

        val call = RetrofitClass.getRetrofitService().getWeather(60, 1, "JSON", baseDate, baseTime, nx, ny)

        call.enqueue(object : retrofit2.Callback<WEATHER> {
            override fun onResponse(call: Call<WEATHER>, response: Response<WEATHER>) {
                if (response.isSuccessful) {
                    val it: List<ITEM> = response.body()!!.response.body.items.item

                    val weatherArr = arrayOf(
                        ModelWeather(),
                        ModelWeather(),
                        ModelWeather(),
                        ModelWeather(),
                        ModelWeather(),
                        ModelWeather()
                    )

                    var index = 0
                    val totalCount = response.body()!!.response.body.totalCount - 1
                    for (i in 0..totalCount) {
                        index %= 6
                        when (it[i].category) {
                            "PTY" -> weatherArr[index].rainType = it[i].fcstValue
                            "REH" -> weatherArr[index].humidity = it[i].fcstValue
                            "SKY" -> weatherArr[index].sky = it[i].fcstValue
                            "T1H" -> weatherArr[index].temp = it[i].fcstValue
                            else -> continue
                        }
                        index++
                    }

                    weatherArr[0].fcstTime = "지금"

                    for (i in 1..5) weatherArr[i].fcstTime = it[i].fcstTime

                    binding.weatherRecyclerView.adapter = WeatherAdapter(weatherArr)

                    Toast.makeText(
                        applicationContext,
                        it[0].fcstDate + ", " + it[0].fcstTime + "의 날씨 정보입니다.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            @SuppressLint("SetTextI18n")
            override fun onFailure(call: Call<WEATHER>, t: Throwable) {

                binding.tvError.text = "api fail : " + t.message.toString() + "\n 다시 시도해주세요."
                binding.tvError.visibility = View.VISIBLE
                Log.d("api fail", t.message.toString())
            }
        })

        }

    @SuppressLint("MissingPermission")
    private fun requestLocation(){
        val locationClient = LocationServices.getFusedLocationProviderClient(this@WeatherActivity)

        try{
            val locationRequest = com.google.android.gms.location.LocationRequest.create()
            locationRequest.run {
                priority = com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY
                interval = 60 * 1000    // 요청 간격(1초)

            }
            val locationCallback = object : LocationCallback() {
                @SuppressLint("SetTextI18n")
                override fun onLocationResult(p0: LocationResult) {
                   p0.let {
                       for (location in it.locations){

                           curPoint = Common().dfsXyConv(location.latitude, location.longitude)

                           binding.tvDate.text = SimpleDateFormat("MM월 dd일", Locale.getDefault()).format(Calendar.getInstance().time) + "날씨"

                           setWeather(curPoint!!.x, curPoint!!.y)

                       }
                   }
                }
            }
            Looper.myLooper()?.let {
                locationClient.requestLocationUpdates(locationRequest, locationCallback, it)
            }

        } catch (e : SecurityException){
            e.printStackTrace()
        }
    }


}


