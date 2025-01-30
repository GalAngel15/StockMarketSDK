package com.example.stockmarketsdk.dto;

import java.util.List;

public class WatchlistDTO {
    private String name;
    private List<StockDTO> stockBoundary;

    public WatchlistDTO() {
    }

    public WatchlistDTO(String name) {
        this.name = name;
    }

    public WatchlistDTO(String name, List<StockDTO> stockBoundary) {
        this.name = name;
        this.stockBoundary = stockBoundary;
    }

    public String getName() {
        return name;
    }

    public List<StockDTO> getStockBoundary() {
        return stockBoundary;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStockBoundary(List<StockDTO> stockBoundary) {
        this.stockBoundary = stockBoundary;
    }

    @Override
    public String toString() {
        return "WatchlistDTO{" +
                "name='" + name + '\'' +
                ", stockBoundary=" + stockBoundary +
                '}';
    }

}
