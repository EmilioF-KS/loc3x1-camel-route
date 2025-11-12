import os
import sys
import argparse
import xml.etree.ElementTree as ET


def _local_name(tag: str) -> str:
    if '}' in tag:
        return tag.split('}', 1)[1]
    return tag


def parse_moves_from_ibm_map(xml_text: str):
    root = ET.fromstring(xml_text)
    moves = []
    # Find all map:move elements regardless of prefix
    for move in root.iter():
        if _local_name(move.tag) != 'move':
            continue
        input_node = None
        output_node = None
        for child in list(move):
            ln = _local_name(child.tag)
            if ln == 'input':
                input_node = child
            elif ln == 'output':
                output_node = child
        if input_node is None or output_node is None:
            continue
        in_prop = input_node.attrib.get('property')
        out_prop = output_node.attrib.get('property')
        in_var = input_node.attrib.get('businessObjectVariableRef')
        out_var = output_node.attrib.get('businessObjectVariableRef')
        if not in_prop or not out_prop:
            continue
        moves.append({
            'input_property': in_prop,
            'output_property': out_prop,
            'input_var': in_var,
            'output_var': out_var,
        })
    return moves


def parse_submaps_from_ibm_map(xml_text: str):
    root = ET.fromstring(xml_text)
    subs = []
    for propmap in root.iter():
        if _local_name(propmap.tag) != 'propertyMap':
            continue
        # Find submap children inside propertyMap
        for child in list(propmap):
            if _local_name(child.tag) != 'submap':
                continue
            input_node = None
            output_node = None
            for grand in list(child):
                ln = _local_name(grand.tag)
                if ln == 'input':
                    input_node = grand
                elif ln == 'output':
                    output_node = grand
            if input_node is None or output_node is None:
                continue
            in_prop = input_node.attrib.get('property')
            out_prop = output_node.attrib.get('property')
            in_var = input_node.attrib.get('businessObjectVariableRef')
            out_var = output_node.attrib.get('businessObjectVariableRef')
            subs.append({
                'input_property': in_prop,
                'output_property': out_prop,
                'input_var': in_var,
                'output_var': out_var,
            })
    return subs


def _emit_nested_elements_for_path(parts, select_xpath):
    # Build nested xsl:element structure with value-of at the deepest node
    lines = []
    indent = ''
    for i, part in enumerate(parts):
        is_last = i == len(parts) - 1
        lines.append(f"{indent}<xsl:element name=\"{part}\">")
        indent += '  '
        if is_last:
            lines.append(f"{indent}<xsl:value-of select=\"{select_xpath}\"/>")
    # Close tags
    for i in range(len(parts) - 1, -1, -1):
        indent = '  ' * i
        lines.append(f"{indent}</xsl:element>")
    return '\n'.join(lines)


