package com.syahputrareno975.dompetku.di.module;

import android.app.Activity;
import com.syahputrareno975.dompetku.ui.activity.mainMenuActivity.MainMenuActivityContract;
import com.syahputrareno975.dompetku.ui.activity.mainMenuActivity.MainMenuActivityPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {
    private Activity activity;

    public ActivityModule(Activity activity){
       this.activity = activity;
    }

    @Provides
    public Activity provideActivity()  {
        return activity;
    }


    // add more
    @Provides
    public MainMenuActivityContract.Presenter provideMainMenuActivityPresenter() {
        return new MainMenuActivityPresenter();
    }
}
