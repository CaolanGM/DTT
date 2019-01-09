package com.example.drcaolangm.testapplication.activities;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.drcaolangm.testapplication.R;

public class InfoScreen extends AppCompatActivity {

    TextView mInfotv;
    RelativeLayout mBackButton;

    @TargetApi(Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_screen);

        mInfotv = findViewById(R.id.tv_info);
        mBackButton = findViewById(R.id.rl_backBtn);

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

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InfoScreen.this, HomeScreen.class);
                startActivity(intent);
            }
        });
    }




}
