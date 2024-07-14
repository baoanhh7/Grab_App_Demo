package com.example.grab_demo.deliver.activity;

import static android.graphics.Color.BLUE;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.grab_demo.R;
import com.example.grab_demo.databinding.ActivityMapsBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.PendingResult;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private GoogleMap mMap;
    private SearchView searchView;
    private ActivityMapsBinding binding;
    private LatLng currentLocation;
    private FusedLocationProviderClient fusedLocationClient;
    private GeoApiContext geoApiContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchLocation(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        geoApiContext = new GeoApiContext.Builder()
                .apiKey(getString(R.string.google_maps_key))
                .build();

        requestLocationPermissions();
    }

    private void requestLocationPermissions() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                enableMyLocation();
            } else {
                Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void enableMyLocation() {
        if (mMap != null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mMap.setMyLocationEnabled(true);

                fusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15f));
                            Log.d("MapsActivity", "Current location: " + currentLocation.toString());
                        } else {
                            Log.e("MapsActivity", "Failed to get current location");
                        }
                    }
                });
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.getUiSettings().setZoomControlsEnabled(true);

        enableMyLocation();
    }

    private void searchLocation(String locationName) {
        Geocoder geocoder = new Geocoder(this);
        List<Address> addressList = null;
        try {
            addressList = geocoder.getFromLocationName(locationName, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (addressList != null && addressList.size() > 0) {
            Address address = addressList.get(0);
            LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());

            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f));
            mMap.addMarker(new MarkerOptions().position(latLng).title(locationName));

            if (currentLocation != null) {
                getRouteToLocation(latLng);
            } else {
                Toast.makeText(this, "Current location not available", Toast.LENGTH_SHORT).show();
                Log.e("MapsActivity", "Current location not available");
            }
        } else {
            Toast.makeText(this, "Location not found: " + locationName, Toast.LENGTH_SHORT).show();
        }
    }

    private void getRouteToLocation(LatLng destination) {
        DirectionsApi.newRequest(geoApiContext)
                .origin(new com.google.maps.model.LatLng(currentLocation.latitude, currentLocation.longitude))
                .destination(new com.google.maps.model.LatLng(destination.latitude, destination.longitude))
                .setCallback(new PendingResult.Callback<DirectionsResult>() {
                    @Override
                    public void onResult(DirectionsResult result) {
                        runOnUiThread(() -> {
                            if (result != null && result.routes != null && result.routes.length > 0) {
                                addPolylineToMap(result.routes[0]);
                            } else {
                                Toast.makeText(MapsActivity.this, "No route found", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        runOnUiThread(() -> Toast.makeText(MapsActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                    }
                });
    }

    private void addPolylineToMap(DirectionsRoute route) {
        PolylineOptions polylineOptions = new PolylineOptions();
        polylineOptions.color(BLUE);  // Đặt màu xanh cho đường đi
        polylineOptions.width(10);  // Đặt độ rộng của đường đi

        for (com.google.maps.model.LatLng latLng : route.overviewPolyline.decodePath()) {
            polylineOptions.add(new LatLng(latLng.lat, latLng.lng));
        }
        mMap.addPolyline(polylineOptions);
    }
}
