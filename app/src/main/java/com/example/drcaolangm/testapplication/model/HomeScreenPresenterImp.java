package com.example.drcaolangm.testapplication.model;

import com.example.drcaolangm.testapplication.HomeScreen;
import com.example.drcaolangm.testapplication.presenter.HomeScreenPresenter;
import com.example.drcaolangm.testapplication.view.HomeScreenView;

public class HomeScreenPresenterImp implements HomeScreenPresenter {

    private HomeScreenView homeScreenView;

    public HomeScreenPresenterImp(HomeScreenView homeScreenView) {
        this.homeScreenView = homeScreenView;
    }

    @Override
    public void openPopUp() {
        homeScreenView.showPopUp();
    }

    @Override
    public void cancelPopUp() {
        homeScreenView.closePopUp();
    }

    @Override
    public void goToMapScreen() {

    }

    @Override
    public void goToPrivacySettings() {

    }
}
