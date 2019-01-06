package com.example.drcaolangm.testapplication;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

public class InfoScreen extends AppCompatActivity {

    TextView mInfotv;

    @TargetApi(Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_screen);

        mInfotv = findViewById(R.id.infotv);

        String part1 = getResources().getString(R.string.info1);
        String part2 = getResources().getString(R.string.info2);
        String link = getResources().getString(R.string.infoLink);
        String part3 = getResources().getString(R.string.info3);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mInfotv.setText(Html.fromHtml("<p>"+part1+"\n\n "+part2+"<a href=\"https://www.rsr.nl/index.php?page=privacy-wetgeving\">"+link+"</a> "+part3+"</p>", Html.FROM_HTML_MODE_COMPACT));
            mInfotv.setMovementMethod(LinkMovementMethod.getInstance());
        } else {
            mInfotv.setText(Html.fromHtml("<p>"+part1+"\n\n "+part2+"<a href=\"https://www.rsr.nl/index.php?page=privacy-wetgeving\">"+link+"</a> "+part3+"</p>", Html.FROM_HTML_MODE_COMPACT));
            mInfotv.setMovementMethod(LinkMovementMethod.getInstance());
        }
    }
}
