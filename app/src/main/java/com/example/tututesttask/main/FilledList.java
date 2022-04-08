package com.example.tututesttask.main;

import android.content.Context;

import com.example.tututesttask.R;
import com.example.tututesttask.WeatherItem;
import com.example.tututesttask.data.Weather;
import com.example.tututesttask.data.WeatherData;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

public class    FilledList {
    private final WeatherIcon weatherIcon = new WeatherIcon();

    public WeatherItem listFillingCycle(WeatherData weatherData, Context context){
        List<Weather> weatherDeception = weatherData.getWeather();
        Weather weatherDecItem = weatherDeception.get(0);
        String message = weatherData.getWind().getSpeed() + context.getResources().getString(R.string.wind_speed);
        int intTemp = Math.round(weatherData.getMain().getTemp());
        String stringTemp = Float.toString(intTemp);

        WeatherItem item = new WeatherItem(message, DateFormat.getDateTimeInstance().format(new Date()),
                stringTemp + "\uF03C", weatherIcon.setWeatherIcon(weatherDecItem.getId(),context), weatherData.getName());

        return item;
    }
}
