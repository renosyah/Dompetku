package com.syahputrareno975.dompetku.repository;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.syahputrareno975.dompetku.db.AppDatabase;
import com.syahputrareno975.dompetku.interfaces.TransactionDao;
import com.syahputrareno975.dompetku.model.transaction.TransactionModel;

import java.sql.Date;
import java.util.List;

public class TransactionRepository {

    private TransactionDao transactionDao;

    public TransactionRepository(@NonNull Application application) {
        transactionDao = AppDatabase.getDatabase(application).transactionDao();
    }

    public LiveData<List<TransactionModel>> all(String by,boolean isAsc,int offset, int limit) {
        return transactionDao.all(by, isAsc, offset, limit);
    }

    public LiveData<Double> total(Date start, Date end) {
        return transactionDao.total(start, end);
    }

    public LiveData<Double> total() {
        return transactionDao.total();
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
