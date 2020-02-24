package com.wawan.dompetku.base;

public class BaseContract {
    public interface Presenter<T> {
        void subscribe();
        void unsubscribe();
        void attach(T view);
    }
    public interface View {}
}
