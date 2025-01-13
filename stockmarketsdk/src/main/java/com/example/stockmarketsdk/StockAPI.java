package com.example.stockmarketsdk;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface StockAPI {

    @GET("/stock")
    Call<GlobalQuoteResponse.GlobalQuote> getStockQuote(@Query("symbol") String symbol);

    @GET("/intraday")
    Call<IntradayResponse> getIntradayData(@Query("symbol") String symbol, @Query("interval") String interval, @Query("apikey") String apikey);

}
