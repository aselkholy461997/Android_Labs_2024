package com.example.googlemaps;

import android.Manifest;
import android.Manifest.permission;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.googlemaps.databinding.ActivityMapsBinding;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements LocationListener, OnMapReadyCallback,
        GoogleMap.OnMyLocationClickListener, GoogleMap.OnMyLocationButtonClickListener {
    private GoogleMap map;
    /*** Broadcast receiver to check the status of the GPS.
     * This receiver will be called when the GPS is enabled or disabled.
     * Shows a toast indicating the status of the GPS.*/
    private final BroadcastReceiver gpsSwitchStateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (LocationManager.PROVIDERS_CHANGED_ACTION.equals(intent.getAction())) {
                LocationManager locationManager = (LocationManager) context.getSystemService
                        (Context.LOCATION_SERVICE);
                boolean isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

                if (isGpsEnabled) { // GPS was enabled
                    Toast.makeText(context, "GPS is enabled", Toast.LENGTH_SHORT).show();
                } else { // GPS was disabled
                    Toast.makeText(context, "GPS is disabled", Toast.LENGTH_SHORT).show();
                }
            }
        }
    };

    /**
     * Request code for location permission request.
     *
     * @see #onRequestPermissionsResult(int, String[], int[])
     */
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private boolean permissionDenied = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        com.example.googlemaps.databinding.ActivityMapsBinding binding =
                ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) mapFragment.getMapAsync(this);
        else System.err.println("mapFragment is null");
    }

    // For memory optimization, unregister the receiver when the activity is paused
    @Override
    protected void onPause() {
        super.onPause();
        this.unregisterReceiver(gpsSwitchStateReceiver);
    }

    // For memory optimization, register the receiver when the activity is resumed
    @SuppressLint("UnspecifiedRegisterReceiverFlag")
    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION);
        intentFilter.addAction(Intent.ACTION_PROVIDER_CHANGED);
        this.registerReceiver(gpsSwitchStateReceiver, intentFilter);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera.
     * In this case, we enable the zoom controls, compass, my location button, and map toolbar,
     * add a marker in FCIS ASU, and move the camera to FCIS ASU,
     * and set listeners for map click events, marker drag events, my location button, and my location click.
     * Also, we enable the my location layer if the location permission has been granted.
     */
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;

        // Enable zoom controls, compass, my location button, and map toolbar
        UiSettings uiSettings = map.getUiSettings();
        uiSettings.setZoomControlsEnabled(true);
        uiSettings.setMyLocationButtonEnabled(true);
        uiSettings.setMapToolbarEnabled(true);

        // Add a marker in FCIS ASU and move the camera
        LatLng fcisAsu = new LatLng(30.07830826392353, 31.28495023045212);
        map.addMarker(new MarkerOptions().position(fcisAsu)
                .title("Faculty of Computer and Information Sciences - Ain Shams University"));
        // Move camera to location with zoom in animation to level 17 (favored by google maps
        // check any link from them) and duration of 5 seconds
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(fcisAsu, 17), 5000, null);

        // Set a listener for map click events
        map.setOnMapClickListener(latLng -> {
            // Add a marker at the clicked location
            Marker marker = map.addMarker(new MarkerOptions().position(latLng).draggable(true));
            if (marker != null) {
                // Get text of the address of the location
                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                try {
                    List<Address> addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
                    if (addresses != null) {
                        Address address = addresses.get(0);
                        String addressText = "";
                        for (int i = 0; i <= address.getMaxAddressLineIndex(); i++)
                            addressText += (address.getAddressLine(i) + " ");
                        // Show the address in a Toast message
                        Toast.makeText(MapsActivity.this, "Marker location:\n" + addressText, Toast.LENGTH_LONG).show();

                        marker.setTitle(addressText); // Add the address as the marker title
                    }
                } catch (IOException e) { // Needed by getFromLocation()
                    throw new RuntimeException(e);
                }
            }
        });

        // Set a listener for marker drag events
        map.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(@NonNull Marker marker) {
                // Called when the marker drag is started
            }

            @Override
            public void onMarkerDrag(@NonNull Marker marker) {
                // Called when the marker is being dragged
            }

            // Called when the marker drag is finished
            @Override
            public void onMarkerDragEnd(@NonNull Marker marker) {
                LatLng position = marker.getPosition(); // The new position of the marker

                // Get the address of the new location
                Geocoder geocoder = new Geocoder(MapsActivity.this, Locale.getDefault());
                try {
                    List<Address> addresses = geocoder.getFromLocation(position.latitude, position.longitude, 1);
                    if (addresses != null) {
                        Address address = addresses.get(0);
                        String addressText = "";
                        for (int i = 0; i <= address.getMaxAddressLineIndex(); i++)
                            addressText += (address.getAddressLine(i));
                        // Show the address in a Toast message
                        Toast.makeText(MapsActivity.this, "Marker location:\n" + addressText, Toast.LENGTH_LONG).show();

                        marker.setTitle(addressText); // Add the address as the marker title
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        // Enable the my location layer if the location permission has been granted
        enableMyLocation();
        map.setOnMyLocationButtonClickListener(this); // Listener for my location button
        map.setOnMyLocationClickListener(this); // Listener for my blue location dot
    }

    /**
     * Enables the My Location layer if the fine location, or coarse permissions has been granted.
     */
    @SuppressLint("MissingPermission")
    private void enableMyLocation() {
        // 1. Check if permissions are granted, if so, enable the my location layer
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            map.setMyLocationEnabled(true);
        } else // 2. Otherwise, request location permissions from the user.
        {
            PermissionUtils.requestLocationPermissions(this, LOCATION_PERMISSION_REQUEST_CODE, true);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            return;
        }

        if (PermissionUtils.isPermissionGranted(permissions, grantResults,
                Manifest.permission.ACCESS_FINE_LOCATION) || PermissionUtils
                .isPermissionGranted(permissions, grantResults,
                        Manifest.permission.ACCESS_COARSE_LOCATION)) {
            // Enable the my location layer if the permission has been granted.
            enableMyLocation();
        } else {
            // Permission was denied. Display an error message
            // Display the missing permission error dialog when the fragments resume.
            permissionDenied = true;
        }
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        if (permissionDenied) {
            // Permission was not granted, display error dialog.
            showMissingPermissionError();
            permissionDenied = false;
        }
    }

    /**
     * Displays a dialog with error message explaining that the location permission is missing.
     */
    private void showMissingPermissionError() {
        PermissionUtils.PermissionDeniedDialog
                .newInstance(true).show(getSupportFragmentManager(), "dialog");
    }

    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(this, "Focusing on my current location", Toast.LENGTH_SHORT)
                .show();
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        // print location address
        // Get the address of the current location
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(),
                    location.getLongitude(), 1);
            if (addresses != null) {
                Address address = addresses.get(0);


                String addressText = "";
                for (int i = 0; i <= address.getMaxAddressLineIndex(); i++)
                    addressText += (address.getAddressLine(i));

                // Show the address in a Toast message
                Toast.makeText(this, "Current location:\n" + addressText,
                        Toast.LENGTH_LONG).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onLocationChanged(@NonNull Location location) {
        Toast.makeText(this, "Location changed: " + location.getLatitude() + ", " +
                location.getLongitude(), Toast.LENGTH_SHORT).show();
    }
}