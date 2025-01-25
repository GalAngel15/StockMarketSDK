package com.example.stockmarketsdk.controllers;

import com.example.stockmarketsdk.Callback_Stock;
import com.example.stockmarketsdk.apis.WatchlistAPI;
import com.example.stockmarketsdk.dto.WatchlistDTO;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WatchlistController {
    private static final String BASE_URL = "https://pale-caitlin-dev-gal-angel-50977206.koyeb.app/";

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

    public void getWatchlist(Callback_Stock<List<WatchlistDTO>> callback) {
        getAPI().getWatchlist().enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<List<WatchlistDTO>> call, Response<List<WatchlistDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onFailure("Failed to fetch watchlist");
                }
            }

            @Override
            public void onFailure(Call<List<WatchlistDTO>> call, Throwable t) {
                callback.onFailure(t.getMessage());
            }
        });
    }

    public void addStockToWatchlist(String stockSymbol ,Callback_Stock<WatchlistDTO> callback) {
        getAPI().addStockToWatchlist(stockSymbol.toUpperCase()).enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<WatchlistDTO> call, Response<WatchlistDTO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onFailure("Failed to fetch watchlist");
                }
            }

            @Override
            public void onFailure(Call<WatchlistDTO> call, Throwable t) {
                callback.onFailure(t.getMessage());
            }
        });
    }

    public void removeStockFromWatchlist(String stockSymbol ,Callback_Stock<Void> callback) {
        getAPI().removeStockFromWatchlist(stockSymbol).enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(null);
                } else {
                    callback.onFailure("Failed to delete stock: HTTP " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                callback.onFailure("Error: " + t.getMessage());
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

