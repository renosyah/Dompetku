package com.syahputrareno975.dompetku.di.component;

import com.syahputrareno975.dompetku.di.module.FragmentModule;
import com.syahputrareno975.dompetku.ui.fragment.home.HomeFragment;
import com.syahputrareno975.dompetku.ui.fragment.setting.SettingFragment;
import com.syahputrareno975.dompetku.ui.fragment.report.ReportFragment;

import dagger.Component;

@Component(modules = { FragmentModule.class })
public interface FragmentComponent {
    // add for each new fragment
    public void inject(HomeFragment homeFragment);
    public void inject(ReportFragment reportFragment);
    public void inject(SettingFragment settingFragment);
}
