package com.example.stockmarketsdk;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stockmarketsdk.adapters.WatchlistAdapter;
import com.example.stockmarketsdk.dto.IntradayDataPoint;
import com.example.stockmarketsdk.dto.WatchlistDTO;
import com.example.stockmarketsdk.managers.ChartManager;
import com.example.stockmarketsdk.managers.WatchlistManager;
import com.example.stockmarketsdk.models.WatchlistItem;
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

public class MainActivity extends AppCompatActivity {
    private RecyclerView watchlistRecyclerView;
    private LineChart lineChart;
    private WatchlistManager watchlistManager;
    private ChartManager chartManager;
    private MaterialButton addStockButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();
        initButtons();
        watchlistManager = new WatchlistManager(this, watchlistRecyclerView);
        chartManager = new ChartManager(lineChart);
        //setChart();
        initWatchlist();
        StockSDK.getTimeSeries("TIME_SERIES_DAILY","AAPL", new Callback_Stock<List<IntradayDataPoint>>() {
            @Override
            public void onSuccess(List<IntradayDataPoint> result) {
                Log.e("MainActivity", "Fetched data: " + result);
                setChart(result, lineChart);
            }

            @Override
            public void onFailure(String errorMessage) {
                Log.e("MainActivity", "Failed to fetch data: " + errorMessage);
                Toast.makeText(MainActivity.this, "Failed to fetch data: " + errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initWatchlist() {
        setWatchlist();
        watchlistManager.getAdapter().setOnStockDeleteListener(this::onStockDelete);
    }


    private void findViews() {
        lineChart = findViewById(R.id.chart);
        watchlistRecyclerView = findViewById(R.id.watchlist_recycler);
        addStockButton = findViewById(R.id.add_stock);
    }

    private void initButtons() {
        addStockButton.setOnClickListener(v -> showAddStockDialog());
    }

    private void setChart() {
        List<Entry> entries = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            entries.add(new Entry(i + 5, (100 - i * 5) * 2));
        }
        chartManager.updateChartData(entries);
    }


    //Watchlist Adapter
    private void setWatchlist() {
        StockSDK.getWatchlist(new Callback_Stock<List<WatchlistDTO>>() {
            @Override
            public void onSuccess(List<WatchlistDTO> result) {
                List<WatchlistItem> watchlist = new ArrayList<>();
                Log.e("Watchlist", "Size: " + result.size() + "result: " + result);
                for (WatchlistDTO watchlistDTO : result) {
                    Log.e("Watchlist", watchlistDTO.getStockSymbol() + " " + watchlistDTO.getPrice());
                    watchlist.add(new WatchlistItem(watchlistDTO.getStockSymbol(), watchlistDTO.getPrice()));
                }
                watchlistManager.updateWatchlist(watchlist);
            }

            @Override
            public void onFailure(String errorMessage) {
                Log.e("Watchlist", "Failed to fetch watchlist: " + errorMessage);
                Toast.makeText(MainActivity.this, "Failed to fetch watchlist: " + errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void onStockDelete(WatchlistItem watchListItem, int i) {
        Log.e("MainActivity", "Attempting to delete: " + watchListItem.getStockSymbol());
        StockSDK.removeStockFromWatchlist(watchListItem.getStockSymbol(), new Callback_Stock<Void>() {
            @Override
            public void onSuccess(Void result) {
                Log.e("MainActivity", "Deleted successfully: " + watchListItem.getStockSymbol());
                watchlistManager.removeItem(i);
            }

            @Override
            public void onFailure(String errorMessage) {
                Log.e("MainActivity", "Failed to delete: " + errorMessage);

                Toast.makeText(MainActivity.this, "Failed to delete: " + errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void handleAddStock(String stockSymbol) {
        if (!stockSymbol.isEmpty()) {
            StockSDK.addStockToWatchlist(stockSymbol, new Callback_Stock<WatchlistDTO>() {
                @Override
                public void onSuccess(WatchlistDTO result) {
                    watchlistManager.addItem(new WatchlistItem(result.getStockSymbol(), result.getPrice()));
                    Toast.makeText(MainActivity.this, "Stock added: " + stockSymbol, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(String errorMessage) {
                    Toast.makeText(MainActivity.this, "Failed to add stock: " + errorMessage, Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(this, "No text entered", Toast.LENGTH_SHORT).show();
        }
    }

    // Dialog for adding a stock
    private void showAddStockDialog() {
        EditText inputEditText = new EditText(this);
        inputEditText.setHint("Enter stock symbol");

        new AlertDialog.Builder(this)
                .setTitle("Add Stock")
                .setView(inputEditText)
                .setPositiveButton("Add", (dialog, which) -> {
                    String stockSymbol = inputEditText.getText().toString().trim();
                    handleAddStock(stockSymbol); // קריאה לפונקציה המטפלת בהוספה
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .show();
    }

    private void setChart(List<IntradayDataPoint> dataPoints, LineChart lineChart) {
        TextView cornerText = findViewById(R.id.corner_text);
        // יצירת ערכי ציר Y (close) ו-X (timestamps כאינדקסים)
        List<Entry> entries = new ArrayList<>();
        for (int i = 0; i < dataPoints.size(); i++) {
            entries.add(new Entry(i, (float) dataPoints.get(i).getClose()));
        }

        // יצירת ה-DataSet
        LineDataSet dataSet = new LineDataSet(entries, "Closing Prices");
        dataSet.setColor(Color.BLUE);
        dataSet.setValueTextColor(Color.BLACK);

        // הגדרת Formatter לתאריכים בציר X
        String[] timestamps = dataPoints.stream()
                .map(IntradayDataPoint::getTimestamp)
                .toArray(String[]::new);

        lineChart.getXAxis().setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                int index = (int) value;
                return (index >= 0 && index < timestamps.length) ? timestamps[index] : "";
            }
        });

        // קביעת נתונים לגרף
        LineData lineData = new LineData(dataSet);
        lineChart.setData(lineData);
        lineChart.getXAxis().setGranularity(1f); // הצגת כל תאריך
        lineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);

        CustomMarkerView markerView = new CustomMarkerView(this, R.layout.custom_marker_view);
        markerView.setChartView(lineChart); // קישור ה-MarkerView לגרף
        lineChart.setMarker(markerView);   // הגדרת ה-MarkerView לגרף


        // הגדרת Listener לנקודות המסומנות
        lineChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                // עדכון הערכים בתצוגה בפינה
                cornerText.setText(String.format("X: %.0f, Y: %.2f", e.getX(), e.getY()));
            }

            @Override
            public void onNothingSelected() {
                // ריקון התצוגה בפינה כשאין נקודה נבחרת
                cornerText.setText("X: -, Y: -");
            }
        });


        lineChart.invalidate(); // רענון
    }

}