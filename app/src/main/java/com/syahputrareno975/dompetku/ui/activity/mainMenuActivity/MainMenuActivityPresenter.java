package com.syahputrareno975.dompetku.ui.activity.mainMenuActivity;

import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.syahputrareno975.dompetku.model.transaction.TransactionModel;
import com.syahputrareno975.dompetku.model.transaction.TransactionViewModel;
import com.syahputrareno975.dompetku.util.UtilFunction;

import java.sql.Date;

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
            public void onChanged(Double t) {
                view.onGetBallance(t);
            }
        });
    }

    @Override
    public void getTransactionExpired(Date d) {
        this.transactionViewModel.oneExpiredTransaction(d, new UtilFunction.Unit<TransactionModel>() {
            @Override
            public void invoke(@Nullable TransactionModel o) {
                view.onGetTransactionExpired(o);
            }
        });
    }
}
