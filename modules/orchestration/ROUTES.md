# Apache Camel Routes Documentation

**Generated:** 2025-10-23T16:54:16.255033
**Process:** LocationRetrievalLOC3X1BProcess
**Source:** orchestration/spec.yaml

## Overview

This module contains Apache Camel routes that implement the mediation logic derived from the original BPEL process. The routes are generated using Java DSL and follow enterprise integration patterns.

## Architecture

- **Inbound Services:** CXF endpoints exposing SOAP operations
- **Orchestration:** Apache Camel routes implementing mediation flows
- **Outbound Clients:** CXF clients for partner service invocations
- **Error Handling:** Centralised fault handling producing SimpleFault responses

## Generated Routes

### GetLocationWithTaxingJurisdictions3X1BRoute

- **Operation:** `GetLocationWithTaxingJurisdictions3X1B`
- **Partner Link:** `LocationRetrievalLOC3X1B`
- **Route ID:** `getlocationwithtaxingjurisdictions3x1b-route`

#### Mediation Flow

1. **Inbound:** CXF endpoint receives SOAP request
2. **Validation:** Request structure validation
3. **Mediation Steps:**
   - Step 1: Invoke LocationRetrievalLOC3X1B.GetLocationWithTaxingJurisdictions
4. **Response Mapping:** Final response transformation
5. **Error Handling:** SimpleFault generation on exceptions

#### Endpoints

- **Inbound:** `cxf:bean:locationretrievalloc3x1bEndpoint`
- **Outbound:** CXF client beans with `operationName` set via `CxfConstants.OPERATION_NAME`

## Configuration

Routes support externalised configuration through Spring profiles:

- **Endpoints:** Service URLs configurable via `application.yml`
- **Timeouts:** Connection and read timeouts per environment
- **Security:** TLS configuration and authentication settings

## Error Handling

All routes implement centralised error handling:

1. **Exception Catching:** Global exception handlers per route
2. **Fault Mapping:** Exceptions mapped to SimpleFault schema
3. **Logging:** Comprehensive logging for troubleshooting
4. **Monitoring:** Integration with Spring Boot Actuator

## Testing

Route testing follows Apache Camel testing patterns:

- **Unit Tests:** Route logic testing with mock endpoints
- **Integration Tests:** End-to-end testing with real services
- **Contract Tests:** Schema validation and compliance

## Deployment

Routes are packaged as Spring Boot applications:

```bash
mvn clean package
java -jar target/orchestration-1.0.0-SNAPSHOT.jar
```

## Monitoring

Routes expose metrics via Spring Boot Actuator:

- `/actuator/health` - Health checks
- `/actuator/metrics` - Route metrics
- `/actuator/camel/routes` - Route status
