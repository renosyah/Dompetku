package com.syahputrareno975.dompetku.ui.activity.mainMenuActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.content.Intent;
import android.graphics.SurfaceTexture;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.syahputrareno975.dompetku.BuildConfig;
import com.syahputrareno975.dompetku.R;
import com.syahputrareno975.dompetku.di.component.ActivityComponent;
import com.syahputrareno975.dompetku.di.component.DaggerActivityComponent;
import com.syahputrareno975.dompetku.di.module.ActivityModule;
import com.syahputrareno975.dompetku.model.menu.MenuModel;
import com.syahputrareno975.dompetku.ui.activity.expenseActivity.ExpenseActivity;
import com.syahputrareno975.dompetku.ui.activity.incomeActivity.IncomeActivity;
import com.syahputrareno975.dompetku.ui.activity.reportMenuActivity.ReportMenuActivity;
import com.syahputrareno975.dompetku.ui.adapter.menuAdapter.MenuAdapter;

import java.text.DecimalFormat;

import javax.inject.Inject;

import static com.syahputrareno975.dompetku.util.Formatter.formatter;

public class MainMenuActivity extends AppCompatActivity implements MainMenuActivityContract.View {

    @Inject
    MainMenuActivityContract.Presenter presenter;

    private Context context;
    private ImageView setting;

    private CardView ballanceLayout;
    private TextView balance;

    private RecyclerView menu;


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
                startActivity(new Intent(context,IncomeActivity.class));
            }
        });

        menu = findViewById(R.id.list_menu_recycleview);
        menu.setAdapter(new MenuAdapter(context,MenuAdapter.MAIN_MENU_LIST, new MenuAdapter.OnMainMenuAdapterItemClickListener() {
            @Override
            public void onItemClick(@NonNull MenuModel m, int pos) {
                switch (m.Id){
                    case MenuModel.ID_FOOD_EXPENSE:
                    case MenuModel.ID_TRANSPORT_EXPENSE:
                    case MenuModel.ID_COMMON_NEED_EXPENSE:

                        Intent i = new Intent(context, ExpenseActivity.class);
                        i.putExtra("menu",m);
                        startActivity(i);

                        break;
                    case MenuModel.ID_REPORT:

                        startActivity(new Intent(context, ReportMenuActivity.class));

                        break;
                        default:
                            break;
                }
            }
        }));
        menu.setLayoutManager(new GridLayoutManager(context,2));

        presenter.getBallance();
    }


    @Override
    public void onGetBallance(Double total) {
        if (total!=null) balance.setText(BuildConfig.CURRENCY  + " " + formatter.format(total));
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
