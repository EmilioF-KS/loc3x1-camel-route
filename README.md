# LOC3X1 — Universal Generation, Delivery, and Isolated Run

## Purpose
- Generate a runnable Spring Boot + Apache Camel project from an IBM input folder.
- Deliver a `generated/result` folder that can be copied to any computer and run in isolation without edits.

## Prerequisites
- Python `3.11+` for generation.
- Java `17+` for running the delivered result.
- Maven `3.8+` for build and run.

## Universal Generation (from IBM Folder to Result)
- Create and use a virtual environment:
  - `python3 -m venv .venv`
  - `source .venv/bin/activate`
  - `pip install -r requirements.txt`
- Run one‑click generation with your IBM folder path:
  - `python3 -m agent.scripts.one_click --input "<IBM_FOLDER_PATH>" --output "generated/result" --clean`
- Outputs:
  - `generated/result/CLIENT_INPUT_PATH.txt` records the original IBM path.
  - `generated/result/pom.xml`, `src/main/resources/routes/*.yaml`, `src/main/resources/contracts/selected/*`, `src/main/java/**`, `src/main/resources/xslt/identity.xsl`.
- Do not hand‑edit any file inside `generated/result`; regenerate if changes are needed.

## Delivery
- Package and share the folder as‑is:
  - `cd generated && tar -czf result.tar.gz result`
  - Send `result.tar.gz` or copy `generated/result` directly.
- The recipient can place the folder anywhere; it runs independently.

## Isolated Build and Run (on Any Computer)
- On the recipient machine:
  - Prerequisites: Java `17+`, Maven `3.8+`.
  - From the `result` folder root:
    - `mvn clean package -DskipTests`
    - Run on port `8081` so built‑in routes succeed:
      - `java -jar -Dserver.port=8081 target/loc-service.jar`
      - Alternatively: `mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Dserver.port=8081"`
  - Health:
    - `curl -sf http://localhost:8081/actuator/health`

## Endpoints
- Request endpoints (XML POST):
  - `http://localhost:8081/loc/GetCountry`
  - `http://localhost:8081/loc/GetStateOrProvince`
  - `http://localhost:8081/loc/GetLocationList3X1B`
  - `http://localhost:8081/loc/GetLocationList3X1M`
  - `http://localhost:8081/loc/GetLocationWithTaxingJurisdictions3X1B`
  - `http://localhost:8081/loc/GetLocationWithTaxingJurisdictions3X1M`
- Provider stub for isolated runs:
  - `http://localhost:8081/provider/locations`

## Smoke Tests
- Use provided sample:
  - `curl -s -X POST -H "Content-Type: application/xml" --data-binary @samples/get_location_list_request_min.xml http://localhost:8081/loc/GetLocationList3X1M`
- Direct stub check:
  - `curl -s -X POST -H "Content-Type: application/xml" --data-binary @samples/get_location_list_request_min.xml http://localhost:8081/provider/locations`

## Troubleshooting
- Service fails with component errors:
  - Ensure you ran on port `8081` so internal provider calls resolve.
  - Use Maven to run if `java -jar` fails: `mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Dserver.port=8081"`.
- 404 on `/loc/...`:
  - Confirm `src/main/resources/routes/*.yaml` exist and the app started without errors.
- Regeneration required:
  - If the result content is incomplete, rerun one‑click generation with `--clean`.

## Notes
- Routes are loaded from `classpath:routes/*.yaml`.
- The folder is self‑contained; no external services are required for smoke tests.
