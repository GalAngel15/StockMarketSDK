package com.example.stockmarketsdk.dto;

public class WatchlistDTO {
    private String stockSymbol;
    private double price;

    public WatchlistDTO() {
    }

    public WatchlistDTO(String stockSymbol) {
        this.stockSymbol = stockSymbol;
    }

    public WatchlistDTO(String stockSymbol, double price) {
        this.stockSymbol = stockSymbol;
        this.price = price;
    }

    public String getStockSymbol() {
        return stockSymbol;
    }

    public double getPrice() {
        return price;
    }

}
