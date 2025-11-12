
# Location Orchestration (REST + Apache Camel)

- Uses **Camel Spring Boot BOM** + **Spring Boot BOM** + **Camel core BOM** so you can omit versions for Camel starters and components.
- Exposes REST via Camel platform-http under `/api/*` and MVC controller under `/mvc/*`.
- Local provider endpoints for CRP10X1 / CRP11X1 / LOC3X1M with pluggable resolvers.
- XSLT helper route for XML mapping.
- OpenAPI UI at `/swagger-ui.html`.
