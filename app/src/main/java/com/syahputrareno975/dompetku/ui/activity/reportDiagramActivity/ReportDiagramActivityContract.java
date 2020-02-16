package com.syahputrareno975.dompetku.ui.activity.reportDiagramActivity;

import androidx.annotation.Nullable;

import com.syahputrareno975.dompetku.base.BaseContract;
import com.syahputrareno975.dompetku.model.transaction.IncomeAndExpenseModel;
import com.syahputrareno975.dompetku.model.transaction.TransactionModel;

import java.sql.Date;
import java.util.List;

public class ReportDiagramActivityContract {
    public interface View extends BaseContract.View {
        public void showProgress(Boolean show);
        public void showError(String error);
        public void onGetAllTransaction(@Nullable List<TransactionModel> t);
        public void onGetBallance(@Nullable Double amount);
        public void onGetIncomeExpense(@Nullable IncomeAndExpenseModel i);
        public void onGetAllTransactionForWaterfall(@Nullable List<TransactionModel> t);
        public void onGetAllTransactionForLine(@Nullable List<TransactionModel> t);
        public void onDeleteTransaction();
    }

    public interface Presenter extends BaseContract.Presenter<View> {
        public void getAllTransaction(int offset, int limit);
        public void getAllTransactionForWaterfall(Date start, Date end);
        public void getAllTransactionForLine(Date start, Date end);
        public void getBallance();
        public void getIncomeExpense();
        public void deleteTransaction(TransactionModel t);
    }
}