import os
import json
import argparse
from agent.src.map_synthesis import synthesize_moves
from agent.src.map_move_to_xslt import generate_xslt_from_moves


def bulk_generate_loc3x1b(base_dir: str, output_dir: str, enable_heuristics: bool = False):
    os.makedirs(output_dir, exist_ok=True)
    generated = []
    report = {'profile': 'LOC3X1B', 'items': []}

    # Request: GetLocationListRequest
    req_xsd = os.path.join(base_dir, 'GetLocationListRequest.xsd')
    from agent.src.map_synthesis import synthesize_mapping_details
    req_moves, req_details = synthesize_mapping_details(
        req_xsd, 'GetLocationListRequest', req_xsd, 'GetLocationListRequest',
        'GetLocationList/GetLocationListRequest', 'GetLocationList3X1B/GetLocationListRequest',
        enable_heuristics=enable_heuristics
    )
    req_xslt = generate_xslt_from_moves(req_moves)
    req_out = os.path.join(output_dir, 'GetLocationListRequest_from_GetLocationListRequest.xsl')
    with open(req_out, 'w', encoding='utf-8') as f:
        f.write(req_xslt)
    generated.append(req_out)
    report['items'].append({'name': 'GetLocationListRequest', 'details': req_details})

    # Reply: LocationListReply
    rep_xsd = os.path.join(base_dir, 'LocationListReply.xsd')
    rep_moves, rep_details = synthesize_mapping_details(
        rep_xsd, 'LocationListReply', rep_xsd, 'LocationListReply',
        'GetLocationList/LocationListReply', 'GetLocationList3X1BResponse/GetLocationListReply',
        enable_heuristics=enable_heuristics
    )
    rep_xslt = generate_xslt_from_moves(rep_moves)
    rep_out = os.path.join(output_dir, 'LocationListReply_from_LocationListReply.xsl')
    with open(rep_out, 'w', encoding='utf-8') as f:
        f.write(rep_xslt)
    generated.append(rep_out)
    report['items'].append({'name': 'LocationListReply', 'details': rep_details})

    # Write coverage report when heuristics enabled (AGENT-024)
    if enable_heuristics:
        artifact = os.path.join(output_dir, '..', 'synthesized_maps.json')
        try:
            with open(artifact, 'w', encoding='utf-8') as f:
                json.dump(report, f, indent=2)
        except Exception:
            pass

    return generated


def bulk_generate_loc3x1m(base_dir: str, output_dir: str, enable_heuristics: bool = False):
    os.makedirs(output_dir, exist_ok=True)
    generated = []
    report = {'profile': 'LOC3X1M', 'items': []}

    # Request: GetLocationListRequest
    req_xsd = os.path.join(base_dir, 'GetLocationListRequest.xsd')
    from agent.src.map_synthesis import synthesize_mapping_details
    req_moves, req_details = synthesize_mapping_details(
        req_xsd, 'GetLocationListRequest', req_xsd, 'GetLocationListRequest',
        'GetLocationList/GetLocationListRequest', 'GetLocationList3X1M/GetLocationListRequest',
        enable_heuristics=enable_heuristics
    )
    req_xslt = generate_xslt_from_moves(req_moves)
    req_out = os.path.join(output_dir, 'GetLocationListRequest_from_GetLocationListRequest_LOC3X1M.xsl')
    with open(req_out, 'w', encoding='utf-8') as f:
        f.write(req_xslt)
    generated.append(req_out)
    report['items'].append({'name': 'GetLocationListRequest', 'details': req_details})

    # Reply: LocationListReply
    rep_xsd = os.path.join(base_dir, 'LocationListReply.xsd')
    rep_moves, rep_details = synthesize_mapping_details(
        rep_xsd, 'LocationListReply', rep_xsd, 'LocationListReply',
        'GetLocationList/LocationListReply', 'GetLocationList3X1MResponse/GetLocationListReply',
        enable_heuristics=enable_heuristics
    )
    rep_xslt = generate_xslt_from_moves(rep_moves)
    rep_out = os.path.join(output_dir, 'LocationListReply_from_LocationListReply_LOC3X1M.xsl')
    with open(rep_out, 'w', encoding='utf-8') as f:
        f.write(rep_xslt)
    generated.append(rep_out)
    report['items'].append({'name': 'LocationListReply', 'details': rep_details})

    if enable_heuristics:
        artifact = os.path.join(output_dir, '..', 'synthesized_maps.json')
        try:
            with open(artifact, 'w', encoding='utf-8') as f:
                json.dump(report, f, indent=2)
        except Exception:
            pass

    return generated


def main():
    parser = argparse.ArgumentParser(description='Bulk synthesize XSLTs for LOC3X1B/LOC3X1M request/reply from Dependencies XSDs.')
    parser.add_argument('--profile', choices=['LOC3X1B', 'LOC3X1M'], default='LOC3X1B', help='Dependency profile to generate for')
    parser.add_argument('--base-dir', help='Base directory for XSDs (defaults per profile)')
    parser.add_argument('--output-dir', default='agent/output/xslt', help='Output directory for generated XSLTs')
    parser.add_argument('--enable-heuristics', action='store_true', help='Enable heuristics for non-identical field matches (AGENT-024)')
    args = parser.parse_args()

    base_dir = args.base_dir or (
        'CHUBB/Dependencies/LocationRetrievalLOC3X1B' if args.profile == 'LOC3X1B'
        else 'CHUBB/Dependencies/LocationRetrievalLOC3X1M'
    )

    if args.profile == 'LOC3X1B':
        generated = bulk_generate_loc3x1b(base_dir, args.output_dir, enable_heuristics=args.enable_heuristics)
    else:
        generated = bulk_generate_loc3x1m(base_dir, args.output_dir, enable_heuristics=args.enable_heuristics)

    for path in generated:
        print(f'Generated: {path}')


if __name__ == '__main__':
    main()