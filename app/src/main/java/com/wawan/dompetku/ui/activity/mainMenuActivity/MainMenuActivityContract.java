package com.wawan.dompetku.ui.activity.mainMenuActivity;

import androidx.annotation.Nullable;

import com.wawan.dompetku.base.BaseContract;
import com.wawan.dompetku.model.transaction.TransactionModel;

import java.sql.Date;

// adalah class contract untuk activity ini
// yg mana class ini akan menghandle
// fungsi-fungsi apa saja yg dibutkan untuk
// komunikasi antar view dengan presenter
public class MainMenuActivityContract {

    // inteface view yg akan diimplement oleh
    // view seperti aktivity atau fragment
    public interface View extends BaseContract.View {
        public void showProgress(Boolean show);
        public void showError(String error);

        // fungsi reponse yg akan digunakan
        // pada saat berhasil melakukan query
        // sisa saldo
        public void onGetBallance(@Nullable Double amount);

        // fungsi yg akan digunakan saat
        // berhasil mendapatkan transaksi
        // yg telah expired
        public void onGetTransactionExpired(@Nullable TransactionModel t);
    }

    // inteface presenter yg akan diimplement oleh
    // presenter seperti aktivity presenter atau fragment presenter
    public interface Presenter extends BaseContract.Presenter<View> {

        // fungsi yg akan digunakan
        // untuk melakukan query
        // sisa saldo oleh view
        public void getBallance();

        // fungsi yg akan digunakan untuk
        // melakukan query transaksi
        // yg telah expired oleh view
        public void getTransactionExpired(Date d);
    }
}
