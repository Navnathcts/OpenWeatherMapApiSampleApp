package com.azhar.weatherapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.poc.openweatherapisample.R
import com.poc.openweatherapisample.model.ForecastData
import com.poc.openweatherapisample.model.CityDetailModel
import kotlinx.android.synthetic.main.city_list_item.view.*
import java.util.*

class CityListAdapter(private val cityItemClickListener: CityItemClickListener) : RecyclerView.Adapter<CityListAdapter.ViewHolder>() {

    private var cityDataHolderMap: HashMap<String?, CityDetailModel?>? = null
    private var cityList: MutableList<String?>? = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.city_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        cityList?.get(position)?.let { cityName ->
            holder.itemView.tvCityDetail.text = cityName
            holder.itemView.setOnClickListener {
                cityItemClickListener.onCityItemClick(cityDataHolderMap?.get(cityName)) }
        }
    }

    override fun getItemCount(): Int {
        return cityList?.size?:0
    }

    fun setCityList(hashMap: HashMap<String?, CityDetailModel?>?) {
        cityDataHolderMap = hashMap
        cityList = cityDataHolderMap?.keys?.toMutableList()
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    interface CityItemClickListener{
        fun onCityItemClick(cityDetailModel: CityDetailModel?)
    }
}