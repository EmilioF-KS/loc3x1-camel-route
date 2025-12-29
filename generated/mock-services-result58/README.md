# Mock Services

Generated from the selected folder's contracts and MAP files.

## MAP to XSLT Preprocessing
- Discovers MAP files via manifest or explicit maps dir.
- Converts to XSLT under `src/main/resources/xsl`.
- Validates XSLTs for parsability; see `XSLT_VALIDATION.txt`.

## Build
- `mvn clean package -DskipTests`

## Run
- `java -jar target/mock-services-result58-1.0.0.jar`

