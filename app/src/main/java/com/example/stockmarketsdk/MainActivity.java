package com.example.stockmarketsdk;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stockmarketsdk.adapters.WatchlistAdapter;
import com.example.stockmarketsdk.dto.WatchlistDTO;
import com.example.stockmarketsdk.managers.ChartManager;
import com.example.stockmarketsdk.managers.WatchlistManager;
import com.example.stockmarketsdk.models.WatchlistItem;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView watchlistRecyclerView;
    private LineChart lineChart;
    private WatchlistManager watchlistManager;
    private ChartManager chartManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();
        watchlistManager = new WatchlistManager(this, watchlistRecyclerView);
        chartManager = new ChartManager(lineChart);
        setChart();
        initWatchlist();

    }

    private void initWatchlist() {
        setWatchlist();
        watchlistManager.getAdapter().setOnStockDeleteListener(this::onStockDelete);
    }


    private void findViews() {
        lineChart = findViewById(R.id.chart);
        watchlistRecyclerView = findViewById(R.id.watchlist_recycler);
    }



    private void setChart() {
        List<Entry> entries = new ArrayList<>();
        for (int i=0; i<5; i++) {
            entries.add(new Entry(i+5, (100-i*5)*2));
        }

        LineDataSet dataSet = new LineDataSet(entries, "ערך מניה");
        dataSet.setColor(Color.BLUE);          // צבע הקו
        dataSet.setValueTextColor(Color.BLACK);

        LineData lineData = new LineData(dataSet);

        lineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        lineChart.setData(lineData);
        lineChart.invalidate(); // מרענן את הגרף

    }


    //Watchlist Adapter
    private void setWatchlist() {
        StockSDK.getWatchlist(new Callback_Stock<List<WatchlistDTO>>() {
            @Override
            public void onSuccess(List<WatchlistDTO> result) {
                List<WatchlistItem> watchlist=new ArrayList<>();
                Log.e("Watchlist", "Size: " + result.size()+ "result: " + result);
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
        StockSDK.removeStockFromWatchlist(watchListItem.getStockSymbol(), new Callback_Stock<String>() {
            @Override
            public void onSuccess(String result) {
                Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT).show();
                watchlistManager.removeItem(i);
            }

            @Override
            public void onFailure(String errorMessage) {
                Toast.makeText(MainActivity.this, "Failed to delete: " + errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }
}

