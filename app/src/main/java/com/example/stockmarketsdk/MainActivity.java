package com.example.stockmarketsdk;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stockmarketsdk.dto.IntradayDataPoint;
import com.example.stockmarketsdk.dto.StockDTO;
import com.example.stockmarketsdk.dto.WatchlistDTO;
import com.example.stockmarketsdk.managers.ChartManager;
import com.example.stockmarketsdk.managers.WatchlistManager;
import com.example.stockmarketsdk.models.WatchlistItem;
import com.example.stockmarketsdk.utils.DialogHelper;
import com.github.mikephil.charting.charts.LineChart;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView watchlistRecyclerView;
    private LineChart lineChart;
    private WatchlistManager watchlistManager;
    private ChartManager chartManager;
    private MaterialButton addStockButton, lastSelectedButton, clearWathclistButton;
    private TextView cornerText;
    private Spinner watchlistSpinner;
    private WatchlistDTO selectedWatchlist;
    private ImageButton addWatchlistButton, DeleteWatchlistButton;

    View.OnClickListener buttonClickListener = v -> {
        // Change the text color of the last selected button to white
        if (lastSelectedButton != null) {
            lastSelectedButton.setTextColor(Color.WHITE);
        }

        // Change the text color of the clicked button to yellow
        MaterialButton clickedButton = (MaterialButton) v;
        clickedButton.setTextColor(Color.YELLOW);

        // Set the last selected button to the clicked button
        lastSelectedButton = clickedButton;
        onStockClick(watchlistManager.getAdapter().getSelectedItem(),-1);
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();
        initButtons();
        watchlistManager = new WatchlistManager(this, watchlistRecyclerView);
        chartManager = new ChartManager(lineChart, cornerText);
        loadWatchlists();
        initWatchlist();
    }

    private void initWatchlist() {
        watchlistManager.getAdapter().setOnStockDeleteListener(this::onStockDelete);
        watchlistManager.getAdapter().setOnItemClickListener(this::onStockClick);
    }


    private void findViews() {
        lineChart = findViewById(R.id.chart);
        watchlistRecyclerView = findViewById(R.id.watchlist_recycler);
        addStockButton = findViewById(R.id.add_stock);
        cornerText = findViewById(R.id.corner_text);
        watchlistSpinner = findViewById(R.id.watchlist_spinner);
        clearWathclistButton=findViewById(R.id.clear_watchlist);
        addWatchlistButton=findViewById(R.id.add_watchlist);
        lastSelectedButton = findViewById(R.id.button_day);
        DeleteWatchlistButton=findViewById(R.id.delete_watchlist);

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
        addWatchlistButton.setOnClickListener(v-> showAddWatchlistDialog());
        DeleteWatchlistButton.setOnClickListener(v-> showDeleteWatchlistDialog());
    }

    //Watchlist Adapter
    private void onStockDelete(WatchlistItem watchListItem, int i) {
        Log.e("MainActivity", "Attempting to delete: " + watchListItem.getStockSymbol());
        StocksSDK.removeStockFromWatchlist(selectedWatchlist.getName(),watchListItem.getStockSymbol(), new Callback_Stock<WatchlistDTO>() {
            @Override
            public void onSuccess(WatchlistDTO result) {
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

    private void onStockClick(WatchlistItem watchListItem, int position) {
        StocksSDK.getTimeSeries(getTimeSeries(lastSelectedButton.getId()), watchListItem.getStockSymbol(), new Callback_Stock<List<IntradayDataPoint>>() {
            @Override
            public void onSuccess(List<IntradayDataPoint> result) {
                Log.e("MainActivity", "Fetched data: " + result);
                watchListItem.setStockPrice(result.get(result.size() - 1).getClose());
                if (position != -1) {
                    watchlistManager.getAdapter().notifyItemChanged(position);
                }
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
        if (!stockSymbol.isEmpty() && selectedWatchlist != null) {
            StocksSDK.addStockToWatchlist(selectedWatchlist.getName(),stockSymbol, new Callback_Stock<WatchlistDTO>() {
                @Override
                public void onSuccess(WatchlistDTO result) {
                    for (StockDTO stock : result.getStockBoundary()) {
                        if (stock.getStockSymbol().equalsIgnoreCase(stockSymbol)) {
                            double price = stock.getPrice();
                            watchlistManager.addItem(new WatchlistItem(stockSymbol, price));
                            Toast.makeText(MainActivity.this, "Stock added: " + stockSymbol, Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    watchlistManager.addItem(new WatchlistItem(stockSymbol, -1));
                    Toast.makeText(MainActivity.this, "Stock added but price unknown: " + stockSymbol, Toast.LENGTH_SHORT).show();
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
        DialogHelper.showInputDialog(
                this,
                "Add Stock",
                "Enter stock symbol",
                this::handleAddStock);
    }

    private StocksSDK.TimeSeries getTimeSeries(int buttonId) {
        if (buttonId == R.id.button_1min) {
            return StocksSDK.TimeSeries.INTRADAY_1MIN;
        } else if (buttonId == R.id.button_5min) {
            return StocksSDK.TimeSeries.INTRADAY_5MIN;
        } else if (buttonId == R.id.button_15min) {
            return StocksSDK.TimeSeries.INTRADAY_15MIN;
        } else if (buttonId == R.id.button_30min) {
            return StocksSDK.TimeSeries.INTRADAY_30MIN;
        } else if (buttonId == R.id.button_60min) {
            return StocksSDK.TimeSeries.INTRADAY_60MIN;
        } else if (buttonId == R.id.button_day) {
            return StocksSDK.TimeSeries.DAILY;
        } else if (buttonId == R.id.button_week) {
            return StocksSDK.TimeSeries.WEEKLY;
        } else if (buttonId == R.id.button_month) {
            return StocksSDK.TimeSeries.MONTHLY;
        } else {
            return StocksSDK.TimeSeries.DAILY; // ערך ברירת מחדל
        }
    }

    private void loadWatchlists() {
        StocksSDK.getAllWatchlists(new Callback_Stock<List<WatchlistDTO>>() {
            @Override
            public void onSuccess(List<WatchlistDTO> result) {

                // יצירת רשימת שמות לתפריט הנפתח
                List<String> watchlistNames = new ArrayList<>();
                for (WatchlistDTO w : result) {
                    watchlistNames.add(w.getName());
                }

                // עדכון ה-Spinner עם הרשימות
                ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_spinner_dropdown_item, watchlistNames);
                watchlistSpinner.setAdapter(adapter);

                // בחירה ראשונית של הרשימה הראשונה (אם קיימת)
                if (selectedWatchlist==null) {
                    selectedWatchlist = result.get(0);
                    updateWatchlist(selectedWatchlist);
                }

                // מאזין לבחירת רשימה מה-Spinner
                watchlistSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        selectedWatchlist = result.get(position);
                        updateWatchlist(selectedWatchlist);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
            }

            @Override
            public void onFailure(String errorMessage) {
                Toast.makeText(MainActivity.this, "Failed to load watchlists: " + errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showAddWatchlistDialog() {
        DialogHelper.showInputDialog(
                this,
                "Add Watchlist",
                "Enter Watchlist name",
                this::addWatchlist);
    }
    private void addWatchlist(String name) {
        StocksSDK.createWatchlist(name, new Callback_Stock<WatchlistDTO>() {
            @Override
            public void onSuccess(WatchlistDTO result) {
                Log.e("MainActivity", "Watchlist created: " + result);
                loadWatchlists();
            }

            @Override
            public void onFailure(String errorMessage) {
                Log.e("MainActivity", "Failed to create watchlist: " + errorMessage);
                Toast.makeText(MainActivity.this, "Failed to create watchlist: " + errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showDeleteWatchlistDialog() {
        if (watchlistSpinner.getAdapter() != null && watchlistSpinner.getAdapter().getCount() <= 1) {
            Toast.makeText(this, "You must have at least one watchlist. Deletion is not allowed.", Toast.LENGTH_SHORT).show();
            return;
        }

        String message = "Are you sure you want to delete the watchlist \"" + watchlistSpinner.getSelectedItem().toString() + "\"?";
        DialogHelper.showConfirmationDialog(this, "Delete Watchlist", message, this::deleteWatchlist);
    }

    private void deleteWatchlist(String unused) {
        StocksSDK.deleteWatchlist(watchlistSpinner.getSelectedItem().toString(), new Callback_Stock<Void>() {
            @Override
            public void onSuccess(Void result) {
                selectedWatchlist=null;
                loadWatchlists();
            }

            @Override
            public void onFailure(String errorMessage) {
                Log.e("MainActivity", "Failed to delete watchlist: " + errorMessage);
                Toast.makeText(MainActivity.this, "Failed to delete watchlist: " + errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void updateWatchlist(WatchlistDTO watchlist) {
        Log.e("MainActivity", "Updating watchlist: " + watchlist);
        if (watchlist != null) {
            StocksSDK.getWatchlistByName(watchlist.getName(), new Callback_Stock<WatchlistDTO>() {
                @Override
                public void onSuccess(WatchlistDTO result) {
                    Log.e("MainActivity", "Watchlist updated: " + result);
                    List<WatchlistItem> watchlistItems = new ArrayList<>();
                    for (StockDTO stock : result.getStockBoundary()) {
                        watchlistItems.add(new WatchlistItem(stock.getStockSymbol(), stock.getPrice()));
                    }
                    watchlistManager.updateWatchlist(watchlistItems);
                }

                @Override
                public void onFailure(String errorMessage) {
                    Log.e("MainActivity", "Failed to update watchlist: " + errorMessage);
                    Toast.makeText(MainActivity.this, "Failed to load watchlist: " + errorMessage, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}