# 📡 API Documentation

## Base URL
```
https://pale-caitlin-dev-gal-angel-50977206.koyeb.app/
```

## Endpoints

### 📌 Get Real-Time Stock Quote
**Endpoint:** `GET /stock?symbol={symbol}`  
**Example:** `/stock?symbol=AAPL`

### 📌 Get Intraday Data
**Endpoint:** `GET /stock/intraday?symbol={symbol}&interval={interval}`  
**Example:** `/stock/intraday?symbol=GOOGL&interval=5min`

### 📌 Get Historical Time-Series Data
**Endpoint:** `GET /stock/time-series?function={type}&symbol={symbol}`  
**Example:** `/stock/time-series?function=DAILY&symbol=TSLA`

### 📌 Watchlist Management

| Method | Endpoint | Description |
|--------|---------|-------------|
| `POST` | `/watchlists?name={name}` | Create a new watchlist |
| `GET` | `/watchlists` | Retrieve all watchlists |
| `GET` | `/watchlists/{name}` | Get a specific watchlist |
| `DELETE` | `/watchlists/{name}` | Delete a watchlist |
| `PUT` | `/watchlists/{name}/add-stock?stockSymbol={symbol}` | Add a stock to watchlist |
| `PUT` | `/watchlists/{name}/remove-stock?stockSymbol={symbol}` | Remove a stock from watchlist |

