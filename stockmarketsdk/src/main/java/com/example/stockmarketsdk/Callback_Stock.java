package com.example.stockmarketsdk;

public interface Callback_Stock<T> {
        void onSuccess(T result);
        void onFailure(String errorMessage);
}
