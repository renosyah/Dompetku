package com.syahputrareno975.dompetku.model.transaction;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.syahputrareno975.dompetku.model.BaseModel;

import java.sql.Date;


@Entity(tableName = "transaction_data")
public class TransactionModel extends BaseModel {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int Id;

    @ColumnInfo(name = "currency")
    private String Currency;

    @ColumnInfo(name = "amount")
    private Double Amount;

    @ColumnInfo(name = "description")
    private String Description;

    @ColumnInfo(name = "date")
    private Date Date;

    @ColumnInfo(name = "flow")
    private int Flow;

    public TransactionModel() {
        super();
    }

    public TransactionModel(int id, String currency, Double amount, String description, java.sql.Date date, int flow) {
        Id = id;
        Currency = currency;
        Amount = amount;
        Description = description;
        Date = date;
        Flow = flow;
    }

    @NonNull
    public String getCurrency() {
        return Currency;
    }

    public void setCurrency(String currency) {
        Currency = currency;
    }

    @NonNull
    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    @NonNull
    public Double getAmount() {
        return Amount;
    }

    public void setAmount(Double amount) {
        Amount = amount;
    }

    @NonNull
    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    @NonNull
    public Date getDate() {
        return Date;
    }

    public void setDate(Date date) {
        Date = date;
    }

    @NonNull
    public int getFlow() {
        return Flow;
    }

    public void setFlow(int flow) {
        Flow = flow;
    }
}
