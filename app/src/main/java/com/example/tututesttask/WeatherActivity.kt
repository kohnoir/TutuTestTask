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

        val windSpeed = intent.getStringExtra(resources.getString(R.string.data_wind_speed))
        val forecastDate = intent.getStringExtra(resources.getString(R.string.data_forecast_date))
        val tempTime = intent.getStringExtra(resources.getString(R.string.data_temp_time))
        val weatherConditions = intent.getStringExtra(resources.getString(R.string.data_weather_conditions))
        val countryWeather = intent.getStringExtra(resources.getString(R.string.data_country_weather))
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