package com.example.stockmarketsdk.dto;

import com.google.gson.annotations.SerializedName;

public class GlobalQuoteResponse {

    @SerializedName("Global Quote")
    private GlobalQuote globalQuote;

    public GlobalQuote getGlobalQuote() {
        return globalQuote;
    }

    public void setGlobalQuote(GlobalQuote globalQuote) {
        this.globalQuote = globalQuote;
    }

    public static class GlobalQuote {

        @SerializedName("01. symbol")
        private String symbol;

        @SerializedName("02. open")
        private double open;

        @SerializedName("03. high")
        private double high;

        @SerializedName("04. low")
        private double low;

        @SerializedName("05. price")
        private double price;

        @SerializedName("06. volume")
        private long volume;

        @SerializedName("07. latest trading day")
        private String latestTradingDay;

        @SerializedName("08. previous close")
        private double previousClose;

        @SerializedName("09. change")
        private double change;

        @SerializedName("10. change percent")
        private String changePercent;

        // Getters and Setters
        public String getSymbol() {
            return symbol;
        }

        public void setSymbol(String symbol) {
            this.symbol = symbol;
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

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public long getVolume() {
            return volume;
        }

        public void setVolume(long volume) {
            this.volume = volume;
        }

        public String getLatestTradingDay() {
            return latestTradingDay;
        }

        public void setLatestTradingDay(String latestTradingDay) {
            this.latestTradingDay = latestTradingDay;
        }

        public double getPreviousClose() {
            return previousClose;
        }

        public void setPreviousClose(double previousClose) {
            this.previousClose = previousClose;
        }

        public double getChange() {
            return change;
        }

        public void setChange(double change) {
            this.change = change;
        }

        public String getChangePercent() {
            return changePercent;
        }

        public void setChangePercent(String changePercent) {
            this.changePercent = changePercent;
        }
    }
}
