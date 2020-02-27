package com.wawan.dompetku.ui.activity.expenseActivity;

import androidx.annotation.NonNull;

import com.wawan.dompetku.base.BaseContract;
import com.wawan.dompetku.model.transaction.TransactionModel;


// adalah class contract untuk activity ini
// yg mana class ini akan menghandle
// fungsi-fungsi apa saja yg dibutkan untuk
// komunikasi antar view dengan presenter
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
