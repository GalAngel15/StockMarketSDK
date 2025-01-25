package com.example.stockmarketsdk.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stockmarketsdk.R;
import com.example.stockmarketsdk.interfaces.OnStockDeleteListener;
import com.example.stockmarketsdk.models.WatchlistItem;

import java.util.List;

public class WatchlistAdapter extends RecyclerView.Adapter<WatchlistAdapter.WatchlistViewHolder> {

    private final List<WatchlistItem> watchlist;
    private OnStockDeleteListener onStockDeleteListener;

    public WatchlistAdapter(List<WatchlistItem> watchlist) {
        this.watchlist = watchlist;
    }

    public void setOnStockDeleteListener(OnStockDeleteListener listener){
        this.onStockDeleteListener = listener;
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
        WatchlistItem stock = watchlist.get(position);
        holder.stockSymbol.setText(stock.getStockSymbol());
        holder.stockPrice.setText(stock.getStockPrice() + " $");
        holder.removeButton.setOnClickListener(v ->
            onStockDeleteListener.onStockDeleted(stock,position)
        );
    }

    @Override
    public int getItemCount() {
        return watchlist.size();
    }

    static class WatchlistViewHolder extends RecyclerView.ViewHolder {
        TextView stockSymbol;
        TextView stockPrice;
        ImageButton removeButton;

        public WatchlistViewHolder(@NonNull View itemView) {
            super(itemView);
            stockSymbol = itemView.findViewById(R.id.stock_symbol);
            stockPrice = itemView.findViewById(R.id.stock_price);
            removeButton = itemView.findViewById(R.id.deleteStockBtn);
        }
    }
}
