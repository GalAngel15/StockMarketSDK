package com.example.stockmarketsdk.apis;

import com.example.stockmarketsdk.dto.StockDTO;
import com.example.stockmarketsdk.dto.WatchlistDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface WatchlistAPI {

    @POST("watchlists")
    Call<WatchlistDTO> createWatchlist(@Query("name") String name);

    @GET("watchlists")
    Call<List<WatchlistDTO>> getAllWatchlists();

    @GET("watchlists/stocks")
    Call<List<StockDTO>> getAllStocks();

    @GET("watchlists/{name}")
    Call<WatchlistDTO> getWatchlistByName(@Path("name") String name);

    @DELETE("watchlists/{name}")
    Call<Void> deleteWatchlist(@Path("name") String name);

    @PUT("watchlists/{name}/add-stock")
    Call<WatchlistDTO> addStockToWatchlist(@Path("name") String name, @Query("stockSymbol") String stockSymbol);

    @PUT("watchlists/{name}/remove-stock")
    Call<WatchlistDTO> removeStockFromWatchlist(@Path("name") String name, @Query("stockSymbol") String stockSymbol);
}