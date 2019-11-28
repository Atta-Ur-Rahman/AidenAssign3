package com.techease.asign3.ui;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.material.snackbar.Snackbar;
import com.techease.asign3.R;


public class CurrentLocationFragment extends Fragment implements LocationListener {

    View parentView;

    private FusedLocationProviderClient fusedLocationClient;

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 2;
    ImageView ivCurrentLocation;

    public static String strLatLog;
    RelativeLayout rlRootView;

    protected LocationManager locationManager;
    protected LocationListener locationListener;
    protected Context context;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.fragment_current_location, container, false);

        ivCurrentLocation = parentView.findViewById(R.id.ivCurrentLocation);
        rlRootView = parentView.findViewById(R.id.rootView);

        context = this.getActivity();

        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        boolean permissionGranted = ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;

        if (permissionGranted) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 100, this);
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 200);
        }


        ivCurrentLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (strLatLog==null) {
                    Snackbar.make(rlRootView, "Please wait.......", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                } else {
                    Snackbar.make(rlRootView, strLatLog, Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                }

            }
        });


        return parentView;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 200: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(context, "permission ", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        strLatLog = ("Latitude:" + location.getLatitude() + ", Longitude:" + location.getLongitude());

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
