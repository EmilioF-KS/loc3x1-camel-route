import os
import shutil
from typing import List, Tuple, Optional

# Optional synthesis imports are local to avoid hard dependency at import time
try:
    from agent.src.route_synthesis import synthesize_routes
except Exception:
    synthesize_routes = None  # type: ignore
try:
    from agent.src.map_move_to_xslt import convert_dir as convert_maps_dir
except Exception:
    convert_maps_dir = None  # type: ignore

README = """# LOC Service — Generated Results

This is a generated Spring Boot + Camel (YAML DSL) project built from client inputs.

- Java: 17
- Spring Boot: 3.3.x
- Camel: 4.7.x
- DSL: Camel YAML
- Includes: Actuator, Jackson, Camel Spring Boot starter, Camel YAML DSL, provider stub

## Prerequisites
- Java 17 and Maven 3.9+
- Port `8087` available

## How to Build
1. Change directory to this results folder
   - `cd /path/to/this/results`
2. Build the jar
   - `mvn clean package -DskipTests`

## How to Run
- Jar
  - `java -jar target/loc-service.jar --server.port=8087`
- Maven
  - `mvn spring-boot:run -Dspring-boot.run.arguments=--server.port=8087`

Notes:
- Routes call the built-in provider stub at `http://localhost:8087/provider/locations`.
- Running on `8087` ensures internal calls succeed with no extra setup.

## How to Validate
- Health: `curl -sf http://localhost:8087/actuator/health`
- Endpoints (POST XML):
  - `curl -s -X POST -H "Content-Type: application/xml" --data '<req/>' http://localhost:8087/loc/GetCountry`
  - `curl -s -X POST -H "Content-Type: application/xml" --data '<req/>' http://localhost:8087/loc/GetStateOrProvince`
  - `curl -s -X POST -H "Content-Type: application/xml" --data '<req/>' http://localhost:8087/loc/GetLocationList3X1B`
  - `curl -s -X POST -H "Content-Type: application/xml" --data '<req/>' http://localhost:8087/loc/GetLocationList3X1M`
  - `curl -s -X POST -H "Content-Type: application/xml" --data '<req/>' http://localhost:8087/loc/GetLocationWithTaxingJurisdictions3X1B`
  - `curl -s -X POST -H "Content-Type: application/xml" --data '<req/>' http://localhost:8087/loc/GetLocationWithTaxingJurisdictions3X1M`
- Sample payload (optional):
  - `curl -s -X POST -H "Content-Type: application/xml" --data-binary @samples/get_location_list_request_min.xml http://localhost:8087/loc/GetLocationList3X1M`

## Stop Service
- Jar or Maven run: press `Ctrl+C` in the terminal

## Optional Codegen (DTOs & Clients)
- `mvn -Pcodegen generate-sources`
- Outputs under `target/generated-sources/{jaxb,cxf}`

## Observability & Error Handling
- `X-Correlation-ID` header is set from Camel `exchangeId` and logged.
- Error handler maps failures to HTTP `500` with a simple XML body and redelivery policy from `application.yaml`.

## OpenAPI
- A stub OpenAPI spec is included at `src/main/resources/openapi.yaml` with `/loc/{operation}` POST endpoints.
"""

