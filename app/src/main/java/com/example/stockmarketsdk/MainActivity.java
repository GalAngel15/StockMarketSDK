package com.example.stockmarketsdk;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stockmarketsdk.adapters.WatchlistAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView watchlistRecyclerView;
    private WatchlistAdapter watchlistAdapter;
    private final List<String> watchlist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize RecyclerView
        watchlistRecyclerView = findViewById(R.id.watchlist_recycler);
        watchlistRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        watchlistAdapter = new WatchlistAdapter(watchlist, stockSymbol -> {
            // Handle item click (load data into chart)
            Toast.makeText(this, "Selected: " + stockSymbol, Toast.LENGTH_SHORT).show();
        });

        watchlistRecyclerView.setAdapter(watchlistAdapter);

        // Load Watchlist
        loadWatchlist();
    }

    private void loadWatchlist() {
        StockSDK.getWatchlist(new Callback_Stock<List<String>>() {
            @Override
            public void onSuccess(List<String> result) {
                watchlist.clear();
                watchlist.addAll(result);
                watchlistAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(String errorMessage) {
                Toast.makeText(MainActivity.this, "Failed to load watchlist", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

