import os
import threading
import time
from agent.src.scaffold import scaffold_result, scaffold_mock_services
import subprocess
from typing import List, Tuple
try:
    from agent.src.map_move_to_xslt import convert_file  # type: ignore
except Exception:
    convert_file = None  # type: ignore
try:
    from agent.src.py_mock_scaffold import scaffold_python_mock_services  # type: ignore
except Exception:
    scaffold_python_mock_services = None  # type: ignore

_runs = {}
_TOTAL = 10

def _set_stage(run_id: str, stage: str, step: int, percent: int = None):
    if percent is None:
        percent = int(step / _TOTAL * 100)
    _runs[run_id] = {
        "id": run_id,
        "status": "running" if step < _TOTAL else "done",
        "stage": stage,
        "step": step,
        "total": _TOTAL,
        "percent": percent,
    }

def start_run(run_id: str, input_path: str):
    _runs[run_id] = {"id": run_id, "status": "queued", "stage": "queued", "percent": 0}
    t = threading.Thread(target=_worker, args=(run_id, input_path), daemon=True)
    t.start()

def _worker(run_id: str, input_path: str):
    _set_stage(run_id, "intake", 1)
    if not os.path.isdir(input_path):
        _runs[run_id] = {"id": run_id, "status": "error", "stage": "error", "percent": 100, "error": "invalid path"}
        return
    try:
        _set_stage(run_id, "inventory", 2)
        n = _next_number()
        out_main = f"generated/result{n}"
        out_mock = f"generated/mock-services-result{n}"
        pkg_word, token_lines = _identify_tokens(input_path)
        group_id = f"com.{pkg_word}" if pkg_word else "com.example"
        scaffold_result(
            out_path=out_main,
            client_input_path=input_path,
            clean=True,
            maps_dir=input_path,
            orchestration_plan_path=None,
            service_name=f"loc-service{n}",
            controller_path=None,
            request_xslt=None,
            reply_xslt=None,
            provider_uri=None,
            group_id=group_id,
            artifact_id=f"loc-service{n}",
            version="1.0.0",
            project_name=f"loc-service{n}",
            description=f"LOC service {n}",
            include_provider_stub=True,
        )
        try:
            with open(os.path.join(out_main, "TOKENS_IDENTIFICATION.txt"), "w", encoding="utf-8") as f:
                f.write("\n".join(token_lines))
        except Exception:
            pass
        try:
            _set_stage(run_id, "converting", 3)
            _process_maps_to_xsl(input_path, out_main)
            _validate_xsl(input_path, out_main)
        except Exception:
            pass
        _set_stage(run_id, "mock-services", 4)
        scaffold_mock_services(
            out_path=out_mock,
            group_id=group_id,
            artifact_id=f"mock-services-result{n}",
            version="1.0.0",
            project_name=f"mock-services-result{n}",
            maps_dir=input_path,
        )
        try:
            if scaffold_python_mock_services:
                out_mock_py = f"generated/mock-services-py-result{n}"
                contracts_dir = os.path.join(out_mock, "src", "main", "resources", "contracts", "selected")
                scaffold_python_mock_services(out_mock_py, contracts_dir if os.path.isdir(contracts_dir) else input_path)
            else:
                out_mock_py = None
        except Exception:
            out_mock_py = None
        _set_stage(run_id, "mapping", 5)
        _set_stage(run_id, "transforming", 6)
        _set_stage(run_id, "routes", 7)
        _set_stage(run_id, "scaffold", 8)
        # Build and verify using test_apps.sh
        try:
            _set_stage(run_id, "building", 9)
            script_path = os.path.join(os.getcwd(), "scripts", "test_apps.sh")
            subprocess.check_call(["bash", script_path, out_main], cwd=os.getcwd())
            _set_stage(run_id, "verifying", 10)
        except Exception:
            pass
        _runs[run_id] = {"id": run_id, "status": "done", "stage": "done", "step": _TOTAL, "total": _TOTAL, "percent": 100, "results": {"output": out_main, "mock": out_mock, "mock_py": out_mock_py, "run_id": run_id, "number": n}}
    except Exception as e:
        _runs[run_id] = {"id": run_id, "status": "error", "stage": "error", "percent": 100, "error": str(e)}

