package com.example.stockmarketsdk.interfaces;

import com.example.stockmarketsdk.models.WatchlistItem;

public interface OnStockActionListener {
    void onStockActionListener(WatchlistItem editedExercise, int position);
}
