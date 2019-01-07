package com.example.drcaolangm.testapplication;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.drcaolangm.testapplication.model.MapScreenPresenterImp;
import com.example.drcaolangm.testapplication.presenter.MapScreenPresenter;
import com.example.drcaolangm.testapplication.view.MapScreenView;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;

import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapScreen extends AppCompatActivity implements OnMapReadyCallback
        , ConnectivityReceiver.ConnectivityReceiverListener, MapScreenView {


    GoogleMap mMap;
    private Boolean mLocationPermissionsGranted = false;
    RelativeLayout mBackButton;
    RelativeLayout mPopUpButton;
    RelativeLayout mCallButton;
    RelativeLayout mCancel;
    LinearLayout mPopUp;
    Button mTint;
    LinearLayout mConnPopUp;
    TextView turnOn;
    TextView mCancelConn;
    Activity activity = this;

    boolean isConnected = true;
    private MapScreenPresenter mapScreenPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Retrieve the content view that renders the map.
        setContentView(R.layout.activity_map_screen);
        // Get the SupportMapFragment and request notification
        // when the map is ready to be used.
//        checkConnection();
        // if(isConnected){
        getLocationPermission();
        //}

        mBackButton = findViewById(R.id.rl_backBtn);
        mPopUpButton = findViewById(R.id.rl_popUpBtn);
        mCallButton = findViewById(R.id.rl_callbtn);
        mCancel = findViewById(R.id.rl_cancel);
        mPopUp = findViewById(R.id.ll_popUp);
        mTint = findViewById(R.id.btn_tint);
        mConnPopUp = findViewById(R.id.noConn);
        //turnOn = findViewById(R.id.turnOn);
        mCancelConn = findViewById(R.id.cancelConn);
        mapScreenPresenter = new MapScreenPresenterImp(MapScreen.this);

        //Navigate back to Home Screen
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MapScreen.this, HomeScreen.class);
                startActivity(intent);
            }
        });

        //CONNECTION CODE

//        turnOn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
//            }
//        });

//        cancelConn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent HomeIntent = new Intent(MapScreen.this,HomeScreen.class);
//                startActivity(HomeIntent);
//            }
//        });
        //Open the pop up when the first button is clicked
        mPopUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mapScreenPresenter.openPopUp();
            }
        });

        //Close the pop up when the cancel button is clicked
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mapScreenPresenter.cancelPopUp();
            }
        });


        mCallButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Checking if the app has permission to call
                mapScreenPresenter.checkCallPermission(activity);
            }
        });


    }


    //initialising the map fragment
    private void initMap() {

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(MapScreen.this);
    }


    //Mandatory callback function for map
    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.e("On Map Ready", "here");
        mMap = googleMap;
        if (mLocationPermissionsGranted) {
            getDeviceLocation();

        }


    }


    //Function to check if app has location permissions. If not, request the permissions, otherwise initialise map
    private void getLocationPermission() {
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                mLocationPermissionsGranted = true;
                initMap();
            } else {
                ActivityCompat.requestPermissions(this, permissions, 1234);

            }
        } else {
            ActivityCompat.requestPermissions(this, permissions, 1234);
        }
    }


    //Deal with the responses from the permission requests
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mLocationPermissionsGranted = false;

        switch (requestCode) {
            //LOCATION REQUEST RESPONSE CODE
            case 1234: {
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            mLocationPermissionsGranted = false;
                            //If permission not accepted, return to home screen
                            Intent HomeIntent = new Intent(MapScreen.this, HomeScreen.class);
                            startActivity(HomeIntent);
                        }
                    }
                    //initialising map
                    mLocationPermissionsGranted = true;
                    Log.e("INIT MAP", "here");
                    initMap();
                }
            }
            break;
            //CALL REQUEST RESPONSE CODE
            case 1: {
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            mLocationPermissionsGranted = false;
                            return;
                        }
                    }
                    //Make phone call
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "+319007788990"));
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    startActivity(intent);
                }
            }
        }
    }

    //Function to get the Device's location using the Fused Location Provider Client
    private void getDeviceLocation() {
        FusedLocationProviderClient flpc = LocationServices.getFusedLocationProviderClient(this);
        try {
            if (mLocationPermissionsGranted) {
                final Task location = flpc.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            Log.e("Found Location", "here");
                            Location currentLocation = (Location) task.getResult();
                            try {
                                //moveCamera to device's location
                                if (currentLocation != null) {
                                    moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()));
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (NullPointerException e) {
                                Toast.makeText(MapScreen.this, "NO LOCO", Toast.LENGTH_SHORT).show();
                            }
                        }

                    }
                });
            }

        } catch (SecurityException e) {
            Log.e("ERROR", "getDeviceLocation: Security Exception");
        }
    }

    //Function to focus camera on location
    private void moveCamera(LatLng latLng) throws IOException {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, (float) 15.0));

        //Use the custom info window created
        mMap.setInfoWindowAdapter(new CustomInfoWindowAdapter(MapScreen.this));

        //Getting information regarding the address
        mapScreenPresenter.checkAddress(latLng);
    }

    @Override
    protected void onResume() {
        super.onResume();

        //SplashScreen.getInstance().setConnectivityListener(this);
    }

    private void checkConnection() {
        isConnected = ConnectivityReceiver.isConnected();
        if (!isConnected) {
            mTint.setVisibility(View.VISIBLE);
            mConnPopUp.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void onNetworkConnectionChanged(boolean connected) {
        isConnected = connected;
        if (isConnected) {
            mTint.setVisibility(View.GONE);
            mConnPopUp.setVisibility(View.GONE);
            getLocationPermission();
        } else {
            mTint.setVisibility(View.VISIBLE);
            mConnPopUp.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void showAddress(String address, String city, String postalCode, LatLng latLng) {
        String labelTxt = getResources().getString(R.string.markerText);

        //Using custom marker
        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.map_marker);
        Marker marker = mMap.addMarker(new MarkerOptions().icon(icon).position(latLng)
                .title("\n" + address + " " + city + "\n" + postalCode + "\n\n" + labelTxt));

        marker.showInfoWindow();
    }

    @Override
    public void unknownAddress(LatLng latLng) {
        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.map_marker);
        Marker marker = mMap.addMarker(new MarkerOptions().icon(icon).position(latLng)
                .title("Cannot calculate address of location"));
        marker.showInfoWindow();
    }

    @Override
    public void showPopUp() {
        mPopUp.setVisibility(View.VISIBLE);
        mTint.setVisibility(View.VISIBLE);
        mPopUpButton.setVisibility(View.GONE);
    }

    @Override
    public void closePopUp() {
        mPopUp.setVisibility(View.GONE);
        mTint.setVisibility(View.GONE);
        mPopUpButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void makeCall() {
        ActivityCompat.requestPermissions(
                activity,
                new String[]{Manifest.permission.CALL_PHONE},
                1);
    }

    @Override
    public void callPermission() {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "+319007788990"));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        startActivity(intent);
    }
}