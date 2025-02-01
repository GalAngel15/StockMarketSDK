# 🏗️ Architecture & Design

## High-Level Overview
```mermaid
graph TD;
    User -->|Requests| API[Stock Market API]
    API -->|Fetches Data| Database[MongoDB Atlas]
    API -->|Returns Data| SDK[Stock Market SDK]
    SDK -->|Displays Data| AndroidApp[Example App]
```

## Components

- **API Service**: Handles data retrieval from financial sources.
- **Android SDK**: Provides a wrapper around the API.
- **Example App**: Demonstrates how to use the SDK.

