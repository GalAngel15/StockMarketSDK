package com.example.stockmarketsdk;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StockApiClient {

    private StockAPI stockAPI;

    // בנאי שמקבל כתובת URL דינמית
    public StockApiClient(String baseUrl) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        stockAPI = retrofit.create(StockAPI.class);
    }

    // פונקציה שמחזירה את ממשק ה-API
    public StockAPI getStockAPI() {
        return stockAPI;
    }
}
