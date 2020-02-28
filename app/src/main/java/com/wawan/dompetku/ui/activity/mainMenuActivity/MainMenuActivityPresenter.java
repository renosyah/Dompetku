package com.wawan.dompetku.ui.activity.mainMenuActivity;

import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.wawan.dompetku.model.transaction.TransactionModel;
import com.wawan.dompetku.model.transaction.TransactionViewModel;
import com.wawan.dompetku.util.UtilFunction;

import java.sql.Date;

// adalah class presenter untuk activity ini
// yg mana class ini akan menghandle
// fungsi-fungsi yg berkaitan dengan proses bisnis aplikasi
// seperti query ke db
public class MainMenuActivityPresenter implements MainMenuActivityContract.Presenter {

    // deklarasi variabel
    private MainMenuActivityContract.View view;
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
    public void attach(MainMenuActivityContract.View view) {
        this.view = view;
        this.transactionViewModel = new ViewModelProvider((ViewModelStoreOwner)view).get(TransactionViewModel.class);
    }

    // fungsi yg akan digunakan
    // untuk melakukan query
    // sisa saldo oleh view
    @Override
    public void getBallance() {
        this.transactionViewModel.total().observe((LifecycleOwner) view, new Observer<Double>() {
            @Override
            public void onChanged(Double t) {
                view.onGetBallance(t);
            }
        });
    }

    // fungsi yg akan digunakan untuk
    // melakukan query transaksi
    // yg telah expired oleh view
    @Override
    public void getTransactionExpired(Date d) {
        this.transactionViewModel.oneExpiredTransaction(d, new UtilFunction.Unit<TransactionModel>() {
            @Override
            public void invoke(@Nullable TransactionModel o) {
                view.onGetTransactionExpired(o);
            }
        });
    }
}
