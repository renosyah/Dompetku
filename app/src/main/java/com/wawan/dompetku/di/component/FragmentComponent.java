package com.wawan.dompetku.di.component;

import com.wawan.dompetku.di.module.FragmentModule;

import dagger.Component;

@Component(modules = { FragmentModule.class })
public interface FragmentComponent {
    // add for each new fragment
}
