package com.example.stockmarketsdk;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
    private MaterialButton addStockButton, lastSelectedButton;
    private TextView cornerText;
    View.OnClickListener buttonClickListener = v -> {
        // אם יש כפתור שנבחר קודם, החזר את הצבע שלו ללבן
        if (lastSelectedButton != null) {
            lastSelectedButton.setTextColor(Color.WHITE);
        }

        // שנה את צבע הטקסט של הכפתור שנלחץ לצהוב
        MaterialButton clickedButton = (MaterialButton) v;
        clickedButton.setTextColor(Color.YELLOW);

        // שמור את הכפתור האחרון שנבחר
        lastSelectedButton = clickedButton;
        onStockClick(watchlistManager.getAdapter().getSelectedItem());
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();
        initButtons();
        watchlistManager = new WatchlistManager(this, watchlistRecyclerView);
        chartManager = new ChartManager(lineChart, cornerText);
        //setChart();
        initWatchlist();
    }

    private void initWatchlist() {
        setWatchlist();
        watchlistManager.getAdapter().setOnStockDeleteListener(this::onStockDelete);
        watchlistManager.getAdapter().setOnItemClickListener(this::onStockClick);
    }


    private void findViews() {
        lineChart = findViewById(R.id.chart);
        watchlistRecyclerView = findViewById(R.id.watchlist_recycler);
        addStockButton = findViewById(R.id.add_stock);
        cornerText = findViewById(R.id.corner_text);
        findViewById(R.id.button_1min).setOnClickListener(buttonClickListener);
        findViewById(R.id.button_5min).setOnClickListener(buttonClickListener);
        findViewById(R.id.button_30min).setOnClickListener(buttonClickListener);
        findViewById(R.id.button_60min).setOnClickListener(buttonClickListener);
        findViewById(R.id.button_day).setOnClickListener(buttonClickListener);
        findViewById(R.id.button_week).setOnClickListener(buttonClickListener);
        findViewById(R.id.button_month).setOnClickListener(buttonClickListener);
    }

    private void initButtons() {
        addStockButton.setOnClickListener(v -> showAddStockDialog());
        lastSelectedButton = findViewById(R.id.button_day);
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

    private void onStockClick(WatchlistItem watchListItem) {
        StockSDK.getTimeSeries(getTimeSeries(lastSelectedButton.getId()), watchListItem.getStockSymbol(), new Callback_Stock<List<IntradayDataPoint>>() {
            @Override
            public void onSuccess(List<IntradayDataPoint> result) {
                Log.e("MainActivity", "Fetched data: " + result);
                chartManager.updateChartData(result);
            }

            @Override
            public void onFailure(String errorMessage) {
                Log.e("MainActivity", "Failed to fetch data: " + errorMessage);
                Toast.makeText(MainActivity.this, "Failed to fetch data: " + errorMessage, Toast.LENGTH_SHORT).show();
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

    private StockSDK.TimeSeries getTimeSeries(int buttonId) {
        if (buttonId == R.id.button_1min) {
            return StockSDK.TimeSeries.INTRADAY_1MIN;
        } else if (buttonId == R.id.button_5min) {
            return StockSDK.TimeSeries.INTRADAY_5MIN;
        } else if (buttonId == R.id.button_15min) {
            return StockSDK.TimeSeries.INTRADAY_15MIN;
        } else if (buttonId == R.id.button_30min) {
            return StockSDK.TimeSeries.INTRADAY_30MIN;
        } else if (buttonId == R.id.button_60min) {
            return StockSDK.TimeSeries.INTRADAY_60MIN;
        } else if (buttonId == R.id.button_day) {
            return StockSDK.TimeSeries.DAILY;
        } else if (buttonId == R.id.button_week) {
            return StockSDK.TimeSeries.WEEKLY;
        } else if (buttonId == R.id.button_month) {
            return StockSDK.TimeSeries.MONTHLY;
        } else {
            return StockSDK.TimeSeries.DAILY; // ערך ברירת מחדל
        }
    }


}