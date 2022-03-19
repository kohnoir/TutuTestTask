package com.example.tututesttask;



import com.example.tututesttask.data.WeatherData;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {
    String DOMAIN = "https://api.openweathermap.org";
    @GET("data/2.5/weather")
    Observable<WeatherData> getWeatherDataByCity(@Query("q") String city, @Query("appid") String appId, @Query("units") String units);

    class Instance{

        private static Retrofit getRetrofit(){
            OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
            Retrofit.Builder retrofitBuilder = new Retrofit.Builder();
            retrofitBuilder.baseUrl(DOMAIN);
            retrofitBuilder.addConverterFactory(GsonConverterFactory.create());
            retrofitBuilder.addCallAdapterFactory(RxJava2CallAdapterFactory.create());
            retrofitBuilder.client(okHttpClient.build());
            return retrofitBuilder.build();
        }
        public static Api getApi(){
            return getRetrofit().create(Api.class);
        }
    }

}
