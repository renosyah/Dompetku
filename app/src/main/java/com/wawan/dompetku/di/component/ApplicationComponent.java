package com.wawan.dompetku.di.component;

import com.wawan.dompetku.BaseApp;
import com.wawan.dompetku.di.module.ApplicationModule;

import dagger.Component;

@Component(modules = { ApplicationModule.class })
public interface ApplicationComponent {
    void inject(BaseApp application);
    // add for each new base
}
