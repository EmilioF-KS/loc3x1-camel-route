import os
import time
import xml.etree.ElementTree as ET
from typing import Dict, List, Optional, Tuple


def _write(path: str, text: str) -> None:
    os.makedirs(os.path.dirname(path), exist_ok=True)
    with open(path, "w", encoding="utf-8") as f:
        f.write(text)


def _sanitize_pkg(name: str) -> str:
    import re
    s = re.sub(r"[^A-Za-z0-9]", "", name or "svc").lower()
    return s or "svc"


def _sanitize_cls(name: str) -> str:
    import re
    s = re.sub(r"[^A-Za-z0-9_]", "", name or "Op")
    return (s[:1].upper() + s[1:]) if s else "Op"


def _discover_ops(contracts_root: str) -> List[Tuple[str, str, str, str]]:
    ops: List[Tuple[str, str, str, str]] = []
    ns = {"wsdl": "http://schemas.xmlsoap.org/wsdl/"}
    for root, dirs, files in os.walk(contracts_root):
        for name in files:
            if not name.lower().endswith(".wsdl"):
                continue
            p = os.path.join(root, name)
            try:
                tree = ET.parse(p)
                r = tree.getroot()
                tns = r.attrib.get("targetNamespace") or ""
                for pt in r.findall("wsdl:portType", ns):
                    svc = pt.attrib.get("name") or "Service"
                    for op in pt.findall("wsdl:operation", ns):
                        op_name = op.attrib.get("name") or "Operation"
                        svc_dir = os.path.basename(root)
                        ops.append((svc, op_name, svc_dir, tns))
            except Exception:
                continue
    return ops


def _find_xsds(resources_root: str, svc_dir: str, op: str) -> Tuple[str, str]:
    base = os.path.join(resources_root, "contracts", "selected")
    cand_req: Optional[str] = None
    cand_rep: Optional[str] = None
    search_dir = os.path.join(base, svc_dir)
    if os.path.isdir(search_dir):
        xsds = [x for x in os.listdir(search_dir) if x.lower().endswith(".xsd")]
        for name in xsds:
            low = name.lower()
            if (not cand_req) and ("request" in low or low.endswith("request.xsd")):
                cand_req = os.path.relpath(os.path.join(search_dir, name), os.path.join(resources_root))
            if (not cand_rep) and ("reply" in low or "response" in low):
                cand_rep = os.path.relpath(os.path.join(search_dir, name), os.path.join(resources_root))
    if not cand_req or not cand_rep:
        for root2, dirs2, files2 in os.walk(base):
            for f in files2:
                if not f.lower().endswith(".xsd"):
                    continue
                low = f.lower()
                parent = os.path.basename(root2).lower()
                ol = op.lower()
                if (ol in low) or (ol in parent):
                    p = os.path.join(root2, f)
                    if (not cand_req) and ("request" in low or low.endswith("request.xsd")):
                        cand_req = os.path.relpath(p, os.path.join(resources_root))
                    if (not cand_rep) and ("reply" in low or "response" in low):
                        cand_rep = os.path.relpath(p, os.path.join(resources_root))
    return cand_req or "", cand_rep or ""


def _app_main_py() -> str:
    return (
        "from fastapi import FastAPI\n"
        "from fastapi.middleware.cors import CORSMiddleware\n"
        "from .logging_conf import setup_logging\n"
        "from .routers import build_router\n"
        "app = FastAPI()\n"
        "app.add_middleware(CORSMiddleware, allow_origins=['*'], allow_credentials=True, allow_methods=['*'], allow_headers=['*'])\n"
        "setup_logging()\n"
        "router = build_router()\n"
        "app.include_router(router)\n"
    )


def _logging_conf_py(log_path: str) -> str:
    return (
        "import logging\n"
        "from logging.handlers import RotatingFileHandler\n"
        f"LOG_PATH = '{log_path.replace('\\\\', '\\\\\\\\')}'\n"
        "def setup_logging():\n"
        "    logger = logging.getLogger('mock-services')\n"
        "    logger.setLevel(logging.INFO)\n"
        "    handler = RotatingFileHandler(LOG_PATH, maxBytes=1048576, backupCount=3)\n"
        "    fmt = logging.Formatter('%(asctime)s %(levelname)s %(message)s')\n"
        "    handler.setFormatter(fmt)\n"
        "    if not logger.handlers:\n"
        "        logger.addHandler(handler)\n"
    )


