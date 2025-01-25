package com.example.stockmarketsdk.interfaces;

import com.example.stockmarketsdk.models.WatchlistItem;

public interface OnStockDeleteListener {
    void onStockDeleted(WatchlistItem editedExercise, int position);
}
