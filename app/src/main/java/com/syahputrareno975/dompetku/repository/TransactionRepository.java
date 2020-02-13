package com.syahputrareno975.dompetku.repository;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.syahputrareno975.dompetku.db.AppDatabase;
import com.syahputrareno975.dompetku.interfaces.TransactionDao;
import com.syahputrareno975.dompetku.model.transaction.IncomeAndExpenseModel;
import com.syahputrareno975.dompetku.model.transaction.TransactionModel;

import java.sql.Date;
import java.util.List;

public class TransactionRepository {

    private TransactionDao transactionDao;

    public TransactionRepository(@NonNull Application application) {
        transactionDao = AppDatabase.getDatabase(application).transactionDao();
    }

    public LiveData<List<TransactionModel>> all(int offset, int limit) {
        return transactionDao.all(offset, limit);
    }

    public LiveData<List<TransactionModel>> all(Date start, Date end) {
        return transactionDao.all(start, end);
    }

    public LiveData<List<TransactionModel>> allIncome(int offset, int limit) {
        return transactionDao.allIncome(offset, limit);
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
