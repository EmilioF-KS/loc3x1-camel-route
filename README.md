# LOC3X1 — One-Click Generation and Validation

## Overview
This repository produces a runnable Spring Boot + Camel project from client inputs (e.g., a `Dependencies` folder) and validates endpoints exhaustively.

## Prerequisites
- Python 3.11+
- Java 17
- Maven 3.9+

## One-Click Generation
```
python -m agent.scripts.one_click --input CHUBB/Dependencies --output generated/result --clean
```
- `--clean`: removes any existing output before regenerating
- Produces `generated/result` with routes, controllers, OpenAPI, and contract closure for codegen

## Build & Run Locally
```
cd generated/result
mvn clean package -DskipTests
java -jar target/loc-service.jar --server.port=8081
```
- Alternatively: `mvn spring-boot:run -Dspring-boot.run.arguments=--server.port=8081`
- Health: `curl -sf http://localhost:8081/actuator/health`
- Validate endpoints (POST XML):
```
curl -s -X POST -H "Content-Type: application/xml" --data '<req/>' http://localhost:8081/loc/GetCountry
curl -s -X POST -H "Content-Type: application/xml" --data '<req/>' http://localhost:8081/loc/GetStateOrProvince
curl -s -X POST -H "Content-Type: application/xml" --data '<req/>' http://localhost:8081/loc/GetLocationList3X1B
curl -s -X POST -H "Content-Type: application/xml" --data '<req/>' http://localhost:8081/loc/GetLocationList3X1M
curl -s -X POST -H "Content-Type: application/xml" --data '<req/>' http://localhost:8081/loc/GetLocationWithTaxingJurisdictions3X1B
curl -s -X POST -H "Content-Type: application/xml" --data '<req/>' http://localhost:8081/loc/GetLocationWithTaxingJurisdictions3X1M
```
- Sample payload: `curl -s -X POST -H "Content-Type: application/xml" --data-binary @generated/result/samples/get_location_list_request_min.xml http://localhost:8081/loc/GetLocationList3X1M`
- Stop: press `Ctrl+C`

## Share Results
- To share with a teammate: copy `generated/result` to any path
- Teammate runs the Build & Run steps above

## Optional Codegen (DTOs & Clients)
- `mvn -Pcodegen generate-sources`
- Outputs under `generated/result/target/generated-sources/{jaxb,cxf}`

## CI Gates
- Strict codegen gate: generates scaffold and runs `mvn -Pcodegen generate-sources`
- Runtime validation: builds, runs, validates health and posts to endpoints

## Notes
- Provider stub endpoint is bundled: `http://localhost:8081/provider/locations`
- Camel loads routes from `classpath:routes/*.yaml`
