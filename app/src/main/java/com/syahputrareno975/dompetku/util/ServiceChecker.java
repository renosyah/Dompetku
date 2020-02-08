package com.syahputrareno975.dompetku.util;

import android.app.ActivityManager;
import android.content.Context;

public class ServiceChecker {
    public static boolean isMyServiceRunning(Context c,Class<?> s) {
        ActivityManager manager = (ActivityManager) c.getSystemService(Context.ACTIVITY_SERVICE);
        assert manager != null;
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (s.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
