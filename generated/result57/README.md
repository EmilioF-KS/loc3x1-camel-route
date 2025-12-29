# LOC Service — Generated Results

This is a generated Spring Boot + Camel (YAML DSL) project built from client inputs.

- Java: 17
- Spring Boot: 3.3.x
- Camel: 4.7.x
- DSL: Camel YAML
- Includes: Actuator, Jackson, Camel Spring Boot starter, Camel YAML DSL, provider stub

## Prerequisites
- Java 17 and Maven 3.9+
- Port `8087` available

## How to Build
1. Change directory to this results folder
   - `cd /path/to/this/results`
2. Build the jar
   - `mvn clean package -DskipTests`

## How to Run
- Jar
  - `java -jar target/loc-service.jar --server.port=8087`
- Maven
  - `mvn spring-boot:run -Dspring-boot.run.arguments=--server.port=8087`

Notes:
- Routes call the built-in provider stub at `http://localhost:8087/provider/locations`.
- Running on `8087` ensures internal calls succeed with no extra setup.

## How to Validate
- Health: `curl -sf http://localhost:8087/actuator/health`
- Endpoints (POST XML):
  - `curl -s -X POST -H "Content-Type: application/xml" --data '<req/>' http://localhost:8087/loc/GetCountry`
  - `curl -s -X POST -H "Content-Type: application/xml" --data '<req/>' http://localhost:8087/loc/GetStateOrProvince`
  - `curl -s -X POST -H "Content-Type: application/xml" --data '<req/>' http://localhost:8087/loc/GetLocationList3X1B`
  - `curl -s -X POST -H "Content-Type: application/xml" --data '<req/>' http://localhost:8087/loc/GetLocationList3X1M`
  - `curl -s -X POST -H "Content-Type: application/xml" --data '<req/>' http://localhost:8087/loc/GetLocationWithTaxingJurisdictions3X1B`
  - `curl -s -X POST -H "Content-Type: application/xml" --data '<req/>' http://localhost:8087/loc/GetLocationWithTaxingJurisdictions3X1M`
- Sample payload (optional):
  - `curl -s -X POST -H "Content-Type: application/xml" --data-binary @samples/get_location_list_request_min.xml http://localhost:8087/loc/GetLocationList3X1M`

## Stop Service
- Jar or Maven run: press `Ctrl+C` in the terminal

## Optional Codegen (DTOs & Clients)
- `mvn -Pcodegen generate-sources`
- Outputs under `target/generated-sources/{jaxb,cxf}`

## Observability & Error Handling
- `X-Correlation-ID` header is set from Camel `exchangeId` and logged.
- Error handler maps failures to HTTP `500` with a simple XML body and redelivery policy from `application.yaml`.

## OpenAPI
- A stub OpenAPI spec is included at `src/main/resources/openapi.yaml` with `/loc/{operation}` POST endpoints.
