package com.example.myapplication


import android.content.ClipData.Item
import android.renderscript.Element.DataType
import com.google.gson.annotations.SerializedName
import okhttp3.Headers
import okhttp3.Response
import okhttp3.internal.http2.ErrorCode
import okhttp3.internal.http2.Header
import retrofit2.http.Body

data class ModelWeather(
    @SerializedName("rainType") var rainType: String = "",
    @SerializedName("humidity") var humidity: String = "",
    @SerializedName("sky") var sky: String = "",
    @SerializedName("temp") var temp: String = "",
    @SerializedName("fcstTime") var fcstTime: String = "",
)

data class WEATHER (val response : REPONSE)
data class REPONSE (val header : HEADER, val body : BODY)
data class HEADER(val resultCode: Int, val resultMsg: String)
data class BODY(val dataType: String, val items : ITEMS, val totalCount: Int)
data class ITEMS(val item : List<ITEM>)

data class ITEM(val category: String, val fcstDate: String, val fcstTime: String, val fcstValue: String)