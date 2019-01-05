package com.example.drcaolangm.testapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

//Class made to make the custom info window for the markers on the map

public class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private final View mWindow;
    private Context mContext;

    //Using the layout file made for this custom info window

    public CustomInfoWindowAdapter(Context context){
        mContext = context;
        mWindow = LayoutInflater.from(context).inflate(R.layout.custom_info_window,null);
    }

    //Function to set the text of the address section of the info window
    private void rendorWindowText(Marker marker, View view){

        String title = marker.getTitle();
        TextView tvAdress = (TextView) view.findViewById(R.id.address);

        if(!tvAdress.equals("")){
            tvAdress.setText(title);
        }
    }
    @Override
    public View getInfoWindow(Marker marker) {
        rendorWindowText(marker,mWindow);
        return mWindow;
    }

    @Override
    public View getInfoContents(Marker marker) {
        rendorWindowText(marker,mWindow);
        return mWindow;
    }
}
