package com.syahputrareno975.dompetku.ui.fragment.report;

public class ReportFragmentPresenter implements ReportFragmentContract.Presenter {

    private ReportFragmentContract.View view;

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public void attach(ReportFragmentContract.View view) {
        this.view = view;
    }
}