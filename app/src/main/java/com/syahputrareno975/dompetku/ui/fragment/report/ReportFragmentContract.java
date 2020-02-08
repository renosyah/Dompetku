package com.syahputrareno975.dompetku.ui.fragment.report;

import com.syahputrareno975.dompetku.base.BaseContract;

public class ReportFragmentContract  {
    public interface View extends BaseContract.View {
        public void showProgress(Boolean show);
        public void showError(String error);
    }

    public interface Presenter extends BaseContract.Presenter<ReportFragmentContract.View> {

    }
}
