package com.syahputrareno975.dompetku.di.component;

import com.syahputrareno975.dompetku.di.module.ActivityModule;
import com.syahputrareno975.dompetku.ui.activity.expenseActivity.ExpenseActivity;
import com.syahputrareno975.dompetku.ui.activity.incomeActivity.IncomeActivity;
import com.syahputrareno975.dompetku.ui.activity.mainMenuActivity.MainMenuActivity;

import dagger.Component;

@Component(modules = { ActivityModule.class })
public interface ActivityComponent {

    // add for each new activity
    public void inject(MainMenuActivity mainMenuActivity);
    public void inject(IncomeActivity incomeActivity);
    public void inject(ExpenseActivity expenseActivity);
}
