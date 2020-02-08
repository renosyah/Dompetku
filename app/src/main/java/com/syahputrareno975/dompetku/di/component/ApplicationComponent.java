package com.syahputrareno975.dompetku.di.component;

import com.syahputrareno975.dompetku.BaseApp;
import com.syahputrareno975.dompetku.di.module.ApplicationModule;

import dagger.Component;

@Component(modules = { ApplicationModule.class })
public interface ApplicationComponent {
    void inject(BaseApp application);
    // add for each new base
}
