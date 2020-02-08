package com.syahputrareno975.dompetku.ui.fragment.setting;

public class SettingFragmentPresenter implements SettingFragmentContract.Presenter {

    private SettingFragmentContract.View view;

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public void attach(SettingFragmentContract.View view) {
        this.view = view;
    }
}