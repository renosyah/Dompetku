package com.syahputrareno975.dompetku.ui.activity.mainMenu;

public class MainMenuActivityPresenter implements MainMenuActivityContract.Presenter {

    private MainMenuActivityContract.View view;

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public void attach(MainMenuActivityContract.View view) {
        this.view = view;
    }
}
