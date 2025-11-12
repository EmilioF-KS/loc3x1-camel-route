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

README = """# a9-like Project Scaffold (GEN-100)

This is an a9-style Spring Boot + Camel (YAML DSL) scaffold.

- Java: 17
- Spring Boot: 3.3.x
- Camel: 4.7.x
- Routes DSL: YAML
- Includes: Actuator, Jackson, Camel Spring Boot starter, Camel YAML DSL

Build (optional, if Maven is available):

```
mvn -DskipTests package
```

Run (example):

```
java -jar target/a9-like-project-0.1.0.jar
```

Camel loads routes from `classpath:routes/*.yaml`.

## Routes & Error Handling (GEN-104)
- Each route sets `X-Correlation-ID` to the Camel `exchangeId`.
- Request logs include the correlation ID at INFO.
- Errors are handled with redelivery policy from provider properties, map to HTTP `500` via `CamelHttpResponseCode`, and return a simple XML error body.

## Correlation-ID Logging
- A servlet filter sets/propagates `X-Correlation-ID` and writes it to logging MDC.
- Console logging pattern includes `[correlationId]` for all application logs.
- Camel route logs also include the correlation ID in messages.

## Actuator Endpoints
- Health and Info are exposed at `/actuator/health` and `/actuator/info`.
- Customize exposure via `management.endpoints.web.exposure.include` in `application.yaml`.
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
            <groupId>org.apache.camel</groupId>
            <artifactId>camel-platform-http</artifactId>
            <version>${camel.version}</version>
        </dependency>
        <dependency>
            <groupId>org.springdoc</groupId>
            <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
            <version>2.6.0</version>
        </dependency>
    </dependencies>

    <build>
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

</project>
"""

APPLICATION_YAML = """spring:
  application:
    name: a9-like-project

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
  uri: http://provider/locations
  timeoutMs: 5000
  retry:
    maxAttempts: 3
    delayMs: 250
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
    artifact_id: str = "a9-like-project",
    version: str = "0.1.0",
    project_name: str = "a9-like-project",
    description: str = "a9-style Spring Boot + Camel YAML scaffold",
    include_provider_stub: bool = True,
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
    if maps_dir and convert_maps_dir:
        try:
            xslt_dir = os.path.join(out_path, "src/main/resources/xslt")
            os.makedirs(xslt_dir, exist_ok=True)
            generated_xslts = convert_maps_dir(maps_dir, xslt_dir)
            created.extend(generated_xslts)
        except Exception:
            pass

    # Record client_input_path for trace (future use)
    _write(os.path.join(out_path, "CLIENT_INPUT_PATH.txt"), client_input_path or "NONE")
    created.append(os.path.join(out_path, "CLIENT_INPUT_PATH.txt"))
    return created