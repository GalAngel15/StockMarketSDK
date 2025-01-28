# Stock Market SDK

[![](https://jitpack.io/v/GalAngel15/StockMarketSDK.svg)](https://jitpack.io/#GalAngel15/StockMarketSDK)

The **Stock Market SDK** is a comprehensive and user-friendly Android library designed to simplify interactions with stock market data. It provides an easy way to retrieve real-time stock quotes, historical data, and manage personalized watchlists.

---

### **Key Features**

1. **Real-Time Stock Data**:
   - Fetch real-time stock quotes.
   - Retrieve intraday and time-series data with multiple intervals (e.g., 1min, 5min, daily).

2. **Watchlist Management**:
   - Add stocks to a personalized watchlist.
   - Remove stocks or clear the watchlist entirely.
   - Retrieve all stocks in the watchlist.

3. **Customizable API**:
   - Provides a callback-based architecture for asynchronous API calls.

4. **Built with Retrofit**:
   - Uses Retrofit and Gson for network calls and JSON parsing, ensuring reliability and scalability.

---

### **How It Works**

1. **Initialization**:
   The library uses a controller-based approach, with `StockController` and `WatchlistController` handling API interactions.

   Example:
   ```java
   StockSDK.getStockQuote("AAPL", new Callback_Stock<GlobalQuoteResponse.GlobalQuote>() {
       @Override
       public void onSuccess(GlobalQuoteResponse.GlobalQuote result) {
           // Handle success
       }

       @Override
       public void onFailure(String errorMessage) {
           // Handle failure
       }
   });
   ```

2. **Watchlist Management**:
   ```java
   StockSDK.addStockToWatchlist("TSLA", new Callback_Stock<WatchlistDTO>() {
       @Override
       public void onSuccess(WatchlistDTO result) {
           // Stock added successfully
       }

       @Override
       public void onFailure(String errorMessage) {
           // Handle error
       }
   });
   ```

3. **Flexible Time Series Queries**:
   ```java
   StockSDK.getTimeSeries(StockSDK.TimeSeries.DAILY, "GOOGL", new Callback_Stock<List<IntradayDataPoint>>() {
       @Override
       public void onSuccess(List<IntradayDataPoint> result) {
           // Handle time-series data
       }

       @Override
       public void onFailure(String errorMessage) {
           // Handle error
       }
   });
   ```

---

### **Setup Instructions**

1. **Add the JitPack Repository to Your Project**:

   In your `settings.gradle`:
   ```kotlin
   dependencyResolutionManagement {
       repositories {
           maven { url 'https://jitpack.io' }
       }
   }
   ```

2. **Add the Dependency**:
   In your `build.gradle`:
   ```kotlin
   dependencies {
       implementation("com.github.GalAngel:StockMarketSDK:$current_version")
   }
   ```

---

### **Basic Usage Example**

1. **Add Permissions to Your AndroidManifest**:
   ```xml
   <uses-permission android:name="android.permission.INTERNET" />
   <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
   ```

2. **Retrieve a Stock Quote**:
   ```java
   StockSDK.getStockQuote("MSFT", new Callback_Stock<GlobalQuoteResponse.GlobalQuote>() {
       @Override
       public void onSuccess(GlobalQuoteResponse.GlobalQuote result) {
           System.out.println("Stock Price: " + result.getPrice());
       }

       @Override
       public void onFailure(String errorMessage) {
           System.err.println("Error: " + errorMessage);
       }
   });
   ```

3. **Manage a Watchlist**:
   ```java
   StockSDK.getWatchlist(new Callback_Stock<List<WatchlistDTO>>() {
       @Override
       public void onSuccess(List<WatchlistDTO> result) {
           for (WatchlistDTO stock : result) {
               System.out.println("Stock: " + stock.getStockSymbol());
           }
       }

       @Override
       public void onFailure(String errorMessage) {
           System.err.println("Error: " + errorMessage);
       }
   });
   ```

---

### **Applications**

- **Financial Dashboards**: Build interactive stock market dashboards.
- **Investment Tools**: Power apps for portfolio tracking and analysis.
- **Education**: Develop tools for teaching stock market concepts.

---

### **License**

This library is distributed under the [MIT License](https://opensource.org/licenses/MIT).
