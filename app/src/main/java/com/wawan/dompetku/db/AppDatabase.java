package com.wawan.dompetku.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.wawan.dompetku.BuildConfig;
import com.wawan.dompetku.dao.TransactionDao;
import com.wawan.dompetku.model.transaction.TransactionModel;
import com.wawan.dompetku.util.UtilFunction;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import kotlin.jvm.Volatile;


// database package adalah sebuah package yg didalamnya terdapat fungsi yg
// untuk digunakan melakukan perintah query ke database room.
@Database(entities = { TransactionModel.class /* add more class */ }, version = 1, exportSchema = false)
@TypeConverters({UtilFunction.Converters.class})
public abstract class AppDatabase extends RoomDatabase  {

    // fungsi abstrack yg diambil dari class
    // interface transaction dao
    public abstract TransactionDao transactionDao();
    /* add more dao */


    // adalah variabel static
    // implementasi dari singleton
    // inisiasi
    @Volatile
    private static AppDatabase INSTANCE = null;

    // jumlah tread yg di set
    private static final int NUMBER_OF_THREADS = 4;

    // object untuk melakukan eksekusi query dengan
    // jumlah tread yg di set
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    // fungsi inisialisasi
    // untuk object INSTANCE
    public static AppDatabase getDatabase(final Context context){
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, BuildConfig.DB_NAME)
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    // variabel callback yg dibutuhkan saat
    // koneksi ke pool pertamakali
    // instance di inisialisasi
    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            databaseWriteExecutor.execute(new Runnable() {
                @Override
                public void run() {

                }
            });
        }
    };
}
