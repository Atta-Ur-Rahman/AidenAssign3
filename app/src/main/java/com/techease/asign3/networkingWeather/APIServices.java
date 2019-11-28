package com.techease.asign3.networkingWeather;

import com.techease.asign3.model.weather.WeatherResponseModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface

APIServices {

    @GET("weather?zip=93709,%20us&appid=b6907d289e10d714a6e88b30761fae22%20xx.%20")
    Call<WeatherResponseModel> getWeather();

}
