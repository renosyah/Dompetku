package com.syahputrareno975.dompetku.ui.activity.expenseActivity;

import androidx.annotation.NonNull;

import com.syahputrareno975.dompetku.base.BaseContract;
import com.syahputrareno975.dompetku.model.transaction.TransactionModel;

public class ExpenseActivityContract {
    public interface View extends BaseContract.View {
        public void showProgress(Boolean show);
        public void showError(String error);
        void onAddExpense();

    }

    public interface Presenter extends BaseContract.Presenter<View> {
        void addExpense(@NonNull TransactionModel t);
    }
}
