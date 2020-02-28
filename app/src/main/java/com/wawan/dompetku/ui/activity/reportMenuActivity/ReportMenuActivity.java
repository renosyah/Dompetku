package com.wawan.dompetku.ui.activity.reportMenuActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.wawan.dompetku.R;
import com.wawan.dompetku.di.component.ActivityComponent;
import com.wawan.dompetku.di.component.DaggerActivityComponent;
import com.wawan.dompetku.di.module.ActivityModule;
import com.wawan.dompetku.model.menu.MenuModel;
import com.wawan.dompetku.ui.activity.reportDiagramActivity.ReportDiagramActivity;
import com.wawan.dompetku.ui.adapter.menuAdapter.MenuAdapter;

import javax.inject.Inject;


// ini adalah activity untuk
// menunjukan list menu untuk laporan
// yg nantinya akan dipilih oleh user
public class ReportMenuActivity extends AppCompatActivity implements ReportMenuActivityContract.View {

    // presenter yg diinject
    @Inject
    public ReportMenuActivityContract.Presenter presenter;


    // deklarasi variabel
    private Context context;
    private ImageView back;
    private RecyclerView listMenuReport;


    // fungsi yg dipanggil saat activity
    // dibuat
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_menu);
        initWidget();
    }

    // fungsi kedua untuk menginisialisasi
    // seleurh variabel yg telah dideklarasi
    private void initWidget(){

        // inisialisasi kontekt
        this.context = this;

        // deklarasi dan inisialisasi
        // object handler yg nantinya digunakan
        // untuk menjalankan aksi
        injectDependency();
        presenter.attach(this);
        presenter.subscribe();


        // inisialisasi tombol kembali
        // yg jika ditekan maka akan
        // menghancurkan activity
        back  = findViewById(R.id.back_imageview);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // inisialisasi recycleview
        // yg nantinya akan diset adapter menu
        // dengan item menu menu laporan
        // yg jika salah satu itemnya diklik
        // maka akan menjalankan activity report diagram
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

        // mengeset layout manager untuk recycle view
        listMenuReport.setLayoutManager(new GridLayoutManager(context,2));
    }

    // untuk saat ini belum dibutuhkan
    @Override
    public void showProgress(Boolean show) {

    }

    // untuk saat ini belum dibutuhkan
    @Override
    public void showError(String error) {

    }

    // fungsi yg akan dipanggil saat activity
    // dihancurkan
    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.unsubscribe();
    }

    // pemanggilan register
    // dependensi injeksi untuk aktivity ini
    private void injectDependency(){
        ActivityComponent listcomponent = DaggerActivityComponent.builder()
                .activityModule(new ActivityModule(this))
                .build();

        listcomponent.inject(this);
    }


}
