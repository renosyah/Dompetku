package com.wawan.dompetku.model.transaction;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.wawan.dompetku.repository.TransactionRepository;
import com.wawan.dompetku.util.UtilFunction;

import java.sql.Date;
import java.util.List;

public class TransactionViewModel extends AndroidViewModel {

    private TransactionRepository repository;

    public TransactionViewModel(@NonNull Application application) {
        super(application);
        repository = new TransactionRepository(application);
    }

    public TransactionViewModel(TransactionRepository repository) {
        super(repository.getApplication());
        this.repository = repository;
    }

    public LiveData<List<TransactionModel>> all(){
        return repository.all();
    }

    public LiveData<List<TransactionModel>> all(int offset, int limit){
        return repository.all(offset, limit);
    }

    public LiveData<List<TransactionModel>> all(Date start, Date end){
        return repository.all(start, end);
    }

    public LiveData<List<TransactionModel>> allIncome(int offset, int limit){
        return repository.allIncome(offset, limit);
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