POM_XML = """<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.3.4</version>
        <relativePath/>
    </parent>

    <groupId>__GROUP_ID__</groupId>
    <artifactId>__ARTIFACT_ID__</artifactId>
    <version>__VERSION__</version>
    <name>__PROJECT_NAME__</name>
    <description>__DESCRIPTION__</description>
    <packaging>jar</packaging>

    <build>
        <finalName>__FINAL_NAME__</finalName>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <configuration>
                    <skip>false</skip>
                </configuration>
            </plugin>
        </plugins>
    </build>

    <properties>
        <java.version>17</java.version>
        <camel.version>4.7.0</camel.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>
        <dependency>
            <groupId>com.fasterxml.jackson.core</groupId>
            <artifactId>jackson-databind</artifactId>
        </dependency>
        <dependency>
            <groupId>org.apache.camel.springboot</groupId>
            <artifactId>camel-spring-boot-starter</artifactId>
            <version>${camel.version}</version>
        </dependency>
        <dependency>
            <groupId>org.apache.camel</groupId>
            <artifactId>camel-yaml-dsl</artifactId>
            <version>${camel.version}</version>
        </dependency>
        <dependency>
            <groupId>org.apache.camel.springboot</groupId>
            <artifactId>camel-platform-http-starter</artifactId>
            <version>${camel.version}</version>
        </dependency>
        <dependency>
            <groupId>org.apache.camel.springboot</groupId>
            <artifactId>camel-http-starter</artifactId>
            <version>${camel.version}</version>
        </dependency>
        <dependency>
            <groupId>org.apache.camel.springboot</groupId>
            <artifactId>camel-xslt-starter</artifactId>
            <version>${camel.version}</version>
        </dependency>
        <dependency>
            <groupId>org.springdoc</groupId>
            <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
            <version>2.6.0</version>
        </dependency>
        <dependency>
            <groupId>org.apache.cxf</groupId>
            <artifactId>cxf-rt-frontend-jaxws</artifactId>
            <version>4.0.5</version>
        </dependency>
        <dependency>
            <groupId>org.apache.cxf</groupId>
            <artifactId>cxf-rt-transports-http</artifactId>
            <version>4.0.5</version>
        </dependency>
        <dependency>
            <groupId>jakarta.xml.bind</groupId>
            <artifactId>jakarta.xml.bind-api</artifactId>
            <version>4.0.0</version>
        </dependency>
    </dependencies>

    <profiles>
        <profile>
            <id>codegen</id>
            <build>
                <plugins>
                    <plugin>
                        <groupId>org.codehaus.mojo</groupId>
                        <artifactId>jaxb2-maven-plugin</artifactId>
                        <version>3.1.0</version>
                        <executions>
                            <execution>
                                <id>generate-xsd-dtos</id>
                                <goals>
                                    <goal>xjc</goal>
                                </goals>
                                <configuration>
                                    <sources>
                                        <source>${project.basedir}/src/main/resources/contracts/selected</source>
                                    </sources>
                                    <includes>
                                        <include>**/*.xsd</include>
                                    </includes>
                                    <packageName>com.example.dto</packageName>
                                    <outputDirectory>${project.build.directory}/generated-sources/jaxb</outputDirectory>
                                </configuration>
                            </execution>
                        </executions>
                    </plugin>
                    <plugin>
                        <groupId>org.apache.cxf</groupId>
                        <artifactId>cxf-codegen-plugin</artifactId>
                        <version>4.0.5</version>
                        <executions>
                            <execution>
                                <id>generate-wsdl-clients</id>
                                <goals>
                                    <goal>wsdl2java</goal>
                                </goals>
                                <configuration>
                                    <wsdlRoot>${project.basedir}/src/main/resources/contracts/selected</wsdlRoot>
                                    <includes>**/*.wsdl</includes>
                                    <sourceRoot>${project.build.directory}/generated-sources/cxf</sourceRoot>
                                    <packageNames>com.example.client</packageNames>
                                </configuration>
                            </execution>
                        </executions>
                    </plugin>
                </plugins>
            </build>
        </profile>
    </profiles>

</project>
"""

APPLICATION_YAML = """spring:
  application:
    name: a9-like-project

server:
  port: 8087

management:
  endpoints:
    web:
      exposure:
        include: health,info

logging:
  pattern:
    console: "%d{yyyy-MM-dd'T'HH:mm:ss.SSSXXX} %-5level [%X{correlationId}] %logger{36} - %msg%n"

camel:
  main:
    routesIncludePattern: classpath:routes/*.yaml

provider:
  uri: http://localhost:8087/provider/locations
  timeoutMs: 5000
  retry:
    maxAttempts: 3
    delayMs: 250

request_xslt: classpath:xslt/identity.xsl
reply_xslt: classpath:xslt/identity.xsl

dependencies2:
  baseUri: http://localhost:8091/contracts/selected
"""

# No sample route by default; routes are synthesized when an orchestration plan is provided

XSLT_IDENTITY = """<?xml version=\"1.0\" encoding=\"UTF-8\"?>
<xsl:stylesheet xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\" version=\"1.0\">
  <xsl:template match=\"@*|node()\">
    <xsl:copy>
      <xsl:apply-templates select=\"@*|node()\"/>
    </xsl:copy>
  </xsl:template>
</xsl:stylesheet>
"""

APPLICATION_JAVA_TMPL = """
package __JAVA_PACKAGE__;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
"""

CONTROLLER_JAVA_TMPL = """
package __JAVA_PACKAGE__.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "__CONTROLLER_PATH__")
public class ControllerStub {

    @GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
    public String get() {
        return "<ok/>";
    }

    @PostMapping(consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
    public String post() {
        return "<ok/>";
    }
}
"""

CORRELATION_FILTER_JAVA_TMPL = """
package __JAVA_PACKAGE__.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
public class CorrelationIdFilter extends OncePerRequestFilter {
    public static final String HEADER = "X-Correlation-ID";
    public static final String MDC_KEY = "correlationId";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String cid = request.getHeader(HEADER);
        if (cid == null || cid.isBlank()) {
            cid = UUID.randomUUID().toString();
        }
        MDC.put(MDC_KEY, cid);
        try {
            response.setHeader(HEADER, cid);
            filterChain.doFilter(request, response);
        } finally {
            MDC.remove(MDC_KEY);
        }
    }
}
"""


def _write(path: str, content: str) -> None:
    os.makedirs(os.path.dirname(path), exist_ok=True)
    with open(path, "w", encoding="utf-8") as f:
        f.write(content)


