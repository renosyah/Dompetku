package com.syahputrareno975.dompetku.ui.activity.mainMenu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.syahputrareno975.dompetku.R;
import com.syahputrareno975.dompetku.di.component.ActivityComponent;
import com.syahputrareno975.dompetku.di.component.DaggerActivityComponent;
import com.syahputrareno975.dompetku.di.module.ActivityModule;

import javax.inject.Inject;

public class MainMenuActivity extends AppCompatActivity implements MainMenuActivityContract.View {

    @Inject
    MainMenuActivityContract.Presenter presenter;

    private Context context;
    private boolean isExit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        initWidget();
    }

    private void initWidget(){
        this.context = this;

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
    protected void onDestroy() {
        super.onDestroy();
        presenter.unsubscribe();
    }


    @Override
    public void onBackPressed() {
        if (!isExit){
            Toast.makeText(context,getString(R.string.press_to_exit),Toast.LENGTH_SHORT).show();
            isExit = true;
            return;
        }
        super.onBackPressed();
    }

    private void injectDependency(){
        ActivityComponent listcomponent = DaggerActivityComponent.builder()
                .activityModule(new ActivityModule(this))
                .build();

        listcomponent.inject(this);
    }
}
