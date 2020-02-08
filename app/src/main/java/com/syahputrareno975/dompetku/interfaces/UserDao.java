package com.syahputrareno975.dompetku.interfaces;

import androidx.lifecycle.LiveData;
import androidx.room.*;
import com.syahputrareno975.dompetku.model.user.UserModel;

@Dao
public interface UserDao {

    @Query("SELECT * FROM user WHERE phone_number = :pn LIMIT 1")
    public LiveData<UserModel> getOne(String pn);

    @Query("SELECT * FROM user WHERE id = :id LIMIT 1")
    public LiveData<UserModel> getOne(int id);

    @Insert
    public void add(UserModel user);

    @Update
    public void update(UserModel user);

    @Delete
    public void delete(UserModel user);
}
