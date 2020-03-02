package com.wawan.dompetku.ui.activity.incomeActivity;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.wawan.dompetku.model.transaction.TransactionModel;
import com.wawan.dompetku.model.transaction.TransactionViewModel;
import com.wawan.dompetku.util.UtilFunction;

import java.util.List;

// adalah class presenter untuk activity ini
// yg mana class ini akan menghandle
// fungsi-fungsi yg berkaitan dengan proses bisnis aplikasi
// seperti query ke db
public class IncomeActivityPresenter implements IncomeActivityContract.Presenter {


    // deklarasi variabel
    private IncomeActivityContract.View view;
    private TransactionViewModel transactionViewModel;

    // untuk saat ini kosong
    // belum dibutuhkan
    @Override
    public void subscribe() {

    }


    // untuk saat ini kosong
    // belum dibutuhkan
    @Override
    public void unsubscribe() {

    }

    // fungsi yg akan menrima data view
    // yg nantinya akan digunakan oleh viewmodel
    // atau untuk keperluhan bisnis aplikasi
    // lainya
    @Override
    public void attach(IncomeActivityContract.View view) {
        this.view = view;
        this.transactionViewModel = new ViewModelProvider((ViewModelStoreOwner) view).get(TransactionViewModel.class);
    }

    // fungsi yg akan dipanggil oleh view
    // untuk menyimpan data transaksi
    @Override
    public void addIncome(@NonNull TransactionModel t) {
        this.transactionViewModel.add(t);
        view.onAddIncome();
    }

    // fungsi yg akan dipanggil oleh view
    // untuk query data transaksi
    @Override
    public void getAllTransactionIncome(int offset, int limit) {
        this.transactionViewModel.allIncome(offset, limit, new UtilFunction.Unit<List<TransactionModel>>() {
            @Override
            public void invoke(@Nullable List<TransactionModel> o) {
                ((Activity) view).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        view.onGetAllTransactionIncome(o);
                    }
                });
            }
        });
    }

    // fungsi yg akan dipanggil oleh view
    // untuk menghapus data transaksi
    @Override
    public void deleteTransaction(TransactionModel t) {
        this.transactionViewModel.delete(t);
        this.view.onDeleteTransaction();
    }
}
