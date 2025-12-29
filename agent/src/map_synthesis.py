import os
import argparse
from lxml import etree
from typing import Dict, List, Tuple


def _ns(tag):
    return tag.split('}', 1)[-1] if '}' in tag else tag


def load_xsd(path):
    with open(path, 'rb') as f:
        return etree.parse(f)


def _find_global_element(xsd_doc: etree._ElementTree, name: str):
    for el in xsd_doc.iterfind('.//{http://www.w3.org/2001/XMLSchema}element'):
        if el.get('name') == name:
            return el
    return None


def collect_child_element_names(element_def: etree._Element):
    names = []
    # Walk sequences/choices one level deep (sufficient for LOC3X1B request)
    for seq in element_def.iterfind('.//{http://www.w3.org/2001/XMLSchema}sequence'):
        for child in seq.iterfind('{http://www.w3.org/2001/XMLSchema}element'):
            nm = child.get('name')
            if nm:
                names.append(nm)
    for choice in element_def.iterfind('.//{http://www.w3.org/2001/XMLSchema}choice'):
        for child in choice.iterfind('{http://www.w3.org/2001/XMLSchema}element'):
            nm = child.get('name')
            if nm:
                names.append(nm)
    return list(dict.fromkeys(names))  # dedupe preserving order


def _resolve_element_children(xsd_doc: etree._ElementTree, element_name: str):
    el = _find_global_element(xsd_doc, element_name)
    if el is None:
        # Some schemas define only complexType with the name
        for ct in xsd_doc.iterfind('.//{http://www.w3.org/2001/XMLSchema}complexType'):
            if ct.get('name') == element_name:
                return collect_child_element_names(ct)
        return []
    # Inline complexType
    inline_ct = el.find('.//{http://www.w3.org/2001/XMLSchema}complexType')
    if inline_ct is not None:
        return collect_child_element_names(inline_ct)
    # Referenced complexType
    tname = el.get('type')
    if tname is not None:
        # Strip namespace prefix if present
        tlocal = tname.split(':')[-1]
        for ct in xsd_doc.iterfind('.//{http://www.w3.org/2001/XMLSchema}complexType'):
            if ct.get('name') == tlocal:
                return collect_child_element_names(ct)
    # Fallback: try element directly
    return collect_child_element_names(el)


def synthesize_moves(src_xsd_path: str, src_root_element: str, dst_xsd_path: str, dst_root_element: str,
                     src_prefix: str, dst_prefix: str, enable_heuristics: bool = False, heuristic_threshold: float = 0.92):
    src_xsd = load_xsd(src_xsd_path)
    dst_xsd = load_xsd(dst_xsd_path)
    src_fields_list = _resolve_element_children(src_xsd, src_root_element)
    dst_fields_list = _resolve_element_children(dst_xsd, dst_root_element)
    src_fields = set(src_fields_list)
    # Preserve destination sequence order to satisfy XSD validation
    dest_order = {f: i for i, f in enumerate(dst_fields_list)}
    mapped = {}
    # Deterministic identical-name mappings
    for f in dst_fields_list:
        if f in src_fields:
            mapped[f] = f

    # Heuristic suggestions for near matches
    if enable_heuristics:
        try:
            from agent.src.map_heuristics import suggest_matches
            suggestions = suggest_matches(src_fields_list, dst_fields_list, threshold=heuristic_threshold)
            # choose best suggestion per destination not already mapped
            best_by_dest = {}
            for s, d, ratio in suggestions:
                if d in mapped:
                    continue
                prev = best_by_dest.get(d)
                if prev is None or ratio > prev[2]:
                    best_by_dest[d] = (s, d, ratio)
            for d, (s, _, _) in best_by_dest.items():
                if s in src_fields:
                    mapped[d] = s
        except Exception:
            # Fallback silently if heuristics fail
            pass

    # Build moves sorted by destination order
    moves = []
    for d in sorted(mapped.keys(), key=lambda x: dest_order.get(x, 9999)):
        s = mapped[d]
        moves.append({
            'input_property': f'{src_prefix}/{s}',
            'output_property': f'{dst_prefix}/{d}',
            'input_var': 'In',
            'output_var': 'Out',
        })
    return moves


