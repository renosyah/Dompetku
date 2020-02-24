package com.wawan.dompetku.ui.activity.incomeActivity;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.wawan.dompetku.model.transaction.TransactionModel;
import com.wawan.dompetku.model.transaction.TransactionViewModel;

import java.util.List;

public class IncomeActivityPresenter implements IncomeActivityContract.Presenter {

    private IncomeActivityContract.View view;
    private TransactionViewModel transactionViewModel;

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public void attach(IncomeActivityContract.View view) {
        this.view = view;
        this.transactionViewModel = new ViewModelProvider((ViewModelStoreOwner) view).get(TransactionViewModel.class);
    }


    @Override
    public void addIncome(@NonNull TransactionModel t) {
        this.transactionViewModel.add(t);
        view.onAddIncome();
    }

    @Override
    public void getAllTransactionIncome(int offset, int limit) {
        Observer<List<TransactionModel>> observer = new Observer<List<TransactionModel>>() {
            @Override
            public void onChanged(List<TransactionModel> transactionModels) {
                view.onGetAllTransactionIncome(transactionModels);
            }
        };
        this.transactionViewModel.allIncome(offset, limit).observe((LifecycleOwner) view, observer);
    }

    @Override
    public void deleteTransaction(TransactionModel t) {
        this.transactionViewModel.delete(t);
        this.view.onDeleteTransaction();
    }
}
