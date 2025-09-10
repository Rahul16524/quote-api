# Quote API

Spring Boot REST API providing random inspirational quotes with IP-based rate limiting.

## 🚀 Quick Start

### Prerequisites
- Java 17 or higher
- Maven 3.8.x or higher
- Git

### Clone the Repository
```bash
git clone https://github.com/Rahul16524/quote-api.git
cd quote-api
   ```
# Build the project:
```
mvn clean install 
```


# Run the project:
```
mvn spring-boot:run
```
The app will start at: http://localhost:8080

# API Endpoint
```
GET http://localhost:8080/api/quote
```

Response (Success):

json

```
{"quote": "The only way to do great work is to love what you do. - Steve Jobs"}
```

Response (Rate Limited):

```
json
{"error": "Rate limit exceeded. Try again in 60 seconds."}
```

Rate Limiting
5 requests per minute per IP

Configurable in application.properties

Returns HTTP 429 when exceeded

## Assumptions and Design Decisions

- **Rate Limiting**: Used Bucket4j for thread-safe, robust rate limiting instead of manual implementation.
- **In-Memory Storage**: Quotes stored in a static list for simplicity, as no persistence is required.
- **Logging**: SLF4J with Logback for request logging, including IP and status.
- **Swagger**: Added for API documentation, accessible via `/swagger-ui.html`.
- **Concurrency**: `ConcurrentHashMap` and Bucket4j ensure thread safety.
- **Custom Rate Limits**: Configurable via `application.properties` for flexibility.
- **Unit Tests**: Focused on rate-limiting logic for core functionality.
# Tech Stack
Spring Boot 3.5.5
Bucket4j for rate limiting
Swagger UI at /swagger-ui.html

# Testing
# Test rate limiting
```
for i in {1..6}; do curl http://localhost:8080/api/quote; echo ""; done
```