def synthesize_mapping_details(src_xsd_path: str, src_root_element: str, dst_xsd_path: str, dst_root_element: str,
                               src_prefix: str, dst_prefix: str, enable_heuristics: bool = False, heuristic_threshold: float = 0.92):
    src_xsd = load_xsd(src_xsd_path)
    dst_xsd = load_xsd(dst_xsd_path)
    src_fields_list = _resolve_element_children(src_xsd, src_root_element)
    dst_fields_list = _resolve_element_children(dst_xsd, dst_root_element)
    src_fields = set(src_fields_list)
    dest_order = {f: i for i, f in enumerate(dst_fields_list)}

    mapped: Dict[str, Tuple[str, str, float]] = {}
    # identical-name mappings
    for f in dst_fields_list:
        if f in src_fields:
            mapped[f] = (f, 'identical', 1.0)

    # heuristic suggestions
    if enable_heuristics:
        try:
            from agent.src.map_heuristics import suggest_matches
            suggestions = suggest_matches(src_fields_list, dst_fields_list, threshold=heuristic_threshold)
            best_by_dest: Dict[str, Tuple[str, float]] = {}
            for s, d, ratio in suggestions:
                if d in mapped:
                    continue
                prev = best_by_dest.get(d)
                if prev is None or ratio > prev[1]:
                    best_by_dest[d] = (s, ratio)
            for d, (s, ratio) in best_by_dest.items():
                if s in src_fields:
                    mapped[d] = (s, 'heuristic', ratio)
        except Exception:
            pass

    moves = []
    details_mapped = []
    for d in sorted(mapped.keys(), key=lambda x: dest_order.get(x, 9999)):
        s, kind, conf = mapped[d]
        moves.append({
            'input_property': f'{src_prefix}/{s}',
            'output_property': f'{dst_prefix}/{d}',
            'input_var': 'In',
            'output_var': 'Out',
        })
        details_mapped.append({'dst': d, 'src': s, 'kind': kind, 'confidence': conf})

    unmapped_dst = [d for d in dst_fields_list if d not in mapped]
    coverage = {
        'total_dst': len(dst_fields_list),
        'mapped': len(details_mapped),
        'ratio': (len(details_mapped) / len(dst_fields_list)) if dst_fields_list else 1.0,
    }

    details = {
        'mapped': details_mapped,
        'unmapped_dst': unmapped_dst,
        'coverage': coverage,
    }
    return moves, details


def main():
    parser = argparse.ArgumentParser(description='Synthesize map moves from source/destination XSD element structures.')
    parser.add_argument('--src-xsd', required=True, help='Path to source XSD')
    parser.add_argument('--src-root', required=True, help='Source global element name')
    parser.add_argument('--dst-xsd', required=True, help='Path to destination XSD')
    parser.add_argument('--dst-root', required=True, help='Destination global element name')
    parser.add_argument('--src-prefix', required=True, help='Source XML prefix path (e.g., GetLocationList/GetLocationListRequest)')
    parser.add_argument('--dst-prefix', required=True, help='Destination XML prefix path (e.g., GetLocationList3X1B/GetLocationListRequest)')
    parser.add_argument('--emit-xslt', action='store_true', help='Generate XSLT using map_move_to_xslt and write to output-dir')
    parser.add_argument('--output-dir', default='agent/output/xslt', help='Output directory for XSLT')
    args = parser.parse_args()

    moves = synthesize_moves(args.src_xsd, args.src_root, args.dst_xsd, args.dst_root, args.src_prefix, args.dst_prefix)
    print(f'Synthesized {len(moves)} moves')
    if args.emit_xslt:
        from agent.src.map_move_to_xslt import generate_xslt_from_moves
        xslt_text = generate_xslt_from_moves(moves)
        os.makedirs(args.output_dir, exist_ok=True)
        base = f'{args.dst_root}_from_{args.src_root}.xsl'
        out_path = os.path.join(args.output_dir, base)
        with open(out_path, 'w', encoding='utf-8') as f:
            f.write(xslt_text)
        print(f'Generated XSLT: {out_path}')


if __name__ == '__main__':
    main()