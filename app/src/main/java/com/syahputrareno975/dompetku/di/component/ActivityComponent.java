package com.syahputrareno975.dompetku.di.component;

import com.syahputrareno975.dompetku.di.module.ActivityModule;
import com.syahputrareno975.dompetku.ui.activity.mainMenu.MainMenuActivity;

import dagger.Component;

@Component(modules = { ActivityModule.class })
public interface ActivityComponent {
    // add for each new activity

    public void inject(MainMenuActivity mainMenuActivity);
}
