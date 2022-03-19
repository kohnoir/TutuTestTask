package com.example.tututesttask

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class WeatherActivity : AppCompatActivity() {
    private var textViewCity: TextView? = null
    private var textViewWindSpeed: TextView? = null
    private var leftButton: ImageButton? = null
    private var textViewTextTempTime: TextView? =  null
    private var textViewForecastDate: TextView? =  null
    private var textViewIcon: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)

        initView()
        viewSet()
        backButton()
    }
    private fun backButton() {
        leftButton!!.setOnClickListener(View.OnClickListener { v: View? ->
            finish()
        })
    }
    private fun viewSet(){
        val windSpeed = intent.getStringExtra("windSpeed")
        val forecastDate = intent.getStringExtra("forecastDate")
        val tempTime = intent.getStringExtra("tempTime")
        val weatherConditions = intent.getStringExtra("weatherConditions")
        val countryWeather = intent.getStringExtra("countryWeather")
        textViewCity?.text = countryWeather
        textViewWindSpeed?.text = windSpeed
        textViewForecastDate?.text = forecastDate
        textViewTextTempTime?.text = tempTime
        textViewIcon?.text = weatherConditions
    }
    private fun initView(){
        textViewCity = findViewById(R.id.text_weather_activity_city_country)
        textViewWindSpeed = findViewById(R.id.text_weather_activity_wind_speed)
        leftButton = findViewById(R.id.left_button)
        textViewTextTempTime = findViewById(R.id.text_weather_activity_time_temp)
        textViewForecastDate = findViewById(R.id.text_weather_activity_forecast_date)
        textViewIcon =  findViewById(R.id.text_weather_activity_icon)
    }
}