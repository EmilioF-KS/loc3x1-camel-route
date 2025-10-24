#!/usr/bin/env python3
"""
Contract Inventory Generator for LOC-001
Scans WSDL, XSD, and BPEL files to create comprehensive inventory
"""

import os
import json
import xml.etree.ElementTree as ET
from pathlib import Path
from datetime import datetime
import re

class ContractInventoryGenerator:
    def __init__(self, project_root):
        self.project_root = Path(project_root)
        self.sample_dir = self.project_root / "sample"
        self.contracts_dir = self.project_root / "contracts"
        self.inventory = {
            "metadata": {
                "generated_at": datetime.now().isoformat(),
                "project_root": str(self.project_root),
                "scan_directory": str(self.sample_dir)
            },
            "wsdl_files": [],
            "xsd_files": [],
            "bpel_files": [],
            "summary": {
                "total_wsdl": 0,
                "total_xsd": 0,
                "total_bpel": 0,
                "namespaces": set(),
                "services": [],
                "operations": []
            }
        }
        
        # Ensure contracts directory exists
        self.contracts_dir.mkdir(exist_ok=True)
    
    def scan_files(self):
        """Scan for all WSDL, XSD, and BPEL files"""
        print(f"Scanning {self.sample_dir} for contract files...")
        
        for file_path in self.sample_dir.rglob("*"):
            if file_path.is_file():
                if file_path.suffix.lower() == '.wsdl':
                    self.process_wsdl(file_path)
                elif file_path.suffix.lower() == '.xsd':
                    self.process_xsd(file_path)
                elif file_path.suffix.lower() == '.bpel':
                    self.process_bpel(file_path)
    
    def process_wsdl(self, file_path):
        """Process a WSDL file and extract metadata"""
        try:
            tree = ET.parse(file_path)
            root = tree.getroot()
            
            # Extract namespaces
            namespaces = dict(root.attrib.items())
            target_namespace = root.get('targetNamespace', '')
            
            # Extract service information
            services = []
            operations = []
            bindings = []
            
            # Find services
            for service in root.findall('.//{http://schemas.xmlsoap.org/wsdl/}service'):
                service_name = service.get('name', '')
                services.append(service_name)
            
            # Find port types and operations
            for porttype in root.findall('.//{http://schemas.xmlsoap.org/wsdl/}portType'):
                porttype_name = porttype.get('name', '')
                for operation in porttype.findall('.//{http://schemas.xmlsoap.org/wsdl/}operation'):
                    op_name = operation.get('name', '')
                    operations.append({
                        'name': op_name,
                        'portType': porttype_name,
                        'file': str(file_path.relative_to(self.project_root))
                    })
            
            # Find bindings
            for binding in root.findall('.//{http://schemas.xmlsoap.org/wsdl/}binding'):
                binding_name = binding.get('name', '')
                binding_type = binding.get('type', '')
                bindings.append({
                    'name': binding_name,
                    'type': binding_type
                })
            
            wsdl_info = {
                'file_path': str(file_path.relative_to(self.project_root)),
                'absolute_path': str(file_path),
                'file_size': file_path.stat().st_size,
                'target_namespace': target_namespace,
                'services': services,
                'operations': [op['name'] for op in operations],
                'bindings': bindings,
                'imports': self.extract_imports(root),
                'namespaces': {k: v for k, v in root.attrib.items() if k.startswith('xmlns')}
            }
            
            self.inventory['wsdl_files'].append(wsdl_info)
            self.inventory['summary']['namespaces'].add(target_namespace)
            self.inventory['summary']['services'].extend(services)
            self.inventory['summary']['operations'].extend(operations)
            
            print(f"✓ Processed WSDL: {file_path.name}")
            
        except Exception as e:
            print(f"✗ Error processing WSDL {file_path}: {e}")
    
    def process_xsd(self, file_path):
        """Process an XSD file and extract metadata"""
        try:
            tree = ET.parse(file_path)
            root = tree.getroot()
            
            target_namespace = root.get('targetNamespace', '')
            
            # Extract elements and types
            elements = []
            complex_types = []
            simple_types = []
            
            for element in root.findall('.//{http://www.w3.org/2001/XMLSchema}element'):
                elem_name = element.get('name', '')
                elem_type = element.get('type', '')
                if elem_name:
                    elements.append({'name': elem_name, 'type': elem_type})
            
            for complex_type in root.findall('.//{http://www.w3.org/2001/XMLSchema}complexType'):
                type_name = complex_type.get('name', '')
                if type_name:
                    complex_types.append(type_name)
            
            for simple_type in root.findall('.//{http://www.w3.org/2001/XMLSchema}simpleType'):
                type_name = simple_type.get('name', '')
                if type_name:
                    simple_types.append(type_name)
            
            xsd_info = {
                'file_path': str(file_path.relative_to(self.project_root)),
                'absolute_path': str(file_path),
                'file_size': file_path.stat().st_size,
                'target_namespace': target_namespace,
                'elements': elements,
                'complex_types': complex_types,
                'simple_types': simple_types,
                'imports': self.extract_imports(root),
                'includes': self.extract_includes(root)
            }
            
            self.inventory['xsd_files'].append(xsd_info)
            if target_namespace:
                self.inventory['summary']['namespaces'].add(target_namespace)
            
            print(f"✓ Processed XSD: {file_path.name}")
            
        except Exception as e:
            print(f"✗ Error processing XSD {file_path}: {e}")
    
    def process_bpel(self, file_path):
        """Process a BPEL file and extract metadata"""
        try:
            tree = ET.parse(file_path)
            root = tree.getroot()
            
            process_name = root.get('name', '')
            target_namespace = root.get('targetNamespace', '')
            
            # Extract partner links
            partner_links = []
            for partner_link in root.findall('.//{http://schemas.xmlsoap.org/ws/2004/03/business-process/}partnerLink'):
                pl_name = partner_link.get('name', '')
                pl_type = partner_link.get('partnerLinkType', '')
                my_role = partner_link.get('myRole', '')
                partner_role = partner_link.get('partnerRole', '')
                partner_links.append({
                    'name': pl_name,
                    'type': pl_type,
                    'myRole': my_role,
                    'partnerRole': partner_role
                })
            
            # Extract variables
            variables = []
            for variable in root.findall('.//{http://schemas.xmlsoap.org/ws/2004/03/business-process/}variable'):
                var_name = variable.get('name', '')
                var_type = variable.get('type', '')
                variables.append({'name': var_name, 'type': var_type})
            
            # Extract imports
            imports = []
            for imp in root.findall('.//{http://schemas.xmlsoap.org/ws/2004/03/business-process/}import'):
                import_type = imp.get('importType', '')
                location = imp.get('location', '')
                namespace = imp.get('namespace', '')
                imports.append({
                    'importType': import_type,
                    'location': location,
                    'namespace': namespace
                })
            
            bpel_info = {
                'file_path': str(file_path.relative_to(self.project_root)),
                'absolute_path': str(file_path),
                'file_size': file_path.stat().st_size,
                'process_name': process_name,
                'target_namespace': target_namespace,
                'partner_links': partner_links,
                'variables': variables,
                'imports': imports
            }
            
            self.inventory['bpel_files'].append(bpel_info)
            if target_namespace:
                self.inventory['summary']['namespaces'].add(target_namespace)
            
            print(f"✓ Processed BPEL: {file_path.name}")
            
        except Exception as e:
            print(f"✗ Error processing BPEL {file_path}: {e}")
    
    def extract_imports(self, root):
        """Extract import statements from XML"""
        imports = []
        for imp in root.findall('.//{http://schemas.xmlsoap.org/wsdl/}import'):
            imports.append({
                'namespace': imp.get('namespace', ''),
                'location': imp.get('location', '')
            })
        for imp in root.findall('.//{http://www.w3.org/2001/XMLSchema}import'):
            imports.append({
                'namespace': imp.get('namespace', ''),
                'schemaLocation': imp.get('schemaLocation', '')
            })
        return imports
    
    def extract_includes(self, root):
        """Extract include statements from XSD"""
        includes = []
        for inc in root.findall('.//{http://www.w3.org/2001/XMLSchema}include'):
            includes.append({
                'schemaLocation': inc.get('schemaLocation', '')
            })
        return includes
    
    def finalize_summary(self):
        """Finalize the summary statistics"""
        self.inventory['summary']['total_wsdl'] = len(self.inventory['wsdl_files'])
        self.inventory['summary']['total_xsd'] = len(self.inventory['xsd_files'])
        self.inventory['summary']['total_bpel'] = len(self.inventory['bpel_files'])
        self.inventory['summary']['namespaces'] = list(self.inventory['summary']['namespaces'])
    
    def generate_json(self):
        """Generate the JSON inventory file"""
        self.finalize_summary()
        
        json_file = self.contracts_dir / "inventory.json"
        with open(json_file, 'w', encoding='utf-8') as f:
            json.dump(self.inventory, f, indent=2, ensure_ascii=False)
        
        print(f"✓ Generated {json_file}")
        return json_file
    
    def generate_markdown(self):
        """Generate the Markdown inventory file"""
        md_file = self.contracts_dir / "inventory.md"
        
        with open(md_file, 'w', encoding='utf-8') as f:
            f.write("# Contract Inventory Report\n\n")
            f.write(f"**Generated:** {self.inventory['metadata']['generated_at']}\n")
            f.write(f"**Project Root:** {self.inventory['metadata']['project_root']}\n")
            f.write(f"**Scan Directory:** {self.inventory['metadata']['scan_directory']}\n\n")
            
            # Summary
            f.write("## Summary\n\n")
            f.write(f"- **WSDL Files:** {self.inventory['summary']['total_wsdl']}\n")
            f.write(f"- **XSD Files:** {self.inventory['summary']['total_xsd']}\n")
            f.write(f"- **BPEL Files:** {self.inventory['summary']['total_bpel']}\n")
            f.write(f"- **Total Files:** {sum([self.inventory['summary']['total_wsdl'], self.inventory['summary']['total_xsd'], self.inventory['summary']['total_bpel']])}\n\n")
            
            # Namespaces
            f.write("## Namespaces\n\n")
            for ns in sorted(self.inventory['summary']['namespaces']):
                if ns:
                    f.write(f"- `{ns}`\n")
            f.write("\n")
            
            # WSDL Files
            if self.inventory['wsdl_files']:
                f.write("## WSDL Files\n\n")
                for wsdl in self.inventory['wsdl_files']:
                    f.write(f"### {Path(wsdl['file_path']).name}\n\n")
                    f.write(f"- **Path:** `{wsdl['file_path']}`\n")
                    f.write(f"- **Target Namespace:** `{wsdl['target_namespace']}`\n")
                    f.write(f"- **Services:** {', '.join(wsdl['services']) if wsdl['services'] else 'None'}\n")
                    f.write(f"- **Operations:** {', '.join(wsdl['operations']) if wsdl['operations'] else 'None'}\n")
                    if wsdl['bindings']:
                        f.write("- **Bindings:**\n")
                        for binding in wsdl['bindings']:
                            f.write(f"  - {binding['name']} (type: {binding['type']})\n")
                    f.write(f"- **File Size:** {wsdl['file_size']} bytes\n\n")
            
            # Key XSD Files (SimpleFault and main schemas)
            f.write("## Key XSD Files\n\n")
            key_xsds = [xsd for xsd in self.inventory['xsd_files'] 
                       if 'SimpleFault' in xsd['file_path'] or 'LocationRequest' in xsd['file_path'] 
                       or 'LocationListReply' in xsd['file_path'] or 'GetLocationListRequest' in xsd['file_path']]
            
            for xsd in key_xsds:
                f.write(f"### {Path(xsd['file_path']).name}\n\n")
                f.write(f"- **Path:** `{xsd['file_path']}`\n")
                f.write(f"- **Target Namespace:** `{xsd['target_namespace']}`\n")
                if xsd['elements']:
                    f.write("- **Elements:**\n")
                    for elem in xsd['elements'][:5]:  # Show first 5
                        f.write(f"  - {elem['name']} ({elem['type']})\n")
                    if len(xsd['elements']) > 5:
                        f.write(f"  - ... and {len(xsd['elements']) - 5} more\n")
                f.write(f"- **Complex Types:** {len(xsd['complex_types'])}\n")
                f.write(f"- **File Size:** {xsd['file_size']} bytes\n\n")
            
            # BPEL Files
            if self.inventory['bpel_files']:
                f.write("## BPEL Files\n\n")
                for bpel in self.inventory['bpel_files']:
                    f.write(f"### {Path(bpel['file_path']).name}\n\n")
                    f.write(f"- **Path:** `{bpel['file_path']}`\n")
                    f.write(f"- **Process Name:** `{bpel['process_name']}`\n")
                    f.write(f"- **Target Namespace:** `{bpel['target_namespace']}`\n")
                    if bpel['partner_links']:
                        f.write("- **Partner Links:**\n")
                        for pl in bpel['partner_links']:
                            f.write(f"  - {pl['name']} (type: {pl['type']})\n")
                    f.write(f"- **Variables:** {len(bpel['variables'])}\n")
                    f.write(f"- **Imports:** {len(bpel['imports'])}\n")
                    f.write(f"- **File Size:** {bpel['file_size']} bytes\n\n")
        
        print(f"✓ Generated {md_file}")
        return md_file

def main():
    project_root = os.getcwd()
    generator = ContractInventoryGenerator(project_root)
    
    print("=== Contract Inventory Generator ===")
    print(f"Project Root: {project_root}")
    
    # Scan all files
    generator.scan_files()
    
    # Generate outputs
    json_file = generator.generate_json()
    md_file = generator.generate_markdown()
    
    print("\n=== Generation Complete ===")
    print(f"JSON Inventory: {json_file}")
    print(f"Markdown Report: {md_file}")
    print(f"Total Files Processed: {generator.inventory['summary']['total_wsdl'] + generator.inventory['summary']['total_xsd'] + generator.inventory['summary']['total_bpel']}")

if __name__ == "__main__":
    main()