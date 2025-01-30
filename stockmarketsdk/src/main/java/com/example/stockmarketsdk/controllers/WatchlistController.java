package com.example.stockmarketsdk.controllers;

import com.example.stockmarketsdk.Callback_Stock;
import com.example.stockmarketsdk.apis.WatchlistAPI;
import com.example.stockmarketsdk.dto.StockDTO;
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


    public void getAllWatchlists(Callback_Stock<List<WatchlistDTO>> callback) {
        getAPI().getAllWatchlists().enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<List<WatchlistDTO>> call, Response<List<WatchlistDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onFailure("Failed to fetch watchlists");
                }
            }

            @Override
            public void onFailure(Call<List<WatchlistDTO>> call, Throwable t) {
                callback.onFailure(t.getMessage());
            }
        });
    }

    public void createWatchlist(String name, Callback_Stock<WatchlistDTO> callback) {
        getAPI().createWatchlist(name).enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<WatchlistDTO> call, Response<WatchlistDTO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onFailure("Failed to create watchlist");
                }
            }

            @Override
            public void onFailure(Call<WatchlistDTO> call, Throwable t) {
                callback.onFailure(t.getMessage());
            }
        });
    }

    public void getAllStocks(Callback_Stock<List<StockDTO>> callback) {
        getAPI().getAllStocks().enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<List<StockDTO>> call, Response<List<StockDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onFailure("Failed to fetch stocks");
                }
            }

            @Override
            public void onFailure(Call<List<StockDTO>> call, Throwable t) {
                callback.onFailure(t.getMessage());
            }
        });
    }

    public void getWatchlistByName(String name, Callback_Stock<WatchlistDTO> callback) {
        getAPI().getWatchlistByName(name).enqueue(new Callback<>() {
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

    public void deleteWatchlist(String name, Callback_Stock<Void> callback) {
        getAPI().deleteWatchlist(name).enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(null);
                } else {
                    callback.onFailure("Failed to delete watchlist");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                callback.onFailure(t.getMessage());
            }
        });
    }

    public void addStockToWatchlist(String name, String stockSymbol, Callback_Stock<WatchlistDTO> callback) {
        getAPI().addStockToWatchlist(name, stockSymbol).enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<WatchlistDTO> call, Response<WatchlistDTO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onFailure("Failed to add stock to watchlist");
                }
            }

            @Override
            public void onFailure(Call<WatchlistDTO> call, Throwable t) {
                callback.onFailure(t.getMessage());
            }
        });
    }

    public void removeStockFromWatchlist(String name, String stockSymbol, Callback_Stock<WatchlistDTO> callback) {
        getAPI().removeStockFromWatchlist(name, stockSymbol).enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<WatchlistDTO> call, Response<WatchlistDTO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onFailure("Failed to remove stock from watchlist");
                }
            }

            @Override
            public void onFailure(Call<WatchlistDTO> call, Throwable t) {
                callback.onFailure(t.getMessage());
            }
        });
    }
}
