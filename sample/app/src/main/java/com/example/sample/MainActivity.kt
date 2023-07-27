package com.example.sample

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sample.adapter.WeatherAdapter
import com.example.sample.model.ModelWeather
import com.example.sample.retrofitinterface.ApiObject
import com.example.sample.retrofitinterface.ITEM
import com.example.sample.retrofitinterface.RESPONSE
import com.example.sample.retrofitinterface.WEATHER
import retrofit2.Call
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class MainActivity : AppCompatActivity() {
    lateinit var weatherRecyclerView : RecyclerView

    private var base_date = "20230727"
    private var base_time = "2330"
    private var nx = "55"
    private var ny = "127"
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tvDate = findViewById<TextView>(R.id.tvDate)
        weatherRecyclerView = findViewById<RecyclerView>(R.id.weatherRecyclerView)
        val btnRefresh = findViewById<Button>(R.id.btnRefresh)

        weatherRecyclerView.layoutManager = LinearLayoutManager(this@MainActivity)

        tvDate.text = SimpleDateFormat("MM월 dd일", Locale.getDefault()).format(Calendar.getInstance().time) + "날씨"

        setWeather(nx, ny)

        btnRefresh.setOnClickListener {
            setWeather(nx, ny)
        }
    }

    private fun setWeather(nx : String, ny : String) {

        val cal = Calendar.getInstance()
        base_date = SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(cal.time)
        val timeH = SimpleDateFormat("HH", Locale.getDefault()).format(cal.time)
        val timeM = SimpleDateFormat("HH", Locale.getDefault()).format(cal.time)

        base_time = getBaseTime(timeH, timeM)

        if (timeH == "00" && base_time == "2330") {
            cal.add(Calendar.DATE, -1).toString()
            base_date = SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(cal.time)
        }

        val call = ApiObject.retrofitService.GetWeather(60, 1, "JSON", base_date, base_time, nx, ny )

        call.enqueue(object : retrofit2.Callback<WEATHER> {
            override fun onResponse(call: Call<WEATHER>, response: Response<WEATHER>) {
                if (response.isSuccessful) {
                    val it: List<ITEM> = response.body()!!.response.body.items.item

                    val weatherArr = arrayOf(ModelWeather(), ModelWeather(), ModelWeather(), ModelWeather(), ModelWeather(), ModelWeather())

                    var index = 0
                    val totalCount = response.body()!!.response.body.totalCount - 1
                    for (i in 0 .. totalCount) {
                        index %= 6
                        when(it[i].category) {
                            "PTY" -> weatherArr[index].rainType = it[i].fcstValue
                            "REH" -> weatherArr[index].humidity = it[i].fcstValue
                            "SKY" -> weatherArr[index].sky = it[i].fcstValue
                            "T1H" -> weatherArr[index].temp = it[i].fcstValue
                            else -> continue
                        }
                        index++
                    }

                    for (i in 0..5) weatherArr[i].fcstTime = it[i].fcstTime

                    weatherRecyclerView.adapter = WeatherAdapter(weatherArr)

                    Toast.makeText(applicationContext, it[0].fcstDate + ", " + it[0].fcstTime + "의 날씨 정보입니다.", Toast.LENGTH_SHORT).show()
                }
            }

            @SuppressLint("SetTextI18n")
            override fun onFailure(call: Call<WEATHER>, t: Throwable) {
                val tvError = findViewById<TextView>(R.id.tvError)
                tvError.text = "api fail : " + t.message.toString() + "\n 다시 시도해주세요."
                tvError.visibility = View.VISIBLE
                Log.d("api fail", t.message.toString())

            }
        })
    }

    private fun getBaseTime(h : String, m : String) : String {
        var result = ""

        if (m.toInt() < 45) {
            if (h == "0") result = "2330"

            else {
                var resultH = h.toInt() -1
                if (resultH < 10) result = "0" + resultH + "30"

                else result = resultH.toString() + "30"
             }
        }
        else result = h + "30"

        return result
    }
}