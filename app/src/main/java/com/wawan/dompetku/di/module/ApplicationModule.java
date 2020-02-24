package com.wawan.dompetku.di.module;

import android.app.Application;
import com.wawan.dompetku.BaseApp;
import com.wawan.dompetku.di.scope.PerApplication;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {
    private BaseApp baseApp;

    public ApplicationModule(BaseApp baseApp){
        this.baseApp = baseApp;
    }

    @Provides
    @Singleton
    @PerApplication
    public Application provideApplication() {
        return baseApp;
    }
}
