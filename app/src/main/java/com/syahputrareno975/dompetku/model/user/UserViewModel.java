package com.syahputrareno975.dompetku.model.user;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.syahputrareno975.dompetku.repository.UserRepository;

public class UserViewModel extends AndroidViewModel {

    private UserRepository repository;

    public UserViewModel(@NonNull Application application) {
        super(application);
        repository = new UserRepository(application);
    }

    public LiveData<UserModel> getOne(String pn){
        return repository.getOne(pn);
    }

    public LiveData<UserModel> getOne(int id){
        return repository.getOne(id);
    }

    public void add(UserModel user){
        repository.add(user);
    }

    public void update(UserModel user){
        repository.update(user);
    }

    public void delete(UserModel user){
        repository.delete(user);
    }
}
