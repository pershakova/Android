package com.badlogic.weatherapp.interfaces;

import com.badlogic.weatherapp.models.WeatherRequest;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OpenWeather {
    @GET("data/2.5/weather")
    Call<WeatherRequest> loadWeather(@Query("q") String cityCountry, @Query("appid") String keyApi);

    @GET("data/2.5/weather")
    Call<WeatherRequest> loadWeatherByCoordinates(@Query("lat") double latitude, @Query("lon") double longitude, @Query("appid") String keyApi);
}
