package com.poc.swipecarouselapp.data



class MainRepository(private val apiHelper: ApiHelper) {

    suspend fun getCurrentWeatherDataByLatLong(lat: String, long: String, unit: String) =
        apiHelper.getCurrentWeatherDataByLatLong(lat, long, unit)
    suspend fun getNextFiveDaysData(lat: String, long: String, unit: String) =
        apiHelper.getNextFiveDaysData(lat, long, unit)

}