package com.example.tututesttask.main;

import static com.example.tututesttask.DBHelper.KEY_CITY;
import static com.example.tututesttask.DBHelper.KEY_FORECAST_DATE;
import static com.example.tututesttask.DBHelper.KEY_TEMP_TIME;
import static com.example.tututesttask.DBHelper.KEY_WEATHER_CONDITIONS;
import static com.example.tututesttask.DBHelper.KEY_WIND_SPEED;
import static com.example.tututesttask.DBHelper.TABLE_NOTES;

import android.database.Cursor;

import com.example.tututesttask.DBHelper;
import com.example.tututesttask.WeatherItem;

import java.util.ArrayList;

public class GetAllNoteData {
    //filling the list from the database
    public ArrayList<WeatherItem> gettingAllNoteData(DBHelper dbHelper){
        Cursor cursor = dbHelper.getWritableDatabase().query(TABLE_NOTES, null, null, null, null, null, null);
        ArrayList<WeatherItem> result = new ArrayList<>();
        if(cursor.moveToFirst()) {
            int rowWindSpeed = cursor.getColumnIndex(KEY_WIND_SPEED);
            int rowCity = cursor.getColumnIndex(KEY_CITY);
            int rowForecastDate = cursor.getColumnIndex(KEY_FORECAST_DATE);
            int rowTempTime = cursor.getColumnIndex(KEY_TEMP_TIME);
            int rowWeatherConditions = cursor.getColumnIndex(KEY_WEATHER_CONDITIONS);
            do {
                result.add(new WeatherItem(cursor.getString(rowWindSpeed), cursor.getString(rowForecastDate), cursor.getString(rowTempTime),
                        cursor.getString(rowWeatherConditions), cursor.getString(rowCity)));
            }while(cursor.moveToNext());
        }
        return result;
    }
}
