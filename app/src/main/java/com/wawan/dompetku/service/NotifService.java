package com.wawan.dompetku.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleService;

import com.wawan.dompetku.BuildConfig;
import com.wawan.dompetku.R;
import com.wawan.dompetku.model.transaction.TransactionModel;
import com.wawan.dompetku.model.transaction.TransactionViewModel;
import com.wawan.dompetku.util.SerializableSave;
import com.wawan.dompetku.util.UtilFunction;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.sql.Date;
import java.util.Calendar;
import java.util.Objects;
import java.util.Random;

import static com.wawan.dompetku.service.AppReceiver.ACTION_RESTART_SERVICE;
import static com.wawan.dompetku.util.UtilFunction.NOTIF_CHANNEL_DES;
import static com.wawan.dompetku.util.UtilFunction.NOTIF_CHANNEL_ID;
import static com.wawan.dompetku.util.UtilFunction.NOTIF_CHANNEL_NAME;
import static com.wawan.dompetku.util.UtilFunction.getExpiredTransactionDate;
import static com.wawan.dompetku.util.UtilFunction.sendNotification;


// adalah service
// yg akan memberitahu user
// jika terdapat data transaksi yg expired
public class NotifService extends LifecycleService {

    // aksi id ini digunakan untuk menangkap
    // brodcast yg berisi data settingan tanggal
    // untuk digunakan untuk kapan notifikasi akan ditampilkan
    public static final String ACTION_CHANGE_TIME_SCHEDULE_FOR_NOTIFICATION = "android.intent.action.ACTION_EXPIRED_CHANGE_TIME_SCHEDULE_FOR_NOTIFICATION";

    // context yg nantinya akan jadi context service
    Context context;

    // variabel pemilik lifecycle
    // yg akan digunakan untuk
    // menginisialisai viewmodel
    LifecycleOwner lifecycleOwner;

    // viewmodel transaksi
    TransactionViewModel transactionViewModel;

    // intent filter
    // untuk broadcast receiver di service ini
    IntentFilter s_intentFilter = new IntentFilter();

    // variabel broadcast receiver
    // untuk menerima broadcast
    // di service ini
    private BroadcastReceiver timeChangedReceiver;

    // tanggal yg digunakan untuk
    // kapan notifikasi akan ditampilkan
    private Date dateNotif;

    // konstruksi
    public NotifService() { }

    // untuk saat ini
    // fungsi inbind
    // tidak dibutuhkan
    @Override
    public IBinder onBind(@NotNull Intent intent) {
        super.onBind(intent);
        return null;
    }

