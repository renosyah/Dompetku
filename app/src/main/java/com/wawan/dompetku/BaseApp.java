package com.wawan.dompetku;

import android.app.Application;
import com.wawan.dompetku.di.component.ApplicationComponent;
import com.wawan.dompetku.di.component.DaggerApplicationComponent;
import com.wawan.dompetku.di.module.ApplicationModule;

// class ini adalah base app
// untuk aplikasi ini
// normalnya setiap project memiliki setidaknya
// satu app atau lebih
// base app ini akan membedakan aplikasi satu dengan yg lainnya
public class BaseApp extends Application {

    // instance base app
    // menggunakan konsep singleton
    public static BaseApp instance = new BaseApp();

    // deklarasi variabel componen
    private ApplicationComponent component;


    // fungsi akan dipanggil
    // pertama kali
    @Override
    public void onCreate() {
        super.onCreate();

        // inisiasi instance
        instance = this;

        // memanggil fungsi setup
        setup();

        // jika aplikasi di run dengan mode debug
        // lakukan sesuatu
        if (BuildConfig.DEBUG){

            // atau tidak lakukan apapun
        }
    }

    // pemanggilan register
    // dependensi injeksi untuk base app ini
    private void setup(){
        component = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this)).build();
        component.inject(this);
    }
}
