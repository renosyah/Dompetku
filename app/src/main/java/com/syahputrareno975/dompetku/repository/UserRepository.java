package com.syahputrareno975.dompetku.repository;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.syahputrareno975.dompetku.db.AppDatabase;
import com.syahputrareno975.dompetku.dao.UserDao;
import com.syahputrareno975.dompetku.model.user.UserModel;

public class UserRepository {
    private UserDao userDao;

    public UserRepository(@NonNull Application application) {
        userDao = AppDatabase.getDatabase(application).userDao();
    }

    public LiveData<UserModel> getOne(String pn){
        return userDao.getOne(pn);
    }

    public LiveData<UserModel> getOne(int id){
        return userDao.getOne(id);
    }

    public void add(final UserModel user){
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                userDao.add(user);
            }
        });
    }

    public void update(final UserModel user){
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                userDao.update(user);
            }
        });
    }

    public void delete(final UserModel user){
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                userDao.delete(user);
            }
        });
    }
}
