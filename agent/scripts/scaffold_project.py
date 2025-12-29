#!/usr/bin/env python3
import argparse
import os
from agent.src.scaffold import scaffold_result


def main():
    parser = argparse.ArgumentParser(description="Scaffold a9-style Spring Boot + Camel project")
    parser.add_argument("--input", dest="client_input", required=False, default=None,
                        help="Optional: client IBM project root for dynamic synthesis (omit to generate neutral scaffold)")
    parser.add_argument("--maps-dir", dest="maps_dir", required=False, default=None,
                        help="Optional: directory containing IBM map XMLs to generate XSLTs")
    parser.add_argument("--orchestration", dest="orchestration_plan", required=False, default=None,
                        help="Optional: orchestration plan path to synthesize Camel routes (JSON/YAML)")
    parser.add_argument("--service-name", dest="service_name", required=False, default="loc-service",
                        help="Service name used in synthesized routes (if --orchestration provided)")
    parser.add_argument("--controller-path", dest="controller_path", required=False, default=None,
                        help="Optional: controller path for platform-http (e.g., loc/getLocationList)")
    parser.add_argument("--request-xslt", dest="request_xslt", required=False, default=None,
                        help="Optional: XSLT resourceUri for request transform (e.g., classpath:GetLocationListRequest.xsl)")
    parser.add_argument("--reply-xslt", dest="reply_xslt", required=False, default=None,
                        help="Optional: XSLT resourceUri for reply transform (e.g., classpath:LocationListReply.xsl)")
    parser.add_argument("--provider-uri", dest="provider_uri", required=False, default=None,
                        help="Optional: provider URI for outbound call (e.g., http://provider/locations)")
    parser.add_argument("--out", dest="out_path", required=False, default="generated/result",
                        help="Output directory for the scaffolded project")
    parser.add_argument("--group-id", dest="group_id", required=False, default="com.example",
                        help="Maven groupId for scaffolded project")
    parser.add_argument("--artifact-id", dest="artifact_id", required=False, default="a9-like-project",
                        help="Maven artifactId for scaffolded project")
    parser.add_argument("--version", dest="version", required=False, default="0.1.0",
                        help="Maven version for scaffolded project")
    parser.add_argument("--project-name", dest="project_name", required=False, default="a9-like-project",
                        help="Human-readable project name")
    parser.add_argument("--description", dest="description", required=False, default="a9-style Spring Boot + Camel YAML scaffold",
                        help="Project description")
    parser.add_argument("--no-provider-stub", dest="no_provider_stub", action="store_true",
                        help="Do not include provider fallback stub controller")
    parser.add_argument("--no-clean", dest="no_clean", action="store_true",
                        help="Do not clean the output directory before scaffolding")
    args = parser.parse_args()

    created = scaffold_result(
        args.out_path,
        client_input_path=args.client_input,
        clean=(not args.no_clean),
        maps_dir=args.maps_dir,
        orchestration_plan_path=args.orchestration_plan,
        service_name=args.service_name,
        controller_path=args.controller_path,
        request_xslt=args.request_xslt,
        reply_xslt=args.reply_xslt,
        provider_uri=args.provider_uri,
        group_id=args.group_id,
        artifact_id=args.artifact_id,
        version=args.version,
        project_name=args.project_name,
        description=args.description,
        include_provider_stub=(not args.no_provider_stub),
    )
    print(f"Scaffolded {len(created)} files into {args.out_path}")
    for p in created:
        print(f" - {p}")


if __name__ == "__main__":
    main()