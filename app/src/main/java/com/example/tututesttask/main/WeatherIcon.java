package com.example.tututesttask.main;

import android.content.Context;

import com.example.tututesttask.R;

public class WeatherIcon {
    // documentation about icons https://openweathermap.org/weather-conditions#How-to-get-icon-URL
    public String setWeatherIcon(int id, Context context){
        String icon = null;
        if (id >=200 & id<=232 ){
            icon = context.getResources().getString(R.string.weather_thunder);
        }else if(id >=300 & id<=332){
            icon = context.getResources().getString(R.string.weather_drizzle);
        }else if(id >=500 & id<=531){
            icon = context.getResources().getString(R.string.weather_rainy);
        }else if(id >=600 & id<=622){
            icon = context.getResources().getString(R.string.weather_snowy);
        }else if(id >=701 & id<=781){
            icon = context.getResources().getString(R.string.weather_foggy);
        }else if(id == 800){
            icon = context.getResources().getString(R.string.weather_clear_night);
        }else if(id >=801 & id<=804){
            icon = context.getResources().getString(R.string.weather_cloudy);
        }
        return icon;
    }
}
