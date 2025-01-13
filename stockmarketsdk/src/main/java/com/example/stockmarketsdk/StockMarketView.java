package com.example.stockmarketsdk;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StockMarketView extends View {

    private Paint paint;
    private String stockSymbol = "AAPL";  // סימול ברירת מחדל
    private double stockPrice = 0.0;

    public StockMarketView(Context context) {
        super(context);
        init();
    }

    public StockMarketView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public StockMarketView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(Color.GREEN);
        paint.setTextSize(50);

        // ביצוע קריאה ל-API כדי לקבל נתוני מניה
        fetchStockData(stockSymbol);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // ציור מחיר המניה על המסך
        canvas.drawText("Stock: " + stockSymbol, 50, 100, paint);
        canvas.drawText("Price: $" + stockPrice, 50, 200, paint);
    }

    public void setStockSymbol(String symbol) {
        this.stockSymbol = symbol;
        fetchStockData(symbol);
    }

    private void fetchStockData(String symbol) {
        StockApiClient client = new StockApiClient("http://10.0.2.2:8083");
        client.getStockAPI().getStockQuote(symbol).enqueue(new Callback<GlobalQuoteResponse.GlobalQuote>() {
            @Override
            public void onResponse(Call<GlobalQuoteResponse.GlobalQuote> call, Response<GlobalQuoteResponse.GlobalQuote> response) {
                if (response.isSuccessful() && response.body() != null) {
                    stockPrice = response.body().getPrice();
                    Log.e("StockMarketView", "Stock price: " + stockPrice);
                    invalidate();  // צייר מחדש את ה-View עם הנתונים החדשים
                }
            }

            @Override
            public void onFailure(Call<GlobalQuoteResponse.GlobalQuote> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
