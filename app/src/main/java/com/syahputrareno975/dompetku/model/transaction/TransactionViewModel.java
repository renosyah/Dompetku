package com.syahputrareno975.dompetku.model.transaction;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.syahputrareno975.dompetku.repository.TransactionRepository;
import java.util.List;

public class TransactionViewModel extends AndroidViewModel {

    private TransactionRepository repository;

    public TransactionViewModel(@NonNull Application application) {
        super(application);
        repository = new TransactionRepository(application);
    }

    public LiveData<List<TransactionModel>> all(int account_id,String by,boolean isAsc,int offset, int limit){
        return repository.all(account_id, by, isAsc, offset, limit);
    }

    public LiveData<TransactionModel> one(int id){
        return repository.one(id);
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
