package com.syahputrareno975.dompetku.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.syahputrareno975.dompetku.model.transaction.IncomeAndExpenseModel;
import com.syahputrareno975.dompetku.model.transaction.TransactionModel;

import java.sql.Date;
import java.util.List;

import static com.syahputrareno975.dompetku.util.Flow.FLOW_EXPENSE;
import static com.syahputrareno975.dompetku.util.Flow.FLOW_INCOME;

@Dao
public interface TransactionDao {

    @Query("SELECT * FROM transaction_data ORDER BY date ASC")
    public LiveData<List<TransactionModel>> all();

    @Query("SELECT * FROM transaction_data ORDER BY date ASC LIMIT :limit OFFSET :offset")
    public LiveData<List<TransactionModel>> all(int offset, int limit);

    @Query("SELECT * FROM transaction_data WHERE date BETWEEN :start AND :end")
    public LiveData<List<TransactionModel>> all(Date start, Date end);

    @Query("SELECT * FROM transaction_data WHERE flow = " + FLOW_INCOME + " ORDER BY date ASC LIMIT :limit OFFSET :offset")
    public LiveData<List<TransactionModel>> allIncome(int offset, int limit);

    @Query("SELECT SUM(CASE WHEN flow = " + FLOW_INCOME + " THEN amount ELSE -amount END) AS total_amount FROM transaction_data WHERE date BETWEEN :start AND :end ORDER BY date ASC")
    public LiveData<Double> total(Date start, Date end);

    @Query("SELECT SUM(CASE WHEN flow = " + FLOW_INCOME + " THEN amount ELSE -amount END) AS total_amount FROM transaction_data ORDER BY date ASC")
    public LiveData<Double> total();

    @Query("SELECT (SELECT SUM(amount) FROM transaction_data WHERE flow = " +  FLOW_INCOME + " ORDER BY date ASC) AS total_income,(SELECT SUM(amount) FROM transaction_data WHERE flow = " + FLOW_EXPENSE + " ORDER BY date ASC) AS total_expense")
    public LiveData<IncomeAndExpenseModel> getIncomeExpense();

    @Query("SELECT * FROM transaction_data WHERE date <= :old LIMIT 1")
    public TransactionModel oneExpiredTransaction(Date old);

    @Insert
    public void add(TransactionModel transactionModel);

    @Update
    public void update(TransactionModel transactionModel);

    @Delete
    public void delete(TransactionModel transactionModel);
}
