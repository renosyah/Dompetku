package com.wawan.dompetku.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.util.Log;
import com.wawan.dompetku.R;

import java.util.Objects;

import static com.wawan.dompetku.util.UtilFunction.isMyServiceRunning;
import static com.wawan.dompetku.util.UtilFunction.sendNotification;

// ini adalah broadcast receiver untuk menerima
// broadcast dari aplikasi yg akan melakukan
// suatu aksi berdasarkan aksi yg diminta
public class AppReceiver extends BroadcastReceiver {

    // aksi id ini digunakan untuk menampilkan pesan umum
    // untuk ditampilkan sebagai notifikasi
    public static final String ACTION_COMMON_MESSAGE = "action.common.message";

    // aksi id ini digunakan untuk mengaktifkan
    // notifikasi service
    public static final String ACTION_RESTART_SERVICE = "action.restart.service";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Objects.equals(intent.getAction(), ACTION_COMMON_MESSAGE) && intent.hasExtra("data")){

            // akan muncul popup
            // notif untuk menampilkan pesan
            // pesan umum
            sendNotification(context, R.drawable.icon,intent.getStringExtra("data"));

        } else if (Objects.equals(intent.getAction(),ACTION_RESTART_SERVICE)){

            // akan merestart notifikasi service
            Intent i = new Intent(context,NotifService.class);
            context.stopService(i);
            context.startService(i);

        }  else if (Objects.equals(intent.getAction(),Intent.ACTION_BOOT_COMPLETED) ||
         Objects.equals(intent.getAction(),Intent.ACTION_DATE_CHANGED) ||
         Objects.equals(intent.getAction(),Intent.ACTION_TIME_CHANGED) ||
         Objects.equals(intent.getAction(),Intent.ACTION_TIMEZONE_CHANGED)){

            //  akan merestart notifikasi service
            // pada saat mendapatkan event
            // tanggal berubah
            // atau ponsel dihidupkan
            Intent i = new Intent(context, NotifService.class);

            if (!isMyServiceRunning(context,NotifService.class)) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    context.startForegroundService(i);
                } else {
                    context.startService(i);
                }
            }
        }

        Log.e("receive broadcast",intent.getAction());
    }
}
