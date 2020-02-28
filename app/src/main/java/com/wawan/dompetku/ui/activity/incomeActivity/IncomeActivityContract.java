package com.wawan.dompetku.ui.activity.incomeActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.wawan.dompetku.base.BaseContract;
import com.wawan.dompetku.model.transaction.TransactionModel;

import java.util.List;

// adalah class contract untuk activity ini
// yg mana class ini akan menghandle
// fungsi-fungsi apa saja yg dibutkan untuk
// komunikasi antar view dengan presenter
public class IncomeActivityContract {

    // inteface view yg akan diimplement oleh
    // view seperti aktivity atau fragment
    public interface View extends BaseContract.View {
        public void showProgress(Boolean show);
        public void showError(String error);

        // fungsi reponse yg akan gunakan
        // pada saat transaksi pemasukkan
        // berhasil diinput
        public void onAddIncome();

        // fungsi reponse yg akan gunakan
        // pada saat transaksi pemasukkan
        // berhasil dihapus
        public void onDeleteTransaction();

        // fungsi reponse yg akan gunakan
        // pada saat transaksi pemasukkan
        // berhasil diquery
        public void onGetAllTransactionIncome(@Nullable List<TransactionModel> t);

    }

    // inteface presenter yg akan diimplement oleh
    // presenter seperti aktivity presenter atau fragment presenter
    public interface Presenter extends BaseContract.Presenter<View> {

        // fungsi yg akan gunakan
        // oleh view untuk menyimpan data
        // transaksi pemasukkan
        public void addIncome(@NonNull TransactionModel t);

        // fungsi yg akan gunakan
        // oleh view untuk melakukan query
        // transaksi pemasukkan
        public void getAllTransactionIncome(int offset,int limit);

        // fungsi yg akan gunakan
        // oleh view untuk menghapus data
        // transaksi pemasukkan
        public void deleteTransaction(TransactionModel t);
    }
}
