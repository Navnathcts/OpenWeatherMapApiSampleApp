package com.azhar.weatherapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.poc.openweatherapisample.R
import com.poc.openweatherapisample.model.ForecastData
import com.poc.openweatherapisample.util.CloudIconProvider
import kotlinx.android.synthetic.main.next_day_weather_card.view.*

class WeatherDataAdapter() : RecyclerView.Adapter<WeatherDataAdapter.ViewHolder>() {

    private var foreCastDataList: List<ForecastData>? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.next_day_weather_card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        foreCastDataList?.get(position)?.apply {
            holder.itemView.apply {
                tvDay.text= dayDate
                tvTemp.text = """${holder.itemView.context.getString(R.string.temperature)} ${main?.temp.toString()} °C"""
                tvHumidity.text = """${holder.itemView.context.getString(R.string.humidity)} ${main?.humidity.toString()} %"""
                tvWind.text = """${holder.itemView.context.getString(R.string.wind)} ${wind?.speed?.toString()} m/s"""
                if (weather?.isNotEmpty()==true) {
                    weather?.get(0)?.description.apply {
                        tvCloudDescri.text = "${holder.itemView.context.getString(R.string.rain)} $this"
                        iconTemp.setAnimation(CloudIconProvider.getCloudIcon(this))
                    }
                }
            }
        }


    }

    override fun getItemCount(): Int {
        return foreCastDataList?.size ?: 0
    }

    fun setForeCastData(foreCastDataList: List<ForecastData>?) {
        this.foreCastDataList = foreCastDataList
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}