package com.example.stockmarketsdk.models;

public class WatchlistItem {
    private final String stockSymbol;
    private double stockPrice;

    public WatchlistItem(String stockSymbol, double stockPrice) {
        this.stockSymbol = stockSymbol;
        this.stockPrice = stockPrice;
    }

    public String getStockSymbol() {
        return stockSymbol;
    }

    public double getStockPrice() {
        return stockPrice;
    }

    public boolean setStockPrice(double stockPrice) {
        if (stockPrice < 0)
            return false;
        this.stockPrice = stockPrice;
        return true;
    }
}
