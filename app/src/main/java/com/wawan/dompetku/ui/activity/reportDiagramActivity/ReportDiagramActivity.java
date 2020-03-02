package com.wawan.dompetku.ui.activity.reportDiagramActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.charts.Pie;
import com.anychart.charts.Waterfall;
import com.anychart.core.cartesian.series.Line;
import com.anychart.data.Mapping;
import com.anychart.data.Set;
import com.anychart.enums.Align;
import com.anychart.enums.Anchor;
import com.anychart.enums.LegendLayout;
import com.anychart.enums.MarkerType;
import com.anychart.enums.TooltipPositionMode;
import com.anychart.graphics.vector.Stroke;
import com.wawan.dompetku.BuildConfig;
import com.wawan.dompetku.R;
import com.wawan.dompetku.di.component.ActivityComponent;
import com.wawan.dompetku.di.component.DaggerActivityComponent;
import com.wawan.dompetku.di.module.ActivityModule;
import com.wawan.dompetku.model.menu.MenuModel;
import com.wawan.dompetku.model.transaction.IncomeAndExpenseModel;
import com.wawan.dompetku.model.transaction.TransactionModel;
import com.wawan.dompetku.ui.adapter.listReportAdapter.ListReportAdapter;
import com.wawan.dompetku.ui.dialog.DialogDeleteTransaction;
import com.wawan.dompetku.util.UtilFunction;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import static com.wawan.dompetku.util.Flow.FLOW_EXPENSE;
import static com.wawan.dompetku.util.Flow.FLOW_INCOME;
import static com.wawan.dompetku.util.UtilFunction.formatter;


// ini adalah activity yg digunakan untuk
// menampilkan laporan dalam bentuk list
// maupun diagram berdasarkan menu
// laporan yg diinginkan
public class ReportDiagramActivity extends AppCompatActivity implements ReportDiagramActivityContract.View {

    // presenter yg diinject
    @Inject
    public ReportDiagramActivityContract.Presenter presenter;

    // deklarasi variabel
    // konteks dan intent
    private Context context;
    private Intent intent;

    // deklarasi menu
    private MenuModel m;

    // deklarasi view
    private TextView title;
    private NestedScrollView scroll;
    private ImageView back;
    private TextView ammountBallance;

    // deklarasi view untuk chart
    private AnyChartView chartReport;
    private LinearLayout chartReportLayout;

    // deklarasi view untuk view list report
    private LinearLayout listLayout;
    private RecyclerView listReport;
    private ListReportAdapter listReportAdapter;
    private ArrayList<TransactionModel> transactions = new ArrayList<>();
    private int offset = 0,limit = 10;


