package com.example.weather_report.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("data/2.5/weather")
    fun getWeather(
        @Query("q") city: String,
        @Query("appid") apiKey: String,
        @Query("units") units: String = "metric" // Для получения температуры в Цельсиях
    ): Call<WeatherResponse> // Использование WeatherResponse здесь
}
