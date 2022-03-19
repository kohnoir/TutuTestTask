package com.example.tututesttask;

public class WeatherItem {
    private String windSpeed; //bottom line with wind speed
    private String forecastDate; // forecast date
    private String tempTime; // temperature at midnight 00:00
    private String weatherConditions; // icon
    private String countryWeather; // city/ country

    public String getCountryWeather() {
        return countryWeather;
    }

    public void setCountryWeather(String countryWeather) {
        this.countryWeather = countryWeather;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getForecastDate() {
        return forecastDate;
    }

    public void setForecastDate(String forecastDate) {
        this.forecastDate = forecastDate;
    }

    public String getTempTime() {
        return tempTime;
    }

    public void setTempTime(String tempTimeMidnight) {
        this.tempTime = tempTimeMidnight;
    }

    public String getWeatherConditions() {
        return weatherConditions;
    }

    public void setWeatherConditions(String weatherConditionsNineAm) {
        this.weatherConditions= weatherConditionsNineAm;
    }

    public WeatherItem(String windSpeed, String forecastDate, String tempTime, String weatherConditions, String countryWeather) {
        this.windSpeed = windSpeed;
        this.forecastDate = forecastDate;
        this.tempTime = tempTime;
        this.weatherConditions = weatherConditions;
        this.countryWeather = countryWeather;
    }
}


