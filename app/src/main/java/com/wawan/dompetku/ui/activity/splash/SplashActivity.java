package com.wawan.dompetku.ui.activity.splash;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import com.wawan.dompetku.R;
import com.wawan.dompetku.service.AppReceiver;
import com.wawan.dompetku.service.NotifService;
import com.wawan.dompetku.ui.activity.mainMenuActivity.MainMenuActivity;
import static com.wawan.dompetku.service.AppReceiver.ACTION_RESTART_SERVICE;
import static com.wawan.dompetku.util.UtilFunction.isMyServiceRunning;

// adalah aktivity yg menampilkan splash screen loading
// aplikasi menggukana delay selama 3 detik
public class SplashActivity extends AppCompatActivity {

    // deklarasi konteks
    private Context context;

    // fungsi yg dipanggil saat activity
    // dibuat
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
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
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                // cek jika service notif berjalan
                if (!isMyServiceRunning(context,NotifService.class)) {

                    // jika tidak, beritahu broadcast receiver
                    // untuk menjalankan service notifikasi
                     Intent i = new Intent(ACTION_RESTART_SERVICE);
                     i.setClass(getBaseContext(), AppReceiver.class);
                     getBaseContext().sendBroadcast(i);
                }

                // panggil aktivity menu utama
                // dan selesai, hancurkan activity ini
                startActivity(new Intent(context, MainMenuActivity.class));
                finish();
            }
        },3000);

    }
}