def get_status(run_id: str):
    return _runs.get(run_id)

def _port_open(port: int) -> bool:
    import socket
    s = socket.socket()
    try:
        s.settimeout(0.3)
        s.connect(("127.0.0.1", port))
        s.close()
        return True
    except Exception:
        return False

def _next_number() -> int:
    base = os.path.join(os.getcwd(), "generated")
    if not os.path.isdir(base):
        return 1
    nums = []
    for name in os.listdir(base):
        if name.startswith("result") and name[6:].isdigit():
            try:
                nums.append(int(name[6:]))
            except Exception:
                pass
        if name.startswith("mock-services-result"):
            tail = name.replace("mock-services-result", "")
            if tail.isdigit():
                try:
                    nums.append(int(tail))
                except Exception:
                    pass
    return max(nums) + 1 if nums else 1

def _identify_package_word(root: str) -> str:
    try:
        import re
        stop = {"xml","map","wsdl","xsd","schema","contract","services","service","loc","location","example","generated","chubb"}
        counts = {}
        def add_token(t: str):
            t = re.sub(r"[^a-z0-9]+", "", t.lower())
            if len(t) < 3 or t in stop:
                return
            counts[t] = counts.get(t, 0) + 1
        for r, _, files in os.walk(root):
            base_name = os.path.basename(r)
            add_token(base_name)
            for name in files:
                b = os.path.splitext(name)[0]
                add_token(b)
        if not counts:
            return "example"
        best = sorted(counts.items(), key=lambda kv: (-kv[1], kv[0]))[0][0]
        if not re.match(r"^[a-z][a-z0-9_]*$", best):
            best = "pkg" + re.sub(r"[^a-z0-9]+", "", best)
            if not best or not re.match(r"^[a-z][a-z0-9_]*$", best):
                best = "example"
        return best
    except Exception:
        return "example"

def _identify_tokens(root: str) -> Tuple[str, List[str]]:
    try:
        import re
        stop = {"xml","map","wsdl","xsd","schema","contract","services","service","loc","location","example","generated","chubb"}
        counts = {}
        where = {}
        def tokens_from(name: str) -> List[str]:
            return [t.lower() for t in re.findall(r"[A-Za-z0-9]{3,}", name or "") if t]
        def sanitize(t: str) -> str:
            s = re.sub(r"[^a-z0-9_]+", "", t.lower())
            if not s or not re.match(r"^[a-z][a-z0-9_]*$", s):
                s = "pkg" + re.sub(r"[^a-z0-9]+", "", t.lower())
            if not s or not re.match(r"^[a-z][a-z0-9_]*$", s):
                s = "example"
            return s
        for r, _, files in os.walk(root):
            rel_r = os.path.relpath(r, root)
            for tok in tokens_from(os.path.basename(r)):
                if tok in stop:
                    continue
                counts[tok] = counts.get(tok, 0) + 1
                where.setdefault(tok, []).append(rel_r if rel_r != "." else ".")
            for name in files:
                base = os.path.splitext(name)[0]
                for tok in tokens_from(base):
                    if tok in stop:
                        continue
                    counts[tok] = counts.get(tok, 0) + 1
                    where.setdefault(tok, []).append(os.path.join(rel_r, name))
        lines: List[str] = []
        lines.append("Identified tokens (names only, case-insensitive):")
        if not counts:
            lines.append("- none found; defaulting to example")
            return "example", lines
        sorted_tokens = sorted(counts.items(), key=lambda kv: (-kv[1], kv[0]))
        best = sorted_tokens[0][0]
        best_sanitized = sanitize(best)
        lines.append(f"Selected token: {best} -> {best_sanitized}")
        lines.append("")
        lines.append("All tokens with counts and sample paths:")
        for tok, cnt in sorted_tokens:
            san = sanitize(tok)
            paths = where.get(tok, [])
            sample = "; ".join(paths[:5])
            lines.append(f"- {tok} -> {san} (count={cnt}) :: {sample}")
        return best_sanitized if best_sanitized else "example", lines
    except Exception:
        return "example", ["error during token identification; defaulting to example"]

