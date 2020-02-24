package com.wawan.dompetku;

import android.app.Application;
import com.wawan.dompetku.di.component.ApplicationComponent;
import com.wawan.dompetku.di.component.DaggerApplicationComponent;
import com.wawan.dompetku.di.module.ApplicationModule;

public class BaseApp extends Application {

    public static BaseApp instance = new BaseApp();
    private ApplicationComponent component;


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        setup();

        if (BuildConfig.DEBUG){

        }
    }

    private void setup(){
        component = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this)).build();
        component.inject(this);
    }
}