    // fungsi yg dipanggil saat activity
    // dibuat
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_diagram);
        initWidget();
    }

    // fungsi kedua untuk menginisialisasi
    // seleurh variabel yg telah dideklarasi
    private void initWidget(){

        // inisialisasi kontekt
        // dan intent
        this.context = this;
        this.intent = getIntent();

        // inisialisasi menu yg datanya diambil dari intent
        m = (MenuModel) this.intent.getSerializableExtra("menu");

        // deklarasi dan inisialisasi
        // object handler yg nantinya digunakan
        // untuk menjalankan aksi
        injectDependency();
        presenter.attach(this);
        presenter.subscribe();

        // inisialisasi title
        // yg textnya diset dari menu yg dipilih sebelumnya
        title = findViewById(R.id.title_textview);
        title.setText(m.Text);

        // inisialisasi tombol kembali
        // pada saat ditekan akan menghancurkan
        // activity
        back = findViewById(R.id.back_imageview);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // inisialisasi laporan sisa saldo
        // yg teksnya yg nantinya akan di set jumlah sisa saldo
        ammountBallance = findViewById(R.id.total_balance_report_textview);
        ammountBallance.setText(BuildConfig.CURRENCY  + " "+formatter.format(0));

        // inisialisasi chart yg pada saat berhasil
        // di render untuk sekarang kosong
        chartReport = findViewById(R.id.chart_report);
        chartReport.setOnRenderedListener(new AnyChartView.OnRenderedListener() {
            @Override
            public void onRendered() {
                // on chart is completed rendered

            }
        });

        // for header
        transactions.add(new TransactionModel(true));

        // inisialisasi layout parent untuk chart
        // yg visibilitynya di set berdasarkan menu yg dipilih sebelumnya
        chartReportLayout = findViewById(R.id.chart_report_layout);
        chartReportLayout.setVisibility(m.Id != MenuModel.ID_REPORT_LIST ? View.VISIBLE : View.GONE);

        // inisialisasi layout parent untuk list report
        // yg visibilitynya di set berdasarkan menu yg dipilih sebelumnya
        listLayout = findViewById(R.id.list_report_layout);
        listLayout.setVisibility(m.Id == MenuModel.ID_REPORT_LIST ? View.VISIBLE : View.GONE);

        // inisialisasi untuk recycleview list report
        listReport = findViewById(R.id.list_report_recycleview);

        // memanggil fungsi untuk mendapatkan data
        // sisa saldo
        presenter.getBallance();

        // memanggil fungsi untuk mendapatkan data
        // report
        getReportData();
    }

    // fungsi untuk mendapatkan data report
    // namun kontent yg akan ditampilkan akan ditentukan
    // berdasarkan item dari menu laporan yg dipilih sebelumnya
    private void getReportData(){


        if (m.Id == MenuModel.ID_REPORT_LIST) {

            // jika menu yg dipilih adalah
            // list report
            setListReport();


        } else if (m.Id == MenuModel.ID_REPORT_PIE_DIAGRAM){

            // jika menu yg dipilih adalah
            // pie diagram
            presenter.getIncomeExpense();

        } else if (m.Id == MenuModel.ID_REPORT_WATERFALL_DIAGRAM){

            // jika menu yg dipilih adalah
            // waterfall chart report
            presenter.getAllTransactionForWaterfall();

        } else if (m.Id == MenuModel.ID_REPORT_LINE_DIAGRAM){

            // jika menu yg dipilih adalah
            // line chart report
            presenter.getAllTransactionForLine();
        }
    }


    // fungsi untuk mengeset adapter
    // yg nantinya akan digunakan oleh recycleview
    // list report
    private void setListReport(){

        listReportAdapter = new ListReportAdapter(context, transactions, new ListReportAdapter.OnListReportAdapterListener() {
            @Override
            public void onClick(@NonNull TransactionModel t, int pos) {

                // pada saat salah satu item dari
                // data transaksi diklik
                // maka tampilkan dialog
                // hapus transaksi
                new DialogDeleteTransaction(context,context.getString(R.string.delete_message) + " "  + t.getDate() + " " + t.getDescription() +  " ?", new DialogDeleteTransaction.OnDialogDeleteTransactionListener() {
                    @Override
                    public void onDelete() {
                        presenter.deleteTransaction(t);
                    }

                    @Override
                    public void onCancel() {

                    }
                }).show();
            }
        });

        // set list report recycleview adapter
        listReport.setAdapter(listReportAdapter);

        // lalu set layout managernya menggunakan linear layout manager
        listReport.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));


        // inisialisasi nested scrollview
        scroll = findViewById(R.id.scroll_report_nestedscrollview);
        if (m.Id == MenuModel.ID_REPORT_LIST){

            // set listener pada saat user melakukan scroll
            scroll.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
                @Override
                public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                    // jika user berhasil menyecroll
                    // sampai ke dasar
                    // maka akan dipanggil query untuk data berikutnya
                    if (scrollY >= v.getChildAt(v.getChildCount() - 1).getMeasuredHeight() - v.getMeasuredHeight()) {

                        // panggil data selajutnya
                        offset += limit;
                        presenter.getAllTransaction(offset,limit);
                    }
                }
            });
        }

        // memanggil semua transaksi pemasukkan
        // dengan paramter offset limit
        // untuk pagination
        presenter.getAllTransaction(offset,limit);
    }


    // fungsi yg digunakan untuk menampilkan diagram
    // char kedalam bentuk piew chart
    // dokumentasi lengkap bisa dilihat di : https://github.com/AnyChart/AnyChart-Android
    private void setPieChart(IncomeAndExpenseModel i){

        Pie pie = AnyChart.pie();
        List<DataEntry> data = new ArrayList<>();
        data.add(new ValueDataEntry(context.getString(R.string.add_income_title), i.getTotalIncome()));
        data.add(new ValueDataEntry(context.getString(R.string.add_expense_title), i.getTotalExpense()));
        pie.data(data);

        String[] colors = {context.getString(R.string.textColorGreen),context.getString(R.string.textColorRed)};
        pie.palette(colors);

        pie.title(context.getString(R.string.pie_chart_report_string));

        pie.labels().position("outside");

        pie.legend().title().enabled(true);
        pie.legend().title()
                .text(context.getString(R.string.flow_piechart_legend_title))
                .padding(0d, 0d, 10d, 0d);

        pie.legend()
                .position("center-bottom")
                .itemsLayout(LegendLayout.HORIZONTAL)
                .align(Align.CENTER);

        chartReport.setChart(pie);

    }

    // fungsi yg digunakan untuk menampilkan diagram
    // char kedalam bentuk waterfall
    // dokumentasi lengkap bisa dilihat di : https://github.com/AnyChart/AnyChart-Android
    private void setWaterfall(List<TransactionModel> t){

        Waterfall waterfall = AnyChart.waterfall();
        waterfall.title(context.getString(R.string.waterfall_chart_report_string));

        waterfall.yScale().minimum(0d);

        waterfall.yAxis(0).labels().format(BuildConfig.CURRENCY + " {%Value}{scale:(1000000)(1)|("+ context.getString(R.string.scale_million)+")}");
        waterfall.labels().enabled(true);
        waterfall.labels().format(
                "function() {\n" +
                        "      if (this['isTotal']) {\n" +
                        "        return anychart.format.number(this.absolute, {\n" +
                        "          scale: true\n" +
                        "        })\n" +
                        "      }\n" +
                        "\n" +
                        "      return anychart.format.number(this.value, {\n" +
                        "        scale: true\n" +
                        "      })\n" +
                        "    }");


        int num = 1;
        List<DataEntry> data = new ArrayList<>();
        for (TransactionModel i : t){
            Calendar cal = Calendar.getInstance();
            cal.setTime(i.getDate());

            data.add(new ValueDataEntry(
                    num + "",
                    i.getFlow() == FLOW_INCOME ? i.getAmount() : i.getAmount() - i.getAmount()*2));

            num++;

        }
        DataEntry end = new DataEntry();
        end.setValue("x", "End");
        end.setValue("isTotal", true);
        data.add(end);

        Set set = Set.instantiate();
        set.data(data);
        com.anychart.core.waterfall.series.Waterfall series = waterfall.waterfall(set, "");

        String green = context.getString(R.string.textColorGreen).toLowerCase();
        String red = context.getString(R.string.textColorRed).toLowerCase();
        String gray = context.getString(R.string.textColorGray).toLowerCase();

        series.normal().fill(gray + " 0.3");
        series.normal().stroke(gray);
        series.hovered().fill(gray + " 0.1");
        series.hovered().stroke(gray + " 2");
        series.selected().fill(gray, 0.5);
        series.selected().stroke(gray + " 4");

        series.normal().fallingFill(red, 0.3);
        series.hovered().fallingFill(red, 0.1);
        series.selected().fallingFill(red, 0.5);

        series.normal().risingFill(green, 0.3);
        series.hovered().risingFill(green, 0.1);
        series.selected().risingFill(green, 0.5);

        chartReport.setChart(waterfall);
    }

    // fungsi yg digunakan untuk menampilkan diagram
    // char kedalam bentuk line chart
    // dokumentasi lengkap bisa dilihat di : https://github.com/AnyChart/AnyChart-Android
    private void setLineGraph(List<TransactionModel> t){

        Cartesian cartesian = AnyChart.line();

        cartesian.animation(true);

        cartesian.padding(10d, 20d, 5d, 20d);

        cartesian.crosshair().enabled(true);
        cartesian.crosshair()
                .yLabel(true)
                // TODO ystroke
                .yStroke((Stroke) null, null, null, (String) null, (String) null);

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);

        cartesian.title(context.getString(R.string.line_chart_report));

        cartesian.yAxis(0).title(context.getString(R.string.money_in_currency));
        cartesian.xAxis(0).labels().padding(5d, 5d, 5d, 5d);

        String[] colors = {context.getString(R.string.textColorGreen),context.getString(R.string.textColorRed)};
        cartesian.palette(colors);



        List<DataEntry> seriesData = new ArrayList<>();
        seriesData.add(new UtilFunction.CustomDataEntry("Start",
                0, 0));

        int num = 1;
        for (TransactionModel i : t){
            Calendar cal = Calendar.getInstance();
            cal.setTime(i.getDate());

            Double totalExpense = i.getFlow() == FLOW_EXPENSE ? i.getAmount() : 0.0;
            Double totalIncome = i.getFlow() == FLOW_INCOME ? i.getAmount() : 0.0;

            seriesData.add(new UtilFunction.CustomDataEntry(num + "",
                    totalIncome, totalExpense));

            num++;

        }



        Set set = Set.instantiate();
        set.data(seriesData);
        Mapping series1Mapping = set.mapAs("{ x: 'x', value: 'value' }");
        Mapping series2Mapping = set.mapAs("{ x: 'x', value: 'value2' }");

        Line series1 = cartesian.line(series1Mapping);
        series1.name(context.getString(R.string.add_income_title));
        series1.hovered().markers().enabled(true);
        series1.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        series1.tooltip()
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(5d)
                .offsetY(5d);

        Line series2 = cartesian.line(series2Mapping);
        series2.name(context.getString(R.string.add_expense_title));
        series2.hovered().markers().enabled(true);
        series2.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        series2.tooltip()
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(5d)
                .offsetY(5d);


        cartesian.legend().enabled(true);
        cartesian.legend().fontSize(13d);
        cartesian.legend().padding(0d, 0d, 10d, 0d);

        chartReport.setChart(cartesian);
    }


    // fungsi yg digunakan pada saat berhasil
    // melakukan query dari db untuk list transaksi
    @Override
    public void onGetAllTransaction(@Nullable List<TransactionModel> t) {
        if (t != null) transactions.addAll(t);
        listReportAdapter.notifyDataSetChanged();
    }

    // fungsi yg digunakan pada saat berhasil
    // melakukan query dari db untuk sisa saldo
    @Override
    public void onGetBallance(@Nullable Double amount) {
        ammountBallance.setText(BuildConfig.CURRENCY  + " " + formatter.format(0));
        if (amount != null) ammountBallance.setText(BuildConfig.CURRENCY  + " " + formatter.format(amount));
    }

    // fungsi yg digunakan pada saat berhasil
    // melakukan query dari db untuk saldo pemasukan
    // dan pengeluaran untuk diagram pie
    @Override
    public void onGetIncomeExpense(@Nullable IncomeAndExpenseModel i) {
        if (i != null) setPieChart(i);
    }


    // fungsi yg digunakan pada saat berhasil
    // melakukan query dari db untuk data transaksi
    // yg akan digunakan untuk diagram waterfall
    @Override
    public void onGetAllTransactionForWaterfall(@Nullable List<TransactionModel> t) {
        if (t != null) setWaterfall(t);
    }

    // fungsi yg digunakan pada saat berhasil
    // melakukan query dari db untuk data transaksi
    // yg akan digunakan untuk diagram line
    @Override
    public void onGetAllTransactionForLine(@Nullable List<TransactionModel> t) {
        if (t != null) setLineGraph(t);
    }

    // fungsi yg digunakan pada saat berhasil
    // menghapus data transaksi dari db
    @Override
    public void onDeleteTransaction() {
        Toast.makeText(context,context.getString(R.string.transaction_is_deleted),Toast.LENGTH_SHORT).show();
        offset = 0;
        limit = 10;
        transactions.clear();

        // for header
        transactions.add(new TransactionModel(true));
        presenter.getAllTransaction(offset,limit);
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