    // fungsi yg akan dijalankan
    // pada saat service di buat
    @Override
    public void onCreate() {
        super.onCreate();

        // context dan lifecycle
        // owner akan di inisialisai
        // ke object service ini
        context = this;
        lifecycleOwner = this;

        // jika dari build config
        // di set true, maka service ini
        // akan menjadi foreground service
        if (BuildConfig.ENABLE_FOREGROUND) {
            startForeground();
        }

        // akan meload data tanggal notif
        // yg sebelumnya telah di set
        if (loadLastSavedDate(context) != null){
            UtilFunction.LastDateCacheModel cache = (UtilFunction.LastDateCacheModel)loadLastSavedDate(context);
            dateNotif = new Date(cache.date);
        }

        // menambah filter aksi-aksi
        // ke intent filter
        s_intentFilter.addAction(ACTION_CHANGE_TIME_SCHEDULE_FOR_NOTIFICATION);
        s_intentFilter.addAction(Intent.ACTION_TIME_TICK);

        // inisialisasi view model
        transactionViewModel = new TransactionViewModel(this.getApplication());

        // inisialisais broadcast receiver
        timeChangedReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context c, Intent intent) {


                if (Objects.equals(intent.getAction(),Intent.ACTION_TIME_TICK) && dateNotif != null){

                    // jika diterima broadcast waktu per menit
                    // berubah maka program akan melakukan query
                    // untuk mengecek di db apakah
                    // ada data transksi yg expired
                    transactionViewModel.oneExpiredTransaction(getExpiredTransactionDate(), new UtilFunction.Unit<TransactionModel>() {
                        @Override
                        public void invoke(@Nullable TransactionModel o) {

                            Calendar d = Calendar.getInstance();
                            d.setTime(dateNotif);

                            // jika tanggal notif yg diset
                            // sama dengan waktu lokal
                            if (o != null && UtilFunction.isSameTime(Calendar.getInstance(),d)){

                                // akan dimunculkan popup
                                // notif untuk memberitahu user
                                // bahwa ada transaksi yg expired
                                sendNotification(context,R.drawable.icon,context.getString(R.string.expire_transaction_message));
                            }
                            Log.e("expired log data", "log data loaded");
                        }
                    });

                } else if (Objects.equals(intent.getAction(),ACTION_CHANGE_TIME_SCHEDULE_FOR_NOTIFICATION) && intent.hasExtra("date")){

                    // akan dimunculkan popup notif
                    // untuk memberitahu user bahwa
                    // waktu notif telah di terima
                    sendNotification(c,R.drawable.icon,c.getString(R.string.new_schedule_received));

                    // mengambil data tgl notif dari
                    // intent
                    dateNotif = new Date(intent.getLongExtra("date",0));

                    // save ke cache
                    // tanggal notif
                    savedDate(context,dateNotif);

                    Log.e("call", "date change");
                }

                Log.e("received broadcast", intent.getAction());
            }
        };

        // register broadcast receiver
        // untuk menerima broadcast
        registerReceiver(timeChangedReceiver, s_intentFilter);
    }


    // fungsi yg akan dijalankan
    // pada saat service di buat
    // dan dijalankan
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        // register broadcast receiver
        // untuk menerima broadcast
        registerReceiver(timeChangedReceiver, s_intentFilter);

        return START_STICKY;
    }


    // fungsi yg akan dijalankan
    // pada saat aplikasi di swipe
    // oleh user
    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);

        try {
            unregisterReceiver(timeChangedReceiver);
        } catch(IllegalArgumentException e) {
            e.printStackTrace();
        }

        // mengirim broadcast
        // untuk mengaktifkan kembali service
         Intent i = new Intent(ACTION_RESTART_SERVICE);
         i.setClass(getBaseContext(),AppReceiver.class);
         sendBroadcast(i);

    }

    // fungsi yg akan dijalankan
    // pada saat service dihancurkan
    @Override
    public void onDestroy() {
        super.onDestroy();

        try {
            unregisterReceiver(timeChangedReceiver);
        } catch(IllegalArgumentException e) {
            e.printStackTrace();
        }
    }


    // fungsi load untuk mengambil date notif
    public static Serializable loadLastSavedDate(Context c){
        return new SerializableSave(c,"date_setting.set").load();
    }

    // fungsi save untuk menyimpan date notif
    public static void savedDate(Context c,Date d){
        if (d != null) new SerializableSave(c,"date_setting.set").save(new UtilFunction.LastDateCacheModel(d.getTime()));
    }

    // id foreground notifikasi
    private final int ONGOING_NOTIFICATION_ID = new Random(System.currentTimeMillis()).nextInt(100);

    // fungsi untuk memunculkan
    // icon notif, yg menunjukan ke user
    // bahwa service sedang berjalan
    private void startForeground() {

        // cek jika versi androidnya
        // adalah android oreo atau diatasnya
        // untuk dapat menjalankan service sebagai foreground service
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    NOTIF_CHANNEL_ID + "_FOREGROUND",
                    NOTIF_CHANNEL_NAME + "_FOREGROUND" ,
                    NotificationManager.IMPORTANCE_LOW);
            channel.setDescription(NOTIF_CHANNEL_DES + "_FOREGROUND");
            channel.setShowBadge(false);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);

                Notification notification = new Notification.Builder(context, NOTIF_CHANNEL_ID + "_FOREGROUND")
                        .setSmallIcon(R.drawable.icon)
                        .setContentText(getText(R.string.foreground_notification_message))
                        .build();

                startForeground(ONGOING_NOTIFICATION_ID, notification);
            }
        }
    }
}
