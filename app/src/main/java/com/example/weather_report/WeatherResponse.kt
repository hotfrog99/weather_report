package com.example.weather_report.network

// Основной класс для парсинга ответа от OpenWeatherMap
data class WeatherResponse(
    val weather: List<Weather>?,  // Список с погодными описаниями
    val main: Main?               // Информация о температуре
)

// Класс для описания погодных условий (например, дождь, ясно, и т. д.)
data class Weather(
    val description: String       // Описание погоды (например, "clear sky")
)

// Класс для получения температуры
data class Main(
    val temp: Double              // Температура в градусах Цельсия
)
