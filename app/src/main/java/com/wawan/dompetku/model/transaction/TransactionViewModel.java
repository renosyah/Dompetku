package com.wawan.dompetku.model.transaction;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.wawan.dompetku.repository.TransactionRepository;
import com.wawan.dompetku.util.UtilFunction;

import java.sql.Date;
import java.util.List;

// transaksi view model
// adalah fungsi yg akan memanggil perintah query
// ke repository
public class TransactionViewModel extends AndroidViewModel {

    // variabel repository
    private TransactionRepository repository;

    // konstruktor
    // untuk inisialisai view model
    // dengan aplikasi sebagai paramter
    public TransactionViewModel(@NonNull Application application) {
        super(application);
        repository = new TransactionRepository(application);
    }

    // konstruktor
    // untuk inisialisai view model
    // dengan repository sebagai paramter
    public TransactionViewModel(TransactionRepository repository) {
        super(repository.getApplication());
        this.repository = repository;
    }

    public LiveData<List<TransactionModel>> all(){
        return repository.all();
    }

    public void all(int offset, int limit, UtilFunction.Unit<List<TransactionModel>> t){
        repository.all(offset, limit,t);
    }

    public LiveData<List<TransactionModel>> all(Date start, Date end){
        return repository.all(start, end);
    }

    public void allIncome(int offset, int limit, UtilFunction.Unit<List<TransactionModel>> t){
        repository.allIncome(offset, limit,t);
    }

    public LiveData<Double> total(Date start, Date end){
        return repository.total(start, end);
    }

    public LiveData<Double> total(){
        return repository.total();
    }

    public LiveData<IncomeAndExpenseModel> getIncomeExpense() {
        return repository.getIncomeExpense();
    }

    public void oneExpiredTransaction(Date old, UtilFunction.Unit<TransactionModel> t){
        repository.oneExpiredTransaction(old,t);
    }

    public void add(TransactionModel c){
        repository.add(c);
    }

    public void update(TransactionModel c){
        repository.update(c);
    }

    public void delete(TransactionModel c){
        repository.delete(c);
    }
}
