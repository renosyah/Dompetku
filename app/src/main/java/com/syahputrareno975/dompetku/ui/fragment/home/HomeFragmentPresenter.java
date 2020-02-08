package com.syahputrareno975.dompetku.ui.fragment.home;


public class HomeFragmentPresenter implements HomeFragmentContract.Presenter {

    private HomeFragmentContract.View view;

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public void attach(HomeFragmentContract.View view) {
        this.view = view;
    }
}
