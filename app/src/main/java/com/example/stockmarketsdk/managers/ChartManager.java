package com.example.stockmarketsdk.managers;

import android.graphics.Color;
import android.widget.TextView;

import com.example.stockmarketsdk.dto.IntradayDataPoint;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class ChartManager {
    private final LineChart chart;
    private final TextView cornerText;
    private String[] timestamps; // Global array to store timestamps


    public ChartManager(LineChart chart, TextView cornerText) {
        this.chart = chart;
        this.cornerText = cornerText;
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
        setCornerText();
    }

    public void updateChartData(List<IntradayDataPoint> lst) {
        List<Entry> entries = new ArrayList<>();
        List<Integer> colors = new ArrayList<>(); // Colors for the trend

        for (int i = 0; i < lst.size(); i++) {
            float currClose = (float) lst.get(i).getClose();
            entries.add(new Entry(i, currClose));

            // Set color based on trend
            if (i > 0) {
                float prevClose = (float) lst.get(i - 1).getClose();
                if (currClose > prevClose) {
                    colors.add(Color.GREEN); // Rise
                } else {
                    colors.add(Color.RED); // Fall
                }
            }
        }

        // Create LineDataSet
        LineDataSet dataSet = new LineDataSet(entries, "Stock Price");
        dataSet.setDrawValues(false); // Do not display values on the points
        dataSet.setDrawCircles(false); // Do not display circles on the points
        dataSet.setColors(colors); // Set colors for the trend

        // Create LineData
        LineData data = new LineData(dataSet);
        chart.setData(data);

        // Set timestamps for the X axis
        timestamps = lst.stream()
                .map(IntradayDataPoint::getTimestamp)
                .toArray(String[]::new);

        chart.getXAxis().setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                int index = (int) value;
                return (index >= 0 && index < timestamps.length) ? timestamps[index] : "";
            }
        });

        chart.getXAxis().setGranularity(1f); // Set the granularity to 1
        chart.invalidate(); // Refresh the chart
    }


    public void setCornerText(){
        chart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                int index = (int) e.getX();
                if (index >= 0 && index < timestamps.length) {
                    String date = timestamps[index];
                    cornerText.setText(String.format("Date: %s, Y: %.2f", date, e.getY()));
                }
            }

            @Override
            public void onNothingSelected() {
                // Reset the corner text
                cornerText.setText("Date: -, Y: -");

            }
        });
    }

}

