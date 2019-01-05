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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
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
        ,ConnectivityReceiver.ConnectivityReceiverListener{


    MapView mapView;
    GoogleMap map;
    private FusedLocationProviderClient flpc;
    private Boolean mLocationPermissionsGranted = false;
    RelativeLayout backButton;
    RelativeLayout button;
    RelativeLayout button2;
    RelativeLayout cancel;
    LinearLayout popUp;
    Button tint;
    LinearLayout connPopUp;
    TextView turnOn;
    TextView cancelConn;
    Activity activity = this;

    boolean isConnected = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Retrieve the content view that renders the map.
        setContentView(R.layout.activity_map_screen);
        // Get the SupportMapFragment and request notification
        // when the map is ready to be used.
        checkConnection();
        if(isConnected){
        getLocationPermission();}

        backButton = findViewById(R.id.backBtn);
        button = findViewById(R.id.button);
        button2 = findViewById(R.id.button2);
        cancel = findViewById(R.id.cancel);
        popUp = findViewById(R.id.popUp);
        tint = findViewById(R.id.tint);
        connPopUp = findViewById(R.id.noConn);
        turnOn = findViewById(R.id.turnOn);
        cancelConn = findViewById(R.id.cancelConn);

        //Navigate back to Home Screen
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MapScreen.this, HomeScreen.class);
                startActivity(intent);
            }
        });

        turnOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
            }
        });

        cancelConn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent HomeIntent = new Intent(MapScreen.this,HomeScreen.class);
                startActivity(HomeIntent);
            }
        });
        //Open the pop up when the first button is clicked
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popUp.setVisibility(View.VISIBLE);
                tint.setVisibility(View.VISIBLE);
                button.setVisibility(View.GONE);
            }
        });

        //Close the pop up when the cancel button is clicked
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popUp.setVisibility(View.GONE);
                tint.setVisibility(View.GONE);
                button.setVisibility(View.VISIBLE);
            }
        });



        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Checking if the app has permission to call
                int checkPermission = ContextCompat.checkSelfPermission(activity.getBaseContext(), Manifest.permission.CALL_PHONE);
                if (checkPermission != PackageManager.PERMISSION_GRANTED) {
                    //Asking for permission
                    ActivityCompat.requestPermissions(
                            activity,
                            new String[]{Manifest.permission.CALL_PHONE},
                            1);
                } else {
                    //Making call
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "+319007788990"));
                    startActivity(intent);
                }
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
        map = googleMap;
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
                    Toast.makeText(activity, "phoneCall", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    //Function to get the Device's location using the Fused Location Provider Client
    private void getDeviceLocation(){
        flpc = LocationServices.getFusedLocationProviderClient(this);
        try{
            if(mLocationPermissionsGranted){
                final Task location = flpc.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if(task.isSuccessful()){
                            Log.e("Found Location", "here");
                            Location currentLocation = (Location) task.getResult();
                            try {
                                //moveCamera to device's location
                                moveCamera(new LatLng(currentLocation.getLatitude(),currentLocation.getLongitude()),15f);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                });
            }

        }catch (SecurityException e){
            Log.e("ERROR", "getDeviceLocation: Security Exception");
        }
    }

    //Function to focus camera on location
    private void moveCamera(LatLng latLng, float zoom) throws IOException {
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,zoom));

        //Use the custom info window created
        map.setInfoWindowAdapter(new CustomInfoWindowAdapter(MapScreen.this));

        //Getting information regarding the address
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());

        addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

        String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
        String city = addresses.get(0).getLocality();
        String state = addresses.get(0).getAdminArea();
        String country = addresses.get(0).getCountryName();
        String postalCode = addresses.get(0).getPostalCode();
        String knownName = addresses.get(0).getFeatureName(); //

        //Setting the marker label
        String labelHeader = getResources().getString(R.string.yourLocation);
        String labelTxt = getResources().getString(R.string.markerText);

        //Using custom marker
        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.map_marker);
        Marker marker =map.addMarker(new MarkerOptions().icon(icon).position(latLng)
                .title("\n"+address+" "+city+"\n"+postalCode+"\n\n"+labelTxt));
        marker.showInfoWindow();
    }

    @Override
    protected void onResume() {
        super.onResume();

        SplashScreen.getInstance().setConnectivityListener(this);
    }

    private void checkConnection() {
         isConnected = ConnectivityReceiver.isConnected();
         if(!isConnected){
             tint.setVisibility(View.VISIBLE);
             connPopUp.setVisibility(View.VISIBLE);
         }
    }
    @Override
    public void onNetworkConnectionChanged(boolean connected) {
        isConnected = connected;
        if(isConnected){
            tint.setVisibility(View.GONE);
            connPopUp.setVisibility(View.GONE);
            getLocationPermission();
        }
        else{
            tint.setVisibility(View.VISIBLE);
            connPopUp.setVisibility(View.VISIBLE);
        }
   }


}