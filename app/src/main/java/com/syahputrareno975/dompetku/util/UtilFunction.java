package com.syahputrareno975.dompetku.util;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Environment;
import android.provider.SyncStateContract;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.room.TypeConverter;

import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.syahputrareno975.dompetku.BuildConfig;
import com.syahputrareno975.dompetku.R;
import com.syahputrareno975.dompetku.model.BaseModel;

import java.io.File;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class UtilFunction {

    public static class CacheModel extends BaseModel {
        public String Text = "";

        public CacheModel(String text) {
            Text = text;
        }
    }

    public static class LastDateCacheModel extends BaseModel {
        public long date = 0;

        public LastDateCacheModel(long date) {
            this.date = date;
        }
    }

    public static class CustomDataEntry extends ValueDataEntry {
        public CustomDataEntry(String x, Number value, Number value2) {
            super(x, value);
            setValue("value2", value2);
        }
    }

    public static final DecimalFormat formatter = new DecimalFormat("##,###");

    /** Create a File for saving an image or video */
    public static File getOutputMediaFile(Context c){
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory()
                + "/Android/data/"
                + c.getPackageName()
                + "/Files");

        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                return null;
            }
        }
        // Create a media file name
        String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmm").format(new Date());
        File mediaFile;
        String mImageName="MI_"+ timeStamp +".png";
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + mImageName);
        return mediaFile;
    }

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


    public static String getMonthForInt(int num) {
        String month = "wrong";
        DateFormatSymbols dfs = new DateFormatSymbols();
        String[] months = dfs.getMonths();
        if (num >= 0 && num <= 11 ) {
            month = months[num];
        }
        return month;
    }

    public static class Converters {
        @TypeConverter
        public static java.sql.Date fromTimestamp(Long value) {
            return value == null ? null : new java.sql.Date(value);
        }

        @TypeConverter
        public static Long dateToTimestamp(java.sql.Date date) {
            return date == null ? null : date.getTime();
        }
    }

    public static final String NOTIF_CHANNEL_ID = BuildConfig.APPLICATION_ID + "_NOTIFICATION_ID";
    public static final String NOTIF_CHANNEL_NAME = BuildConfig.APPLICATION_ID + "_NOTIFICATION_NAME";
    public static final String NOTIF_CHANNEL_DES = BuildConfig.APPLICATION_ID + "_NOTIFICATION_DES";
    public static final int importance = NotificationManager.IMPORTANCE_HIGH;

    public static void sendNotification(Context c,int icon,String message) {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(c, NOTIF_CHANNEL_ID)
                .setSmallIcon(icon)
                .setLargeIcon(BitmapFactory.decodeResource(c.getResources(), icon))
                .setContentTitle(c.getString(R.string.app_name))
                .setContentText(message)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(importance)
                .setAutoCancel(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(NOTIF_CHANNEL_ID, NOTIF_CHANNEL_NAME , importance);
            channel.setDescription(NOTIF_CHANNEL_DES);
            NotificationManager notificationManager = c.getSystemService(NotificationManager.class);
            assert notificationManager != null;
            notificationManager.createNotificationChannel(channel);
        }

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(c);
        notificationManager.notify(new Random(System.currentTimeMillis()).nextInt(100), builder.build());
    }

    public static java.sql.Date getExpiredTransactionDate(){
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, -1);
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        return new java.sql.Date(c.getTimeInMillis());
    }


    public static void saveCache(Context c,String n,String v){
        new SerializableSave(c,"cache_" + n +".cache").save(new UtilFunction.CacheModel(v));
    }

    public static Serializable getNotifiedCache(Context c,String n){
        return new SerializableSave(c,"cache_" + n +".cache").load();
    }

    public interface Unit<T>{
        void invoke(@Nullable T o);
    }

    public static boolean isSameTime(Calendar a,Calendar b){
        return a.get(Calendar.HOUR_OF_DAY) == b.get(Calendar.HOUR_OF_DAY) && a.get(Calendar.MINUTE) == b.get(Calendar.MINUTE);
    }
}