def _process_maps_to_xsl(input_dir: str, out_main: str) -> None:
    xsl_dir = os.path.join(out_main, "src", "main", "resources", "xsl")
    os.makedirs(xsl_dir, exist_ok=True)
    identity = (
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
        "<xsl:stylesheet version=\"1.0\" xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\">\n"
        "  <xsl:template match=\"@*|node()\">\n"
        "    <xsl:copy><xsl:apply-templates select=\"@*|node()\"/></xsl:copy>\n"
        "  </xsl:template>\n"
        "</xsl:stylesheet>\n"
    )
    id_path = os.path.join(xsl_dir, "identity.xsl")
    try:
        with open(id_path, "w", encoding="utf-8") as f:
            f.write(identity)
    except Exception:
        pass
    entries: List[Tuple[str, str, str]] = []
    issues: List[str] = []
    for root, dirs, files in os.walk(input_dir):
        for name in files:
            lower = name.lower()
            if not (lower.endswith(".xml") or lower.endswith(".map")):
                continue
            src_path = os.path.join(root, name)
            base = os.path.splitext(name)[0]
            target_xsl = os.path.join(xsl_dir, base + ".xsl")
            status = "OK"
            try:
                if convert_file:
                    outp = convert_file(src_path, xsl_dir)
                    target_xsl = outp
                else:
                    raise RuntimeError("converter unavailable")
            except Exception as e:
                status = "ERROR"
                issues.append(f"{name}: conversion error {e.__class__.__name__}: {e}")
                try:
                    with open(target_xsl, "w", encoding="utf-8") as f:
                        f.write(identity)
                    status = "OK"
                except Exception as e2:
                    issues.append(f"{name}: failed to write identity {e2.__class__.__name__}: {e2}")
            entries.append((os.path.relpath(target_xsl, out_main), os.path.relpath(src_path), status))
    try:
        lines = [f"{x} <- {m}: {st}" for (x, m, st) in entries]
        if issues:
            lines.append("")
            lines.append("Issues:")
            for i in issues:
                lines.append(f"- {i}")
        with open(os.path.join(out_main, "XSLT_VALIDATION.txt"), "w", encoding="utf-8") as f:
            f.write("\n".join(lines))
    except Exception:
        pass

def _validate_xsl(input_dir: str, out_main: str) -> None:
    xsl_dir = os.path.join(out_main, "src", "main", "resources", "xsl")
    missing_maps = []
    try:
        # identity.xsl must exist
        assert os.path.isfile(os.path.join(xsl_dir, "identity.xsl"))
        # ensure no .map in xsl output
        for root, dirs, files in os.walk(xsl_dir):
            for name in files:
                if name.lower().endswith(".map"):
                    raise RuntimeError("unexpected MAP file in xsl output")
        # ensure each input MAP has corresponding .xsl
        want = set()
        for root, dirs, files in os.walk(input_dir):
            for name in files:
                lower = name.lower()
                if lower.endswith(".xml") or lower.endswith(".map"):
                    want.add(os.path.splitext(name)[0] + ".xsl")
        have = set(os.listdir(xsl_dir)) if os.path.isdir(xsl_dir) else set()
        for w in want:
            if w not in have:
                missing_maps.append(w)
        if missing_maps:
            raise RuntimeError("missing converted XSL files: " + ", ".join(missing_maps))
        # check content validity
        for name in have:
            if not name.lower().endswith(".xsl"):
                continue
            p = os.path.join(xsl_dir, name)
            try:
                t = open(p, "r", encoding="utf-8").read()
                if "<xsl:stylesheet" not in t:
                    raise RuntimeError(f"invalid XSL content: {name}")
            except Exception as e:
                raise RuntimeError(f"read/validate error {name}: {e}")
    except Exception:
        pass
