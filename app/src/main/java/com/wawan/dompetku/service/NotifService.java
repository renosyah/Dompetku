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

public class NotifService extends LifecycleService {

    public static final String ACTION_CHANGE_TIME_SCHEDULE_FOR_NOTIFICATION = "android.intent.action.ACTION_EXPIRED_CHANGE_TIME_SCHEDULE_FOR_NOTIFICATION";

    Context context;
    LifecycleOwner lifecycleOwner;
    TransactionViewModel transactionViewModel;

    IntentFilter s_intentFilter = new IntentFilter();
    private BroadcastReceiver timeChangedReceiver;

    private Date dateNotif;

    public NotifService() { }

    @Override
    public IBinder onBind(@NotNull Intent intent) {
        super.onBind(intent);
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        context = this;
        lifecycleOwner = this;

        if (BuildConfig.ENABLE_FOREGROUND) {
            startForeground();
        }

        if (loadLastSavedDate(context) != null){
            UtilFunction.LastDateCacheModel cache = (UtilFunction.LastDateCacheModel)loadLastSavedDate(context);
            dateNotif = new Date(cache.date);
        }

        s_intentFilter.addAction(ACTION_CHANGE_TIME_SCHEDULE_FOR_NOTIFICATION);
        s_intentFilter.addAction(Intent.ACTION_TIME_TICK);

        transactionViewModel = new TransactionViewModel(this.getApplication());

        timeChangedReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context c, Intent intent) {
                if (Objects.equals(intent.getAction(),Intent.ACTION_TIME_TICK) && dateNotif != null){

                    transactionViewModel.oneExpiredTransaction(getExpiredTransactionDate(), new UtilFunction.Unit<TransactionModel>() {
                        @Override
                        public void invoke(@Nullable TransactionModel o) {

                            Calendar d = Calendar.getInstance();
                            d.setTime(dateNotif);

                            if (o != null && UtilFunction.isSameTime(Calendar.getInstance(),d)){

                                // tell user
                                // expired transaction detected
                                sendNotification(context,R.drawable.icon,context.getString(R.string.expire_transaction_message));

                            }
                            Log.e("expired log data", "log data loaded");
                        }
                    });

                } else if (Objects.equals(intent.getAction(),ACTION_CHANGE_TIME_SCHEDULE_FOR_NOTIFICATION) && intent.hasExtra("date")){

                    // tell user
                    // new setting is received
                    sendNotification(c,R.drawable.icon,c.getString(R.string.new_schedule_received));

                    // reset date notif
                    dateNotif = new Date(intent.getLongExtra("date",0));

                    savedDate(context,dateNotif);

                    Log.e("call", "date change");
                }

                Log.e("received broadcast", intent.getAction());
            }
        };
        registerReceiver(timeChangedReceiver, s_intentFilter);
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        registerReceiver(timeChangedReceiver, s_intentFilter);

        return START_STICKY;
    }


    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);

        try {
            unregisterReceiver(timeChangedReceiver);
        } catch(IllegalArgumentException e) {
            e.printStackTrace();
        }

        // broadcast request to restart service
         Intent i = new Intent(ACTION_RESTART_SERVICE);
         i.setClass(getBaseContext(),AppReceiver.class);
         sendBroadcast(i);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        try {
            unregisterReceiver(timeChangedReceiver);
        } catch(IllegalArgumentException e) {
            e.printStackTrace();
        }
    }


    public static Serializable loadLastSavedDate(Context c){
        return new SerializableSave(c,"date_setting.set").load();
    }

    public static void savedDate(Context c,Date d){
        if (d != null) new SerializableSave(c,"date_setting.set").save(new UtilFunction.LastDateCacheModel(d.getTime()));
    }

    private final int ONGOING_NOTIFICATION_ID = new Random(System.currentTimeMillis()).nextInt(100);

    private void startForeground() {
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
