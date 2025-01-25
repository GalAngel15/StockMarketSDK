package com.example.stockmarketsdk.apis;

import com.example.stockmarketsdk.dto.WatchlistDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.DELETE;
import retrofit2.http.Path;


public interface WatchlistAPI {
    @GET("/watchlist")
    Call<List<WatchlistDTO>> getWatchlist();

    @POST("/watchlist/{stockSymbol}")
    Call<WatchlistDTO> addStockToWatchlist(@Path("stockSymbol") String stockSymbol);

    @DELETE("/watchlist/{symbol}")
    Call<Void> removeStockFromWatchlist(@Path("symbol") String symbol);

    @DELETE("/watchlist")
    Call<Void> clearWatchlist();
}

