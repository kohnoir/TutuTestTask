package com.example.tututesttask;

import static com.example.tututesttask.DBHelper.KEY_CITY;
import static com.example.tututesttask.DBHelper.KEY_FORECAST_DATE;
import static com.example.tututesttask.DBHelper.KEY_TEMP_TIME;
import static com.example.tututesttask.DBHelper.KEY_WEATHER_CONDITIONS;
import static com.example.tututesttask.DBHelper.KEY_WIND_SPEED;
import static com.example.tututesttask.DBHelper.TABLE_NOTES;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tututesttask.data.Weather;
import com.example.tututesttask.data.WeatherData;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private final Api mApi = Api.Instance.getApi();
    private RecyclerView recyclerView;
    private final int LAUNCH_WEATHER_ACTIVITY = 1;
    private final List<WeatherItem> notes = new ArrayList<>();
    private DBHelper dbHelper;
    private RecyclerWeatherAdapter adapter;
    private SearchView searchView;
    private final int numberOfColumns = 2;
    private SQLiteDatabase db;
    private final String[] City = new String[]{
            "Kabul", "Algiers", "Yerevan", "Canberra", "Vienna", "Baku",
            "Brussels", "Hamilton", "La Paz", "Brasilia", "Sofia", "Bujumbura", " Ottawa", "Santiago"
    };

    @SuppressLint({"CheckResult", "NotifyDataSetChanged"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        dbHelper = new DBHelper(this);
        db = dbHelper.getReadableDatabase();
        // if there is a connection, the list is filled
        if (hasConnection(this)) {
            for (String s : City) {
                // api called
                mApi.getWeatherDataByCity(s, getResources().getString(R.string.api_weather_key), getResources().getString(R.string.metric))
                        .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<WeatherData>() {
                    @Override
                    public void accept(WeatherData weatherData) throws Exception {
                        List<Weather> weatherDeception = weatherData.getWeather();
                        Weather weatherDecItem = weatherDeception.get(0);
                        String message = weatherData.getWind().getSpeed() + getResources().getString(R.string.wind_speed);
                        int intTemp = Math.round(weatherData.getMain().getTemp());
                        String stringTemp = Float.toString(intTemp);

                        WeatherItem item = new WeatherItem(message, DateFormat.getDateTimeInstance().format(new Date()),
                                stringTemp + "\uF03C", setWeatherIcon(weatherDecItem.getId()), weatherData.getName());
                        notes.add(item);

                        ContentValues contentValues = new ContentValues();
                        contentValues.put(KEY_WIND_SPEED, item.getWindSpeed());
                        contentValues.put(KEY_FORECAST_DATE, item.getForecastDate());
                        contentValues.put(KEY_TEMP_TIME, item.getTempTime());
                        contentValues.put(KEY_WEATHER_CONDITIONS, item.getWeatherConditions());
                        contentValues.put(KEY_CITY, item.getCountryWeather());
                        Cursor cursor = db.query(TABLE_NOTES, null, null, null, null, null,null);
                        //if the array of cities is greater than or equal to the database is full and we update the data
                        if(cursor.getCount()>=City.length) {
                            db.update(TABLE_NOTES, contentValues, KEY_CITY + " = ?", new String[] {item.getCountryWeather()});
                        }else {
                            db.insert(TABLE_NOTES, null, contentValues);
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        }else {
            notes.addAll(getAllNoteData()); //if there is no connection, the list is filled from the database
        }
        recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        adapter = new RecyclerWeatherAdapter(this, notes);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new CharacterItemDecoration(25,0));
        itemClickListener();
        // the search is configured for cities
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String text) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String text) {
                adapter.getFilter().filter(text);
                return true;
            }
        });

    }
    private void initView(){
        recyclerView = findViewById(R.id.recycle_view);
        searchView = findViewById(R.id.et_search_bar);
    }
    //filling the list from the database
    public ArrayList<WeatherItem> getAllNoteData(){
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
    // when switching to the view, we send all the data to WeatherActivity to fill it
    private void itemClickListener() {
        adapter.setOnEntryClickListener(new RecyclerWeatherAdapter.OnEntryClickListener() {
            @Override
            public void onEntryClick(View view, int position) {
                Intent intentNote = new Intent(MainActivity.this, WeatherActivity.class);
                WeatherItem note = notes.get(position);
                intentNote.putExtra("windSpeed", note.getWindSpeed());
                intentNote.putExtra("forecastDate", note.getForecastDate());
                intentNote.putExtra("tempTime", note.getTempTime());
                intentNote.putExtra("weatherConditions", note.getWeatherConditions());
                intentNote.putExtra("countryWeather", note.getCountryWeather());
                startActivityForResult(intentNote, LAUNCH_WEATHER_ACTIVITY);
            }
        });
    }
    // documentation about icons https://openweathermap.org/weather-conditions#How-to-get-icon-URL
    private String setWeatherIcon(int id){
        String icon = null;
            if (id >=200 & id<=232 ){
                icon = getResources().getString(R.string.weather_thunder);
            }else if(id >=300 & id<=332){
                icon = getResources().getString(R.string.weather_drizzle);
            }else if(id >=500 & id<=531){
                icon = getResources().getString(R.string.weather_rainy);
            }else if(id >=600 & id<=622){
                icon = getResources().getString(R.string.weather_snowy);
            }else if(id >=701 & id<=781){
                icon = getResources().getString(R.string.weather_foggy);
            }else if(id == 800){
                icon = getResources().getString(R.string.weather_clear_night);
            }else if(id >=801 & id<=804){
                icon = getResources().getString(R.string.weather_cloudy);
            }
        return icon;
    }
    //checking the connection
    public static boolean hasConnection(final Context context)
    {
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiInfo != null && wifiInfo.isConnected())
        {
            return true;
        }
        wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (wifiInfo != null && wifiInfo.isConnected())
        {
            return true;
        }
        wifiInfo = cm.getActiveNetworkInfo();
        return wifiInfo != null && wifiInfo.isConnected();
    }
}