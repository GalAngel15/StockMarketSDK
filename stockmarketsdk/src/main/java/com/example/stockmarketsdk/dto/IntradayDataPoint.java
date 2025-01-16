package com.example.stockmarketsdk.dto;

import com.google.gson.annotations.SerializedName;


public class IntradayDataPoint {

    @SerializedName("timestamp")
    private String timestamp;

    @SerializedName("open")
    private double open;

    @SerializedName("high")
    private double high;

    @SerializedName("low")
    private double low;

    @SerializedName("close")
    private double close;

    @SerializedName("volume")
    private long volume;

    // Getters and Setters
    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public double getOpen() {
        return open;
    }

    public void setOpen(double open) {
        this.open = open;
    }

    public double getHigh() {
        return high;
    }

    public void setHigh(double high) {
        this.high = high;
    }

    public double getLow() {
        return low;
    }

    public void setLow(double low) {
        this.low = low;
    }

    public double getClose() {
        return close;
    }

    public void setClose(double close) {
        this.close = close;
    }

    public long getVolume() {
        return volume;
    }

    public void setVolume(long volume) {
        this.volume = volume;
    }
}
