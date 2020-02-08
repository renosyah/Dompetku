package com.syahputrareno975.dompetku.di.module;

import androidx.fragment.app.Fragment;

import com.syahputrareno975.dompetku.ui.fragment.home.HomeFragmentContract;
import com.syahputrareno975.dompetku.ui.fragment.home.HomeFragmentPresenter;
import com.syahputrareno975.dompetku.ui.fragment.setting.SettingFragmentContract;
import com.syahputrareno975.dompetku.ui.fragment.setting.SettingFragmentPresenter;
import com.syahputrareno975.dompetku.ui.fragment.report.ReportFragmentContract;
import com.syahputrareno975.dompetku.ui.fragment.report.ReportFragmentPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class FragmentModule {
    private Fragment fragment;

    public FragmentModule(Fragment fragment){
        this.fragment = fragment;
    }

    @Provides
    public Fragment provideFragment() {
        return fragment;
    }

    // add more
    @Provides
    public HomeFragmentContract.Presenter provideHomeFragmentPresenter() {
        return new HomeFragmentPresenter();
    }

    @Provides
    public ReportFragmentContract.Presenter provideReportFragmentPresenter() {
        return new ReportFragmentPresenter();
    }

    @Provides
    public SettingFragmentContract.Presenter provideSettingFragmentPresenter() {
        return new SettingFragmentPresenter();
    }
}
