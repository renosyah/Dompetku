package com.syahputrareno975.dompetku.ui.activity.settingActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.syahputrareno975.dompetku.R;
import com.syahputrareno975.dompetku.di.component.ActivityComponent;
import com.syahputrareno975.dompetku.di.component.DaggerActivityComponent;
import com.syahputrareno975.dompetku.di.module.ActivityModule;
import com.syahputrareno975.dompetku.service.AppReceiver;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;

import javax.inject.Inject;

import static com.syahputrareno975.dompetku.service.NotifService.ACTION_CHANGE_TIME_SCHEDULE_FOR_NOTIFICATION;

public class SettingActivity extends AppCompatActivity implements SettingActivityContract.View {

    @Inject
    public SettingActivityContract.Presenter presenter;

    private Context context;

    private ImageView back;
    private TextView settingNotif;
    private TextView aboutApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initWidget();
    }

    private void initWidget(){
        this.context = this;

        injectDependency();
        presenter.attach(this);
        presenter.subscribe();

        back = findViewById(R.id.back_imageview);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        settingNotif = findViewById(R.id.setting_notif);
        settingNotif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // time picker
                Calendar now = Calendar.getInstance();
                TimePickerDialog dpd = TimePickerDialog.newInstance(new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {

                        now.set(Calendar.HOUR_OF_DAY,hourOfDay);
                        now.set(Calendar.MINUTE,minute);
                        now.set(Calendar.SECOND,0);

                        // new notif time is set
                        Intent i = new Intent(ACTION_CHANGE_TIME_SCHEDULE_FOR_NOTIFICATION);
                        i.putExtra("date",now.getTimeInMillis());
                        sendBroadcast(i);

                        Log.e("date send",now.getTime().toString());

                    }
                },true);
                dpd.setAccentColor(ContextCompat.getColor(context,R.color.colorPrimaryLight));
                dpd.show(getSupportFragmentManager().beginTransaction(), "Datepickerdialog");


            }
        });
        aboutApp = findViewById(R.id.about_app);
        aboutApp .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(context)
                        .setTitle(context.getString(R.string.about_title))
                        .setMessage(context.getString(R.string.abount_message))
                        .setPositiveButton(context.getString(R.string.ok), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).setCancelable(false)
                        .create()
                        .show();

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


    private void injectDependency(){
        ActivityComponent listcomponent = DaggerActivityComponent.builder()
                .activityModule(new ActivityModule(this))
                .build();

        listcomponent.inject(this);
    }
}
