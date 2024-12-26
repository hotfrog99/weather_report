package com.example.weather_report

import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.weather_report.network.ApiService
import com.example.weather_report.network.RetrofitInstance
import com.example.weather_report.network.WeatherResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var citySpinner: Spinner
    private lateinit var weatherText: TextView
    private lateinit var temperatureText: TextView
    private val cities = listOf("Moscow", "Saint Petersburg", "Novosibirsk", "Yekaterinburg", "Nizhny Novgorod") // Список городов

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Инициализация элементов UI
        citySpinner = findViewById(R.id.citySpinner)
        weatherText = findViewById(R.id.weatherText)
        temperatureText = findViewById(R.id.temperatureText)

        // Настройка Spinner с городами
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, cities)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        citySpinner.adapter = adapter

        // Обработчик выбора города
        citySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>, view: android.view.View?, position: Int, id: Long) {
                val selectedCity = cities[position]
                fetchWeather(selectedCity)
            }

            override fun onNothingSelected(parentView: AdapterView<*>) {
                // Можно обработать случай, если ничего не выбрано
            }
        }
    }

    // Функция для получения прогноза погоды
    private fun fetchWeather(city: String) {
        val apiKey = "01d46d563db23551473dcf1ace3ab153"  // Замените на свой API ключ
        val apiService = RetrofitInstance.getRetrofitInstance().create(ApiService::class.java)
        val call = apiService.getWeather(city, apiKey)

        call.enqueue(object : Callback<WeatherResponse> {
            override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
                if (response.isSuccessful) {
                    val weatherResponse = response.body()
                    if (weatherResponse != null) {
                        val weatherDescription = weatherResponse.weather?.get(0)?.description ?: "No description"
                        val temperature = weatherResponse.main?.temp?.toString() ?: "No temperature"
                        weatherText.text = "Weather: $weatherDescription"
                        temperatureText.text = "Temperature: $temperature°C"
                    }
                }
            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                weatherText.text = "Error: ${t.message}"
                temperatureText.text = ""
            }
        })
    }
}
