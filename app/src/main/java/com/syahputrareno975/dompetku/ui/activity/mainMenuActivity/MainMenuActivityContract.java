package com.syahputrareno975.dompetku.ui.activity.mainMenuActivity;

import androidx.annotation.Nullable;

import com.syahputrareno975.dompetku.base.BaseContract;
import com.syahputrareno975.dompetku.model.transaction.TransactionModel;

import java.sql.Date;

public class MainMenuActivityContract {
    public interface View extends BaseContract.View {
        public void showProgress(Boolean show);
        public void showError(String error);
        public void onGetBallance(@Nullable Double amount);
        public void onGetTransactionExpired(@Nullable TransactionModel t);
    }

    public interface Presenter extends BaseContract.Presenter<View> {
        public void getBallance();
        public void getTransactionExpired(Date d);
    }
}
