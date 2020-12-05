package com.poc.swipecarouselapp.data

import com.poc.openweatherapisample.model.WeatherDataList
import com.poc.openweatherapisample.model.WeatherResponse
import com.poc.openweatherapisample.util.APP_KEY
import retrofit2.http.GET
import retrofit2.http.Query


interface APIInterface {

    @GET("data/2.5/weather?")
    suspend fun getCurrentWeatherDataByLatLong(
        @Query("lat") lat: String?,
        @Query("lon") lon: String?,
        @Query("units") units: String?,
        @Query("appid") app_id: String?
    ): WeatherResponse?

    @GET("data/2.5/forecast?")
    suspend fun getNextFiveDaysData(
        @Query("lat") lat: String?,
        @Query("lon") lon: String?,
        @Query("units") units: String?,
        @Query("appid") app_id: String?
    ): WeatherDataList?
}