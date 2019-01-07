package com.example.drcaolangm.testapplication.presenter;

import android.app.Activity;
import android.location.Address;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;

public interface MapScreenPresenter {
    void checkAddress(LatLng latLng) throws IOException;
    void openPopUp();
    void cancelPopUp();
    void checkCallPermission(Activity activity);
}
