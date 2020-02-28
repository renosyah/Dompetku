package com.wawan.dompetku.util;

import android.app.ActivityManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Environment;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.room.TypeConverter;

import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.wawan.dompetku.BuildConfig;
import com.wawan.dompetku.R;
import com.wawan.dompetku.model.BaseModel;

import java.io.File;
import java.io.Serializable;
import java.text.DateFormatSymbols;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

// ini adalah class untuk mendeklarasikan
// fungsi yg nantinya akna digunakan berkali kali
// oleh program
// dalam kasus ini fungsi-fungsi umum
public class UtilFunction {

    // class cache model
    // untuk menyimpan cache sederhana
    // yg datanya hanya berisi text
    public static class CacheModel extends BaseModel {
        public String Text = "";
        public CacheModel(String text) {
            Text = text;
        }
    }

    // class cache model
    // untuk menyimpan cache sederhana
    // yg datanya hanya berisi data tanggal
    // bertipe long (int64)
    public static class LastDateCacheModel extends BaseModel {
        public long date = 0;
        public LastDateCacheModel(long date) {
            this.date = date;
        }
    }

    // class custom data entry
    // yg diguanakn sebagai content
    // untuk line chart
    public static class CustomDataEntry extends ValueDataEntry {
        public CustomDataEntry(String x, Number value, Number value2) {
            super(x, value);
            setValue("value2", value2);
        }
    }

    // simple variabel untuk format angka deciman
    public static final DecimalFormat formatter = new DecimalFormat("##,###");

    // simpel fungsi untuk mengecek apakah
    // service sedang running
    // dengan memberikan nama class service
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

    // class converter tanggal
    // yg dibutuhkan untuk melakukan
    // konversi tanggal di android room
    // ini bersifat mandatory
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

    // fungsi untuk memunculkan notifikasi
    // yg akan menampilkan notif transaksi expired
    // atau notif biasa
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

    // fungsi untuk mendapatkan tanggal kurang satu bulan
    public static java.sql.Date getExpiredTransactionDate(){
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, -1);
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        return new java.sql.Date(c.getTimeInMillis());
    }


    // simpel unterface untuk callback
    // jika di kotlin menggunakan lamba
    // seperti: ()->Unit
    public interface Unit<T>{
        void invoke(@Nullable T o);
    }

    // fungsi untuk membandingkan tanggal
    // jika sama maka true
    // namun hanya membandingkan jam dan menit
    public static boolean isSameTime(Calendar a,Calendar b){
        return a.get(Calendar.HOUR_OF_DAY) == b.get(Calendar.HOUR_OF_DAY) && a.get(Calendar.MINUTE) == b.get(Calendar.MINUTE);
    }
}
