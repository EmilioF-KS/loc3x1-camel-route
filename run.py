import os
import sys
import subprocess
import time
import socket
import shutil
import webbrowser

def _which(x):
    return shutil.which(x) is not None

def ensure_prereqs():
    errs = []
    if sys.version_info < (3, 11):
        print("Python 3.11+ required. Current: %s" % ".".join(map(str, sys.version_info[:3])))
        sys.exit(1)
    if not _which("java"):
        errs.append("java")
    if not _which("mvn"):
        errs.append("mvn")
    if not _which("node"):
        errs.append("node")
    if not _which("npm"):
        errs.append("npm")
    if errs:
        print("Missing tools: " + ", ".join(errs))
        print("Install Java 17+, Maven 3.9+, Node 18+, npm, then re-run.")
        sys.exit(1)

def port_open(port):
    s = socket.socket()
    try:
        s.settimeout(0.3)
        s.connect(("127.0.0.1", port))
        s.close()
        return True
    except Exception:
        return False

def ensure_venv():
    if not os.path.isdir(".venv"):
        subprocess.check_call([sys.executable, "-m", "venv", ".venv"]) 
    scripts = "Scripts" if os.name == "nt" else "bin"
    python_bin = os.path.join(".venv", scripts, "python.exe" if os.name == "nt" else "python")
    subprocess.check_call([python_bin, "-m", "pip", "install", "-r", "requirements.txt"]) 
    return python_bin

def start_backend(python_bin):
    cmd = [python_bin, "-m", "uvicorn", "backend.app.main:app", "--port", "8000", "--reload"]
    p = subprocess.Popen(cmd)
    for _ in range(60):
        if port_open(8000):
            break
        time.sleep(0.5)
    return p

def build_java():
    if not os.path.isdir("generated/result"):
        return False
    if os.path.isfile(os.path.join("generated/result", "pom.xml")):
        subprocess.check_call(["mvn", "-DskipTests", "package"], cwd="generated/result")
        return True
    return False

def start_java():
    jar = os.path.join("generated/result", "target", "loc-service.jar")
    if not os.path.isfile(jar):
        ok = build_java()
        if not ok or not os.path.isfile(jar):
            return None
    cmd = ["java", "-jar", "-Dserver.port=8081", jar]
    p = subprocess.Popen(cmd, cwd="generated/result")
    for _ in range(60):
        if port_open(8081):
            break
        time.sleep(0.5)
    return p

def start_frontend():
    if not os.path.isfile(os.path.join("frontend", "package.json")):
        return None, None
    npm_path = shutil.which("npm")
    node_path = shutil.which("node")
    if not npm_path or not node_path:
        return None, None
    install_cmd = [npm_path, "install", "--legacy-peer-deps"]
    try:
        subprocess.check_call(install_cmd, cwd="frontend")
    except subprocess.CalledProcessError:
        return None, None
    env = os.environ.copy()
    env.setdefault("VITE_API_BASE_URL", "http://localhost:8000")
    cmd = [npm_path, "run", "dev"]
    p = subprocess.Popen(cmd, cwd="frontend", env=env)
    chosen = None
    for _ in range(120):
        for port in range(5173, 5184):
            if port_open(port):
                chosen = port
                break
        if chosen:
            break
        time.sleep(0.5)
    return p, chosen

def main():
    ensure_prereqs()
    python_bin = ensure_venv()
    be = start_backend(python_bin)
    js = None
    if not port_open(8081):
        js = start_java()
    fe, fe_port = start_frontend()
    print("Backend: http://localhost:8000")
    if fe_port:
        print(f"Frontend: http://localhost:{fe_port}")
    else:
        print("Frontend: unavailable")
    print("Service health: http://localhost:8081/actuator/health")
    if fe_port:
        try:
            webbrowser.open(f"http://localhost:{fe_port}")
        except Exception:
            pass
    elif port_open(8000):
        try:
            webbrowser.open("http://localhost:8000/docs")
        except Exception:
            pass
    print("Press Ctrl+C to stop")
    try:
        while True:
            time.sleep(1)
    except KeyboardInterrupt:
        pass
    for p in [fe, js, be]:
        if p and p.poll() is None:
            try:
                p.terminate()
            except Exception:
                pass

if __name__ == "__main__":
    main()
