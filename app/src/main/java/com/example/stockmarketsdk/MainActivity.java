package com.example.stockmarketsdk;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.stockmarketsdk.StockMarketView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // שימוש ב-SDK
        StockMarketView stockMarketView = findViewById(R.id.stockMarketView);
        stockMarketView.setStockSymbol("NVDA");
    }
}
