package com.example.tututesttask;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NOTES = "notes";

    public static final String KEY_ID = "_id";
    public static final String KEY_WIND_SPEED = "windSpeed";
    public static final String KEY_CITY = "city";
    public static final String KEY_TEMP_TIME = "tempTime";
    public static final String KEY_FORECAST_DATE = "forecastDate";
    public static final String KEY_WEATHER_CONDITIONS = "weatherConditions";

    //database and its creation
    public DBHelper(Context context) {
        // конструктор суперкласса
        super(context, TABLE_NOTES, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NOTES + "("
                + KEY_ID + "integer primary key,"
                + KEY_WIND_SPEED + " text,"
                + KEY_CITY + " text,"
                + KEY_FORECAST_DATE + " text,"
                + KEY_TEMP_TIME + " text,"
                + KEY_WEATHER_CONDITIONS + " text"
                + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_NOTES);
        onCreate(db);
    }

}