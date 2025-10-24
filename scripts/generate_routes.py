#!/usr/bin/env python3
"""
Integration Builder Agent - Camel Route Generator

Generates Java DSL Apache Camel routes from orchestration/spec.yaml
Implements Phase E: Integration Builder Agent (CXF/Camel) per master plan.

Features:
- Reads orchestration/spec.yaml for process flows and data mappings
- Generates Java DSL Camel routes with proper mediation sequences
- Creates Maven module structure for services, clients, and orchestration
- Externalises endpoints and configuration for profiles
- Produces ROUTES.md documentation
"""

import os
import sys
import yaml
import json
from pathlib import Path
from datetime import datetime
from typing import Dict, List, Any, Optional, Tuple
from dataclasses import dataclass


@dataclass
class RouteConfig:
    """Configuration for route generation"""
    project_root: Path
    spec_file: Path
    inventory_file: Path
    output_dir: Path
    package_base: str = "com.ei.camel"
    

class CamelRouteGenerator:
    """Generates Java DSL Camel routes from orchestration specification"""
    
    def __init__(self, config: RouteConfig):
        self.config = config
        self.spec_data: Dict[str, Any] = {}
        self.inventory_data: Dict[str, Any] = {}
        self.inbound_services: List[str] = []
        self.client_services: List[str] = []
    
    def load_spec(self) -> None:
        """Load orchestration specification from YAML"""
        if not self.config.spec_file.exists():
            raise FileNotFoundError(f"Orchestration spec not found: {self.config.spec_file}")
            
        with open(self.config.spec_file, 'r', encoding='utf-8') as f:
            self.spec_data = yaml.safe_load(f)
            
        print(f"✓ Loaded orchestration spec: {self.config.spec_file}")
    
    def load_inventory(self) -> None:
        if not self.config.inventory_file.exists():
            raise FileNotFoundError(f"Contract inventory not found: {self.config.inventory_file}")
        with open(self.config.inventory_file, 'r', encoding='utf-8') as f:
            self.inventory_data = json.load(f)
        print(f"✓ Loaded contracts inventory: {self.config.inventory_file}")
    
    def _normalize(self, name: str) -> str:
        return ''.join(ch.lower() if ch.isalnum() else '-' for ch in name).strip('-')
    
    def derive_modules(self) -> None:
        inbound_ops = self.spec_data.get('inbound', [])
        inbound_links = []
        for op in inbound_ops:
            pl = op.get('partnerLink') or op.get('service') or self.spec_data.get('process', {}).get('name')
            if pl:
                inbound_links.append(pl)
        self.inbound_services = sorted(set(inbound_links))
    
        invokes = []
        for op in inbound_ops:
            for inv in op.get('invokes', []):
                pl = inv.get('partnerLink') or inv.get('service')
                if pl:
                    invokes.append(pl)
        self.client_services = sorted(set(invokes))
    
        print(f"✓ Derived inbound services: {self.inbound_services}")
        print(f"✓ Derived client services: {self.client_services}")
    
    def find_wsdl_for_partner(self, partner: str) -> Optional[str]:
        wsdl_files = self.inventory_data.get('wsdl_files', [])
        key = self._normalize(partner)
        for w in wsdl_files:
            fp = w.get('file_path', '')
            ap = w.get('absolute_path', '')
            if key and (key in self._normalize(fp) or key in self._normalize(ap)):
                return ap or fp
        # Fallback: attempt partial token match
        tokens = [t.lower() for t in partner.split() if t]
        for w in wsdl_files:
            target = (w.get('file_path', '') + ' ' + w.get('absolute_path', '')).lower()
            if all(tok in target for tok in tokens):
                return w.get('absolute_path') or w.get('file_path')
        return None
    
    def generate_maven_structure(self) -> None:
        modules_dir = self.config.output_dir / "modules"
        modules_dir.mkdir(parents=True, exist_ok=True)
    
        # Always present generic modules
        base_modules = ["contracts", "mappings", "orchestration"]
        for module in base_modules:
            for sub in ["src/main/java", "src/main/resources", "src/test/java"]:
                (modules_dir / module / sub).mkdir(parents=True, exist_ok=True)
    
        # Dynamic client modules only (inbound endpoints hosted in orchestration)
        for cli in self.client_services:
            norm = self._normalize(cli)
            base = modules_dir / f"clients/{norm}"
            for sub in ["src/main/java", "src/main/resources", "src/test/java"]:
                (base / sub).mkdir(parents=True, exist_ok=True)
    
        print(f"✓ Created dynamic Maven structure under {modules_dir}")
    
    def generate_parent_pom(self) -> None:
        modules_dir = self.config.output_dir / "modules"
        client_mods = [f"clients/{self._normalize(c)}" for c in self.client_services]
        all_modules = ["contracts"] + client_mods + ["orchestration", "mappings"]
        parent_pom = self._create_parent_pom_content(all_modules)
        pom_path = modules_dir / "pom.xml"
        with open(pom_path, 'w', encoding='utf-8') as f:
            f.write(parent_pom)
        print(f"✓ Generated parent pom.xml: {pom_path}")
    
    def _create_parent_pom_content(self, modules: List[str]) -> str:
        mods_xml = "\n".join([f"        <module>{m}</module>" for m in modules])
        return f'''<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <groupId>com.ei</groupId>
    <artifactId>camel-route-parent</artifactId>
    <version>1.0.0-SNAPSHOT</version>
    <packaging>pom</packaging>
    <name>LOC-CAMEL-ROUTE Parent</name>
    <description>Apache Camel routes generated from contracts and BPEL</description>
    <properties>
        <maven.compiler.source>17</maven.compiler.source>
        <maven.compiler.target>17</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <spring-boot.version>3.2.0</spring-boot.version>
        <camel.version>4.15.0</camel.version>
        <cxf.version>4.0.3</cxf.version>
        <mapstruct.version>1.5.5.Final</mapstruct.version>
    </properties>
    <modules>
{mods_xml}
    </modules>
    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-dependencies</artifactId>
                <version>${{spring-boot.version}}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
            <dependency>
                <groupId>org.apache.cxf</groupId>
                <artifactId>cxf-bom</artifactId>
                <version>${{cxf.version}}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>
    <build>
        <pluginManagement>
            <plugins>
                <plugin>
                    <groupId>org.apache.cxf</groupId>
                    <artifactId>cxf-codegen-plugin</artifactId>
                    <version>${{cxf.version}}</version>
                </plugin>
            </plugins>
        </pluginManagement>
    </build>
</project>
'''
    
    def generate_client_poms(self) -> None:
        """Generate minimal Maven poms for dynamic client modules"""
        modules_dir = self.config.output_dir / "modules"
        for cli in self.client_services:
            norm = self._normalize(cli)
            pom_path = modules_dir / f"clients/{norm}/pom.xml"
            pom_content = f'''<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 
         http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    
    <parent>
        <groupId>com.ei</groupId>
        <artifactId>camel-route-parent</artifactId>
        <version>1.0.0-SNAPSHOT</version>
        <relativePath>../../pom.xml</relativePath>
    </parent>
    
    <artifactId>{norm}-client</artifactId>
    <name>{cli} Client</name>
    <description>Dynamic CXF client stub for {cli}</description>
    
    <dependencies>
        <!-- Depends on generated contract classes -->
        <dependency>
            <groupId>com.ei</groupId>
            <artifactId>contracts</artifactId>
            <version>1.0.0-SNAPSHOT</version>
        </dependency>
    </dependencies>
</project>
'''
            pom_path.write_text(pom_content, encoding="utf-8")
            print(f"✓ Generated client pom.xml: {pom_path}")
    def generate_orchestration_routes(self) -> None:
        """Generate main orchestration Camel routes"""
        if 'inbound' not in self.spec_data:
            print("⚠ No inbound operations found in spec")
            return
            
        orchestration_dir = self.config.output_dir / "modules/orchestration/src/main/java/com/ei/camel/orchestration"
        orchestration_dir.mkdir(parents=True, exist_ok=True)
        
        # Generate route for each inbound operation
        for inbound_op in self.spec_data['inbound']:
            route_class = self._generate_route_class(inbound_op)
            class_name = f"{inbound_op['operation']}Route"
            route_file = orchestration_dir / f"{class_name}.java"
            
            with open(route_file, 'w', encoding='utf-8') as f:
                f.write(route_class)
                
            print(f"✓ Generated route: {route_file}")
            
    def _generate_route_class(self, inbound_op: Dict[str, Any]) -> str:
        """Generate Java DSL route class for an inbound operation"""
        operation = inbound_op['operation']
        class_name = f"{operation}Route"
        partner_link = inbound_op.get('partnerLink') or inbound_op.get('service') or self.spec_data.get('process', {}).get('name', 'inbound')
        bean_base = ''.join(ch.lower() for ch in partner_link if ch.isalnum())
        inbound_bean = f"{bean_base}Endpoint"
        
        # Extract invokes for mediation sequence
        invokes = inbound_op.get('invokes', [])
        
        # Generate route content
        route_content = f'''package com.ei.camel.orchestration;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;
import org.apache.camel.component.cxf.common.message.CxfConstants;

/**
 * Camel route for {operation}
 * Generated from orchestration/spec.yaml
 * 
 * Mediation flow: Inbound -> {' -> '.join([inv.get('partnerLink', 'Unknown') for inv in invokes])}
 */
@Component
public class {class_name} extends RouteBuilder {{

    @Override
    public void configure() throws Exception {{
        
        // Inbound endpoint for {operation}
        from("cxf:bean:{inbound_bean}")
            .routeId("{operation.lower()}-route")
            .log("Processing {operation} request: ${{body}}")
            
            // Error handling
            .onException(Exception.class)
                .handled(true)
                .log("Error in {operation}: ${{exception.message}}")
                .to("direct:simpleFaultHandler")
            .end()
            
            // Request validation
            .to("direct:validateRequest")
            
{self._generate_mediation_steps(invokes)}
            
            // Final response mapping
            .to("direct:mapFinalResponse")
            .log("Completed {operation} processing: ${{body}}");
            
        // Request validation route
        from("direct:validateRequest")
            .log("Validating request structure");
            
        // Simple fault handler
        from("direct:simpleFaultHandler")
            .log("Generating SimpleFault response")
            .setBody(constant("{{\\\"fault\\\": \\\"Processing error occurred\\\"}}"));
            
        // Final response mapping
        from("direct:mapFinalResponse")
            .log("Mapping final response");

        // Stub mapping routes for partner invocations
            .log("Mapping final response");
            
        // Simple fault handler
        from("direct:simpleFaultHandler")
            .log("Generating SimpleFault response")
            .setBody(constant("{{\\\"fault\\\": \\\"Processing error occurred\\\"}}"));
            
        // Final response mapping
        from("direct:mapFinalResponse")
            .log("Mapping final response");
    }}
}}
'''
        return route_content
        
    def _generate_mediation_steps(self, invokes: List[Dict[str, Any]]) -> str:
        """Generate mediation steps for service invocations"""
        if not invokes:
            return "            // No external service invocations\n"
            
        steps = []
        for i, invoke in enumerate(invokes):
            partner_link = invoke.get('partnerLink', 'unknown')
            operation = invoke.get('operation', 'unknown')
            
            steps.append(f'''            // Step {i+1}: Invoke {partner_link}.{operation}
            .to("direct:map{partner_link}Request")
            .setHeader(CxfConstants.OPERATION_NAME, constant("{operation}"))
            .to("cxf:bean:{partner_link.lower()}Client")
            .to("direct:map{partner_link}Response")''')
            
        return '\n'.join(steps) + '\n'
        
    def generate_orchestration_pom(self) -> None:
        """Generate orchestration module pom.xml"""
        pom_content = '''<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 
         http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    
    <parent>
        <groupId>com.ei</groupId>
        <artifactId>camel-route-parent</artifactId>
        <version>1.0.0-SNAPSHOT</version>
        <relativePath>../pom.xml</relativePath>
    </parent>
    
    <artifactId>orchestration</artifactId>
    <name>Orchestration Routes</name>
    <description>Apache Camel orchestration routes</description>
    
    <dependencies>
        <dependency>
            <groupId>org.apache.camel.springboot</groupId>
            <artifactId>camel-spring-boot-starter</artifactId>
            <version>${camel.version}</version>
        </dependency>
        <dependency>
            <groupId>org.apache.camel.springboot</groupId>
            <artifactId>camel-cxf-soap-starter</artifactId>
            <version>${camel.version}</version>
        </dependency>
        <dependency>
            <groupId>org.apache.cxf</groupId>
            <artifactId>cxf-spring-boot-starter-jaxws</artifactId>
            <version>${cxf.version}</version>
        </dependency>
        <dependency>
            <groupId>org.apache.cxf</groupId>
            <artifactId>cxf-rt-frontend-jaxws</artifactId>
            <version>${cxf.version}</version>
        </dependency>
        <dependency>
            <groupId>org.apache.cxf</groupId>
            <artifactId>cxf-rt-transports-http</artifactId>
            <version>${cxf.version}</version>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.apache.camel</groupId>
            <artifactId>camel-test-spring-junit5</artifactId>
            <scope>test</scope>
            <version>${camel.version}</version>
        </dependency>
    </dependencies>
    
    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <version>${spring-boot.version}</version>
            </plugin>
        </plugins>
    </build>
</project>
'''
        
        pom_path = self.config.output_dir / "modules/orchestration/pom.xml"
        with open(pom_path, 'w', encoding='utf-8') as f:
            f.write(pom_content)
            
        print(f"✓ Generated orchestration pom.xml: {pom_path}")
        
    def generate_routes_documentation(self) -> None:
        """Generate ROUTES.md documentation"""
        doc_content = self._create_routes_documentation()
        doc_path = self.config.output_dir / "modules/orchestration/ROUTES.md"
        
        with open(doc_path, 'w', encoding='utf-8') as f:
            f.write(doc_content)
            
        print(f"✓ Generated ROUTES.md: {doc_path}")
        
    def _create_routes_documentation(self) -> str:
        """Create ROUTES.md content"""
        process_name = self.spec_data.get('process', {}).get('name', 'Unknown')
        inbound_ops = self.spec_data.get('inbound', [])
        
        doc = f'''# Apache Camel Routes Documentation

**Generated:** {datetime.now().isoformat()}
**Process:** {process_name}
**Source:** orchestration/spec.yaml

## Overview

This module contains Apache Camel routes that implement the mediation logic derived from the original BPEL process. The routes are generated using Java DSL and follow enterprise integration patterns.

## Architecture

- **Inbound Services:** CXF endpoints exposing SOAP operations
- **Orchestration:** Apache Camel routes implementing mediation flows
- **Outbound Clients:** CXF clients for partner service invocations
- **Error Handling:** Centralised fault handling producing SimpleFault responses

## Generated Routes

'''
        
        for inbound_op in inbound_ops:
            operation = inbound_op['operation']
            partner_link = inbound_op.get('partnerLink', 'Unknown')
            bean_base = ''.join(ch.lower() for ch in (partner_link or 'inbound') if ch.isalnum())
            inbound_bean = f"{bean_base}Endpoint"
            invokes = inbound_op.get('invokes', [])
            
            doc += f'''### {operation}Route

- **Operation:** `{operation}`
- **Partner Link:** `{partner_link}`
- **Route ID:** `{operation.lower()}-route`

#### Mediation Flow

1. **Inbound:** CXF endpoint receives SOAP request
2. **Validation:** Request structure validation
3. **Mediation Steps:**
'''
            
            if invokes:
                for i, invoke in enumerate(invokes):
                    inv_partner = invoke.get('partnerLink', 'unknown')
                    inv_operation = invoke.get('operation', 'unknown')
                    doc += f'''   - Step {i+1}: Invoke {inv_partner}.{inv_operation}
'''
            else:
                doc += '''   - No external service invocations
'''
                
            doc += f'''4. **Response Mapping:** Final response transformation
5. **Error Handling:** SimpleFault generation on exceptions

#### Endpoints

- **Inbound:** `cxf:bean:{inbound_bean}`
- **Outbound:** CXF client beans with `operationName` set via `CxfConstants.OPERATION_NAME`

'''
        
        doc += '''## Configuration

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
'''
        
        return doc
        
    def generate_all(self) -> None:
        """Generate complete Camel route structure"""
        print(f"🚀 Starting generic Camel route generation...")
        print(f"   Project root: {self.config.project_root}")
        print(f"   Spec file: {self.config.spec_file}")
        print(f"   Inventory file: {self.config.inventory_file}")
        print(f"   Output dir: {self.config.output_dir}")
        self.load_spec()
        self.load_inventory()
        self.derive_modules()
        self.generate_maven_structure()
        self.generate_parent_pom()
        self.generate_client_poms()
        self.generate_orchestration_routes()
        self.generate_orchestration_pom()
        self.generate_routes_documentation()
        print(f"    Generic Camel route generation completed!")
        print(f"   Generated modules under: {self.config.output_dir}/modules/")
        

def main():
    """Main entry point"""
    import argparse
    
    parser = argparse.ArgumentParser(description="Generate Apache Camel routes from orchestration spec")
    parser.add_argument("--spec", default="orchestration/spec.yaml", 
                       help="Path to orchestration spec file")
    parser.add_argument("--output", default=".", 
                       help="Output directory for generated modules")
    parser.add_argument("--package", default="com.ei.camel",
                       help="Base Java package name")
    parser.add_argument("--inventory", default="contracts/inventory.json",
                       help="Path to contracts inventory JSON")
    
    args = parser.parse_args()
    
    # Resolve paths
    project_root = Path.cwd()
    spec_file = project_root / args.spec
    inventory_file = project_root / args.inventory
    output_dir = Path(args.output).resolve()
    
    # Create configuration
    config = RouteConfig(
        project_root=project_root,
        spec_file=spec_file,
        inventory_file=inventory_file,
        output_dir=output_dir,
        package_base=args.package
    )
    
    # Generate routes
    generator = CamelRouteGenerator(config)
    generator.generate_all()


if __name__ == "__main__":
    main()