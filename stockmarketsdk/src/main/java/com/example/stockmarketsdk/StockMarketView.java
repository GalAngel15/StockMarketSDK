package com.example.stockmarketsdk;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.stockmarketsdk.dto.GlobalQuoteResponse;
import com.example.stockmarketsdk.dto.IntradayDataPoint;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StockMarketView extends View {

    private Paint paint;
    Context context;

    private String stockSymbol = "TSLA";
    private double stockPrice = 0.0;
    float width = 200 , height = 240;

    private float SIZE = 48f, MIN = 0f, MAX = 40f;
    StockApiClient client = new StockApiClient("http://10.0.2.2:8083/");

    public StockMarketView(Context context) {
        super(context);
        init(context);
    }

    public StockMarketView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs,R.styleable.StockMarketView);
        SIZE = typedArray.getInt(R.styleable.StockMarketView_size, 48);
        init(context);
    }

    public StockMarketView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs,R.styleable.StockMarketView);
        SIZE = typedArray.getInt(R.styleable.StockMarketView_size, 48);
        init(context);
    }

    public void init(Context context) {
        this.paint = new Paint();
        this.paint.setColor(Color.GREEN);

        this.context = context;

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

    public void fetchStockData(String symbol) {
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
                Log.e("API_ERROR", "Error: " + t.getMessage());
                t.printStackTrace();
            }
        });
    }

    public void fetchIntradayData(String symbol, String interval) {
        client.getStockAPI().getIntradayData(symbol, interval)
                .enqueue(new Callback<List<IntradayDataPoint>>() {
                    @Override
                    public void onResponse(Call<List<IntradayDataPoint>> call, Response<List<IntradayDataPoint>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            List<IntradayDataPoint> intradayData = response.body();
                            for (IntradayDataPoint dataPoint : intradayData) {
                                Log.d("StockMarketView", "Timestamp: " + dataPoint.getTimestamp() +
                                        ", Open: " + dataPoint.getOpen() +
                                        ", Close: " + dataPoint.getClose());
                            }
                            invalidate();  // צייר מחדש את ה-View עם הנתונים החדשים
                        }
                    }

                    @Override
                    public void onFailure(Call<List<IntradayDataPoint>> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
    }

}
