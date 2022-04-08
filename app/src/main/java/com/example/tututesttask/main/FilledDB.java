package com.example.tututesttask.main;

import static com.example.tututesttask.DBHelper.KEY_CITY;
import static com.example.tututesttask.DBHelper.KEY_FORECAST_DATE;
import static com.example.tututesttask.DBHelper.KEY_TEMP_TIME;
import static com.example.tututesttask.DBHelper.KEY_WEATHER_CONDITIONS;
import static com.example.tututesttask.DBHelper.KEY_WIND_SPEED;

import android.content.ContentValues;

import com.example.tututesttask.WeatherItem;

public class FilledDB {
    public ContentValues fillingDB(WeatherItem item){
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_WIND_SPEED, item.getWindSpeed());
        contentValues.put(KEY_FORECAST_DATE, item.getForecastDate());
        contentValues.put(KEY_TEMP_TIME, item.getTempTime());
        contentValues.put(KEY_WEATHER_CONDITIONS, item.getWeatherConditions());
        contentValues.put(KEY_CITY, item.getCountryWeather());
        return contentValues;
    }
}
