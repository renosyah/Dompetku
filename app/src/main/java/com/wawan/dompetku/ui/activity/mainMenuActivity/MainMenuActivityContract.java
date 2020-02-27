package com.wawan.dompetku.ui.activity.mainMenuActivity;

import androidx.annotation.Nullable;

import com.wawan.dompetku.base.BaseContract;
import com.wawan.dompetku.model.transaction.TransactionModel;

import java.sql.Date;

// adalah class contract untuk activity ini
// yg mana class ini akan menghandle
// fungsi-fungsi apa saja yg dibutkan untuk
// komunikasi antar view dengan presenter
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
