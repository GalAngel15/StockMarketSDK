package com.example.stockmarketsdk.apis;

import com.example.stockmarketsdk.dto.GlobalQuoteResponse;
import com.example.stockmarketsdk.dto.IntradayDataPoint;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface StockAPI {

    @GET("stock")
    Call<GlobalQuoteResponse.GlobalQuote> getStockQuote(@Query("symbol") String symbol);

    @GET("stock/intraday")
    Call<List<IntradayDataPoint>> getIntraday(
            @Query("symbol") String symbol,
            @Query("interval") String interval
    );

    @GET("stock/time-series")
    Call<List<IntradayDataPoint>> getTimeSeries(
            @Query("function") String function,
            @Query("symbol") String symbol
    );

}
