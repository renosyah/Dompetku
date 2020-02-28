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

    // inteface view yg akan diimplement oleh
    // view seperti aktivity atau fragment
    public interface View extends BaseContract.View {
        public void showProgress(Boolean show);
        public void showError(String error);

        // fungsi reponse yg akan digunakan
        // pada saat berhasil melakukan query
        // data transaksi
        public void onGetAllTransaction(@Nullable List<TransactionModel> t);

        // fungsi reponse yg akan digunakan
        // pada saat berhasil melakukan query
        // sisa saldo
        public void onGetBallance(@Nullable Double amount);

        // fungsi reponse yg akan digunakan
        // pada saat berhasil melakukan query
        // data total pemasukkan dan pengeluaran
        public void onGetIncomeExpense(@Nullable IncomeAndExpenseModel i);

        // fungsi reponse yg akan digunakan
        // pada saat berhasil melakukan query
        // data transaksi untuk diagram waterfall
        public void onGetAllTransactionForWaterfall(@Nullable List<TransactionModel> t);

        // fungsi reponse yg akan digunakan
        // pada saat berhasil melakukan query
        // data transaksi untuk diagram line
        public void onGetAllTransactionForLine(@Nullable List<TransactionModel> t);

        // fungsi reponse yg akan digunakan
        // pada saat berhasil menghapus
        // data transaksi
        public void onDeleteTransaction();
    }

    // inteface presenter yg akan diimplement oleh
    // presenter seperti aktivity presenter atau fragment presenter
    public interface Presenter extends BaseContract.Presenter<View> {

        // fungsi yg akan digunakan
        // untuk melakukan query
        // data transaksi dengan offset dan limit
        public void getAllTransaction(int offset, int limit);

        // fungsi yg akan digunakan
        // untuk melakukan query
        // data transaksi untuk diagram waterfall
        public void getAllTransactionForWaterfall();

        // fungsi yg akan digunakan
        // untuk melakukan query
        // data transaksi untuk diagram line
        public void getAllTransactionForLine();

        // fungsi yg akan digunakan
        // untuk melakukan query
        // sisa saldo oleh view
        public void getBallance();

        // fungsi yg akan digunakan
        // untuk melakukan query
        // data saldo pemasukkan
        // dan pengeluaran
        public void getIncomeExpense();

        // fungsi yg akan digunakan
        // untuk menghapus data transaksi
        public void deleteTransaction(TransactionModel t);
    }
}