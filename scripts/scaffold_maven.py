#!/usr/bin/env python3
"""
Maven Multi-Module Scaffold (LOC-004)

Verifies and patches the parent aggregator at modules/pom.xml to include all
existing child modules that have a pom.xml. Then runs a Maven build to validate.

Outputs:
- ci/artifacts/maven-build.log
- doc/.preflight/scaffold-report.md

Behavior:
- Detects submodules under modules/* that have pom.xml (one level deep), including
  clients/* submodules.
- Ensures <modules> section of parent includes discovered modules (adds missing ones).
- Leaves existing structure intact; adds only missing entries deterministically.
- Runs `mvn -B -q -DskipITs=false clean verify` to validate scaffold.
"""
from pathlib import Path
import sys
import subprocess
import xml.etree.ElementTree as ET
from datetime import datetime

PROJECT_ROOT = Path(__file__).resolve().parents[1]
MODULES_DIR = PROJECT_ROOT / "modules"
PARENT_POM = MODULES_DIR / "pom.xml"
CI_DIR = PROJECT_ROOT / "ci" / "artifacts"
REPORT_MD = PROJECT_ROOT / "doc" / ".preflight" / "scaffold-report.md"


def discover_modules() -> list[str]:
    mods: list[str] = []
    for child in MODULES_DIR.iterdir():
        if child.is_dir():
            pom = child / "pom.xml"
            if pom.exists():
                # Add top-level module
                mods.append(child.name)
            # Detect clients submodules
            if child.name == "clients":
                for sub in child.iterdir():
                    spom = sub / "pom.xml"
                    if sub.is_dir() and spom.exists():
                        mods.append(f"clients/{sub.name}")
    return sorted(mods)


def read_parent_modules() -> list[str]:
    if not PARENT_POM.exists():
        raise FileNotFoundError(f"Parent POM not found at {PARENT_POM}")
    tree = ET.parse(str(PARENT_POM))
    root = tree.getroot()
    ns = {"m": root.tag.split('}')[0].strip('{')} if '}' in root.tag else {}
    modules_el = root.find("m:modules", ns) if ns else root.find("modules")
    listed: list[str] = []
    if modules_el is not None:
        for m in list(modules_el):
            if m.tag.endswith('module') and m.text:
                listed.append(m.text.strip())
    return listed


def patch_parent_modules(discovered: list[str], listed: list[str]) -> bool:
    tree = ET.parse(str(PARENT_POM))
    root = tree.getroot()
    ns_uri = root.tag.split('}')[0].strip('{') if '}' in root.tag else None
    def q(tag: str):
        return f"{{{ns_uri}}}{tag}" if ns_uri else tag
    modules_el = root.find(q("modules"))
    changed = False
    if modules_el is None:
        modules_el = ET.SubElement(root, q("modules"))
        changed = True
    existing = {m.text.strip() for m in modules_el.findall(q("module")) if m.text}
    for m in discovered:
        if m not in existing:
            el = ET.SubElement(modules_el, q("module"))
            el.text = m
            changed = True
    if changed:
        # Pretty print minimal
        ET.indent(tree, space="  ", level=0)  # Python 3.9+
        tree.write(str(PARENT_POM), encoding="utf-8", xml_declaration=True)
    return changed


def run_maven() -> tuple[int, Path]:
    CI_DIR.mkdir(parents=True, exist_ok=True)
    log_path = CI_DIR / "maven-build.log"
    cmd = ["mvn", "-B", "-q", "-DskipITs=false", "clean", "verify"]
    with log_path.open("w", encoding="utf-8") as lf:
        proc = subprocess.run(cmd, cwd=str(MODULES_DIR), stdout=lf, stderr=subprocess.STDOUT)
        rc = proc.returncode
    return rc, log_path


def write_report(discovered: list[str], listed: list[str], changed: bool, mvn_rc: int, log_path: Path) -> Path:
    REPORT_MD.parent.mkdir(parents=True, exist_ok=True)
    lines: list[str] = []
    lines.append("# Maven Scaffold Report\n\n")
    lines.append(f"Generated: {datetime.now().isoformat()}\n\n")
    lines.append("## Modules (Discovered)\n")
    for m in discovered:
        lines.append(f"- {m}\n")
    lines.append("\n## Modules (Listed in Parent)\n")
    for m in listed:
        lines.append(f"- {m}\n")
    missing = [m for m in discovered if m not in listed]
    lines.append("\n## Actions\n")
    if missing:
        lines.append(f"- Added missing modules: {', '.join(missing)}\n")
    else:
        lines.append("- Parent POM already included all discovered modules\n")
    lines.append(f"- Maven build exit code: {mvn_rc}\n")
    lines.append(f"- Build log: `{log_path}`\n")
    REPORT_MD.write_text(''.join(lines), encoding='utf-8')
    return REPORT_MD


def main() -> dict:
    discovered = discover_modules()
    listed = read_parent_modules()
    changed = patch_parent_modules(discovered, listed)
    mvn_rc, log_path = run_maven()
    report_path = write_report(discovered, listed, changed, mvn_rc, log_path)
    return {
        "stage": "scaffold",
        "outputs": {
            "scaffold_report_md": str(report_path),
            "maven_build_log": str(log_path),
        },
        "changed_parent_pom": changed,
        "ok": mvn_rc == 0,
    }


if __name__ == "__main__":
    res = main()
    print(res)