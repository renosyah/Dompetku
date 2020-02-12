package com.syahputrareno975.dompetku.ui.activity.incomeActivity;

import androidx.annotation.NonNull;

import com.syahputrareno975.dompetku.base.BaseContract;
import com.syahputrareno975.dompetku.model.transaction.TransactionModel;

public class IncomeActivityContract {
    public interface View extends BaseContract.View {
        public void showProgress(Boolean show);
        public void showError(String error);
        void onAddIncome();

    }

    public interface Presenter extends BaseContract.Presenter<View> {
        void addIncome(@NonNull TransactionModel t);
    }
}
