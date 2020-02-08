package com.syahputrareno975.dompetku.model.user;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.syahputrareno975.dompetku.model.BaseModel;

@Entity(tableName = "user")
public class UserModel extends BaseModel {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int Id;

    @ColumnInfo(name = "name")
    private String Name;

    @ColumnInfo(name = "phone_number")
    private String PhoneNumber;

    public UserModel() {
        super();
    }

    public UserModel(String name, String phoneNumber) {
        Name = name;
        PhoneNumber = phoneNumber;
    }

    public UserModel(int id) {
        Id = id;
    }

    @NonNull
    public void setId(int id) {
        Id = id;
    }

    @NonNull
    public void setName(String name) {
        Name = name;
    }

    @NonNull
    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    @NonNull
    public int getId() {
        return Id;
    }

    @NonNull
    public String getName() {
        return Name;
    }

    @NonNull
    public String getPhoneNumber() {
        return PhoneNumber;
    }

}
