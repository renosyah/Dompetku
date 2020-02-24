package com.wawan.dompetku.di.component;

import com.wawan.dompetku.di.module.ActivityModule;
import com.wawan.dompetku.ui.activity.expenseActivity.ExpenseActivity;
import com.wawan.dompetku.ui.activity.incomeActivity.IncomeActivity;
import com.wawan.dompetku.ui.activity.mainMenuActivity.MainMenuActivity;
import com.wawan.dompetku.ui.activity.reportDiagramActivity.ReportDiagramActivity;
import com.wawan.dompetku.ui.activity.reportMenuActivity.ReportMenuActivity;
import com.wawan.dompetku.ui.activity.settingActivity.SettingActivity;

import dagger.Component;

@Component(modules = { ActivityModule.class })
public interface ActivityComponent {

    // add for each new activity
    public void inject(MainMenuActivity mainMenuActivity);
    public void inject(IncomeActivity incomeActivity);
    public void inject(ExpenseActivity expenseActivity);
    public void inject(ReportMenuActivity reportMenuActivity);
    public void inject(ReportDiagramActivity reportDiagramActivity);
    public void inject(SettingActivity settingActivity);
}
