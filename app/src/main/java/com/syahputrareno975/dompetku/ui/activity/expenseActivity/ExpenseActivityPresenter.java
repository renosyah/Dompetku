package com.syahputrareno975.dompetku.ui.activity.expenseActivity;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;

import com.syahputrareno975.dompetku.model.transaction.TransactionModel;
import com.syahputrareno975.dompetku.model.transaction.TransactionViewModel;

public class ExpenseActivityPresenter implements ExpenseActivityContract.Presenter {

    private ExpenseActivityContract.View view;
    private TransactionViewModel transactionViewModel;

    @Override
    public void addExpense(@NonNull TransactionModel t) {
        transactionViewModel.add(t);
        view.onAddExpense();
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public void attach(ExpenseActivityContract.View view) {
        this.view = view;
        this.transactionViewModel = new ViewModelProvider((ViewModelStoreOwner) view).get(TransactionViewModel.class);
    }
}
