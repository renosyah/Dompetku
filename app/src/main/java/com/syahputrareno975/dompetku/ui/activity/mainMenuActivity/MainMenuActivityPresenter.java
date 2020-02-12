package com.syahputrareno975.dompetku.ui.activity.mainMenuActivity;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.syahputrareno975.dompetku.model.transaction.TransactionViewModel;

public class MainMenuActivityPresenter implements MainMenuActivityContract.Presenter {

    private MainMenuActivityContract.View view;
    private TransactionViewModel transactionViewModel;

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public void attach(MainMenuActivityContract.View view) {
        this.view = view;
        this.transactionViewModel = new ViewModelProvider((ViewModelStoreOwner)view).get(TransactionViewModel.class);
    }


    @Override
    public void getBallance() {
        this.transactionViewModel.total().observe((LifecycleOwner) view, new Observer<Double>() {
            @Override
            public void onChanged(Double aDouble) {
                view.onGetBallance(aDouble);
            }
        });
    }
}
