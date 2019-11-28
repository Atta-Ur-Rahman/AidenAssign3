package com.techease.asign3.ui;


import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.techease.asign3.R;
import com.techease.asign3.genrelUtills.AlertUtils;
import com.techease.asign3.model.weather.WeatherResponseModel;
import com.techease.asign3.networkingWeather.BaseNetworking;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class WeatherFragment extends Fragment {


    View parentView;

    Dialog dialog;

    TextView tvLatLon, tvHumidity, tvName, tvTemp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.fragment_weather, container, false);


        tvLatLon = parentView.findViewById(R.id.tvLatLon);
        tvName = parentView.findViewById(R.id.tvName);
        tvHumidity = parentView.findViewById(R.id.tvHumidity);
        tvTemp = parentView.findViewById(R.id.tvTemp);

        dialog = AlertUtils.createProgressDialog(getActivity());

        getWeather();
        return parentView;
    }


    private void getWeather() {


        dialog.show();
        retrofit2.Call<WeatherResponseModel> wallpaperResponseModelCall1 = BaseNetworking.apiServices().getWeather();

        wallpaperResponseModelCall1.enqueue(new Callback<WeatherResponseModel>() {
            @Override
            public void onResponse(Call<WeatherResponseModel> call, Response<WeatherResponseModel> response) {


                Log.d("response", String.valueOf(response.body().getName()));


                dialog.dismiss();
                if (response.isSuccessful()) {
                    Log.d("response", String.valueOf(response));
                    tvLatLon.setText(response.body().getCoord().getLat() + "," + response.body().getCoord().getLon());
                    tvName.setText(response.body().getName());
                    tvHumidity.setText(String.valueOf(response.body().getMain().getHumidity()));
                    tvTemp.setText(String.valueOf(response.body().getMain().getTemp()));

                }
            }

            @Override
            public void onFailure(Call<WeatherResponseModel> call, Throwable t) {

                dialog.dismiss();
                Log.d("response", String.valueOf(t));

            }
        });

    }


}
