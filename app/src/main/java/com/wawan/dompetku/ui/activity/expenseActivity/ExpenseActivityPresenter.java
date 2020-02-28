package com.wawan.dompetku.ui.activity.expenseActivity;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.wawan.dompetku.model.transaction.TransactionModel;
import com.wawan.dompetku.model.transaction.TransactionViewModel;

// adalah class presenter untuk activity ini
// yg mana class ini akan menghandle
// fungsi-fungsi yg berkaitan dengan proses bisnis aplikasi
// seperti query ke db
public class ExpenseActivityPresenter implements ExpenseActivityContract.Presenter {

    // deklarasi variabel
    private ExpenseActivityContract.View view;
    private TransactionViewModel transactionViewModel;


    // fungsi yg akan dipanggil oleh view
    // untuk menyimpan data transaksi
    @Override
    public void addExpense(@NonNull TransactionModel t) {
        transactionViewModel.add(t);
        view.onAddExpense();
    }

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
    public void attach(ExpenseActivityContract.View view) {
        this.view = view;
        this.transactionViewModel = new ViewModelProvider((ViewModelStoreOwner) view).get(TransactionViewModel.class);
    }
}
