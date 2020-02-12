package com.syahputrareno975.dompetku.db;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;
import com.syahputrareno975.dompetku.BuildConfig;
import com.syahputrareno975.dompetku.interfaces.TransactionDao;
import com.syahputrareno975.dompetku.interfaces.UserDao;
import com.syahputrareno975.dompetku.model.transaction.TransactionModel;
import com.syahputrareno975.dompetku.model.user.UserModel;
import com.syahputrareno975.dompetku.util.Converters;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import kotlin.jvm.Volatile;

@Database(entities = { UserModel.class, TransactionModel.class /* add more class */ }, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase  {
    public abstract UserDao userDao();
    public abstract TransactionDao transactionDao();
    /* add more dao */

    @Volatile
    private static AppDatabase INSTANCE = null;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
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
