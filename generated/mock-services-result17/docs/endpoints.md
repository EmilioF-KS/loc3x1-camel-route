# Standalone Mock Service API

## Base URL
`http://localhost:8090`

## Endpoints
- `POST /api/crp10x1/v1/get-country` (application/xml)
- `POST /api/crp11x1/v1/get-state-or-province` (application/xml)
- `POST /api/loc3x1b/v1/get-location-list` (application/xml)

## Request/Response
- Requests are validated against XSDs under `contracts/selected/*`
- Responses are mock XMLs under `mocks/*`, validated against their XSDs

## Authentication
- None (can be enabled later via headers)

## Error Codes
- `400` schema validation failure
- `500` server error

## Documentation
- Swagger UI: `http://localhost:8090/swagger-ui/index.html`
- OpenAPI JSON: `http://localhost:8090/v3/api-docs`
