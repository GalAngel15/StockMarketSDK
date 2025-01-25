package com.example.stockmarketsdk.managers;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stockmarketsdk.adapters.WatchlistAdapter;
import com.example.stockmarketsdk.models.WatchlistItem;

import java.util.ArrayList;
import java.util.List;

public class WatchlistManager {
    private final List<WatchlistItem> watchlist;
    private final WatchlistAdapter adapter;
    private final Context context;

    public WatchlistManager(Context context, RecyclerView recyclerView) {
        this.context = context;
        this.watchlist = new ArrayList<>();
        this.adapter = new WatchlistAdapter(watchlist);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter);
    }

    public WatchlistAdapter getAdapter() {
        return adapter;
    }
    public void updateWatchlist(List<WatchlistItem> items) {
        watchlist.clear();
        watchlist.addAll(items);
        adapter.notifyDataSetChanged();
    }

    public void addItem(WatchlistItem item) {
        watchlist.add(item);
        adapter.notifyItemInserted(watchlist.size() - 1);
    }

    public void removeItem(int position) {
        if (position >= 0 && position < watchlist.size()) {
            watchlist.remove(position);
            adapter.notifyItemRemoved(position);
        }
    }
    public List<WatchlistItem> getWatchlist() {
        return new ArrayList<>(watchlist); // Return a copy of the list
    }


}

