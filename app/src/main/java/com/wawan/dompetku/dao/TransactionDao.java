package com.wawan.dompetku.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.wawan.dompetku.model.transaction.IncomeAndExpenseModel;
import com.wawan.dompetku.model.transaction.TransactionModel;

import java.sql.Date;
import java.util.List;

import static com.wawan.dompetku.util.Flow.FLOW_EXPENSE;
import static com.wawan.dompetku.util.Flow.FLOW_INCOME;


// ini adalah interface dao
// untuk melakukan query data transaksi
@Dao
public interface TransactionDao {

    // perintah query untuk mengambil semua data transaksi
    // yg hasil querinya adakan diurutkan berdasarkan tanggal
    @Query("SELECT * FROM transaction_data ORDER BY date ASC")
    public LiveData<List<TransactionModel>> all();

    // perintah query untuk mengambil semua data transaksi
    // yg hasil querinya adakan diurutkan berdasarkan tanggal
    // dengan offset dan limit untuk pagination
    @Query("SELECT * FROM transaction_data ORDER BY date ASC LIMIT :limit OFFSET :offset")
    public LiveData<List<TransactionModel>> all(int offset, int limit);


    // perintah query untuk mengambil semua data transaksi
    // yg hasil querinya adakan diurutkan berdasarkan tanggal
    // dengan tanggal mulai dan tanggal akhir
    @Query("SELECT * FROM transaction_data WHERE date BETWEEN :start AND :end")
    public LiveData<List<TransactionModel>> all(Date start, Date end);


    // perintah query untuk mengambil semua data transaksi
    // yg hasil querinya adakan diurutkan berdasarkan tanggal
    // dengan offset dan limit untuk pagination
    // namun hanya akan menampilkan data pemasukkan
    @Query("SELECT * FROM transaction_data WHERE flow = " + FLOW_INCOME + " ORDER BY date ASC LIMIT :limit OFFSET :offset")
    public LiveData<List<TransactionModel>> allIncome(int offset, int limit);


    // perintah query untuk mengambil total saldo dari data transaksi
    // yg hasil querinya adakan diurutkan berdasarkan tanggal
    // dengan tanggal mulai dan tanggal akhir
    @Query("SELECT SUM(CASE WHEN flow = " + FLOW_INCOME + " THEN amount ELSE -amount END) AS total_amount FROM transaction_data WHERE date BETWEEN :start AND :end ORDER BY date ASC")
    public LiveData<Double> total(Date start, Date end);


    // perintah query untuk mengambil total saldo pemasukkan dan pengeluaran dari data transaksi
    // yg hasil querinya adakan diurutkan berdasarkan tanggal
    @Query("SELECT SUM(CASE WHEN flow = " + FLOW_INCOME + " THEN amount ELSE -amount END) AS total_amount FROM transaction_data ORDER BY date ASC")
    public LiveData<Double> total();


    // perintah query untuk mengambil total saldo pemasukkan dan pengeluaran dari data transaksi
    // yg hasil querinya adakan diurutkan berdasarkan tanggal
    // dengan tanggal mulai dan tanggal akhir
    @Query("SELECT (SELECT SUM(amount) FROM transaction_data WHERE flow = " +  FLOW_INCOME + " ORDER BY date ASC) AS total_income,(SELECT SUM(amount) FROM transaction_data WHERE flow = " + FLOW_EXPENSE + " ORDER BY date ASC) AS total_expense")
    public LiveData<IncomeAndExpenseModel> getIncomeExpense();

    // perintah query untuk mengambil data transaksi
    // yang tanggal datanya lebih atau sama tua dengan
    // tanggal parameter
    @Query("SELECT * FROM transaction_data WHERE date <= :old LIMIT 1")
    public TransactionModel oneExpiredTransaction(Date old);

    // perintah query untuk insert data transaksi
    @Insert
    public void add(TransactionModel transactionModel);

    // perintah query untuk update data transaksi
    @Update
    public void update(TransactionModel transactionModel);

    // perintah query untuk update delete transaksi
    @Delete
    public void delete(TransactionModel transactionModel);
}
