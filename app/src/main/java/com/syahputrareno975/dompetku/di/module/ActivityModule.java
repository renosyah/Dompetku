package com.syahputrareno975.dompetku.di.module;

import android.app.Activity;

import com.syahputrareno975.dompetku.ui.activity.expenseActivity.ExpenseActivityContract;
import com.syahputrareno975.dompetku.ui.activity.expenseActivity.ExpenseActivityPresenter;
import com.syahputrareno975.dompetku.ui.activity.incomeActivity.IncomeActivityContract;
import com.syahputrareno975.dompetku.ui.activity.incomeActivity.IncomeActivityPresenter;
import com.syahputrareno975.dompetku.ui.activity.mainMenuActivity.MainMenuActivityContract;
import com.syahputrareno975.dompetku.ui.activity.mainMenuActivity.MainMenuActivityPresenter;
import com.syahputrareno975.dompetku.ui.activity.reportDiagramActivity.ReportDiagramActivityContract;
import com.syahputrareno975.dompetku.ui.activity.reportDiagramActivity.ReportDiagramActivityPresenter;
import com.syahputrareno975.dompetku.ui.activity.reportMenuActivity.ReportMenuActivityContract;
import com.syahputrareno975.dompetku.ui.activity.reportMenuActivity.ReportMenuActivityPresenter;
import com.syahputrareno975.dompetku.ui.activity.settingActivity.SettingActivityContract;
import com.syahputrareno975.dompetku.ui.activity.settingActivity.SettingActivityPresenter;

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

    @Provides
    public IncomeActivityContract.Presenter provideIncomeActivityPresenter() {
        return new IncomeActivityPresenter();
    }

    @Provides
    public ExpenseActivityContract.Presenter provideExpenseActivityPresenter() {
        return new ExpenseActivityPresenter();
    }

    @Provides
    public ReportMenuActivityContract.Presenter provideReportMenuActivityPresenter() {
        return new ReportMenuActivityPresenter();
    }

    @Provides
    public ReportDiagramActivityContract.Presenter provideReportDiagramActivityPresenter() {
        return new ReportDiagramActivityPresenter();
    }

    @Provides
    public SettingActivityContract.Presenter provideSettingActivityPresenter() {
        return new SettingActivityPresenter();
    }
}
