package com.wawan.dompetku.ui.activity.incomeActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wawan.dompetku.BuildConfig;
import com.wawan.dompetku.R;
import com.wawan.dompetku.di.component.ActivityComponent;
import com.wawan.dompetku.di.component.DaggerActivityComponent;
import com.wawan.dompetku.di.module.ActivityModule;
import com.wawan.dompetku.model.transaction.TransactionModel;
import com.wawan.dompetku.ui.adapter.listReportAdapter.ListReportAdapter;
import com.wawan.dompetku.ui.dialog.DialogDeleteTransaction;
import com.wawan.dompetku.ui.dialog.DialogSimpleEditText;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import static com.wawan.dompetku.util.ExpenseCategory.CATEGORY_NON_EXPENSE;
import static com.wawan.dompetku.util.Flow.FLOW_INCOME;
import static com.wawan.dompetku.util.UtilFunction.formatter;

// adalah aktivity yg menyediakan form
// untuk menginput data transaksi pemasukkan
public class IncomeActivity extends AppCompatActivity implements IncomeActivityContract.View {

    // presenter yg diinject ke activity
    @Inject
    public IncomeActivityContract.Presenter presenter;

    // deklarasi konteks
    private Context context;

    // inisialisasi data transaksi pemasukkan
    private TransactionModel income = new TransactionModel(CATEGORY_NON_EXPENSE,"", 0.0, "", null, FLOW_INCOME);

    // deklarasi view untuk activity
    // deklarasi view layout
    // dan view untuk form
    private ImageView back;
    private LinearLayout addDate,addDes,addAmount;
    private TextView date,des,amount;
    private Button save;
    private NestedScrollView scroll;
    private RecyclerView listTransaction;


    // deklarasi adapter
    private ListReportAdapter listReportAdapter;
    private ArrayList<TransactionModel> transactions = new ArrayList<TransactionModel>();
    private int offset = 0,limit = 15;