def _utils_xml_py() -> str:
    return (
        "import time\n"
        "import xml.etree.ElementTree as ET\n"
        "from typing import Dict, Optional\n"
        "def parse_xml(xml: str) -> ET.Element:\n"
        "    return ET.fromstring(xml)\n"
        "def extract_soap_body(root: ET.Element) -> ET.Element:\n"
        "    ns = {'soap': 'http://schemas.xmlsoap.org/soap/envelope/'}\n"
        "    if root.tag.endswith('Envelope'):\n"
        "        body = root.find('soap:Body', ns)\n"
        "        return list(body)[0] if body is not None and len(list(body)) > 0 else root\n"
        "    return root\n"
        "def validate_xml_with_xsd(xml: str, xsd_path: Optional[str]) -> Optional[str]:\n"
        "    if not xsd_path:\n"
        "        return None\n"
        "    try:\n"
        "        import xmlschema\n"
        "        schema = xmlschema.XMLSchema(xsd_path)\n"
        "        schema.validate(xml)\n"
        "        return None\n"
        "    except Exception as e:\n"
        "        return str(e)\n"
        "def build_fault(message: str) -> str:\n"
        "    return ('<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">'"
        "            '<soap:Body><soap:Fault><faultcode>Client</faultcode><faultstring>' + message + '</faultstring>'"
        "            '</soap:Fault></soap:Body></soap:Envelope>')\n"
        "def delay_ms(ms: int) -> None:\n"
        "    if ms and ms > 0:\n"
        "        time.sleep(ms / 1000.0)\n"
    )


def _routers_py() -> str:
    return (
        "import os\n"
        "from fastapi import APIRouter, Request\n"
        "from typing import Callable\n"
        "def build_router() -> APIRouter:\n"
        "    router = APIRouter()\n"
        "    base_pkg = 'app.controllers'\n"
        "    meta_path = os.path.join(os.path.dirname(__file__), '..', 'META_ENDPOINTS.txt')\n"
        "    if os.path.isfile(meta_path):\n"
        "        for line in open(meta_path, 'r', encoding='utf-8').read().splitlines():\n"
        "            if not line.strip() or line.strip().startswith('#'):\n"
        "                continue\n"
        "            parts = line.split('|')\n"
        "            if len(parts) < 3:\n"
        "                continue\n"
        "            endpoint, mod_name, cls_name = parts[0], parts[1], parts[2]\n"
        "            mod = __import__(f'{base_pkg}.{mod_name}', fromlist=[cls_name])\n"
        "            cls = getattr(mod, cls_name)\n"
        "            inst = cls()\n"
        "            async def handler(request: Request, delay: int = 0):\n"
        "                body = await request.body()\n"
        "                return await inst.handle(body.decode('utf-8'), delay)\n"
        "            router.add_api_route(endpoint, handler, methods=['POST'])\n"
        "    return router\n"
    )


