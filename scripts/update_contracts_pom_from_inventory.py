#!/usr/bin/env python3
"""
Update modules/contracts/pom.xml to drive CXF/JAXB code generation from
contracts/inventory.json instead of hardcoded sample WSDLs.

It selects WSDLs that declare concrete services (typically *Http.wsdl wrappers)
so that wsdl2java has service/port definitions to generate client stubs.
"""
import json
from pathlib import Path
import sys
from typing import List

PROJECT_ROOT = Path.cwd()
INVENTORY_PATH = PROJECT_ROOT / "contracts/inventory.json"
POM_PATH = PROJECT_ROOT / "modules/contracts/pom.xml"

POM_TEMPLATE_TOP = """<?xml version=\"1.0\" encoding=\"UTF-8\"?>
<project xmlns=\"http://maven.apache.org/POM/4.0.0\"
         xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"
         xsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0 
         http://maven.apache.org/xsd/maven-4.0.0.xsd\">
    <modelVersion>4.0.0</modelVersion>
    
    <parent>
        <groupId>com.ei</groupId>
        <artifactId>camel-route-parent</artifactId>
        <version>1.0.0-SNAPSHOT</version>
        <relativePath>../pom.xml</relativePath>
    </parent>
    
    <artifactId>contracts</artifactId>
    <name>Service Contracts</name>
    <description>Shared WSDL and XSD contracts for all services and clients</description>
    
    <build>
        <plugins>
            <!-- CXF/JAXB code generation from inventory-driven WSDLs -->
            <plugin>
                <groupId>org.apache.cxf</groupId>
                <artifactId>cxf-codegen-plugin</artifactId>
                <executions>
                    <execution>
                        <id>generate-sources</id>
                        <phase>generate-sources</phase>
                        <configuration>
                            <sourceRoot>${project.build.directory}/generated-sources/cxf</sourceRoot>
                            <wsdlOptions>
"""

POM_TEMPLATE_BOTTOM = """
                            </wsdlOptions>
                        </configuration>
                        <goals>
                            <goal>wsdl2java</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>
    
    <dependencies>
        <dependency>
            <groupId>jakarta.xml.ws</groupId>
            <artifactId>jakarta.xml.ws-api</artifactId>
            <version>4.0.0</version>
        </dependency>
        <dependency>
            <groupId>jakarta.xml.bind</groupId>
            <artifactId>jakarta.xml.bind-api</artifactId>
            <version>4.0.2</version>
        </dependency>
        <dependency>
            <groupId>org.glassfish.jaxb</groupId>
            <artifactId>jaxb-runtime</artifactId>
            <version>4.0.2</version>
        </dependency>
    </dependencies>
</project>
"""


def select_wsdl_paths(inventory: dict) -> List[str]:
    wsdl_files = inventory.get("wsdl_files", [])
    selected: List[str] = []
    for wf in wsdl_files:
        services = wf.get("services") or []
        ap = wf.get("absolute_path") or wf.get("file_path")
        # Choose WSDLs that expose concrete services (e.g., HttpService wrappers)
        if services and ap:
            selected.append(ap)
    # Deduplicate while preserving order
    seen = set()
    unique = []
    for p in selected:
        if p not in seen:
            unique.append(p)
            seen.add(p)
    return unique


def make_wsdl_option_block(paths: List[str]) -> str:
    lines = []
    for p in paths:
        # Use absolute path to avoid resolution issues
        lines.append(f"                                <wsdlOption>\n                                    <wsdl>{p}</wsdl>\n                                </wsdlOption>")
    return "\n".join(lines) + "\n"


def main() -> int:
    if not INVENTORY_PATH.exists():
        print(f"ERROR: Inventory not found: {INVENTORY_PATH}", file=sys.stderr)
        return 1
    try:
        inventory = json.loads(INVENTORY_PATH.read_text(encoding="utf-8"))
    except Exception as e:
        print(f"ERROR: Failed to parse inventory: {e}", file=sys.stderr)
        return 1
    paths = select_wsdl_paths(inventory)
    if not paths:
        print("ERROR: No service WSDLs found in inventory.json", file=sys.stderr)
        return 1
    wsdl_block = make_wsdl_option_block(paths)
    pom_content = POM_TEMPLATE_TOP + wsdl_block + POM_TEMPLATE_BOTTOM
    POM_PATH.write_text(pom_content, encoding="utf-8")
    print(f"✓ Updated contracts POM with {len(paths)} WSDLs: {POM_PATH}")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())