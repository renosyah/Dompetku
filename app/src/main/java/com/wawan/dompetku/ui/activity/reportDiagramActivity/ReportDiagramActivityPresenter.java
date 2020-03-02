package com.wawan.dompetku.ui.activity.reportDiagramActivity;

import android.app.Activity;

import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.wawan.dompetku.model.transaction.IncomeAndExpenseModel;
import com.wawan.dompetku.model.transaction.TransactionModel;
import com.wawan.dompetku.model.transaction.TransactionViewModel;
import com.wawan.dompetku.util.UtilFunction;

import java.util.List;


// adalah class presenter untuk activity ini
// yg mana class ini akan menghandle
// fungsi-fungsi yg berkaitan dengan proses bisnis aplikasi
// seperti query ke db
public class ReportDiagramActivityPresenter implements ReportDiagramActivityContract.Presenter {

    // deklarasi variabel
    private ReportDiagramActivityContract.View view;
    private TransactionViewModel transactionViewModel;

    // untuk saat ini kosong
    // belum dibutuhkan
    @Override
    public void subscribe() {

    }

    // untuk saat ini kosong
    // belum dibutuhkan
    @Override
    public void unsubscribe() {

    }

    // fungsi yg akan menrima data view
    // yg nantinya akan digunakan oleh viewmodel
    // atau untuk keperluhan bisnis aplikasi
    // lainya
    @Override
    public void attach(ReportDiagramActivityContract.View view) {
        this.view = view;
        this.transactionViewModel = new ViewModelProvider((ViewModelStoreOwner)view).get(TransactionViewModel.class);
    }

    // fungsi yg akan digunakan
    // untuk melakukan query
    // data transaksi dengan offset dan limit
    @Override
    public void getAllTransaction(int offset, int limit) {
        transactionViewModel.all(offset, limit, new UtilFunction.Unit<List<TransactionModel>>() {
            @Override
            public void invoke(@Nullable List<TransactionModel> o) {
                ((Activity) view).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        view.onGetAllTransaction(o);
                    }
                });
            }
        });
    }

    // fungsi yg akan digunakan
    // untuk melakukan query
    // data transaksi untuk diagram waterfall
    @Override
    public void getAllTransactionForWaterfall() {
        Observer<List<TransactionModel>> observer = new Observer<List<TransactionModel>>() {
            @Override
            public void onChanged(List<TransactionModel> transactionModels) {
                view.onGetAllTransactionForWaterfall(transactionModels);
            }
        };
        transactionViewModel.all().observe((LifecycleOwner) view, observer);
    }

    // fungsi yg akan digunakan
    // untuk melakukan query
    // data transaksi untuk diagram line
    @Override
    public void getAllTransactionForLine() {
        Observer<List<TransactionModel>> observer = new Observer<List<TransactionModel>>() {
            @Override
            public void onChanged(List<TransactionModel> transactionModels) {
                view.onGetAllTransactionForLine(transactionModels);
            }
        };
        transactionViewModel.all().observe((LifecycleOwner) view, observer);
    }

    // fungsi yg akan digunakan
    // untuk melakukan query
    // sisa saldo oleh view
    @Override
    public void getBallance() {
        transactionViewModel.total().observe((LifecycleOwner) view, new Observer<Double>() {
            @Override
            public void onChanged(Double aDouble) {
                view.onGetBallance(aDouble);
            }
        });
    }

    // fungsi yg akan digunakan
    // untuk melakukan query
    // data saldo pemasukkan
    // dan pengeluaran
    @Override
    public void getIncomeExpense() {
        Observer<IncomeAndExpenseModel> observer = new Observer<IncomeAndExpenseModel>() {
            @Override
            public void onChanged(IncomeAndExpenseModel incomeAndExpenseModel) {
                view.onGetIncomeExpense(incomeAndExpenseModel);
            }
        };
        transactionViewModel.getIncomeExpense().observe((LifecycleOwner) view,observer );
    }

    // fungsi yg akan digunakan
    // untuk menghapus data transaksi
    @Override
    public void deleteTransaction(TransactionModel t) {
        transactionViewModel.delete(t);
        view.onDeleteTransaction();
    }
}
