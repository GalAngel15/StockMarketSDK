package com.example.stockmarketsdk.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stockmarketsdk.R;

import java.util.List;

public class WatchlistAdapter extends RecyclerView.Adapter<WatchlistAdapter.WatchlistViewHolder> {

    private final List<String> watchlist;
    private final OnItemClickListener listener;

    public WatchlistAdapter(List<String> watchlist, OnItemClickListener listener) {
        this.watchlist = watchlist;
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(String stockSymbol);
    }

    @NonNull
    @Override
    public WatchlistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_watchlist, parent, false);
        return new WatchlistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WatchlistViewHolder holder, int position) {
        String stockSymbol = watchlist.get(position);
        holder.stockSymbolText.setText(stockSymbol);

        holder.itemView.setOnClickListener(v -> listener.onItemClick(stockSymbol));
    }

    @Override
    public int getItemCount() {
        return watchlist.size();
    }

    static class WatchlistViewHolder extends RecyclerView.ViewHolder {
        private final TextView stockSymbolText;

        public WatchlistViewHolder(@NonNull View itemView) {
            super(itemView);
            stockSymbolText = itemView.findViewById(R.id.stock_symbol);
        }
    }
}
