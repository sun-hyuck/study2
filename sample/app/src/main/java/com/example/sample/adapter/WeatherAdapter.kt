package com.example.sample.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.sample.R
import com.example.sample.model.ModelWeather

class WeatherAdapter (var items : Array<ModelWeather>) : RecyclerView.Adapter<WeatherAdapter.ViewHoler>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherAdapter.ViewHoler {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item_weather, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: WeatherAdapter.ViewHoler, position: Int) {
        val item = items[position]
        holder.setItem(item)
    }
    override fun getItemCount() = items.count()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun setItem(item : ModelWeather) {
            val tvTime = itemView.findViewById<TextView>(R.id.tvTime)
            val tvRainType = itemView.findViewById<TextView>(R.id.tvRainType)
            val tvHumudity = itemView.findViewById<TextView>(R.id.tvHumidity)
            val tvSky = itemView.findViewById<TextView>(R.id.tvSky)
            val tvTemp = itemView.findViewById<TextView>(R.id.tvTemp)

            tvTime.text = item.fcstTime
            tvRainType.text = getRainType(item.rainType)
            tvHumudity.text = item.humidity
            tvSky.text = item.temp + "°"

        }
    }

    fun getRainType(rainType : String) : String {

    }
}