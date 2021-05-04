package com.salekur.kayeslocation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class MainActivity extends AppCompatActivity {
    // text view to display user latitude and longitude
    private TextView LocationViewText;
    // fused location provider by google
    private FusedLocationProviderClient LocationProvider;
    // integer code to identify location request
    private static final int LOCATION_PERMISSION_ACCESS_CODE = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // assigning variables
        LocationViewText = (TextView) findViewById(R.id.location);
        LocationProvider = LocationServices.getFusedLocationProviderClient(this);

        // calling function to get user location
        GetCurrentLocation();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // identify request by integer location code and check results for empty or not
        if (requestCode == LOCATION_PERMISSION_ACCESS_CODE && grantResults.length > 0) {
            // check weather user accept location request or not
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // if location allowed by user then getting their location
                GetCurrentLocation();
            } else {
                // otherwise display that they did not give the permission to view their location
                LocationViewText.setText("Permission Denied!");
            }
        }
    }

    // function to get user current location
    public void GetCurrentLocation() {
        // check whether user gave the permission to access their location or not
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
            // if user did not give the access to get location then asking them to give it
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_ACCESS_CODE);
        } else {
            // getting user location by fused location provider from google
            LocationProvider.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    // checking weather google give an real location or not
                    if (location != null) {
                        // assigning latitude and longitude from location provider
                        double UserLatitude = location.getLatitude();
                        double UserLongitude = location.getLatitude();
                        // display user latitude and longitude
                        LocationViewText.setText("Lat: " + UserLatitude + "\nLong: " + UserLongitude);
                    }
                }
            });
        }
    }

}