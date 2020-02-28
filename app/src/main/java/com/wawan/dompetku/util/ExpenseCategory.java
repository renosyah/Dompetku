package com.wawan.dompetku.util;

import static com.wawan.dompetku.model.menu.MenuModel.ID_COMMON_NEED_EXPENSE;
import static com.wawan.dompetku.model.menu.MenuModel.ID_FOOD_EXPENSE;
import static com.wawan.dompetku.model.menu.MenuModel.ID_TRANSPORT_EXPENSE;


// ini adalah class untuk mendeklarasikan
// variabel static yg nantinya akna digunakan berkali kali
// oleh program
// dalam kasus ini adalah kategory pengeluaran
public class ExpenseCategory {

    // tipe kategory untuk transaksi yg bukan pengeluaran
    public static final int CATEGORY_NON_EXPENSE = -1;

    // tipe kategory untuk transaksi yg bertipe pengeluaran
    // untuk pembelian makanan
    public static final int CATEGORY_FOOD_EXPENSE = 0;

    // tipe kategory untuk transaksi yg bertipe pengeluaran
    // untuk pembayaran uang transportasi
    public static final int CATEGORY_TRANSPORT_EXPENSE = 1;

    // tipe kategory untuk transaksi yg bertipe pengeluaran
    // untuk kebutuhan umum
    public static final int CATEGORY_COMMON_NEED_EXPENSE = 2;


    // fungsi yg akan digunakan untuk menetukan
    // tipe kategory dati id menu
    public static int getCategory(int menuCode){
        switch (menuCode){
            case ID_FOOD_EXPENSE:
                return CATEGORY_FOOD_EXPENSE;
            case ID_TRANSPORT_EXPENSE:
                return CATEGORY_TRANSPORT_EXPENSE;
            case ID_COMMON_NEED_EXPENSE:
                return CATEGORY_COMMON_NEED_EXPENSE ;
                default:
                    break;
        }
        return CATEGORY_NON_EXPENSE;
    }
}