def generate_xslt_from_moves(moves, submaps=None):
    submaps = submaps or []
    if not moves and not submaps:
        return (
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
            "<xsl:stylesheet version=\"1.0\" xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\">\n"
            "  <xsl:template match=\"/\">\n"
            "    <Mapped/>\n"
            "  </xsl:template>\n"
            "</xsl:stylesheet>\n"
        )

    # Derive root element from first output_property (e.g., "GetLocationList3X1B/..." -> root "GetLocationList3X1B")
    if moves:
        first_out = moves[0]['output_property']
        root_name = first_out.split('/')[0]
    else:
        # Derive root from first submap's output property
        first_out = submaps[0]['output_property']
        root_name = first_out.split('/')[0]

    # Build a tree of output paths to avoid emitting duplicate siblings
    def new_node():
        return {'children': {}, 'values': {}, 'copy_of': {}}

    tree = new_node()

    for mv in moves:
        out_parts = mv['output_property'].split('/')
        tail = out_parts[1:] if len(out_parts) > 1 else []
        select_xpath = mv['input_property']
        node = tree
        for part in tail[:-1]:
            node['children'].setdefault(part, new_node())
            node = node['children'][part]
        if tail:
            leaf = tail[-1]
            node['values'].setdefault(leaf, [])
            node['values'][leaf].append(select_xpath)
        else:
            # Direct mapping to root value
            node['values'].setdefault('', [])
            node['values'][''].append(select_xpath)

    for sm in submaps:
        out_parts = sm['output_property'].split('/')
        tail = out_parts[1:] if len(out_parts) > 1 else []
        select_xpath = sm['input_property']
        node = tree
        for part in tail[:-1]:
            node['children'].setdefault(part, new_node())
            node = node['children'][part]
        if tail:
            leaf = tail[-1]
            node['copy_of'].setdefault(leaf, [])
            node['copy_of'][leaf].append(select_xpath)
        else:
            node['copy_of'].setdefault('', [])
            node['copy_of'][''].append(select_xpath)

    lines = []
    lines.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")
    lines.append("<xsl:stylesheet version=\"1.0\" xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\">")
    lines.append("  <xsl:output method=\"xml\" indent=\"yes\"/>")
    lines.append("  <xsl:strip-space elements=\"*\"/>")
    lines.append("  <xsl:template match=\"/\">")
    lines.append(f"    <xsl:element name=\"{root_name}\">")

    def render(node, indent='      '):
        # Render child containers
        for child_name, child_node in node['children'].items():
            lines.append(f"{indent}<xsl:element name=\"{child_name}\">")
            render(child_node, indent + '  ')
            lines.append(f"{indent}</xsl:element>")
        # Render value leaves
        for leaf_name, selects in node['values'].items():
            if leaf_name:
                lines.append(f"{indent}<xsl:element name=\"{leaf_name}\">")
                for sel in selects:
                    lines.append(f"{indent}  <xsl:value-of select=\"{sel}\"/>")
                lines.append(f"{indent}</xsl:element>")
            else:
                for sel in selects:
                    lines.append(f"{indent}<xsl:value-of select=\"{sel}\"/>")
        # Render copy-of leaves
        for leaf_name, selects in node['copy_of'].items():
            if leaf_name:
                lines.append(f"{indent}<xsl:element name=\"{leaf_name}\">")
                for sel in selects:
                    lines.append(f"{indent}  <xsl:copy-of select=\"{sel}\"/>")
                lines.append(f"{indent}</xsl:element>")
            else:
                for sel in selects:
                    lines.append(f"{indent}<xsl:copy-of select=\"{sel}\"/>")

    render(tree)

    lines.append("    </xsl:element>")
    lines.append("  </xsl:template>")
    lines.append("</xsl:stylesheet>")
    return '\n'.join(lines) + '\n'


def convert_file(input_path: str, output_dir: str) -> str:
    with open(input_path, 'r', encoding='utf-8') as f:
        xml_text = f.read()
    moves = parse_moves_from_ibm_map(xml_text)
    submaps = parse_submaps_from_ibm_map(xml_text)
    xslt_text = generate_xslt_from_moves(moves, submaps)
    base = os.path.splitext(os.path.basename(input_path))[0]
    os.makedirs(output_dir, exist_ok=True)
    out_path = os.path.join(output_dir, base + '.xsl')
    with open(out_path, 'w', encoding='utf-8') as f:
        f.write(xslt_text)
    return out_path


def convert_dir(input_dir: str, output_dir: str):
    os.makedirs(output_dir, exist_ok=True)
    generated = []
    for name in os.listdir(input_dir):
        if not name.lower().endswith('.xml'):
            continue
        in_path = os.path.join(input_dir, name)
        out_path = convert_file(in_path, output_dir)
        generated.append(out_path)
    return generated


def main():
    parser = argparse.ArgumentParser(description='Convert IBM map:move operations to XSLT.')
    parser.add_argument('--input', help='Path to an IBM map XML file')
    parser.add_argument('--input-dir', help='Directory containing IBM map XML files')
    parser.add_argument('--output-dir', default='agent/output/xslt', help='Directory to write generated XSLT files')
    args = parser.parse_args()

    if args.input_dir:
        paths = convert_dir(args.input_dir, args.output_dir)
        for p in paths:
            print(f"Generated XSLT: {p}")
    if args.input:
        out_path = convert_file(args.input, args.output_dir)
        print(f"Generated XSLT: {out_path}")


if __name__ == '__main__':
    main()