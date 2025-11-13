# One-Click Generator — Usage

## Prerequisites
- Python 3.11+
- Java 17
- Maven 3.9+

## Generate Project (from client Dependencies)
```
python -m agent.scripts.one_click --input CHUBB/Dependencies --output generated/result --clean
```
- `--clean`: removes any existing `generated/result` before regenerating

## Optional Build & Run (local validation)
```
python -m agent.scripts.one_click --input CHUBB/Dependencies --output generated/result --clean --build-run
```
- Builds with Maven and runs the service, validating actuator health

## What Gets Generated
- `pom.xml`
- `src/main/resources/routes/loc-service.yaml` (resolved placeholders)
- `src/main/java/com/example/controller/*Controller.java` (per operation)
- `src/main/resources/openapi.yaml`
- `src/main/resources/contracts/selected/**` (WSDL/XSD closure for codegen)

## CI Gates
- Codegen gate: `mvn -q -Pcodegen -DskipTests generate-sources` (strict)
- Runtime validation: builds, runs, validates health and POSTs XML to `/loc/{operation}` endpoints

