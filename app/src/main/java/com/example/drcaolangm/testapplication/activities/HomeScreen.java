package com.example.drcaolangm.testapplication.activities;

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

import com.example.drcaolangm.testapplication.R;
import com.example.drcaolangm.testapplication.model.HomeScreenPresenterImp;
import com.example.drcaolangm.testapplication.presenter.HomeScreenPresenter;
import com.example.drcaolangm.testapplication.view.HomeScreenView;

public class HomeScreen extends AppCompatActivity implements HomeScreenView {

    RelativeLayout mMapButton;
    ImageView mPrivacyInfo;
    RelativeLayout mInfoButton;
    LinearLayout mPopUp;
    Button mTint;
    TextView mAccept;
    TextView mPrivacyLink;
    private HomeScreenPresenter homeScreenPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        mMapButton = findViewById(R.id.rl_mapbutton);
        mPrivacyInfo = findViewById(R.id.iv_privInfo);
        mInfoButton = findViewById(R.id.button3);
        mPopUp = findViewById(R.id.ll_popUp);
        mTint = findViewById(R.id.btn_tint);
        mAccept = findViewById(R.id.tv_accept);
        mPrivacyLink = findViewById(R.id.et_privLinkTxt);

        homeScreenPresenter = new HomeScreenPresenterImp(HomeScreen.this);

        //Splitting the text into three different Strings so I can use a specific part of the text as the link

        String part1 = getResources().getString(R.string.privacypart1);
        String part2 = getResources().getString(R.string.privacypart2);
        String part3 = getResources().getString(R.string.privacypart3);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mPrivacyLink.setText(Html.fromHtml("<p>"+part1+" "+"<a href=\"https://www.rsr.nl/index.php?page=privacy-wetgeving\">"+part2+"</a> "+part3+"</p>", Html.FROM_HTML_MODE_COMPACT));
            mPrivacyLink.setMovementMethod(LinkMovementMethod.getInstance());
        } else {
            mPrivacyLink.setText(part1+" "+part2+" "+part3);
//            mPrivacyLink.setText(Html.fromHtml("<p>"+part1+" "+"<a href=\"https://www.rsr.nl/index.php?page=privacy-wetgeving\">"+part2+"</a> "+part3+"</p>", Html.FROM_HTML_MODE_COMPACT));
//            mPrivacyLink.setMovementMethod(LinkMovementMethod.getInstance());
        }

        //Navigate to the Map Screen
        mMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeScreen.this,MapScreen.class);
                startActivity(intent);
            }
        });

        //Pop Up the privacy info box
        mPrivacyInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homeScreenPresenter.openPopUp();
            }
        });

        //Close the privacy info box
        mAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homeScreenPresenter.cancelPopUp();
            }
        });


        mInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeScreen.this,InfoScreen.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void showPopUp() {
        mPopUp.setVisibility(View.VISIBLE);
        mTint.setVisibility(View.VISIBLE);

    }

    @Override
    public void closePopUp() {
        mPopUp.setVisibility(View.GONE);
        mTint.setVisibility(View.GONE);
    }
}
