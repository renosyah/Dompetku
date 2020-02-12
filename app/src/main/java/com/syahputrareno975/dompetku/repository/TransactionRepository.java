package com.syahputrareno975.dompetku.repository;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.syahputrareno975.dompetku.db.AppDatabase;
import com.syahputrareno975.dompetku.interfaces.TransactionDao;
import com.syahputrareno975.dompetku.model.transaction.TransactionModel;

import java.util.List;

public class TransactionRepository {

    private TransactionDao transactionDao;

    public TransactionRepository(@NonNull Application application) {
        transactionDao = AppDatabase.getDatabase(application).transactionDao();
    }

    public LiveData<List<TransactionModel>> all(int account_id,String by,boolean isAsc,int offset, int limit) {
        return transactionDao.all(account_id, by, isAsc, offset, limit);
    }

    public LiveData<TransactionModel> one(int id) {
        return transactionDao.one(id);
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
