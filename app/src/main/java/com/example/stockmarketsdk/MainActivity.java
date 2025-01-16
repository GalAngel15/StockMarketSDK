package com.example.stockmarketsdk;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.stockmarketsdk.StockMarketView;

public class MainActivity extends AppCompatActivity {

    private StockMarketView stockMarketView;
    private EditText stockSymbolInput;
    private Spinner intervalSpinner;
    private Button searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();
        initButtons();
    }

    private void findViews() {
         stockMarketView = findViewById(R.id.stockMarketView);
         stockSymbolInput = findViewById(R.id.stockSymbolInput);
         intervalSpinner = findViewById(R.id.intervalSpinner);
         searchButton = findViewById(R.id.searchButton);
        }

    private void initButtons() {
        searchButton.setOnClickListener(v -> {
            String stockSymbol = stockSymbolInput.getText().toString();
            String interval = intervalSpinner.getSelectedItem().toString();
            stockMarketView.fetchIntradayData(stockSymbol, interval);
        });
    }
}
