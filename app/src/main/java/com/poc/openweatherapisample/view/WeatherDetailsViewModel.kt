package com.poc.openweatherapisample.view

import android.text.format.DateFormat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.poc.openweatherapisample.model.WeatherDataList
import com.poc.openweatherapisample.model.WeatherResponse
import com.poc.openweatherapisample.util.DATE_FILTER
import com.poc.openweatherapisample.util.DISPLAY_DATE_FORMAT
import com.poc.swipecarouselapp.data.MainRepository
import com.poc.swipecarouselapp.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*

class WeatherDetailsViewModel(private val mainRepository: MainRepository) : ViewModel() {
    private var currentDayWeatherResponse: WeatherResponse? = null
    private var nextFiveDaysDataList: WeatherDataList? = null
    fun getCurrentWeatherDataByLatLong(lat: String, long: String, unit: String) =
        liveData(Dispatchers.IO) {
            emit(Resource.loading(data = null))
            try {
                withContext(Dispatchers.IO) {
                    currentDayWeatherResponse =
                        mainRepository.getCurrentWeatherDataByLatLong(lat, long, unit)
                }
                withContext(Dispatchers.Main) {
                    if (currentDayWeatherResponse != null) {
                        emit(Resource.success(currentDayWeatherResponse))
                    } else {
                        emit(Resource.error(data = null, message = "Oops!!!\nWe can not load Today's weather information..."))
                    }
                }
            } catch (e: Exception) {
                emit(Resource.error(data = null, message = "Error Occurred!"))
            }

        }

    fun getNextFiveDaysData(lat: String, long: String, unit: String) =
        liveData(Dispatchers.IO) {
            emit(Resource.loading(data = null))
            try {
                withContext(Dispatchers.IO) {
                    nextFiveDaysDataList =
                        mainRepository.getNextFiveDaysData(lat, long, unit)
                }
                withContext(Dispatchers.Main) {
                    if (nextFiveDaysDataList != null) {
                        //Added filter in order to show morning weather details.
                        val data = nextFiveDaysDataList?.forecastData?.filter {
                            it.dayDate = getDayDate(it.dt)
                            it.dtTxt?.contains(DATE_FILTER) == true
                        }
                        emit(Resource.success(data))
                    } else {
                        emit(Resource.error(data = null, message = "Oops !!!\n We can not load next five days weather information..."))
                    }
                }
            } catch (e: Exception) {
                emit(Resource.error(data = null, message = "Oops !!!\n We can not load next five days weather information..."))
            }

        }

    private fun getDayDate(time: Long?): String? {
        return time?.let {
            val cal = Calendar.getInstance(Locale.ENGLISH)
            cal.timeInMillis = time * 1000
            return DateFormat.format(DISPLAY_DATE_FORMAT, cal).toString()
        }
    }
}