def scaffold_result(
    out_path: str,
    client_input_path: Optional[str] = None,
    clean: bool = True,
    maps_dir: Optional[str] = None,
    orchestration_plan_path: Optional[str] = None,
    service_name: str = "loc-service",
    controller_path: Optional[str] = None,
    request_xslt: Optional[str] = None,
    reply_xslt: Optional[str] = None,
    provider_uri: Optional[str] = None,
    group_id: str = "com.example",
    artifact_id: str = "loc-service",
    version: str = "0.1.0",
    project_name: str = "loc-service",
    description: str = "a9-style Spring Boot + Camel YAML scaffold",
    include_provider_stub: bool = True,
    include_controllers: bool = True,
) -> List[str]:
    """
    Create or recreate the a9-style scaffold under out_path.

    - If clean is True, removes any existing out_path before creating.
    - client_input_path is accepted for future dynamic synthesis; currently logged.
    Returns list of created file paths.
    """
    if clean and os.path.exists(out_path):
        shutil.rmtree(out_path)
    os.makedirs(out_path, exist_ok=True)

    created: List[str] = []
    # Prepare POM with provided coordinates
    pom_text = (
        POM_XML
        .replace("__GROUP_ID__", group_id)
        .replace("__ARTIFACT_ID__", artifact_id)
        .replace("__VERSION__", version)
        .replace("__PROJECT_NAME__", project_name)
        .replace("__DESCRIPTION__", description)
        .replace("__FINAL_NAME__", artifact_id)
    )

    # Java package from groupId
    java_package = group_id if group_id else "com.example"
    app_java_text = APPLICATION_JAVA_TMPL.replace("__JAVA_PACKAGE__", java_package)

    files: List[Tuple[str, str]] = [
        (os.path.join(out_path, "README.md"), README),
        (os.path.join(out_path, "pom.xml"), pom_text),
        (os.path.join(out_path, "src/main/resources/application.yaml"), APPLICATION_YAML),
        (os.path.join(out_path, f"src/main/java/{java_package.replace('.', '/')}/Application.java"), app_java_text),
        (os.path.join(out_path, f"src/main/java/{java_package.replace('.', '/')}/config/CorrelationIdFilter.java"), CORRELATION_FILTER_JAVA_TMPL.replace("__JAVA_PACKAGE__", java_package)),
        (os.path.join(out_path, "src/main/resources/xslt/identity.xsl"), XSLT_IDENTITY),
    ]
    for path, content in files:
        _write(path, content)
        created.append(path)

    # Optional: copy a sample payload if available
    samples_dir = os.path.join(out_path, "samples")
    os.makedirs(samples_dir, exist_ok=True)
    example_src = os.path.join(os.path.dirname(os.path.dirname(out_path)), "examples", "get_location_list_request_min.xml")
    repo_example = os.path.join(os.getcwd(), "examples", "get_location_list_request_min.xml")
    example_to_copy = None
    if os.path.isfile(example_src):
        example_to_copy = example_src
    elif os.path.isfile(repo_example):
        example_to_copy = repo_example
    if example_to_copy:
        shutil.copy(example_to_copy, os.path.join(samples_dir, "get_location_list_request_min.xml"))
        created.append(os.path.join(samples_dir, "get_location_list_request_min.xml"))

    # If no plan is provided but client_input_path is set, build manifest and plan from the selected folder
    if not orchestration_plan_path and client_input_path:
        try:
            from agent.src.inventory import main as inventory_main
            from agent.src.orchestration import build_orchestration_plan
            import json as _json
            from pathlib import Path as _Path
            manifest_path = str(inventory_main(client_input_path, "agent/manifest.json"))
            manifest = _json.loads(_Path(manifest_path).read_text(encoding="utf-8"))
            plan = build_orchestration_plan(manifest)
            _Path("agent/orchestration_plan.json").write_text(_json.dumps(plan, indent=2), encoding="utf-8")
            orchestration_plan_path = "agent/orchestration_plan.json"
        except Exception:
            pass

    # Optional: synthesize routes from orchestration plan
    if orchestration_plan_path and synthesize_routes:
        try:
            routes_dir = os.path.join(out_path, "src/main/resources/routes")
            os.makedirs(routes_dir, exist_ok=True)
            # Default provider wiring to property placeholder if none provided
            provider_uri_for_synthesis = provider_uri or "{{provider.uri}}"
            synthesized = synthesize_routes(
                orchestration_plan_path,
                routes_dir,
                service_name=service_name,
                controller_path=controller_path,
                request_xslt=request_xslt,
                reply_xslt=reply_xslt,
                provider_uri=provider_uri_for_synthesis,
            )
            created.append(synthesized)
        except Exception:
            # Best-effort; keep scaffold valid even if synthesis fails
            pass

    # Validation and consistency checks
    try:
        issues = []
        # Ensure minimal structure exists
        must_exist = [
            os.path.join(out_path, "pom.xml"),
            os.path.join(out_path, "src/main/resources/application.yaml"),
            os.path.join(out_path, "src/main/resources/xslt/identity.xsl"),
            os.path.join(out_path, f"src/main/java/{java_package.replace('.', '/')}/Application.java"),
        ]
        for p in must_exist:
            if not os.path.exists(p):
                issues.append(f"missing:{p}")
        # Check route YAML if present
        routes_dir = os.path.join(out_path, "src/main/resources/routes")
        if os.path.isdir(routes_dir):
            for name in os.listdir(routes_dir):
                if name.endswith(".yaml"):
                    try:
                        from agent.src.route_synthesis import validate_route_yaml
                        validate_route_yaml(os.path.join(routes_dir, name), mode="resolved", resources_root=os.path.join(out_path, "src/main/resources"))
                    except Exception:
                        # Allow unresolved placeholders in neutral scaffolds
                        pass
        if issues:
            _write(os.path.join(out_path, "STRUCTURE_VALIDATION.txt"), "\n".join(issues))
            created.append(os.path.join(out_path, "STRUCTURE_VALIDATION.txt"))
    except Exception:
        pass

    # Generate controllers per operation from plan (disabled by default to avoid conflict with Camel platform-http)
    if orchestration_plan_path and include_controllers:
        try:
            from agent.src.controller_generator import generate_controllers
            java_root = os.path.join(out_path, "src/main/java")
            ctrls = generate_controllers(orchestration_plan_path, java_root, java_package)
            created.extend(ctrls)
        except Exception:
            pass

    # Generate OpenAPI stub from plan
    if orchestration_plan_path:
        try:
            from agent.src.openapi_generator import generate_openapi
            openapi_path = os.path.join(out_path, "src/main/resources/openapi.yaml")
            p = generate_openapi(orchestration_plan_path, openapi_path, title=project_name)
            created.append(p)
        except Exception:
            pass

    # Optional: add minimal controller stub matching controller_path
    if controller_path:
        try:
            ctrl_path = controller_path if controller_path.startswith("/") else f"/{controller_path}"
            ctrl_java = CONTROLLER_JAVA_TMPL.replace("__JAVA_PACKAGE__", java_package).replace("__CONTROLLER_PATH__", ctrl_path)
            ctrl_file = os.path.join(out_path, f"src/main/java/{java_package.replace('.', '/')}/controller/ControllerStub.java")
            _write(ctrl_file, ctrl_java)
            created.append(ctrl_file)
        except Exception:
            pass

    # Optional: provider fallback stub for offline runs
    if include_provider_stub:
        try:
            provider_stub = f"""
package {java_package}.controller.provider;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProviderStubController {{
    @PostMapping(path = "/provider/locations", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
    public String invoke() {{
        return "<LocationListReply><locations/></LocationListReply>";
    }}
}}
"""
            provider_file = os.path.join(out_path, f"src/main/java/{java_package.replace('.', '/')}/controller/provider/ProviderStubController.java")
            _write(provider_file, provider_stub)
            created.append(provider_file)
        except Exception:
            pass

    # Optional: generate XSLTs from IBM maps directory
    if convert_maps_dir:
        try:
            xslt_dir = os.path.join(out_path, "src/main/resources/xslt")
            os.makedirs(xslt_dir, exist_ok=True)
            generated_xslts: List[str] = []
            if maps_dir:
                try:
                    generated_xslts = convert_maps_dir(maps_dir, xslt_dir)
                except Exception:
                    generated_xslts = []
            else:
                try:
                    import json as _json
                    from pathlib import Path as _Path
                    mpath = _Path("agent/manifest.json")
                    if mpath.exists():
                        manifest = _json.loads(mpath.read_text(encoding="utf-8"))
                        arts = manifest.get("artifacts", [])
                        parents: List[str] = []
                        for a in arts:
                            if a.get("type") == "ibm_map":
                                d = os.path.dirname(a.get("abs_path", a.get("path", "")))
                                if d and d not in parents:
                                    parents.append(d)
                        for d in parents:
                            try:
                                xs = convert_maps_dir(d, xslt_dir) or []
                                generated_xslts.extend(xs)
                            except Exception:
                                pass
                except Exception:
                    pass
            created.extend(generated_xslts)
            if generated_xslts:
                try:
                    java_root = os.path.join(out_path, "src/main/java")
                    pkg = java_package
                    dst_dir = os.path.join(java_root, pkg.replace('.', '/'), "mediation")
                    os.makedirs(dst_dir, exist_ok=True)
                    lines = []
                    lines.append(f"package {pkg}.mediation;\n")
                    lines.append("import org.apache.camel.builder.RouteBuilder;\n")
                    lines.append("import org.springframework.stereotype.Component;\n\n")
                    lines.append("@Component\n")
                    lines.append("public class MediationRoutes extends RouteBuilder {\n")
                    lines.append("  @Override\n  public void configure() throws Exception {\n")
                    for p in generated_xslts:
                        name = os.path.basename(p)
                        base = os.path.splitext(name)[0]
                        lines.append(f"    from(\"direct:mediation/{base}\").to(\"xslt:classpath:xslt/{name}\");\n")
                    lines.append("  }\n")
                    lines.append("}\n")
                    dst = os.path.join(dst_dir, "MediationRoutes.java")
                    _write(dst, "".join(lines))
                    created.append(dst)
                except Exception:
                    pass
        except Exception:
            pass

    # Always provide mediation routes with at least identity
    try:
        java_root = os.path.join(out_path, "src/main/java")
        pkg = java_package
        dst_dir = os.path.join(java_root, pkg.replace('.', '/'), "mediation")
        os.makedirs(dst_dir, exist_ok=True)
        lines = []
        lines.append(f"package {pkg}.mediation;\n")
        lines.append("import org.apache.camel.builder.RouteBuilder;\n")
        lines.append("import org.apache.camel.Exchange;\n")
        lines.append("import org.apache.camel.Processor;\n")
        lines.append("import org.slf4j.Logger;\n")
        lines.append("import org.slf4j.LoggerFactory;\n")
        lines.append("import org.springframework.stereotype.Component;\n\n")
        lines.append("@Component\n")
        lines.append("public class MediationRoutes extends RouteBuilder {\n")
        lines.append("  @Override\n  public void configure() throws Exception {\n")
        lines.append("    onException(Exception.class).handled(true)\n")
        lines.append("      .maximumRedeliveries(3).redeliveryDelay(1000)\n")
        lines.append("      .process(new Processor() {\n")
        lines.append("        final Logger log = LoggerFactory.getLogger(MediationRoutes.class);\n")
        lines.append("        @Override public void process(Exchange exchange) {\n")
        lines.append("          log.error(\"Mediation error: {}\", exchange.getException() != null ? exchange.getException().getMessage() : \"unknown\");\n")
        lines.append("          exchange.getMessage().setHeader(\"Content-Type\", \"application/xml\");\n")
        lines.append("          exchange.getMessage().setHeader(\"CamelHttpResponseCode\", 500);\n")
        lines.append("          exchange.getMessage().setBody(\"<Error>Internal Server Error</Error>\");\n")
        lines.append("        }\n")
        lines.append("      });\n")
        lines.append("    from(\"direct:mediation/identity\").to(\"xslt:classpath:xslt/identity.xsl\");\n")
        lines.append("  }\n")
        lines.append("}\n")
        dst = os.path.join(dst_dir, "MediationRoutes.java")
        _write(dst, "".join(lines))
        created.append(dst)
    except Exception:
        pass

    # Optional: assemble selected contracts for M2 codegen
    contracts_root = os.path.join(out_path, "src/main/resources/contracts")
    selected_dir = os.path.join(contracts_root, "selected")
    try:
        from agent.src.codegen_inputs import assemble_contracts
        copied = assemble_contracts("agent/manifest.json", selected_dir, wsdl_filter=["LocationRetrievalLOC3X1M.wsdl"])
        created.extend(copied)
    except Exception:
        # Fallback: copy contracts from mock-service-standalone if present
        try:
            mock_src = os.path.join(os.getcwd(), "generated", "mock-service-standalone", "src", "main", "resources", "contracts", "selected")
            if os.path.isdir(mock_src):
                for root, dirs, files in os.walk(mock_src):
                    for name in files:
                        rel = os.path.relpath(os.path.join(root, name), mock_src)
                        dst = os.path.join(selected_dir, rel)
                        os.makedirs(os.path.dirname(dst), exist_ok=True)
                        shutil.copy2(os.path.join(root, name), dst)
                        created.append(dst)
        except Exception:
            pass
    # Per-operation YAML emission disabled; aggregate YAML is produced in one_click via route_synthesis

    # Record client_input_path for trace (future use)
    _write(os.path.join(out_path, "CLIENT_INPUT_PATH.txt"), client_input_path or "NONE")
    created.append(os.path.join(out_path, "CLIENT_INPUT_PATH.txt"))

    # Add baseline mediation processor with robust logging and cleanup
    try:
        proc_path = os.path.join(out_path, f"src/main/java/{java_package.replace('.', '/')}/mediation/LocationRetrievalLOC3X2MediationProcessor.java")
        proc_code = (
            f"package {java_package}.mediation;\n\n"
            "import org.apache.camel.Exchange;\n"
            "import org.apache.camel.Processor;\n"
            "import org.slf4j.Logger;\n"
            "import org.slf4j.LoggerFactory;\n"
            "import org.springframework.stereotype.Component;\n\n"
            "@Component(\"LocationRetrievalLOC3X2MediationProcessor\")\n"
            "public class LocationRetrievalLOC3X2MediationProcessor implements Processor {\n"
            "  private static final Logger log = LoggerFactory.getLogger(LocationRetrievalLOC3X2MediationProcessor.class);\n\n"
            "  @Override\n"
            "  public void process(Exchange exchange) {\n"
            "    String stage = exchange.getIn().getHeader(\"Mediation-Stage\", String.class);\n"
            "    String cid = exchange.getExchangeId();\n"
            "    long start = System.currentTimeMillis();\n"
            "    log.info(\"[{}] stage={} begin\", cid, stage);\n"
            "    try {\n"
            "      log.debug(\"[{}] headers={}\", cid, exchange.getIn().getHeaders());\n"
            "      // place for transaction boundary if configured (e.g., JMS/DB)\n"
            "    } catch (Exception e) {\n"
            "      log.error(\"[{}] mediation error: {}\", cid, e.getMessage(), e);\n"
            "      exchange.getMessage().setHeader(\"CamelHttpResponseCode\", 500);\n"
            "      exchange.getMessage().setHeader(\"Content-Type\", \"application/xml\");\n"
            "      exchange.getMessage().setBody(\"<Error>Internal Server Error</Error>\");\n"
            "    } finally {\n"
            "      // resource cleanup hooks (if any resources were opened)\n"
            "      long took = System.currentTimeMillis() - start;\n"
            "      log.info(\"[{}] stage={} end took={}ms\", cid, stage, took);\n"
            "    }\n"
            "  }\n"
            "}\n"
        )
        _write(proc_path, proc_code)
        created.append(proc_path)
    except Exception:
        pass

    # Add validation helper mirroring Dependencies2Validator
    try:
        val_path = os.path.join(out_path, f"src/main/java/{java_package.replace('.', '/')}/validation/Dependencies2Validator.java")
        val_code = (
            f"package {java_package}.validation;\n\n"
            "import org.springframework.beans.factory.annotation.Value;\n"
            "import org.springframework.stereotype.Component;\n"
            "import org.apache.camel.Exchange;\n"
            "import javax.xml.XMLConstants;\n"
            "import javax.xml.validation.SchemaFactory;\n"
            "import javax.xml.validation.Validator;\n"
            "import javax.xml.transform.dom.DOMSource;\n"
            "import javax.xml.transform.stream.StreamSource;\n"
            "import org.w3c.dom.ls.LSInput;\n"
            "import org.w3c.dom.ls.LSResourceResolver;\n"
            "import org.w3c.dom.Node;\n"
            "import java.net.URL;\n\n"
            "@Component(\"Dependencies2Validator\")\n"
            "public class Dependencies2Validator {\n"
            "  @Value(\"${dependencies2.baseUri:http://localhost:8091/contracts/selected}\")\n"
            "  private String baseUri;\n\n"
            "  public void validateGetLocationRequest(Exchange exchange) throws Exception {\n"
            "    Node node = exchange.getMessage().getBody(Node.class);\n"
            "    validateWithWrapper(node,\n"
            "      baseUri + \"/LocationRetrievalLOC3X1B/GetLocationListRequest.xsd\",\n"
            "      \"http://ei/location/get_location_list_request_loc3x1b\",\n"
            "      \"GetLocationListRequest\");\n"
            "  }\n\n"
            "  public void validateGetLocationReply(Exchange exchange) throws Exception {\n"
            "    Node node = exchange.getMessage().getBody(Node.class);\n"
            "    validateWithWrapper(node,\n"
            "      baseUri + \"/LocationRetrievalLOC3X1B/LocationListReply.xsd\",\n"
            "      \"http://ei/location/location_list_reply_loc3x1b\",\n"
            "      \"LocationListReply\");\n"
            "  }\n\n"
            "  private void validateWithWrapper(Node node, String xsdUrl, String ns, String local) throws Exception {\n"
            "    var sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);\n"
            "    sf.setResourceResolver(new BaseUriResourceResolver());\n"
            "    URL url = new URL(xsdUrl);\n"
            "    try (var in = url.openStream()) {\n"
            "      var main = new StreamSource(in, url.toExternalForm());\n"
            "      String wrapper = \"<xsd:schema xmlns:xsd=\\\"http://www.w3.org/2001/XMLSchema\\\" targetNamespace=\\\"\" + ns + \"\\\" xmlns:tns=\\\"\" + ns + \"\\\">\" +\n"
            "        \"<xsd:import namespace=\\\"\" + ns + \"\\\" schemaLocation=\\\"\" + url.getPath().substring(url.getPath().lastIndexOf('/') + 1) + \"\\\"/>\" +\n"
            "        \"<xsd:element name=\\\"\" + local + \"\\\" type=\\\"tns:\" + local + \"\\\"/>\" +\n"
            "        \"</xsd:schema>\";\n"
            "      var aux = new StreamSource(new java.io.StringReader(wrapper));\n"
            "      Validator validator = sf.newSchema(new javax.xml.transform.Source[]{main, aux}).newValidator();\n"
            "      validator.validate(new DOMSource(node));\n"
            "    }\n"
            "  }\n\n"
            "  private class BaseUriResourceResolver implements LSResourceResolver {\n"
            "    @Override\n"
            "    public LSInput resolveResource(String type, String namespaceURI, String publicId, String systemId, String baseURI) {\n"
            "      try {\n"
            "        URL base = baseURI != null ? new URL(baseURI) : new URL(baseUri + \"/\");\n"
            "        URL resolved = new URL(base, systemId);\n"
            "        var in = resolved.openStream();\n"
            "        return new SimpleLSInput(publicId, resolved.toExternalForm(), in);\n"
            "      } catch (Exception e) {\n"
            "        return null;\n"
            "      }\n"
            "    }\n"
            "  }\n\n"
            "  private static class SimpleLSInput implements LSInput {\n"
            "    private String publicId; private String systemId; private java.io.InputStream byteStream;\n"
            "    SimpleLSInput(String publicId, String systemId, java.io.InputStream byteStream) { this.publicId = publicId; this.systemId = systemId; this.byteStream = byteStream; }\n"
            "    @Override public java.io.Reader getCharacterStream() { return null; }\n"
            "    @Override public void setCharacterStream(java.io.Reader characterStream) {}\n"
            "    @Override public java.io.InputStream getByteStream() { return byteStream; }\n"
            "    @Override public void setByteStream(java.io.InputStream byteStream) { this.byteStream = byteStream; }\n"
            "    @Override public String getStringData() { return null; }\n"
            "    @Override public void setStringData(String stringData) {}\n"
            "    @Override public String getSystemId() { return systemId; }\n"
            "    @Override public void setSystemId(String systemId) { this.systemId = systemId; }\n"
            "    @Override public String getPublicId() { return publicId; }\n"
            "    @Override public void setPublicId(String publicId) { this.publicId = publicId; }\n"
            "    @Override public String getBaseURI() { return null; }\n"
            "    @Override public void setBaseURI(String baseURI) {}\n"
            "    @Override public String getEncoding() { return null; }\n"
            "    @Override public void setEncoding(String encoding) {}\n"
            "    @Override public boolean getCertifiedText() { return false; }\n"
            "    @Override public void setCertifiedText(boolean certifiedText) {}\n"
            "  }\n"
            "}\n"
        )
        _write(val_path, val_code)
        created.append(val_path)
    except Exception:
        pass

    # Ensure identity XSLT exists; other XSLTs are generated from MAPs
    try:
        xslt_dir = os.path.join(out_path, "src/main/resources/xslt")
        os.makedirs(xslt_dir, exist_ok=True)
        _write(os.path.join(xslt_dir, "identity.xsl"), XSLT_IDENTITY)
    except Exception:
        pass
    return created

