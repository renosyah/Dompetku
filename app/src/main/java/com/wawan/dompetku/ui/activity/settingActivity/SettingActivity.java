package com.wawan.dompetku.ui.activity.settingActivity;

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

import com.wawan.dompetku.R;
import com.wawan.dompetku.di.component.ActivityComponent;
import com.wawan.dompetku.di.component.DaggerActivityComponent;
import com.wawan.dompetku.di.module.ActivityModule;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;

import javax.inject.Inject;

import static com.wawan.dompetku.service.NotifService.ACTION_CHANGE_TIME_SCHEDULE_FOR_NOTIFICATION;
import static com.wawan.dompetku.service.NotifService.savedDate;

// adalah activity yg digunakan
// untuk mengatur waktu notifikasi
// dan menunjukan profile tentang aplikasi
public class SettingActivity extends AppCompatActivity implements SettingActivityContract.View {


    // presenter yg akan diinject ke activity
    @Inject
    public SettingActivityContract.Presenter presenter;

    // deklarasi variabel kontekt
    private Context context;

    // deklarasi view
    private ImageView back;
    private TextView settingNotif;
    private TextView aboutApp;


    // fungsi yg dipanggil saat activity
    // dibuat
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
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
        // yg pada saat ditekan akan menghancurkan aplikasi
        back = findViewById(R.id.back_imageview);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // inisialisasi tombol setting
        // pada saat ditekan akan menampilkan popup
        // untuk user memilih waktu notifikasi
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

                        // broadcast setingan baru ke service
                        Intent i = new Intent(ACTION_CHANGE_TIME_SCHEDULE_FOR_NOTIFICATION);
                        i.putExtra("date",now.getTimeInMillis());
                        sendBroadcast(i);


                        // simpan cache tanggal notifikasi
                        savedDate(context,new java.sql.Date(now.getTimeInMillis()));
                    }
                },true);
                dpd.setAccentColor(ContextCompat.getColor(context,R.color.colorPrimaryLight));
                dpd.show(getSupportFragmentManager().beginTransaction(), "Datepickerdialog");


            }
        });

        // inisialisai tombol tentang aplikasi
        // yg pada saat diklik akan muncul dialog
        // untuk menunjukan profile tentang aplikasi
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
