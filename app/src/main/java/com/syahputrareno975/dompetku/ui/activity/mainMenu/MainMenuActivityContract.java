package com.syahputrareno975.dompetku.ui.activity.mainMenu;

import com.syahputrareno975.dompetku.base.BaseContract;

public class MainMenuActivityContract {
    public interface View extends BaseContract.View {
        public void showProgress(Boolean show);
        public void showError(String error);
    }

    public interface Presenter extends BaseContract.Presenter<View> {

    }
}
