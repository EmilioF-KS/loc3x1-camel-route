# Component REST Services

## loc3x1b
- Endpoint: `POST /api/loc3x1b/v1/get-location-list`
- Input: XML body conforming to `contracts/selected/LocationRetrievalLOC3X1B/GetLocationListRequest.xsd`
- Output: XML conforming to `contracts/selected/LocationRetrievalLOC3X1B/LocationListReply.xsd`
- Method: `POST`
- Auth: Optional API key `X-API-Key` (enable via `security.requireApiKey=true`)

## crp10x1
- Endpoint: `POST /api/crp10x1/v1/get-country`
- Input: XML body conforming to `contracts/selected/CountryRetrievalCRP10X1/GetCountryRequest.xsd`
- Output: XML conforming to `contracts/selected/CountryRetrievalCRP10X1/GetCountryReply.xsd`
- Method: `POST`
- Auth: Optional API key `X-API-Key`

## crp11x1
- Endpoint: `POST /api/crp11x1/v1/get-state-or-province`
- Input: XML body conforming to `contracts/selected/StateOrProvinceRetrievalCRP11X1/GetStateOrProvinceRequest.xsd`
- Output: XML conforming to `contracts/selected/StateOrProvinceRetrievalCRP11X1/GetStateOrProvinceListReply.xsd`
- Method: `POST`
- Auth: Optional API key `X-API-Key`

## Logging & Security
- Requests and responses are logged via Camel `log` steps with correlation IDs in console pattern.
- Enable API key requirement by setting `security.requireApiKey=true` in `application.yaml` and passing header `X-API-Key`.

## Scaling
- Each service is implemented as an independent Camel route. Deploy the application horizontally to scale all services. For independent scaling, split routes into dedicated Spring Boot apps or use profiles to isolate route loading.

