package com.example.stockmarketsdk;

import com.example.stockmarketsdk.dto.GlobalQuoteResponse;
import com.example.stockmarketsdk.dto.IntradayDataPoint;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface StockAPI {

    @GET("stocks/stock")
    Call<GlobalQuoteResponse.GlobalQuote> getStockQuote(@Query("symbol") String symbol);

    @GET("stocks/stock/intraday")
    Call<List<IntradayDataPoint>> getIntradayData(@Query("symbol") String symbol, @Query("interval") String interval);

}
