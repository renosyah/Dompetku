package com.syahputrareno975.dompetku.ui.activity.mainMenuActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.syahputrareno975.dompetku.BuildConfig;
import com.syahputrareno975.dompetku.R;
import com.syahputrareno975.dompetku.di.component.ActivityComponent;
import com.syahputrareno975.dompetku.di.component.DaggerActivityComponent;
import com.syahputrareno975.dompetku.di.module.ActivityModule;
import com.syahputrareno975.dompetku.model.menu.MenuModel;
import com.syahputrareno975.dompetku.ui.adapter.menuAdapter.MenuAdapter;

import java.text.DecimalFormat;

import javax.inject.Inject;

public class MainMenuActivity extends AppCompatActivity implements MainMenuActivityContract.View {

    @Inject
    MainMenuActivityContract.Presenter presenter;

    private Context context;
    private ImageView setting;

    private CardView ballanceLayout;
    private TextView balance;

    private RecyclerView menu;

    private DecimalFormat formatter = new DecimalFormat("##.###");

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

        setting = findViewById(R.id.setting_imageview);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        balance = findViewById(R.id.total_ballance_textview);
        balance.setText(BuildConfig.CURRENCY  + " "+formatter.format(0));

        ballanceLayout = findViewById(R.id.balance_layout_cardview);
        ballanceLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        menu = findViewById(R.id.list_menu_recycleview);
        menu.setAdapter(new MenuAdapter(context, new MenuAdapter.OnMainMenuAdapterItemClickListener() {
            @Override
            public void onItemClick(@NonNull MenuModel m, int pos) {
                switch (m.Id){
                    case MenuModel.ID_FOOD_EXPENSE:
                        break;
                    case MenuModel.ID_TRANSPORT_EXPENSE:
                        break;
                    case MenuModel.ID_COMMON_NEED_EXPENSE:
                        break;
                    case MenuModel.ID_REPORT:
                        break;
                        default:
                            break;
                }
            }
        }));
        menu.setLayoutManager(new GridLayoutManager(context,2));

    }

    @Override
    public void onGetBallance(Double total) {
        if (total!=null) balance.setText(BuildConfig.CURRENCY  + " "+formatter.format(total));
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


    private void injectDependency(){
        ActivityComponent listcomponent = DaggerActivityComponent.builder()
                .activityModule(new ActivityModule(this))
                .build();

        listcomponent.inject(this);
    }
}
