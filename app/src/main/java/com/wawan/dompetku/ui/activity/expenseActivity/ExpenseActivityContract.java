package com.wawan.dompetku.ui.activity.expenseActivity;

import androidx.annotation.NonNull;

import com.wawan.dompetku.base.BaseContract;
import com.wawan.dompetku.model.transaction.TransactionModel;

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
