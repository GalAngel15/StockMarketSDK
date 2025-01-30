package com.example.stockmarketsdk.dto;

public class StockDTO {
    private String stockSymbol;
    private double price;

    public StockDTO() {
    }

    public StockDTO(String stockSymbol) {
        this.stockSymbol = stockSymbol;
    }

    public StockDTO(String stockSymbol, double price) {
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
