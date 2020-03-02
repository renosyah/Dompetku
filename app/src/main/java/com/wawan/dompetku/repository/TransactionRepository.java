package com.wawan.dompetku.repository;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.wawan.dompetku.db.AppDatabase;
import com.wawan.dompetku.dao.TransactionDao;
import com.wawan.dompetku.model.transaction.IncomeAndExpenseModel;
import com.wawan.dompetku.model.transaction.TransactionModel;
import com.wawan.dompetku.util.UtilFunction;

import java.sql.Date;
import java.util.List;

// transaksi repository
// adalah fungsi yg akan memanggil perintah query
// ke db pool
public class TransactionRepository {

    // variabel aplikasi
    private Application application;

    // variabel transaction dao
    private TransactionDao transactionDao;


    // konstruktor
    // untuk inisialisai view model
    // dengan aplikasi sebagai paramter
    public TransactionRepository(@NonNull Application application) {
        transactionDao = AppDatabase.getDatabase(application).transactionDao();
        this.application = application;
    }

    public Application getApplication() {
        return application;
    }

    public LiveData<List<TransactionModel>> all() {
        return transactionDao.all();
    }

    public void all(int offset, int limit, UtilFunction.Unit<List<TransactionModel>> t) {
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                t.invoke(transactionDao.all(offset, limit));
            }
        });
    }

    public LiveData<List<TransactionModel>> all(Date start, Date end) {
        return transactionDao.all(start, end);
    }

    public void allIncome(int offset, int limit, UtilFunction.Unit<List<TransactionModel>> t) {
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                t.invoke(transactionDao.allIncome(offset, limit));
            }
        });

    }

    public LiveData<Double> total(Date start, Date end) {
        return transactionDao.total(start, end);
    }

    public LiveData<Double> total() {
        return transactionDao.total();
    }

    public LiveData<IncomeAndExpenseModel> getIncomeExpense() {
        return transactionDao.getIncomeExpense();
    }

    public void oneExpiredTransaction(Date old, UtilFunction.Unit<TransactionModel> t){
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                t.invoke(transactionDao.oneExpiredTransaction(old));
            }
        });
    }

    public void add(final TransactionModel c) {
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                transactionDao.add(c);
            }
        });
    }

    public void update(final TransactionModel c) {
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                transactionDao.update(c);
            }
        });
    }

    public void delete(final TransactionModel c) {
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                transactionDao.delete(c);
            }
        });
    }
}
