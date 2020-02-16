package com.syahputrareno975.dompetku.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleService;
import androidx.lifecycle.Observer;

import com.syahputrareno975.dompetku.R;
import com.syahputrareno975.dompetku.model.transaction.TransactionModel;
import com.syahputrareno975.dompetku.model.transaction.TransactionViewModel;
import com.syahputrareno975.dompetku.util.SerializableSave;
import com.syahputrareno975.dompetku.util.UtilFunction;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.sql.Date;
import java.util.Calendar;
import java.util.Objects;

import static com.syahputrareno975.dompetku.service.AppReceiver.ACTION_RESTART_SERVICE;
import static com.syahputrareno975.dompetku.util.UtilFunction.getExpiredTransactionDate;
import static com.syahputrareno975.dompetku.util.UtilFunction.sendNotification;

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

        savedDate(context,dateNotif);

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

        savedDate(context,dateNotif);
    }

    private Serializable loadLastSavedDate(Context c){
        return new SerializableSave(c,"date_setting.set").load();
    }

    private void savedDate(Context c,Date d){
        if (d != null) new SerializableSave(c,"date_setting.set").save(new UtilFunction.LastDateCacheModel(d.getTime()));
    }
}
