# 📦 Stock Market SDK

## Installation

1. Add JitPack to `settings.gradle`:
   ```kotlin
   dependencyResolutionManagement {
       repositories {
           maven { url 'https://jitpack.io' }
       }
   }
   ```

2. Add the SDK dependency:
   ```kotlin
   dependencies {
       implementation("com.github.GalAngel15:StockMarketSDK:1.1.0")
   }
   ```

## Features
- Fetch **real-time** stock quotes: `StockSDK.getStockQuote()`
- Retrieve **historical** data: `StockSDK.getTimeSeries()`
- **Manage watchlists**: Add and remove stocks.

