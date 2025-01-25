package com.example.stockmarketsdk.managers;

import android.graphics.Color;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.List;

public class ChartManager {
    private final LineChart chart;

    public ChartManager(LineChart chart) {
        this.chart = chart;
        setupChart();
    }

    private void setupChart() {
        chart.setBackgroundColor(Color.WHITE);
        chart.setDrawGridBackground(false);
        chart.getDescription().setEnabled(false);
        chart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        chart.getAxisRight().setEnabled(false);
        chart.getAxisLeft().setTextColor(Color.BLACK);
        //dataSet.setColor(Color.BLUE);
    }

    public void updateChartData(List<Entry> entries) {
        LineDataSet dataSet = new LineDataSet(entries, "Stock Prices2");
        LineData data = new LineData(dataSet);
        chart.setData(data);
        chart.invalidate(); // Refresh the chart
    }
}

