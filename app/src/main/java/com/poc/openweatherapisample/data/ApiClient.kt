package com.poc.swipecarouselapp.data

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object ApiClient {
    private var retrofit: Retrofit? = null
    val base_url = "http://api.openweathermap.org"
    val client: Retrofit?
        get() {
            val interceptor = HttpLoggingInterceptor()
            val gson = GsonBuilder()
                .setLenient()
                .create()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            val client = OkHttpClient.Builder().readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS).addInterceptor(interceptor)
                .build()
            retrofit = Retrofit.Builder()
                .baseUrl(base_url)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build()
            return retrofit
        }
    val apiService: APIInterface? = client?.create(
        APIInterface::class.java)
}