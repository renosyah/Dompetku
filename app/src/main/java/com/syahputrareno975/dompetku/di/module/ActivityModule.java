package com.syahputrareno975.dompetku.di.module;

import android.app.Activity;

import com.syahputrareno975.dompetku.ui.activity.expenseActivity.ExpenseActivityContract;
import com.syahputrareno975.dompetku.ui.activity.expenseActivity.ExpenseActivityPresenter;
import com.syahputrareno975.dompetku.ui.activity.incomeActivity.IncomeActivityContract;
import com.syahputrareno975.dompetku.ui.activity.incomeActivity.IncomeActivityPresenter;
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

    @Provides
    public IncomeActivityContract.Presenter provideIncomeActivityPresenter() {
        return new IncomeActivityPresenter();
    }

    @Provides
    public ExpenseActivityContract.Presenter provideExpenseActivityPresenter() {
        return new ExpenseActivityPresenter();
    }
}
