package com.example.stockmarketsdk;

import com.example.stockmarketsdk.controllers.StockController;
import com.example.stockmarketsdk.controllers.WatchlistController;
import com.example.stockmarketsdk.dto.GlobalQuoteResponse;
import com.example.stockmarketsdk.dto.IntradayDataPoint;

import java.util.List;

public class StockSDK {
    private static final StockController stockController = new StockController();
    private static final WatchlistController watchlistController = new WatchlistController();

    public static void getStockQuote(String symbol, Callback_Stock<GlobalQuoteResponse.GlobalQuote> callback) {
        stockController.getStockQuote(symbol, callback);
    }

    public static void getIntraday(String symbol, String interval, Callback_Stock<List<IntradayDataPoint>> callback) {
        stockController.getIntraday(symbol, interval, callback);
    }

    public static void getTimeSeries(String symbol, String interval, Callback_Stock<List<IntradayDataPoint>> callback) {
        stockController.getTimeSeries(symbol, interval, callback);
    }

    public static void getWatchlist(Callback_Stock<List<String>> callback) {
        watchlistController.getWatchlist(callback);
    }

    public static void addStockToWatchlist(String stockSymbol, Callback_Stock<String> callback) {
        watchlistController.addStockToWatchlist(stockSymbol, callback);
    }

    public static void removeStockFromWatchlist(String symbol, Callback_Stock<String> callback) {
        watchlistController.removeStockFromWatchlist(symbol, callback);
    }

    public static void clearWatchlist(Callback_Stock<Void> callback) {
        watchlistController.clearWatchlist(callback);
    }
}
