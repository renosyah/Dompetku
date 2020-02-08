package com.syahputrareno975.dompetku.ui.activity.mainMenu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.syahputrareno975.dompetku.R;
import com.syahputrareno975.dompetku.di.component.ActivityComponent;
import com.syahputrareno975.dompetku.di.component.DaggerActivityComponent;
import com.syahputrareno975.dompetku.di.module.ActivityModule;
import com.syahputrareno975.dompetku.model.user.UserModel;
import com.syahputrareno975.dompetku.model.user.UserViewModel;
import com.syahputrareno975.dompetku.ui.fragment.home.HomeFragment;
import com.syahputrareno975.dompetku.ui.fragment.setting.SettingFragment;
import com.syahputrareno975.dompetku.ui.fragment.report.ReportFragment;

import javax.inject.Inject;

public class MainMenuActivity extends AppCompatActivity implements MainMenuActivityContract.View {

    @Inject
    MainMenuActivityContract.Presenter presenter;

    private Context context;
    private BottomNavigationView menu_bottom_nav_view;

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

        changeFragment(HomeFragment.instance());

        this.menu_bottom_nav_view = findViewById(R.id.menu_bottom_nav_view);
        this.menu_bottom_nav_view.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.menu_home :
                        changeFragment(HomeFragment.instance());
                        break;

                    case R.id.menu_report :
                        changeFragment(ReportFragment.instance());
                        break;

                    case R.id.menu_setting_mini:
                        changeFragment(SettingFragment.instance());
                        break;
                }
                return true;
            }
        });
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

    private void changeFragment(Fragment fragment){
        isExit = false;
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_fragment_frame_layout, fragment);
        fragmentTransaction.commit();
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
