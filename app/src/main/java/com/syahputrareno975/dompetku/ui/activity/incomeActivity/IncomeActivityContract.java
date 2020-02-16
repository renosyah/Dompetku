package com.syahputrareno975.dompetku.ui.activity.incomeActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.syahputrareno975.dompetku.base.BaseContract;
import com.syahputrareno975.dompetku.model.transaction.TransactionModel;

import java.util.List;

public class IncomeActivityContract {
    public interface View extends BaseContract.View {
        public void showProgress(Boolean show);
        public void showError(String error);
        void onAddIncome();
        void onDeleteTransaction();
        void onGetAllTransactionIncome(@Nullable List<TransactionModel> t);

    }

    public interface Presenter extends BaseContract.Presenter<View> {
        void addIncome(@NonNull TransactionModel t);
        void getAllTransactionIncome(int offset,int limit);
        void deleteTransaction(TransactionModel t);
    }
}
