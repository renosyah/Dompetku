package com.wawan.dompetku.ui.activity.reportDiagramActivity;

import androidx.annotation.Nullable;

import com.wawan.dompetku.base.BaseContract;
import com.wawan.dompetku.model.transaction.IncomeAndExpenseModel;
import com.wawan.dompetku.model.transaction.TransactionModel;

import java.util.List;

// adalah class contract untuk activity ini
// yg mana class ini akan menghandle
// fungsi-fungsi apa saja yg dibutkan untuk
// komunikasi antar view dengan presenter
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
        public void getAllTransactionForWaterfall();
        public void getAllTransactionForLine();
        public void getBallance();
        public void getIncomeExpense();
        public void deleteTransaction(TransactionModel t);
    }
}