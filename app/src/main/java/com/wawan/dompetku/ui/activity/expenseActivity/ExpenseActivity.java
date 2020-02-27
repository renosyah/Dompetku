package com.wawan.dompetku.ui.activity.expenseActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
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
import com.wawan.dompetku.model.menu.MenuModel;
import com.wawan.dompetku.model.transaction.TransactionModel;
import com.wawan.dompetku.ui.dialog.DialogSimpleEditText;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.sql.Date;
import java.util.Calendar;

import javax.inject.Inject;

import static com.wawan.dompetku.util.ExpenseCategory.CATEGORY_NON_EXPENSE;
import static com.wawan.dompetku.util.ExpenseCategory.getCategory;
import static com.wawan.dompetku.util.Flow.FLOW_EXPENSE;
import static com.wawan.dompetku.util.UtilFunction.formatter;

// adalah aktivity yg menyediakan form
// untuk menginput data transaksi pengeluaran
public class ExpenseActivity extends AppCompatActivity implements ExpenseActivityContract.View {

    // presenter yg diinject ke activity
    @Inject
    public ExpenseActivityContract.Presenter presenter;

    // deklarasi konteks dan intent
    private Context context;
    private Intent intent;

    // deklarasi view untuk activity
    // dan data menu yg nantinya didapat dari
    // intent
    private MenuModel m;
    private ImageView back;
    private TextView title;
    private ImageView icon;

    // inisialisasi data transaksi pengeluaran
    private TransactionModel expense = new TransactionModel(CATEGORY_NON_EXPENSE,"", 0.0, "", null, FLOW_EXPENSE);

    // deklarasi view layout
    // dan view untuk form
    private LinearLayout addDate,addDes,addAmount;
    private TextView date,des,amount;
    private Button save;


    // fungsi yg dipanggil saat activity
    // dibuat
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);
        initWidget();
    }

    // fungsi kedua untuk menginisialisasi
    // seleurh variabel yg telah dideklarasi
    private void initWidget() {

        // inisialisasi konteks dan intent
        this.context = this;
        this.intent = getIntent();

        // inisialisai menu yg datanya didapat dari intent
        this.m = (MenuModel) intent.getSerializableExtra("menu");

        // memanggil fungsi inject
        // ke activity ini
        injectDependency();
        presenter.attach(this);
        presenter.subscribe();


        // inisialisai text view tanggal
        date = findViewById(R.id.date_expense_textview);
        Calendar cal = Calendar.getInstance();
        expense.setDate(new Date(cal.getTime().getTime()));

        // dan diset text dengan default tanggal lokal
        date.setText(expense.getDate().toString());


        // inisialisasi text view deskripsi
        des = findViewById(R.id.des_expense_textview);

        // inisialisasi text view jumlah saldo
        amount = findViewById(R.id.amount_expense_textview);

        // inisialisasi text view judul aktivity
        title = findViewById(R.id.title_expense_textview);

        // yg teksnya didapat dari text dari object menu
        title.setText(m.Text);

        // inisialisasi image view icon
        // untuk menujukan pengeluaran tipe apa
        icon = findViewById(R.id.icon_expense_imageview);

        // yg image idnya didapat dari icon dari object menu
        icon.setImageDrawable(ContextCompat.getDrawable(context,m.Icon));

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
        addDate = findViewById(R.id.date_expense_layout);
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
                                expense.setDate(new Date(cal.getTime().getTime()));
                                date.setText(expense.getDate().toString());
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
        addDes = findViewById(R.id.des_expense_layout);
        addDes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // show dialog edit text for description
                new DialogSimpleEditText(context, context.getString(R.string.add_income_des), expense.getDescription(), new DialogSimpleEditText.OnDialogListener() {
                    @Override
                    public void onOk(String text) {
                        expense.setDescription(text);
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
        // meminta user mengeset jumlah saldo pengeluaran
        addAmount = findViewById(R.id.amount_expense_layout);
        addAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // show dialog edit text for amount
                new DialogSimpleEditText(context, context.getString(R.string.add_income_amount), expense.getAmount().toString(), new DialogSimpleEditText.OnDialogListener() {
                    @Override
                    public void onOk(String text) {
                        expense.setAmount(Double.parseDouble(text));
                        amount.setText(formatter.format(expense.getAmount()));
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
        save = findViewById(R.id.add_expense_button);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (expense.getDate() == null || expense.getAmount() == 0.0 || expense.getDescription().isEmpty()){
                    Toast.makeText(context,context.getString(R.string.data_income_is_not_valid),Toast.LENGTH_SHORT).show();
                    return;
                }

                // lalu akan memanggil presenter untuk menyimpan data
                // transaksi ke database
                expense.setCategoryCode(getCategory(m.Id));
                expense.setCurrency(BuildConfig.CURRENCY);
                presenter.addExpense(expense);
            }
        });
    }

    // yg akan dipanggil saat
    // sebuah transaksi pengeluaran
    // berhasil di simpan ke db
    // dan akan menghancurkan aktiviti ini
    @Override
    public void onAddExpense() {
        finish();
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
