package com.poc.swipecarouselapp.data

import com.poc.openweatherapisample.util.APP_KEY

class ApiHelper(private val apiService: APIInterface?) {

    suspend fun getCurrentWeatherDataByLatLong(lat: String, long: String, unit: String) =
        apiService?.getCurrentWeatherDataByLatLong(lat, long, unit, APP_KEY)

    suspend fun getNextFiveDaysData(lat: String, long: String, unit: String) =
        apiService?.getNextFiveDaysData(lat, long, unit, APP_KEY)
}