package com.example.stockmarketsdk.controllers;

import com.example.stockmarketsdk.Callback_Stock;
import com.example.stockmarketsdk.apis.StockAPI;
import com.example.stockmarketsdk.dto.GlobalQuoteResponse;
import com.example.stockmarketsdk.dto.IntradayDataPoint;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StockController {
    private static final String BASE_URL = "https://your-stock-api.com/";

    private StockAPI getAPI() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(
                        GsonConverterFactory.create(
                                new GsonBuilder()
                                    .setLenient()
                                    .create()
                        )
                )
                .build();

        return retrofit.create(StockAPI.class);
    }

    public void getStockQuote(String symbol, Callback_Stock<GlobalQuoteResponse.GlobalQuote> callback) {
        getAPI().getStockQuote(symbol).enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<GlobalQuoteResponse.GlobalQuote> call, Response<GlobalQuoteResponse.GlobalQuote> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onFailure("Failed to fetch stock quote");
                }
            }

            @Override
            public void onFailure(Call<GlobalQuoteResponse.GlobalQuote> call, Throwable t) {
                callback.onFailure(t.getMessage());
            }
        });
    }

    public void getIntraday(String symbol, String interval, Callback_Stock<List<IntradayDataPoint>> callback) {
        Call<List<IntradayDataPoint>> call = getAPI().getIntraday(symbol, interval);
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<List<IntradayDataPoint>> call, Response<List<IntradayDataPoint>> response) {
                callback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<List<IntradayDataPoint>> call, Throwable t) {
                callback.onFailure(t.getMessage());
            }
        });
    }

    public void getTimeSeries(String function, String symbol, Callback_Stock<List<IntradayDataPoint>> callback) {
        getAPI().getTimeSeries(function,symbol).enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<List<IntradayDataPoint>> call, Response<List<IntradayDataPoint>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onFailure("Failed to fetch stock quote");
                }
            }

            @Override
            public void onFailure(Call<List<IntradayDataPoint>> call, Throwable t) {
                callback.onFailure(t.getMessage());
            }
        });
    }
}
