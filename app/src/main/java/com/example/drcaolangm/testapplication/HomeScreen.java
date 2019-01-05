package com.example.drcaolangm.testapplication;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class HomeScreen extends AppCompatActivity {

    RelativeLayout mapButton;
    ImageView privacyInfo;
    RelativeLayout infoButton;
    LinearLayout popUp;
    Button tint;
    TextView accept;
    TextView privacyLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        mapButton = findViewById(R.id.button);
        privacyInfo = findViewById(R.id.privInfo);
        infoButton = findViewById(R.id.button3);
        popUp = findViewById(R.id.popUp);
        tint = findViewById(R.id.tint);
        accept = findViewById(R.id.accept);
        privacyLink = findViewById(R.id.privLinkTxt);


        //Splitting the text into three different Strings so I can use a specific part of the text as the link

        String part1 = getResources().getString(R.string.privacypart1);
        String part2 = getResources().getString(R.string.privacypart2);
        String part3 = getResources().getString(R.string.privacypart3);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            privacyLink.setText(Html.fromHtml("<p>"+part1+" "+"<a href=\"https://www.rsr.nl/index.php?page=privacy-wetgeving\">"+part2+"</a> "+part3+"</p>", Html.FROM_HTML_MODE_COMPACT));
            privacyLink.setMovementMethod(LinkMovementMethod.getInstance());
        } else {
            privacyLink.setText(part1+" "+part2+" "+part3);
//            privacyLink.setText(Html.fromHtml("<p>"+part1+" "+"<a href=\"https://www.rsr.nl/index.php?page=privacy-wetgeving\">"+part2+"</a> "+part3+"</p>", Html.FROM_HTML_MODE_COMPACT));
//            privacyLink.setMovementMethod(LinkMovementMethod.getInstance());
        }

        //Navigate to the Map Screen
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeScreen.this,MapScreen.class);
                startActivity(intent);
            }
        });

        //Pop Up the privacy info box
        privacyInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popUp.setVisibility(View.VISIBLE);
                tint.setVisibility(View.VISIBLE);
            }
        });

        //Close the privacy info box
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popUp.setVisibility(View.GONE);
                tint.setVisibility(View.GONE);
            }
        });


        infoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeScreen.this,InfoScreen.class);
                startActivity(intent);
            }
        });
    }
}
