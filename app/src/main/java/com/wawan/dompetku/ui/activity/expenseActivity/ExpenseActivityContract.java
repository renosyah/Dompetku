package com.wawan.dompetku.ui.activity.expenseActivity;

import androidx.annotation.NonNull;

import com.wawan.dompetku.base.BaseContract;
import com.wawan.dompetku.model.transaction.TransactionModel;


// adalah class contract untuk activity ini
// yg mana class ini akan menghandle
// fungsi-fungsi apa saja yg dibutkan untuk
// komunikasi antar view dengan presenter
public class ExpenseActivityContract {

    // inteface view yg akan diimplement oleh
    // view seperti aktivity atau fragment
    public interface View extends BaseContract.View {
        public void showProgress(Boolean show);
        public void showError(String error);

        // fungsi response yg akan digunakan
        // pada saat transaksi
        // pengeluaran berhasil disimpan
        void onAddExpense();

    }

    // inteface presenter yg akan diimplement oleh
    // presenter seperti aktivity presenter atau fragment presenter
    public interface Presenter extends BaseContract.Presenter<View> {

        // fungsi yg akan digunakan oleh view
        // pada saat menyimpan transaksi
        // pengeluaran ke presenter
        void addExpense(@NonNull TransactionModel t);
    }
}
