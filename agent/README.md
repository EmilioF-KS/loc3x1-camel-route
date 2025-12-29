# Agent

This directory will contain the Python agent code (LangGraph pipeline, deterministic converters, and orchestration utilities).

Key subfolders (to be added in subsequent tickets):
- `src/` — agent modules and nodes
- `tests/` — pytest test suite
- `schemas/` — JSON schemas and XSDs used by the agent

Generated artifacts (current):
- `agent/manifest.json` — discovered artifacts and classifications
- `agent/contracts.json` — WSDL operations, messages, and XSD types
- `agent/dto_plan.json` — DTO generation plan from XSDs
- `agent/graph.json` — import graph linking WSDL/XSD/BPEL/Mediation
- `agent/orchestration_plan.json` — normalized plan extracted from BPEL/Mediation
- `agent/orchestration_plan.yaml` — YAML mirror of the orchestration plan (aligned with Emilio’s step 7: Camel route in YAML)

Usage:
- Orchestration plan generation:
  `python -m agent.src.orchestration --manifest agent/manifest.json --output agent/orchestration_plan.json --yaml-output agent/orchestration_plan.yaml`

- Síntesis de mapeos desde contratos → XSLT (AGENT-022):
  - Sin depender de `.map` de IBM. Se infieren movimientos directamente desde XSDs en `CHUBB/Dependencies`.
  - Ejemplo (Request LOC3X1B):
    `python -m agent.src.map_synthesis --src-xsd CHUBB/Dependencies/LocationRetrievalLOC3X1B/GetLocationListRequest.xsd --src-root GetLocationListRequest --dst-xsd CHUBB/Dependencies/LocationRetrievalLOC3X1B/GetLocationListRequest.xsd --dst-root GetLocationListRequest --src-prefix GetLocationList/GetLocationListRequest --dst-prefix GetLocationList3X1B/GetLocationListRequest --emit-xslt --output-dir agent/output/xsl`
  - Nota: El sintetizador empareja campos con nombres idénticos y el conversor XSLT agrupa contenedores correctamente; `CHUBB/00-Emilio-Logic` queda deprecado para validación.
- Síntesis masiva (Request y Reply LOC3X1B/LOC3X1M):
    - LOC3X1B: `python -m agent.src.bulk_synthesis --profile LOC3X1B --output-dir agent/output/xsl`
    - LOC3X1M: `python -m agent.src.bulk_synthesis --profile LOC3X1M --output-dir agent/output/xsl`
    - Opcional: habilitar heurísticas (AGENT-024) para emparejar nombres similares:
      `python -m agent.src.bulk_synthesis --profile LOC3X1B --enable-heuristics --output-dir agent/output/xsl`
    - Genera: `GetLocationListRequest_from_GetLocationListRequest.xsl`, `LocationListReply_from_LocationListReply.xsl` (y variantes LOC3X1M)
    - Artefacto AGENT-024: cuando se habilitan heurísticas, se emite `agent/output/synthesized_maps.json` con:
      - Cobertura (campos destino totales vs. mapeados y ratio)
      - Lista de campos destino no mapeados
      - Trazas explicables por campo (tipo: idéntico/heurístico, y confianza)

- Route Skeleton Synthesis (AGENT-023):
  - Generate generic Camel YAML route skeletons without hardcoded paths.
  - Usage: `python -m agent.src.route_synthesis --plan agent/orchestration_plan.json --output-dir agent/output/routes --service-name loc-service`.
  - Emit one file per operation: add `--split-per-operation`.
  - The generated YAML uses placeholders like `{{controller_path}}`, `{{request_xslt}}`, `{{provider_uri}}`, `{{reply_xslt}}` to remain client-agnostic.
  - Optionally fill placeholders via CLI for integration:
    `python -m agent.src.route_synthesis --plan agent/orchestration_plan.json --output-dir agent/output/routes --service-name loc-service --controller-path loc/getLocationList --request-xslt classpath:GetLocationListRequest.xsl --reply-xslt classpath:LocationListReply.xsl --provider-uri http://provider/locations`
  - Validate YAML for placeholders:
    - Require placeholders present: `--validate-file agent/output/routes/loc-service.yaml --validate-mode unresolved`
    - Require all placeholders resolved: `--validate-file agent/output/routes/loc-service.yaml --validate-mode resolved`

- Scaffold Spring Boot + Camel project (neutral):
  - `python agent/scripts/scaffold_project.py --out generated/result`

- Synthesize Camel routes via scaffold (GEN-101 wiring):
  - `python agent/scripts/scaffold_project.py --orchestration agent/orchestration_plan.json --service-name loc-service --out generated/result`
  - Fill placeholders to make routes runnable:
    `python agent/scripts/scaffold_project.py --orchestration agent/orchestration_plan.json --service-name loc-service --controller-path loc/getLocationList --request-xslt classpath:GetLocationListRequest.xsl --reply-xslt classpath:LocationListReply.xsl --provider-uri http://provider/locations --out generated/result`

- Generate XSLT from IBM map XMLs via scaffold:
  - `python agent/scripts/scaffold_project.py --maps-dir CHUBB/Dependencies/LocationRetrievalLOC3X1B --out generated/result`

- Customize POM coordinates:
  - `python agent/scripts/scaffold_project.py --group-id com.client --artifact-id client-service --version 1.0.0 --project-name ClientService --description "Client Spring Boot + Camel" --out generated/result`

Outputs are written to `generated/result`. Spring Boot Maven plugin is configured to produce a runnable JAR.

OpenAPI UI:
- Included via `springdoc-openapi-starter-webmvc-ui`. Access at `http://localhost:8080/swagger-ui/index.html` when the app is running.

Provider wiring (GEN-102 baseline):
- Default provider properties in `application.yaml` under `provider.*` (URI, timeoutMs, retry settings).
- A fallback provider stub is included by default; disable with `--no-provider-stub`.

- Validación de transformaciones XSLT contra XSD:
-  - Aplicar y validar: `python -m agent.src.validate_transform --xslt agent/output/xsl/GetLocationListRequest_from_GetLocationListRequest.xsl --input examples/get_location_list_request_min.xml --mode request --base-xsd-dir CHUBB/Dependencies/LocationRetrievalLOC3X1B`
  - El validador envuelve el resultado con un elemento raíz del espacio de nombres `location_retrieval_loc3x1b` y valida contra los XSD importados (`GetLocationListRequest.xsd` / `LocationListReply.xsd`).
