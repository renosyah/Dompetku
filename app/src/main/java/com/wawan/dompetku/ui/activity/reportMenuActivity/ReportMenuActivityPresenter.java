package com.wawan.dompetku.ui.activity.reportMenuActivity;

public class ReportMenuActivityPresenter implements ReportMenuActivityContract.Presenter {

    private ReportMenuActivityContract.View view;

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public void attach(ReportMenuActivityContract.View view) {
        this.view = view;
    }
}
