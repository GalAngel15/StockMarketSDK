package com.example.stockmarketsdk;

import com.google.gson.annotations.SerializedName;
import java.util.Map;

public class IntradayResponse {

    @SerializedName("Meta Data")
    private MetaData metaData;

    @SerializedName("Time Series (5min)")
    private Map<String, TimeSeriesData> timeSeries;

    public MetaData getMetaData() {
        return metaData;
    }

    public void setMetaData(MetaData metaData) {
        this.metaData = metaData;
    }

    public Map<String, TimeSeriesData> getTimeSeries() {
        return timeSeries;
    }

    public void setTimeSeries(Map<String, TimeSeriesData> timeSeries) {
        this.timeSeries = timeSeries;
    }
}
