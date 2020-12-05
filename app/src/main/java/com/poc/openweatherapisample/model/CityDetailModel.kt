package com.poc.openweatherapisample.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class CityDetailModel : Serializable {

    @SerializedName("postalcode")
    var postalcode: String? = null

    @SerializedName("city")
    var city: String? = null

    @SerializedName("address")
    var address: String? = null

    @SerializedName("currentLatitude")
    var currentLatitude: String? = null

    @SerializedName("currentLongitude")
    var currentLongitude: String? = null
}