package com.wawan.dompetku.ui.activity.reportMenuActivity;

// adalah class presenter untuk activity ini
// yg mana class ini akan menghandle
// fungsi-fungsi yg berkaitan dengan proses bisnis aplikasi
// seperti query ke db
public class ReportMenuActivityPresenter implements ReportMenuActivityContract.Presenter {

    // deklarasi variabel
    private ReportMenuActivityContract.View view;

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
    public void attach(ReportMenuActivityContract.View view) {
        this.view = view;
    }
}
