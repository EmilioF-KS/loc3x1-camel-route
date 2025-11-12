# a9-like Project Scaffold (GEN-100)

This is an a9-style Spring Boot + Camel (YAML DSL) scaffold.

- Java: 17
- Spring Boot: 3.3.x
- Camel: 4.7.x
- Routes DSL: YAML
- Includes: Actuator, Jackson, Camel Spring Boot starter, Camel YAML DSL

Build (optional, if Maven is available):

```
mvn -DskipTests package
```

Run (example):

```
java -jar target/a9-like-project-0.1.0.jar
```

Camel loads routes from `classpath:routes/*.yaml`.

## Routes & Error Handling (GEN-104)
- Each route sets `X-Correlation-ID` to the Camel `exchangeId`.
- Request logs include the correlation ID at INFO.
- Errors are handled with redelivery policy from provider properties, map to HTTP `500` via `CamelHttpResponseCode`, and return a simple XML error body.

## Correlation-ID Logging
- A servlet filter sets/propagates `X-Correlation-ID` and writes it to logging MDC.
- Console logging pattern includes `[correlationId]` for all application logs.
- Camel route logs also include the correlation ID in messages.

## Actuator Endpoints
- Health and Info are exposed at `/actuator/health` and `/actuator/info`.
- Customize exposure via `management.endpoints.web.exposure.include` in `application.yaml`.
