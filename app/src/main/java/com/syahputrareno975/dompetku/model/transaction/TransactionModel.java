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

    @ColumnInfo(name = "account_id")
    private int AccountId;

    @ColumnInfo(name = "currency_id")
    private int CurrencyId;

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

    public TransactionModel(int accountId, int currencyId, Double amount, String description, Date date, int flow) {
        AccountId = accountId;
        CurrencyId = currencyId;
        Amount = amount;
        Description = description;
        Date = date;
        Flow = flow;
    }

    @NonNull
    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    @NonNull
    public int getAccountId() {
        return AccountId;
    }

    public void setAccountId(int accountId) {
        AccountId = accountId;
    }

    @NonNull
    public int getCurrencyId() {
        return CurrencyId;
    }

    public void setCurrencyId(int currencyId) {
        CurrencyId = currencyId;
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