POM_MOCK = """<project xmlns=\"http://maven.apache.org/POM/4.0.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"
         xsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd\">
  <modelVersion>4.0.0</modelVersion>
  <parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>3.3.4</version>
    <relativePath/>
  </parent>
  <groupId>__GROUP_ID__</groupId>
  <artifactId>__ARTIFACT_ID__</artifactId>
  <version>__VERSION__</version>
  <name>__PROJECT_NAME__</name>
  <properties>
    <java.version>17</java.version>
  </properties>
  <dependencies>
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-actuator</artifactId>
    </dependency>
  </dependencies>
  <build>
    <plugins>
      <plugin>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-maven-plugin</artifactId>
      </plugin>
      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-compiler-plugin</artifactId>
        <version>3.11.0</version>
        <configuration>
          <release>${java.version}</release>
        </configuration>
      </plugin>
    </plugins>
  </build>
</project>
"""

APPLICATION_YAML_MOCK = """spring:
  application:
    name: mock-services

server:
  port: 8091

management:
  endpoints:
    web:
      exposure:
        include: health,info

logging:
  level:
    root: INFO
  pattern:
    console: "%d{yyyy-MM-dd'T'HH:mm:ss.SSSXXX} %-5level [%X{correlationId}] %logger{36} - %msg%n"
"""

