package com.wawan.dompetku.ui.activity.incomeActivity;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.wawan.dompetku.model.transaction.TransactionModel;
import com.wawan.dompetku.model.transaction.TransactionViewModel;

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
        Observer<List<TransactionModel>> observer = new Observer<List<TransactionModel>>() {
            @Override
            public void onChanged(List<TransactionModel> transactionModels) {
                view.onGetAllTransactionIncome(transactionModels);
            }
        };
        this.transactionViewModel.allIncome(offset, limit).observe((LifecycleOwner) view, observer);
    }

    // fungsi yg akan dipanggil oleh view
    // untuk menghapus data transaksi
    @Override
    public void deleteTransaction(TransactionModel t) {
        this.transactionViewModel.delete(t);
        this.view.onDeleteTransaction();
    }
}
