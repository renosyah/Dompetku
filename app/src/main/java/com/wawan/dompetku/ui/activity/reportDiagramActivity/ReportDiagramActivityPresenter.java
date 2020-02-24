package com.wawan.dompetku.ui.activity.reportDiagramActivity;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.wawan.dompetku.model.transaction.IncomeAndExpenseModel;
import com.wawan.dompetku.model.transaction.TransactionModel;
import com.wawan.dompetku.model.transaction.TransactionViewModel;

import java.util.List;

public class ReportDiagramActivityPresenter implements ReportDiagramActivityContract.Presenter {

    private ReportDiagramActivityContract.View view;
    private TransactionViewModel transactionViewModel;

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public void attach(ReportDiagramActivityContract.View view) {
        this.view = view;
        this.transactionViewModel = new ViewModelProvider((ViewModelStoreOwner)view).get(TransactionViewModel.class);
    }


    @Override
    public void getAllTransaction(int offset, int limit) {
        Observer<List<TransactionModel>> observer = new Observer<List<TransactionModel>>() {
            @Override
            public void onChanged(List<TransactionModel> transactionModels) {
                view.onGetAllTransaction(transactionModels);
            }
        };
        transactionViewModel.all(offset, limit).observe((LifecycleOwner) view,observer);
    }

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

    @Override
    public void getBallance() {
        transactionViewModel.total().observe((LifecycleOwner) view, new Observer<Double>() {
            @Override
            public void onChanged(Double aDouble) {
                view.onGetBallance(aDouble);
            }
        });
    }

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

    @Override
    public void deleteTransaction(TransactionModel t) {
        transactionViewModel.delete(t);
        view.onDeleteTransaction();
    }
}
