package com.example.drcaolangm.testapplication.view;

import com.google.android.gms.maps.model.LatLng;

public interface MapScreenView {

    void showAddress(String address, String city, String postalCode, LatLng latLng);
    void unknownAddress(LatLng latLng);
    void showPopUp();
    void closePopUp();
    void makeCall();
    void callPermission();


}
