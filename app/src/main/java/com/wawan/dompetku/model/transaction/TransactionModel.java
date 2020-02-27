package com.wawan.dompetku.model.transaction;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.wawan.dompetku.model.BaseModel;

import java.sql.Date;
import java.util.Calendar;

// adalah model untuk data transaksi
// dengan nama tabel transaction_data
@Entity(tableName = "transaction_data")
public class TransactionModel extends BaseModel {

    // id sebagai primary key
    // dengan tipe int
    // dan auto increment
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int Id;

    // adalah tipe kategory yg dipilih
    // untuk kategori pengeluaran
    // dan pemasukkan
    @ColumnInfo(name = "category_code")
    private int CategoryCode;

    // adalah kurensi
    // atau mata uang yg digunakan
    // saat transaksi
    @ColumnInfo(name = "currency")
    private String Currency;


    // adalah jumlah total saldo
    // pada transaksi
    @ColumnInfo(name = "amount")
    private Double Amount;

    // adalah deskripsi
    // yg digunakan oleh user
    // untuk membedakan transaksi
    @ColumnInfo(name = "description")
    private String Description;

    // tanggal transaksi
    // pada saat transaksi terjadi
    @ColumnInfo(name = "date")
    private Date Date;

    // tanggal data transaksi dibuat
    // pada saat data transaksi dibuat
    @ColumnInfo(name = "create_at")
    private Date CreateAt;

    // adalah tipe yg menentukan
    // apakah data transaksi
    // transaksi pengeluaran atau pemasukkan
    @ColumnInfo(name = "flow")
    private int Flow;

    // digunakan untuk menampilkan header
    // pada laporan list report
    @Ignore
    public boolean isHeader = false;

    public TransactionModel(boolean isHeader) {
        this.isHeader = isHeader;
    }

    public TransactionModel() {
        super();
        CreateAt = new Date(Calendar.getInstance().getTime().getTime());
    }


    public TransactionModel(int categoryCode, String currency, Double amount, String description, java.sql.Date date, int flow) {
        CategoryCode = categoryCode;
        Currency = currency;
        Amount = amount;
        Description = description;
        Date = date;
        Flow = flow;
        CreateAt = new Date(Calendar.getInstance().getTime().getTime());
    }


    @NonNull
    public java.sql.Date getCreateAt() {
        return CreateAt;
    }

    public void setCreateAt(java.sql.Date createAt) {
        CreateAt = createAt;
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

    public int getCategoryCode() {
        return CategoryCode;
    }

    public void setCategoryCode(int categoryCode) {
        CategoryCode = categoryCode;
    }
}
