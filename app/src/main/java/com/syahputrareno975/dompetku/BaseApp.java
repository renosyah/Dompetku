package com.syahputrareno975.dompetku;

import android.app.Application;
import com.syahputrareno975.dompetku.di.component.ApplicationComponent;
import com.syahputrareno975.dompetku.di.component.DaggerApplicationComponent;
import com.syahputrareno975.dompetku.di.module.ApplicationModule;

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
