package com.syahputrareno975.dompetku.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;

public class NotifService extends Service {

    final Handler handler = new Handler();

    public NotifService() { }

    @Override
    public IBinder onBind(Intent intent) {
       return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        handler.post(runnable);
        return START_NOT_STICKY;
    }

    final Runnable runnable = new Runnable() {
        public void run() {
            Toast.makeText(getBaseContext(),"Notif",Toast.LENGTH_SHORT).show();
            handler.postDelayed(this, 5000);
        }
    };

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        Toast.makeText(getBaseContext(),"Notif is stoped",Toast.LENGTH_SHORT).show();
        super.onTaskRemoved(rootIntent);
    }

    @Override
    public void onDestroy() {
        Toast.makeText(getBaseContext(),"Notif is destroyed",Toast.LENGTH_SHORT).show();
        super.onDestroy();
    }
}
