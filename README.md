# Hello World API

A simple Java Spring Boot HTTP API with a single endpoint that greets users based on the first letter of their name.

## Prerequisites

- **Java 17** or higher
- **Maven 3.6+** (or use the Maven wrapper if included)

## How to Run the Application

```bash
mvn spring-boot:run
```

The application starts on **http://localhost:8080**.

### Example Requests

```bash
# Valid name (starts with A-M) → 200 OK
curl "http://localhost:8080/hello-world?name=alice"
# Response: {"message":"Hello Alice"}

# Invalid name (starts with N-Z) → 400 Bad Request
curl "http://localhost:8080/hello-world?name=nancy"
# Response: {"error":"Invalid Input"}

# Missing name → 400 Bad Request
curl "http://localhost:8080/hello-world"
# Response: {"error":"Invalid Input"}
```

## How to Run the Tests

```bash
mvn test
```

This runs all unit and integration tests using JUnit 5 and MockMvc.

## Project Structure

```
src/
├── main/java/com/example/helloworld/
│   ├── HelloWorldApplication.java          # Spring Boot entry point
│   ├── controller/
│   │   └── HelloWorldController.java       # REST controller
│   ├── service/
│   │   └── HelloWorldService.java          # Business logic
│   └── exception/
│       └── GlobalExceptionHandler.java     # Centralized error handling
├── main/resources/
│   └── application.properties              # Application configuration
└── test/java/com/example/helloworld/
    ├── controller/
    │   └── HelloWorldControllerTest.java   # MockMvc integration tests
    └── service/
        └── HelloWorldServiceTest.java      # Unit tests for service logic
```

## Endpoint

### `GET /hello-world?name={name}`

| Scenario | Status | Response Body |
|---|---|---|
| Name starts with A–M (case-insensitive) | `200 OK` | `{"message": "Hello <Name>"}` |
| Name starts with N–Z (case-insensitive) | `400 Bad Request` | `{"error": "Invalid Input"}` |
| Name is missing or empty | `400 Bad Request` | `{"error": "Invalid Input"}` |
| Name starts with non-alphabetic character | `400 Bad Request` | `{"error": "Invalid Input"}` |

## Assumptions

1. **Name capitalization**: The name in the response is formatted with the first letter uppercased and the rest lowercased (e.g., `alice` → `Alice`, `BOB` → `Bob`).
2. **First letter only**: Only the first character of the `name` parameter determines the A–M vs N–Z classification.
3. **Non-alphabetic first characters**: Names starting with digits, special characters, or other non-alphabetic characters are treated as invalid input (400 Bad Request).
4. **Whitespace-only names**: Names consisting only of whitespace are treated the same as empty/missing names.