    // fungsi yg dipanggil saat activity
    // dibuat
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income);
        initWidget();
    }

    // fungsi kedua untuk menginisialisasi
    // seleurh variabel yg telah dideklarasi
    private void initWidget(){

        // inisialisasi konteks dan intent
        this.context = this;

        // memanggil fungsi inject
        // ke activity ini
        injectDependency();
        presenter.attach(this);
        presenter.subscribe();

        // inisialisai text view tanggal
        date = findViewById(R.id.date_income_textview);
        Calendar cal = Calendar.getInstance();

        // dan diset text dengan default tanggal lokal
        income.setDate(new Date(cal.getTime().getTime()));

        // dan diset text dengan default tanggal lokal
        date.setText(income.getDate().toString());

        // inisialisai text view deksripsi
        des = findViewById(R.id.des_income_textview);

        // inisialisai text view jumlah saldo
        amount = findViewById(R.id.amount_income_textview);

        // inisialisasi image view untuk tombol kembali
        back = findViewById(R.id.back_imageview);

        // lalu di set pada saat image ditekan
        // akan menghancurkan activity
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        // inisialisasi image view untuk memunculkan dialog
        // meminta user memilih tanggal transaksi
        addDate = findViewById(R.id.date_income_layout);
        addDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // date picker
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                                Calendar cal = Calendar.getInstance();
                                cal.set(Calendar.YEAR, year);
                                cal.set(Calendar.MONTH, monthOfYear);
                                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                                income.setDate(new Date(cal.getTime().getTime()));
                                date.setText(income.getDate().toString());
                            }
                        },
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.setAccentColor(ContextCompat.getColor(context,R.color.colorPrimaryLight));
                dpd.show(getSupportFragmentManager().beginTransaction(), "Datepickerdialog");
            }
        });

        // inisialisasi image view untuk memunculkan dialog
        // meminta user mengeset deksripsi
        addDes = findViewById(R.id.des_income_layout);
        addDes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // show dialog edit text for description
                new DialogSimpleEditText(context, context.getString(R.string.add_income_des), income.getDescription(), new DialogSimpleEditText.OnDialogListener() {
                    @Override
                    public void onOk(String text) {
                        income.setDescription(text);
                        des.setText(text);
                    }

                    // untuk saat ini belum dibutuhkan
                    @Override
                    public void onEmpty() {

                    }

                    // untuk saat ini belum dibutuhkan
                    @Override
                    public void onCancel() {

                    }
                }).show();

            }
        });

        // inisialisasi image view untuk memunculkan dialog
        // meminta user mengeset jumlah saldo pemasukkan
        addAmount = findViewById(R.id.amount_income_layout);
        addAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // show dialog edit text for amount
                new DialogSimpleEditText(context, context.getString(R.string.add_income_amount), income.getAmount().toString(), new DialogSimpleEditText.OnDialogListener() {
                    @Override
                    public void onOk(String text) {
                        income.setAmount(Double.parseDouble(text));
                        amount.setText(formatter.format(income.getAmount()));
                    }

                    // untuk saat ini belum dibutuhkan
                    @Override
                    public void onEmpty() {

                    }

                    // untuk saat ini belum dibutuhkan
                    @Override
                    public void onCancel() {

                    }
                },true).show();
            }
        });

        // inisialisasi button save
        // pada saat diklik akan melakukan
        // validasi jika data transaksi valid
        save = findViewById(R.id.add_income_button);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (income.getDate() == null || income.getAmount() == 0.0 || income.getDescription().isEmpty()){
                    Toast.makeText(context,context.getString(R.string.data_income_is_not_valid),Toast.LENGTH_SHORT).show();
                    return;
                }

                // lalu akan memanggil presenter untuk menyimpan data
                // transaksi ke database
                income.setCurrency(BuildConfig.CURRENCY);
                presenter.addIncome(income);
            }
        });


        // agar header bisa ditampilkan
        transactions.add(new TransactionModel(true));

        // inisialisasi adapter
        // untuk menampilkan list laporan transaksi
        // pendapatan
        listReportAdapter = new ListReportAdapter(context, transactions, new ListReportAdapter.OnListReportAdapterListener() {
            @Override
            public void onClick(@NonNull TransactionModel t, int pos) {

                // show dialog delete transaction
                new DialogDeleteTransaction(context,context.getString(R.string.delete_message) + " " + t.getDate() + " " + t.getDescription() +  " ?", new DialogDeleteTransaction.OnDialogDeleteTransactionListener() {
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

        // inisialisasi recycleview
        // lalu di set adapter ke receycleview
        // list laporan transasi pemasukkan
        listTransaction = findViewById(R.id.list_tansaction_recycleview);
        listTransaction.setAdapter(listReportAdapter);
        listTransaction.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));

        // inisialisasi nested scrollview
        scroll = findViewById(R.id.income_scroll_nestedscrollview);

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
                    presenter.getAllTransactionIncome(offset,limit);
                }
            }
        });


        // memanggil semua transaksi pemasukkan
        // dengan paramter offset limit
        // untuk pagination
        presenter.getAllTransactionIncome(offset,limit);
    }

    // yg akan dipanggil saat
    // sebuah transaksi pemasukkan
    // berhasil di simpan ke db
    // dan akan menghancurkan aktiviti ini
    @Override
    public void onAddIncome() {
        finish();
    }

    // yg akan dipanggil saat
    // sebuah transaksi pengeluaran
    // berhasil di delete
    @Override
    public void onDeleteTransaction() {
        Toast.makeText(context,context.getString(R.string.transaction_is_deleted),Toast.LENGTH_SHORT).show();
        offset = 0;
        limit = 15;
        transactions.clear();

        // for header
        transactions.add(new TransactionModel(true));
        listReportAdapter.notifyDataSetChanged();
    }

    // yg akan dipanggil saat
    // transaksi pengeluaran
    // berhasil di query
    @Override
    public void onGetAllTransactionIncome(@Nullable List<TransactionModel> t) {
        if (t != null) transactions.addAll(t);
        listReportAdapter.notifyDataSetChanged();
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
