package com.example.stockmarketsdk.controllers;

import com.example.stockmarketsdk.Callback_Stock;
import com.example.stockmarketsdk.apis.WatchlistAPI;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WatchlistController {
    private static final String BASE_URL = "https://your-stock-api.com/";

    private WatchlistAPI getAPI() {
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

        return retrofit.create(WatchlistAPI.class);
    }

    public void getWatchlist(Callback_Stock<List<String>> callback) {
        getAPI().getWatchlist().enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onFailure("Failed to fetch watchlist");
                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                callback.onFailure(t.getMessage());
            }
        });
    }

    public void addStockToWatchlist(String stockSymbol ,Callback_Stock<String> callback) {
        getAPI().addStockToWatchlist(stockSymbol).enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onFailure("Failed to fetch watchlist");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                callback.onFailure(t.getMessage());
            }
        });
    }

    public void removeStockFromWatchlist(String stockSymbol ,Callback_Stock<String> callback) {
        getAPI().removeStockFromWatchlist(stockSymbol).enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onFailure("Failed to fetch watchlist");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                callback.onFailure(t.getMessage());
            }
        });
    }

    public void clearWatchlist(Callback_Stock<Void> callback) {
        getAPI().clearWatchlist().enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(null);
                } else {
                    callback.onFailure("Failed to clear watchlist");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                callback.onFailure(t.getMessage());
            }
        });
    }
}

