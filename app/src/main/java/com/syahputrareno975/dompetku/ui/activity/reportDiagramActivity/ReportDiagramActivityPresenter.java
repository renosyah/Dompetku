package com.syahputrareno975.dompetku.ui.activity.reportDiagramActivity;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;

import com.syahputrareno975.dompetku.model.transaction.IncomeAndExpenseModel;
import com.syahputrareno975.dompetku.model.transaction.TransactionModel;
import com.syahputrareno975.dompetku.model.transaction.TransactionViewModel;
import com.syahputrareno975.dompetku.ui.activity.reportMenuActivity.ReportMenuActivityContract;

import java.sql.Date;
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
        transactionViewModel.all(offset, limit).observe((LifecycleOwner) view, new Observer<List<TransactionModel>>() {
            @Override
            public void onChanged(List<TransactionModel> transactionModels) {
                transactionViewModel.all(offset, limit).removeObservers((LifecycleOwner) view);
                view.onGetAllTransaction(transactionModels);
            }
        });
    }

    @Override
    public void getAllTransactionForWaterfall(Date start, Date end) {
        transactionViewModel.all(start, end).observe((LifecycleOwner) view, new Observer<List<TransactionModel>>() {
            @Override
            public void onChanged(List<TransactionModel> transactionModels) {
                transactionViewModel.all(start, end).removeObservers((LifecycleOwner) view);
                view.onGetAllTransactionForWaterfall(transactionModels);
            }
        });
    }

    @Override
    public void getAllTransactionForLine(Date start, Date end) {
        transactionViewModel.all(start, end).observe((LifecycleOwner) view, new Observer<List<TransactionModel>>() {
            @Override
            public void onChanged(List<TransactionModel> transactionModels) {
                transactionViewModel.all(start, end).removeObservers((LifecycleOwner) view);
                view.onGetAllTransactionForLine(transactionModels);
            }
        });
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
        transactionViewModel.getIncomeExpense().observe((LifecycleOwner) view, new Observer<IncomeAndExpenseModel>() {
            @Override
            public void onChanged(IncomeAndExpenseModel incomeAndExpenseModel) {
                transactionViewModel.getIncomeExpense().removeObservers((LifecycleOwner) view);
                view.onGetIncomeExpense(incomeAndExpenseModel);
            }
        });
    }
}
