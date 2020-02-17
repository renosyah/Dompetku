package com.syahputrareno975.dompetku.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.util.Log;
import com.syahputrareno975.dompetku.R;
import java.util.Objects;

import static com.syahputrareno975.dompetku.util.UtilFunction.isMyServiceRunning;
import static com.syahputrareno975.dompetku.util.UtilFunction.sendNotification;

public class AppReceiver extends BroadcastReceiver {

    public static final String ACTION_COMMON_MESSAGE = "action.common.message";
    public static final String ACTION_RESTART_SERVICE = "action.restart.service";

    public static IntentFilter getFilter(){
        IntentFilter f = new IntentFilter();
        f.addAction(AppReceiver.ACTION_COMMON_MESSAGE);
        f.addAction(AppReceiver.ACTION_RESTART_SERVICE);
        f.addAction(Intent.ACTION_DATE_CHANGED);
        f.addAction(Intent.ACTION_TIME_CHANGED);
        f.addAction(Intent.ACTION_TIMEZONE_CHANGED);
        f.addAction(Intent.ACTION_BOOT_COMPLETED);
        return f;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Objects.equals(intent.getAction(), ACTION_COMMON_MESSAGE) && intent.hasExtra("data")){

            // will just show notif
            sendNotification(context, R.drawable.icon,intent.getStringExtra("data"));

        } else if (Objects.equals(intent.getAction(),ACTION_RESTART_SERVICE)){

            // will restart notif service
            Intent i = new Intent(context,NotifService.class);
            context.stopService(i);
            context.startService(i);

        }  else if (Objects.equals(intent.getAction(),Intent.ACTION_BOOT_COMPLETED) ||
         Objects.equals(intent.getAction(),Intent.ACTION_DATE_CHANGED) ||
         Objects.equals(intent.getAction(),Intent.ACTION_TIME_CHANGED) ||
         Objects.equals(intent.getAction(),Intent.ACTION_TIMEZONE_CHANGED)){

            // will restart notif service
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
