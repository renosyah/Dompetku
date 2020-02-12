package com.syahputrareno975.dompetku.ui.activity.mainMenuActivity;

import androidx.annotation.Nullable;

import com.syahputrareno975.dompetku.base.BaseContract;

public class MainMenuActivityContract {
    public interface View extends BaseContract.View {
        public void showProgress(Boolean show);
        public void showError(String error);
        public void onGetBallance(@Nullable Double amount);
    }

    public interface Presenter extends BaseContract.Presenter<View> {
        public void getBallance();
    }
}