def scaffold_python_mock_services(out_path: str, contracts_dir: Optional[str] = None) -> List[str]:
    if os.path.exists(out_path):
        import shutil
        shutil.rmtree(out_path)
    os.makedirs(out_path, exist_ok=True)
    created: List[str] = []
    resources_root = os.path.join(out_path, "resources")
    if contracts_dir and os.path.isdir(contracts_dir):
        dst = os.path.join(resources_root, "contracts", "selected")
        for root, dirs, files in os.walk(contracts_dir):
            for name in files:
                if name.lower().endswith((".wsdl", ".xsd")):
                    src = os.path.join(root, name)
                    rel = os.path.relpath(src, contracts_dir)
                    _write(os.path.join(dst, rel), open(src, "rb").read().decode("utf-8", errors="ignore"))
                    created.append(os.path.join(dst, rel))
    app_root = os.path.join(out_path, "app")
    _write(os.path.join(app_root, "__init__.py"), "")
    _write(os.path.join(app_root, "main.py"), _app_main_py())
    _write(os.path.join(app_root, "logging_conf.py"), _logging_conf_py(os.path.join(out_path, "mock-services.log")))
    utils_root = os.path.join(app_root, "utils")
    _write(os.path.join(utils_root, "__init__.py"), "")
    _write(os.path.join(utils_root, "xml.py"), _utils_xml_py())
    controllers_root = os.path.join(app_root, "controllers")
    _write(os.path.join(controllers_root, "__init__.py"), "")
    _write(os.path.join(app_root, "routers.py"), _routers_py())
    contracts_selected = os.path.join(resources_root, "contracts", "selected")
    ops = _discover_ops(contracts_selected) if os.path.isdir(contracts_selected) else []
    meta_lines: List[str] = []
    for svc, op, svc_dir, tns in ops:
        svc_pkg = _sanitize_pkg(svc_dir)
        cls_name = _sanitize_cls(svc) + _sanitize_cls(op) + "Controller"
        mod_name = os.path.join(svc_pkg, op).replace("\\", "/").replace("/", ".")
        endpoint = f"/api/mock/{svc}/{op}"
        req_xsd_rel, rep_xsd_rel = _find_xsds(resources_root, svc_dir, op)
        meta_lines.append("|".join([endpoint, mod_name, cls_name]))
        _write(os.path.join(controllers_root, svc_pkg, "__init__.py"), "")
        ctrl_path = os.path.join(controllers_root, svc_pkg, f"{op}.py")
        ctrl_code = (
            "from fastapi import Response\n"
            "import logging\n"
            "from ..utils.xml import parse_xml, extract_soap_body, validate_xml_with_xsd, build_fault, delay_ms\n"
            "class " + cls_name + ":\n"
            "    async def handle(self, xml: str, delay: int = 0):\n"
            "        logger = logging.getLogger('mock-services')\n"
            "        logger.info('request %s %s', '" + svc + "', '" + op + "')\n"
            "        delay_ms(delay)\n"
            "        err = validate_xml_with_xsd(xml, '" + (req_xsd_rel or "") + "') if '" + (req_xsd_rel or "") + "' else None\n"
            "        if err:\n"
            "            body = build_fault('invalid request')\n"
            "            return Response(content=body, media_type='application/xml', status_code=400)\n"
            "        root = parse_xml(xml)\n"
            "        body_node = extract_soap_body(root)\n"
            "        rep_root = '" + op + "Response'\n"
            "        if '" + (rep_xsd_rel or "") + "':\n"
            "            try:\n"
            "                import re\n"
            "                t = open('" + os.path.join(resources_root, (rep_xsd_rel or "")).replace('\\', '\\\\') + "', 'r', encoding='utf-8').read()\n"
            "                m = re.search(r\"<\\s*xs:element\\s+name=\\\"([^\\\"]+)\\\"\", t)\n"
            "                if m:\n"
            "                    rep_root = m.group(1)\n"
            "            except Exception:\n"
            "                pass\n"
            "        body = f\"<{rep_root}><status>OK</status></{rep_root}>\"\n"
            "        logger.info('response %s %s', '" + svc + "', '" + op + "')\n"
            "        return Response(content=body, media_type='application/xml', status_code=200)\n"
        )
        _write(ctrl_path, ctrl_code)
        created.append(ctrl_path)
    _write(os.path.join(out_path, "META_ENDPOINTS.txt"), "\n".join(meta_lines) if meta_lines else "")
    docs_root = os.path.join(out_path, "docs")
    _write(os.path.join(docs_root, "conf.py"), "project = 'mock-services'\nextensions = []\n")
    _write(os.path.join(docs_root, "index.rst"), "Mock Services\n=============\n")
    tests_root = os.path.join(out_path, "tests")
    _write(os.path.join(tests_root, "__init__.py"), "")
    _write(
        os.path.join(tests_root, "test_endpoints.py"),
        "import os\n"
        "import unittest\n"
        "from fastapi.testclient import TestClient\n"
        "from app.main import app\n"
        "class TestEndpoints(unittest.TestCase):\n"
        "    def test_any_endpoint_exists(self):\n"
        "        meta = os.path.join(os.path.dirname(__file__), '..', 'META_ENDPOINTS.txt')\n"
        "        lines = [l for l in open(meta, 'r', encoding='utf-8').read().splitlines() if l.strip()]\n"
        "        self.assertTrue(len(lines) > 0)\n"
        "        endpoint = lines[0].split('|')[0]\n"
        "        c = TestClient(app)\n"
        "        r = c.post(endpoint, data='<root/>', headers={'Content-Type':'application/xml'})\n"
        "        self.assertEqual(r.status_code, 200)\n"
    )
    created.extend([
        os.path.join(app_root, "main.py"),
        os.path.join(app_root, "logging_conf.py"),
        os.path.join(app_root, "routers.py"),
        os.path.join(out_path, "META_ENDPOINTS.txt"),
        os.path.join(docs_root, "conf.py"),
        os.path.join(docs_root, "index.rst"),
        os.path.join(tests_root, "test_endpoints.py"),
    ])
    return created
