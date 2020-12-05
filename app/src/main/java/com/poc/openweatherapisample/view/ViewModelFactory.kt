package com.poc.openweatherapisample.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.poc.swipecarouselapp.data.ApiHelper
import com.poc.swipecarouselapp.data.MainRepository

class ViewModelFactory(private val apiHelper: ApiHelper) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WeatherDetailsViewModel::class.java)) {
            return WeatherDetailsViewModel(
                MainRepository(
                    apiHelper
                )
            ) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}