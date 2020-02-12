package com.syahputrareno975.dompetku.interfaces;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.syahputrareno975.dompetku.model.transaction.TransactionModel;

import java.util.List;

@Dao
public interface TransactionDao {

    @Query("SELECT * FROM transaction_data WHERE account_id = :account_id ORDER BY CASE WHEN :isAsc = 1 THEN :by END ASC, CASE WHEN :isAsc = 0 THEN :by END DESC LIMIT :limit OFFSET :offset")
    public LiveData<List<TransactionModel>> all(int account_id,String by,boolean isAsc,int offset, int limit);

    @Query("SELECT * FROM transaction_data WHERE id = :id LIMIT 1")
    public LiveData<TransactionModel> one(int id);

    @Insert
    public void add(TransactionModel transactionModel);

    @Update
    public void update(TransactionModel transactionModel);

    @Delete
    public void delete(TransactionModel transactionModel);
}
