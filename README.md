# Stock Market SDK

[![](https://jitpack.io/v/GalAngel15/StockMarketSDK.svg)](https://jitpack.io/#GalAngel15/StockMarketSDK)

The **Stock Market SDK** is a powerful and easy-to-use Android library that simplifies the process of retrieving stock market data and managing watchlists. Built with **Retrofit** and **Gson**, it offers a straightforward, callback-based API for fetching real-time quotes, intraday/historical data, and handling custom watchlists.

---

## **Table of Contents**
1. [What's New in 1.1.0](#whats-new-in-110)
2. [Key Features](#key-features)
3. [Architecture Overview](#architecture-overview)
4. [Installation](#installation)
5. [Permissions](#permissions)
6. [Initialization](#initialization)
7. [Usage Examples](#usage-examples)
    - [Fetch Real-Time Stock Quote](#fetch-real-time-stock-quote)
    - [Get Time-Series Data](#get-time-series-data)
    - [Watchlist Management](#watchlist-management)
8. [Error Handling](#error-handling)
9. [Use Cases](#use-cases)
10. [Known Limitations](#known-limitations)
11. [Contributing](#contributing)
12. [License](#license)

---

## **What's New in 1.1.0**
- **Improved Time-Series**: Enhanced support for daily, weekly, and monthly intervals.
- **Expanded Watchlist Features**: Additional methods for removing and clearing watchlists.
- **Better Error Handling**: More detailed error messages in callbacks.
- **Gradle/Dependency Updates**: Updated dependencies for improved stability.

---

## **Key Features**
1. **Real-Time Stock Quotes**: Easily fetch the latest stock prices for tickers like `AAPL`, `GOOGL`, `AMZN`, and more.
2. **Historical & Intraday Data**: Retrieve time-series data (daily, intraday, or custom intervals).
3. **Watchlist Management**: Maintain a personalized watchlist with methods to add, remove, or list all tracked stocks.
4. **Callback-Based Design**: Asynchronous calls return results via simple callbacks for `onSuccess` and `onFailure`.
5. **Retrofit + Gson**: Reliable networking and JSON parsing out of the box.

---

## **Architecture Overview**
At the core of this SDK are two main controllers:
- **`StockController`**: Handles API calls related to stock quotes and time-series data.
- **`WatchlistController`**: Manages the watchlist by adding, removing, and fetching tracked stocks.

All public methods are exposed via the **`StockSDK`** facade:
- `getStockQuote(...)`
- `getTimeSeries(...)`
- `addStockToWatchlist(...)`
- `removeStockFromWatchlist(...)`
- `getWatchlist(...)`

Under the hood, the SDK uses Retrofit to perform HTTP requests and Gson to parse responses into data models like **`GlobalQuoteResponse.GlobalQuote`**, **`IntradayDataPoint`**, and **`WatchlistDTO`**.

---

## **Installation**

1. **Add JitPack to Repositories**

   In your **`settings.gradle`**:
   
```kotlin
    dependencyResolutionManagement {
        ...
        repositories {
            ...
            maven { url 'https://jitpack.io' }
        }
    }
```

2. **Add the Dependency**

   In your **`module-level build.gradle`**:
```kotlin
dependencies {
    implementation("com.github.GalAngel15:StockMarketSDK:1.1.0")
}
```

---

## **Permissions**
Since the SDK makes network requests, ensure your `AndroidManifest.xml` includes:
```xml
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
```

---

## **Initialization**
Typically, the `StockSDK` is ready to use without explicit initialization. However, if you have additional setup (like providing an API key or customizing the base URL), you might do so in an `Application` class:

```java
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // Example initialization if needed
        // StockSDK.initialize("YOUR_API_KEY");
    }
}
```

> **Note**: If your SDK version requires an API key, see the documentation for the exact method signature.

---

## **Usage Examples**

For more in-depth instructions on setting up and using the Stock Market SDK, visit the full documentation [here](https://galangel15.github.io/StockMarketSDK/).


Below are common operations you might perform with the Stock Market SDK. Each function uses a **`Callback_Stock<T>`**, which has two methods:
- `onSuccess(T result)` – Returns the requested data.
- `onFailure(String errorMessage)` – Returns an error description.

### **Fetch Real-Time Stock Quote**
Retrieve the latest price and related info for a given stock symbol.

```java
StockSDK.getStockQuote("AAPL", new Callback_Stock<GlobalQuoteResponse.GlobalQuote>() {
    @Override
    public void onSuccess(GlobalQuoteResponse.GlobalQuote result) {
        // Example usage
        System.out.println("Ticker: " + result.getSymbol());
        System.out.println("Price: " + result.getPrice());
        System.out.println("Volume: " + result.getVolume());
    }

    @Override
    public void onFailure(String errorMessage) {
        System.err.println("Error fetching quote: " + errorMessage);
    }
});
```

### **Get Time-Series Data**
Fetch historical or intraday data. The SDK supports an enum `StockSDK.TimeSeries` which may include `INTRADAY`, `DAILY`, `WEEKLY`, `MONTHLY` (depending on your SDK setup).

```java
// Example: Daily time-series for GOOGL
StockSDK.getTimeSeries(StockSDK.TimeSeries.DAILY, "GOOGL", new Callback_Stock<List<IntradayDataPoint>>() {
    @Override
    public void onSuccess(List<IntradayDataPoint> result) {
        for (IntradayDataPoint point : result) {
            System.out.println("Date: " + point.getDate() + ", Close: " + point.getClose());
        }
    }

    @Override
    public void onFailure(String errorMessage) {
        System.err.println("Time-Series Error: " + errorMessage);
    }
});
```

### **Watchlist Management**

#### **Add a Stock to Watchlist**
```java
StockSDK.addStockToWatchlist("TSLA", new Callback_Stock<WatchlistDTO>() {
    @Override
    public void onSuccess(WatchlistDTO result) {
        System.out.println("Added to watchlist: " + result.getStockSymbol());
    }

    @Override
    public void onFailure(String errorMessage) {
        System.err.println("Failed to add stock: " + errorMessage);
    }
});
```

#### **Remove a Stock from Watchlist**
```java
StockSDK.removeStockFromWatchlist("TSLA", new Callback_Stock<Void>() {
    @Override
    public void onSuccess(Void ignored) {
        System.out.println("TSLA removed from watchlist.");
    }

    @Override
    public void onFailure(String errorMessage) {
        System.err.println("Failed to remove stock: " + errorMessage);
    }
});
```

#### **Retrieve Entire Watchlist**
```java
StockSDK.getWatchlist(new Callback_Stock<List<WatchlistDTO>>() {
    @Override
    public void onSuccess(List<WatchlistDTO> watchlist) {
        for (WatchlistDTO stock : watchlist) {
            System.out.println("Symbol: " + stock.getStockSymbol());
        }
    }

    @Override
    public void onFailure(String errorMessage) {
        System.err.println("Error retrieving watchlist: " + errorMessage);
    }
});
```

---

## **Error Handling**

All asynchronous calls use the `Callback_Stock<T>` interface. On failure, you'll receive an `errorMessage` string that describes the issue. Common errors include:
1. **Network Unavailable** – The user device is offline.
2. **Invalid Ticker Symbol** – The requested stock symbol doesn't exist.
3. **API Quota Exceeded** – If the backend API has usage limits.
4. **Server Errors (5xx)** – Temporary server or network problem.

**Tip**: Use logs or user-friendly toasts/snackbars to handle errors gracefully.

---

## **Use Cases**
- **Finance & Investment Apps** – Provide real-time quotes, charts, and analytics.
- **Portfolio Trackers** – Let users manage their watchlists and track gains/losses.
- **Educational Tools** – Demonstrate real-world stock behavior and historical trends.

---

## **Known Limitations**
- **API Rate Limits**: Depending on the underlying data provider, you may encounter request limits.
- **Data Delay**: Real-time quotes may still have slight delays (e.g., 15 minutes) based on the data source.
- **No Built-in Caching**: Currently, the SDK doesn't store data locally. If you need caching, consider implementing your own solution.

---

## **Contributing**

Contributions are welcome! If you'd like to add features or fix bugs:
1. Fork the repo.
2. Create a new branch for your feature.
3. Commit and push changes.
4. Submit a Pull Request describing your changes.

---

## **License**
Distributed under the [MIT License](https://opensource.org/licenses/MIT). See `LICENSE` for more information.
---

### **Questions or Feedback?**
Feel free to open an issue on GitHub if you have any questions, suggestions, or run into problems.

---

Happy coding with **Stock Market SDK**! 🚀

