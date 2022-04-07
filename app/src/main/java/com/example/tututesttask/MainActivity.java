package com.example.tututesttask;

import static com.example.tututesttask.DBHelper.KEY_CITY;
import static com.example.tututesttask.DBHelper.TABLE_NOTES;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tututesttask.data.WeatherData;
import com.example.tututesttask.main.InternetConnectionCheck;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private final com.example.tututesttask.main.GetAllNoteData getAllNoteData = new com.example.tututesttask.main.GetAllNoteData();
    private final com.example.tututesttask.main.FilledList filledList = new com.example.tututesttask.main.FilledList();
    private final com.example.tututesttask.main.FilledDB filledDB = new com.example.tututesttask.main.FilledDB();

    private final int LAUNCH_WEATHER_ACTIVITY = 1;
    private final List<WeatherItem> notes = new ArrayList<>();
    private final Api mApi = Api.Instance.getApi();
    private final int numberOfColumns = 2;
    private RecyclerView recyclerView;

    private DBHelper dbHelper;

    private RecyclerWeatherAdapter adapter;
    private SearchView searchView;

    private SQLiteDatabase db;

    private WeatherItem item;
    private Cursor cursor;
    private ContentValues contentValues;

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
        if (InternetConnectionCheck.hasConnection(this)) {
            for (String s : City) {
                // api called
                mApi.getWeatherDataByCity(s, getResources().getString(R.string.api_weather_key), getResources().getString(R.string.metric))
                        .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<WeatherData>() {
                    @Override
                    public void accept(WeatherData weatherData) throws Exception {
                        item = filledList.listFillingCycle(weatherData, MainActivity.this);
                        notes.add(item);
                        //if the array of cities is greater than or equal to the database is full and we update the data
                        cursor = db.query(TABLE_NOTES, null, null, null, null, null,null);
                        contentValues = filledDB.fillingDB(item);
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
            notes.addAll(getAllNoteData.gettingAllNoteData(dbHelper)); //if there is no connection, the list is filled from the database
        }
        recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        adapter = new RecyclerWeatherAdapter(this, notes);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new com.example.tututesttask.CharacterItemDecoration(25,0));
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
    // when switching to the view, we send all the data to WeatherActivity to fill it
    private void itemClickListener() {
        adapter.setOnEntryClickListener(new RecyclerWeatherAdapter.OnEntryClickListener() {
            @Override
            public void onEntryClick(View view, int position) {
                Intent intentNote = new Intent(MainActivity.this, WeatherActivity.class);
                WeatherItem note = notes.get(position);
                intentNote.putExtra(getResources().getString(R.string.data_wind_speed), note.getWindSpeed());
                intentNote.putExtra(getResources().getString(R.string.data_forecast_date), note.getForecastDate());
                intentNote.putExtra(getResources().getString(R.string.data_temp_time), note.getTempTime());
                intentNote.putExtra(getResources().getString(R.string.data_weather_conditions), note.getWeatherConditions());
                intentNote.putExtra(getResources().getString(R.string.data_country_weather), note.getCountryWeather());
                startActivityForResult(intentNote, LAUNCH_WEATHER_ACTIVITY);
            }
        });
    }
}