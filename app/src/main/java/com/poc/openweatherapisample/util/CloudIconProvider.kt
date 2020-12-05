package com.poc.openweatherapisample.util

import com.poc.openweatherapisample.R

object CloudIconProvider {
    fun getCloudIcon(decription: String?): Int {
        return when (decription) {
            "broken clouds" -> R.raw.broken_clouds
            "light rain" -> R.raw.light_rain
            "haze" -> R.raw.broken_clouds
            "overcast clouds" -> R.raw.overcast_clouds
            "moderate rain" -> R.raw.moderate_rain
            "few clouds" -> R.raw.few_clouds
            "heavy intensity rain" -> R.raw.heavy_intentsity
            "clear sky" -> R.raw.clear_sky
            "scattered clouds" -> R.raw.scattered_clouds
            else -> R.raw.unknown
        }
    }
}


