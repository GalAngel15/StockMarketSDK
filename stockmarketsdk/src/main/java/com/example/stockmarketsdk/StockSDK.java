package com.example.stockmarketsdk;

import com.example.stockmarketsdk.controllers.StockController;
import com.example.stockmarketsdk.controllers.WatchlistController;
import com.example.stockmarketsdk.dto.GlobalQuoteResponse;
import com.example.stockmarketsdk.dto.IntradayDataPoint;
import com.example.stockmarketsdk.dto.WatchlistDTO;

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

    public static void getTimeSeries(String function, String symbol, Callback_Stock<List<IntradayDataPoint>> callback) {
        stockController.getTimeSeries(function, symbol, callback);
    }

    public static void getWatchlist(Callback_Stock<List<WatchlistDTO>> callback) {
        watchlistController.getWatchlist(callback);
    }

    public static void addStockToWatchlist(String stockSymbol, Callback_Stock<WatchlistDTO> callback) {
        watchlistController.addStockToWatchlist(stockSymbol, callback);
    }

    public static void removeStockFromWatchlist(String symbol, Callback_Stock<Void> callback) {
        watchlistController.removeStockFromWatchlist(symbol, callback);
    }

    public static void clearWatchlist(Callback_Stock<Void> callback) {
        watchlistController.clearWatchlist(callback);
    }
}
