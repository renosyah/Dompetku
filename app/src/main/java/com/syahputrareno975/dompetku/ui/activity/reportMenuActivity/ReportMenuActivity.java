package com.syahputrareno975.dompetku.ui.activity.reportMenuActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.syahputrareno975.dompetku.R;
import com.syahputrareno975.dompetku.di.component.ActivityComponent;
import com.syahputrareno975.dompetku.di.component.DaggerActivityComponent;
import com.syahputrareno975.dompetku.di.module.ActivityModule;
import com.syahputrareno975.dompetku.model.menu.MenuModel;
import com.syahputrareno975.dompetku.ui.activity.expenseActivity.ExpenseActivity;
import com.syahputrareno975.dompetku.ui.activity.reportDiagramActivity.ReportDiagramActivity;
import com.syahputrareno975.dompetku.ui.adapter.menuAdapter.MenuAdapter;

import javax.inject.Inject;

public class ReportMenuActivity extends AppCompatActivity implements ReportMenuActivityContract.View {

    @Inject
    public ReportMenuActivityContract.Presenter presenter;

    private Context context;
    private ImageView back;
    private RecyclerView listMenuReport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_menu);
        initWidget();
    }

    private void initWidget(){
        this.context = this;

        injectDependency();
        presenter.attach(this);
        presenter.subscribe();

        back  = findViewById(R.id.back_imageview);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        listMenuReport = findViewById(R.id.list_report_menu_recycleview);
        listMenuReport.setAdapter(new MenuAdapter(context,MenuAdapter.REPORT_MENU_LIST, new MenuAdapter.OnMainMenuAdapterItemClickListener() {
            @Override
            public void onItemClick(@NonNull MenuModel m, int pos) {
                switch (m.Id){
                    case MenuModel.ID_REPORT_LIST:
                    case MenuModel.ID_REPORT_LINE_DIAGRAM:
                    case MenuModel.ID_REPORT_PIE_DIAGRAM:
                    case MenuModel.ID_REPORT_WATERFALL_DIAGRAM:

                        Intent i = new Intent(context, ReportDiagramActivity.class);
                        i.putExtra("menu",m);
                        startActivity(i);

                    default:
                        break;
                }
            }
        }));
        listMenuReport.setLayoutManager(new GridLayoutManager(context,2));
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
