
# Location Orchestration (Spring Boot + Apache Camel)

This project migrates the orchestration logic from an IBM BPEL process (LocationRetrievalLOC3X1*) into Apache Camel routes running on Spring Boot.

## How to run

```bash
mvn spring-boot:run
```

## REST endpoint

* `POST /api/locations/list` – body is a JSON matching `LocationRequest`.

Configure the downstream endpoints in `application.yml` or by setting the exchange properties `crp11x1OverrideUri`, `crp10x1OverrideUri`, `loc3x1mOverrideUri` before routing. Replace them with real SOAP calls using Camel CXF if needed.

## What is implemented

* Validation of required input (one of postalCode, locationPlaceCode, stateOrProvinceCode, postalStateAbbreviation).
* Conditional enrichment through external services (CRP11X1, CRP10X1) to fill missing state/country info.
* Mapping to the module request (LOC3X1M) and mapping back to the API reply.
* Post-processing rule clearing `FireDistrictCode` == `"9999"` (when AddressLine1 is missing) and emitting warning `EPLOC30002`.
* Centralized error handling that returns a `SimpleFault` payload for validation or processing errors.

> **Note**: External client routes are intentionally left unimplemented to avoid generating synthetic data. Plug your endpoints or CXF clients.
