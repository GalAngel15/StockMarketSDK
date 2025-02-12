package com.example.stockmarketsdk;

import com.example.stockmarketsdk.controllers.StockController;
import com.example.stockmarketsdk.controllers.WatchlistController;
import com.example.stockmarketsdk.dto.GlobalQuoteResponse;
import com.example.stockmarketsdk.dto.IntradayDataPoint;
import com.example.stockmarketsdk.dto.StockDTO;
import com.example.stockmarketsdk.dto.WatchlistDTO;

import java.util.List;

public class StocksSDK {
    public enum TimeSeries {INTRADAY_1MIN, INTRADAY_5MIN, INTRADAY_15MIN, INTRADAY_30MIN, INTRADAY_60MIN, DAILY, WEEKLY, MONTHLY
    }
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

    public static void getTimeSeries(TimeSeries function, String symbol, Callback_Stock<List<IntradayDataPoint>> callback) {
        switch (function){
            case INTRADAY_1MIN:
                stockController.getIntraday(symbol, "1min", callback);
                break;
            case INTRADAY_5MIN:
                stockController.getIntraday(symbol, "5min", callback);
                break;
            case INTRADAY_15MIN:
                stockController.getIntraday(symbol, "15min", callback);
                break;
            case INTRADAY_30MIN:
                stockController.getIntraday(symbol, "30min", callback);
                break;
            case INTRADAY_60MIN:
                stockController.getIntraday(symbol, "60min", callback);
                break;
            case DAILY:
                stockController.getTimeSeries("TIME_SERIES_DAILY", symbol, callback);
                break;
            case WEEKLY:
                stockController.getTimeSeries("TIME_SERIES_WEEKLY", symbol, callback);
                break;
            case MONTHLY:
                stockController.getTimeSeries("TIME_SERIES_MONTHLY", symbol, callback);
                break;
        }
    }

    public static void getAllWatchlists(Callback_Stock<List<WatchlistDTO>> callback) {
        watchlistController.getAllWatchlists(callback);
    }
    public static void createWatchlist(String name, Callback_Stock<WatchlistDTO> callback) {
        watchlistController.createWatchlist(name, callback);
    }

    public static void getAllStocks(Callback_Stock<List<StockDTO>> callback) {
        watchlistController.getAllStocks(callback);
    }

    public static void getWatchlistByName(String name, Callback_Stock<WatchlistDTO> callback) {
        watchlistController.getWatchlistByName(name, callback);
    }

    public static void deleteWatchlist(String name, Callback_Stock<Void> callback) {
        watchlistController.deleteWatchlist(name, callback);
    }

    public static void addStockToWatchlist(String name, String stockSymbol, Callback_Stock<WatchlistDTO> callback) {
        watchlistController.addStockToWatchlist(name, stockSymbol, callback);
    }

    public static void removeStockFromWatchlist(String name, String stockSymbol, Callback_Stock<WatchlistDTO> callback) {
        watchlistController.removeStockFromWatchlist(name, stockSymbol, callback);
    }
}
