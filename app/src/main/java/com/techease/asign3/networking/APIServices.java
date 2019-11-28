package com.techease.asign3.networking;


import com.techease.asign3.model.WallpaperResponseModel;
import com.techease.asign3.model.weather.WeatherResponseModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface

APIServices {

    @GET("search?")
    Call<WallpaperResponseModel> getAllImages(@Query("page") int pageId, @Query("sorting") String sorting, @Query("ratios") String purity);


}
