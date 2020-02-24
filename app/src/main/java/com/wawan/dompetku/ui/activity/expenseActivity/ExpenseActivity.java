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

public class ExpenseActivity extends AppCompatActivity implements ExpenseActivityContract.View {

    @Inject
    public ExpenseActivityContract.Presenter presenter;

    private Context context;
    private Intent intent;

    private MenuModel m;
    private ImageView back;
    private TextView title;
    private ImageView icon;

    private TransactionModel expense = new TransactionModel(CATEGORY_NON_EXPENSE,"", 0.0, "", null, FLOW_EXPENSE);

    private LinearLayout addDate,addDes,addAmount;
    private TextView date,des,amount;

    private Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);
        initWidget();
    }
    private void initWidget() {
        this.context = this;
        this.intent = getIntent();

        this.m = (MenuModel) intent.getSerializableExtra("menu");

        injectDependency();
        presenter.attach(this);
        presenter.subscribe();

        date = findViewById(R.id.date_expense_textview);
        Calendar cal = Calendar.getInstance();
        expense.setDate(new Date(cal.getTime().getTime()));
        date.setText(expense.getDate().toString());

        des = findViewById(R.id.des_expense_textview);
        amount = findViewById(R.id.amount_expense_textview);

        title = findViewById(R.id.title_expense_textview);
        title.setText(m.Text);

        icon = findViewById(R.id.icon_expense_imageview);
        icon.setImageDrawable(ContextCompat.getDrawable(context,m.Icon));

        back = findViewById(R.id.back_imageview);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

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

                    @Override
                    public void onEmpty() {

                    }

                    @Override
                    public void onCancel() {

                    }
                }).show();

            }
        });

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

                    @Override
                    public void onEmpty() {

                    }

                    @Override
                    public void onCancel() {

                    }
                },true).show();
            }
        });



        save = findViewById(R.id.add_expense_button);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (expense.getDate() == null || expense.getAmount() == 0.0 || expense.getDescription().isEmpty()){
                    Toast.makeText(context,context.getString(R.string.data_income_is_not_valid),Toast.LENGTH_SHORT).show();
                    return;
                }
                expense.setCategoryCode(getCategory(m.Id));
                expense.setCurrency(BuildConfig.CURRENCY);
                presenter.addExpense(expense);
            }
        });
    }

    @Override
    public void onAddExpense() {
        finish();
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
