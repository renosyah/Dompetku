package com.syahputrareno975.dompetku.ui.fragment.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.syahputrareno975.dompetku.R;
import com.syahputrareno975.dompetku.di.component.DaggerFragmentComponent;
import com.syahputrareno975.dompetku.di.component.FragmentComponent;
import com.syahputrareno975.dompetku.di.module.FragmentModule;

import javax.inject.Inject;

public class HomeFragment extends Fragment implements HomeFragmentContract.View {

    @Inject
    HomeFragmentContract.Presenter presenter;

    public static HomeFragment instance() { return new HomeFragment(); }
    private Context ctx;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_fragment,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initWidget(view);
    }

    private void initWidget(View v){
        this.ctx = getActivity();

        injectDependency();
        presenter.attach(this);
        presenter.subscribe();

    }

    @Override
    public void showProgress(Boolean show) {

    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.unsubscribe();
    }


    private void injectDependency(){
        FragmentComponent listcomponent = DaggerFragmentComponent.builder()
                .fragmentModule(new FragmentModule(this))
                .build();

        listcomponent.inject(this);
    }

}