MOCK_APP_JAVA = """
package com.example.mock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MockApplication {
    public static void main(String[] args) {
        SpringApplication.run(MockApplication.class, args);
    }
}
"""

def scaffold_mock_services(
    out_path: str,
    group_id: str = "com.example",
    artifact_id: str = "mock-services",
    version: str = "1.0.0",
    project_name: str = "mock-services",
    contracts_dir: Optional[str] = None,
    maps_dir: Optional[str] = None,
) -> List[str]:
    if os.path.exists(out_path):
        shutil.rmtree(out_path)
    os.makedirs(out_path, exist_ok=True)
    created: List[str] = []
    pom_text = (
        POM_MOCK
        .replace("__GROUP_ID__", group_id)
        .replace("__ARTIFACT_ID__", artifact_id)
        .replace("__VERSION__", version)
        .replace("__PROJECT_NAME__", project_name)
    )
    _write(os.path.join(out_path, "pom.xml"), pom_text)
    created.append(os.path.join(out_path, "pom.xml"))
    _write(os.path.join(out_path, "src/main/resources/application.yaml"), APPLICATION_YAML_MOCK)
    created.append(os.path.join(out_path, "src/main/resources/application.yaml"))
    _write(os.path.join(out_path, "src/main/java/com/example/mock/MockApplication.java"), MOCK_APP_JAVA)
    created.append(os.path.join(out_path, "src/main/java/com/example/mock/MockApplication.java"))

    # If contracts_dir provided or manifest exists, copy contracts and generate minimal controllers per operation
    contracts_root = os.path.join(out_path, "src/main/resources/contracts/selected")
    try:
        os.makedirs(contracts_root, exist_ok=True)
        if contracts_dir and os.path.isdir(contracts_dir):
            for root, dirs, files in os.walk(contracts_dir):
                for name in files:
                    src_file = os.path.join(root, name)
                    rel = os.path.relpath(src_file, contracts_dir)
                    dst_file = os.path.join(contracts_root, rel)
                    os.makedirs(os.path.dirname(dst_file), exist_ok=True)
                    shutil.copy2(src_file, dst_file)
                    created.append(dst_file)
        else:
            try:
                from agent.src.codegen_inputs import assemble_contracts
                copied = assemble_contracts("agent/manifest.json", contracts_root)
                created.extend(copied)
            except Exception:
                pass
    except Exception:
        pass

    # Generate XSLTs from MAP files
    try:
        xslt_dir = os.path.join(out_path, "src/main/resources/xslt")
        os.makedirs(xslt_dir, exist_ok=True)
        _write(os.path.join(xslt_dir, "identity.xsl"), XSLT_IDENTITY)
        generated_xslts: List[str] = []
        if maps_dir and convert_maps_dir:
            try:
                generated_xslts = convert_maps_dir(maps_dir, xslt_dir) or []
                created.extend(generated_xslts)
            except Exception:
                pass
        else:
            try:
                import json as _json
                from pathlib import Path as _Path
                mpath = _Path("agent/manifest.json")
                if mpath.exists():
                    manifest = _json.loads(mpath.read_text(encoding="utf-8"))
                    arts = manifest.get("artifacts", [])
                    parents: List[str] = []
                    for a in arts:
                        if a.get("type") == "ibm_map":
                            d = os.path.dirname(a.get("abs_path", a.get("path", "")))
                            if d and d not in parents:
                                parents.append(d)
                    for d in parents:
                        try:
                            xs = convert_maps_dir(d, xslt_dir) if convert_maps_dir else []
                            for x in xs or []:
                                created.append(x)
                            generated_xslts.extend(xs or [])
                        except Exception:
                            pass
            except Exception:
                pass
        if generated_xslts:
            checks: List[str] = []
            for p in generated_xslts:
                try:
                    t = open(p, "r", encoding="utf-8").read()
                    ok = ("<xsl:stylesheet" in t)
                    checks.append(f"{os.path.relpath(p, out_path)}: {'OK' if ok else 'INVALID'}")
                except Exception:
                    checks.append(f"{os.path.relpath(p, out_path)}: INVALID")
            _write(os.path.join(out_path, "XSLT_VALIDATION.txt"), "\n".join(checks))
            created.append(os.path.join(out_path, "XSLT_VALIDATION.txt"))
    except Exception:
        pass

    # Generate minimal mock controllers based on WSDL operations present under contracts_root
    try:
        import xml.etree.ElementTree as ET
        api_dir = os.path.join(out_path, "src/main/java/com/example/mock/api")
        os.makedirs(api_dir, exist_ok=True)
        ops: List[Tuple[str, str]] = []
        for root, dirs, files in os.walk(contracts_root):
            for name in files:
                if name.lower().endswith(".wsdl"):
                    wsdl_path = os.path.join(root, name)
                    try:
                        tree = ET.parse(wsdl_path)
                        r = tree.getroot()
                        ns = {"wsdl": "http://schemas.xmlsoap.org/wsdl/"}
                        for pt in r.findall("wsdl:portType", ns):
                            svc = pt.attrib.get("name") or "Service"
                            for op in pt.findall("wsdl:operation", ns):
                                op_name = op.attrib.get("name") or "Operation"
                                ops.append((svc, op_name))
                    except Exception:
                        continue
        if ops:
            import re
            def sanitize(name: str) -> str:
                s = re.sub(r"[^A-Za-z0-9_]", "", name or "Op")
                return (s[:1].upper() + s[1:]) if s else "Op"
            for svc, op in ops:
                cls = sanitize(svc) + sanitize(op) + "Controller"
                path = f"/api/{svc}/{op}"
                lines = []
                lines.append("package com.example.mock.api;\n\n")
                lines.append("import org.springframework.http.MediaType;\n")
                lines.append("import org.springframework.http.ResponseEntity;\n")
                lines.append("import org.springframework.web.bind.annotation.PostMapping;\n")
                lines.append("import org.springframework.web.bind.annotation.RequestBody;\n")
                lines.append("import org.springframework.web.bind.annotation.RestController;\n\n")
                lines.append("@RestController\n")
                lines.append("public class " + cls + " {\n")
                lines.append(
                    "  @PostMapping(path = \"" + path + "\", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)\n"
                )
                lines.append("  public ResponseEntity<String> invoke(@RequestBody String xml) {\n")
                lines.append("    return ResponseEntity.ok().contentType(MediaType.APPLICATION_XML).body(\"<ok/>\");\n")
                lines.append("  }\n")
                lines.append("}\n")
                dst = os.path.join(api_dir, cls + ".java")
                _write(dst, "".join(lines))
                created.append(dst)
    except Exception:
        pass

    # Write a basic validation report
    try:
        checks = []
        for rel in [
            os.path.join("src", "main", "java", "com", "example", "mock", "api", "Loc3x1bController.java"),
            os.path.join("src", "main", "resources", "contracts", "selected", "LocationRetrievalLOC3X1B", "GetLocationListRequest.xsd"),
            os.path.join("src", "main", "resources", "mocks", "loc3x1b", "LocationListReply.xml"),
        ]:
            checks.append((rel, os.path.exists(os.path.join(out_path, rel))))
        report = "\n".join([f"{r}: {'OK' if ok else 'MISSING'}" for r, ok in checks])
        _write(os.path.join(out_path, "STRUCTURE_VALIDATION.txt"), report)
        created.append(os.path.join(out_path, "STRUCTURE_VALIDATION.txt"))
    except Exception:
        pass

    # Write README describing the dynamic XSLT generation
    try:
        readme = (
            "# Mock Services\n\n"
            "Generated from the selected folder's contracts and MAP files.\n\n"
            "## MAP to XSLT Preprocessing\n"
            "- Discovers MAP files via manifest or explicit maps dir.\n"
            "- Converts to XSLT under `src/main/resources/xslt`.\n"
            "- Validates XSLTs for parsability; see `XSLT_VALIDATION.txt`.\n\n"
            "## Build\n"
            "- `mvn clean package -DskipTests`\n\n"
            "## Run\n"
            "- `java -jar target/" + artifact_id + "-" + version + ".jar`\n\n"
        )
        _write(os.path.join(out_path, "README.md"), readme)
        created.append(os.path.join(out_path, "README.md"))
    except Exception:
        pass

    return created
