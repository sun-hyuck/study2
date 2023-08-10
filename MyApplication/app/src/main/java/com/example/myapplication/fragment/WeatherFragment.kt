package com.example.myapplication.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.graphics.Point
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.myapplication.Common
import com.example.myapplication.ITEM
import com.example.myapplication.Location
import com.example.myapplication.ModelWeather
import com.example.myapplication.R
import com.example.myapplication.RetrofitClass
import com.example.myapplication.WEATHER
import com.example.myapplication.WeatherActivity
import com.example.myapplication.WeatherAdapter
import com.example.myapplication.databinding.ActivityWeatherBinding
import com.example.myapplication.databinding.FragmentWeatherBinding
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.OnTokenCanceledListener
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks.call
import retrofit2.Call
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class WeatherFragment: Fragment() {
    private var _binding: FragmentWeatherBinding? = null
    private val binding get() = _binding!!
    private var baseDate = "20230729"
    private var baseTime = "2330"
    private var curPoint: Point? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWeatherBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n", "MissingPermission", "CommitTransaction")
    @RequiresApi(Build.VERSION_CODES.S)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.tvDate.text = SimpleDateFormat(
            "MM월 dd일",
            Locale.getDefault()
        ).format(Calendar.getInstance().time) + "날씨"

        requestLocation()

        binding.btnRefresh.setOnClickListener {
            requestLocation()
        }
    }

        private fun setWeather(nx: Int, ny: Int) {
            val cal = Calendar.getInstance()
            baseDate = SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(cal.time)
            val timeH = SimpleDateFormat("HH", Locale.getDefault()).format(cal.time)
            val timeM = SimpleDateFormat("HH", Locale.getDefault()).format(cal.time)

            baseTime = Common().getBaseTime(timeH, timeM)

            if (timeH == "00" && baseTime == "2330") {
                cal.add(Calendar.DATE, -1).toString()
                baseDate = SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(cal.time)
            }
            val call = RetrofitClass.getRetrofitService()
                .getWeather(60, 1, "JSON", baseDate, baseTime, nx, ny)

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
                            context,
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
       private fun requestLocation()  {
//            val locationClient = LocationServices.getFusedLocationProviderClient(requireContext())
//
//            try {
//                val locationRequest =
//                    LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 100000)
//                        .build()
//
//                val currentLocation = locationClient.getCurrentLocation(
//                    Priority.PRIORITY_HIGH_ACCURACY,
//                    object : CancellationToken() {
//                        override fun onCanceledRequested(p0: OnTokenCanceledListener): CancellationToken =
//                            CancellationTokenSource().token
//
//                        override fun isCancellationRequested(): Boolean = false
//
//                    }).addOnSuccessListener {
//                    if (it == null)
//                        Toast.makeText(context, "Cannot get location.", Toast.LENGTH_SHORT).show()
//                    else {
//                        val lat = it.latitude
//                        val lon = it.longitude
//
//                        Log.d("CurrentLocation", "${lat.toInt()}, ${lon.toInt()}")
//                        setWeather(lat.toInt(), lon.toInt())
//                    }
//                    val locationCallback = object : LocationCallback() {
//                        @SuppressLint("SetTextI18n")
//                        override fun onLocationResult(p0: LocationResult) {
//                            p0.let {
//                                for (location in it.locations) {
//
//                                    curPoint =
//                                        Common().dfsXyConv(location.latitude, location.longitude)
//
//                                    Log.d("Location", "${curPoint!!.x}, ${curPoint!!.y}")
//
//                                    binding.tvDate.text = SimpleDateFormat(
//                                        "MM월 dd일",
//                                        Locale.getDefault()
//                                    ).format(Calendar.getInstance().time) + "날씨"
//
//                                    setWeather(curPoint!!.x, curPoint!!.y)
//                                }
//                            }
//
//                        }
//                    }
//
//                    Looper.myLooper()?.let {
//                        locationClient.requestLocationUpdates(locationRequest, locationCallback, it)
//                    }
//                }
//
//                } catch (e: SecurityException) {
//                    e.printStackTrace()
//                }

                val currentLocation = Location().getLocation(requireContext())
                currentLocation.addOnSuccessListener {
                    if (it == null)
                        Toast.makeText(context, "Cannot get location.", Toast.LENGTH_SHORT).show()
                    else {
                        val lat = it.latitude
                        val lon = it.longitude

                        Log.d("CurrentLocation", "${lat.toInt()}, ${lon.toInt()}")
                        setWeather(lat.toInt(), lon.toInt())
                    }
                }
            }



        override fun onDestroy() {
            super.onDestroy()
            _binding = null
        }
}

