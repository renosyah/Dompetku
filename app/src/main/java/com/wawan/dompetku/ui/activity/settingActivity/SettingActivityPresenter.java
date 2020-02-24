package com.wawan.dompetku.ui.activity.settingActivity;

public class SettingActivityPresenter implements SettingActivityContract.Presenter {

    private SettingActivityContract.View view;

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public void attach(SettingActivityContract.View view) {
        this.view = view;
    }

}
