import os
import shutil
import re
import yaml


BASE = "generated/result"


def test_gen100_structure_exists():
    assert os.path.isdir(BASE)
    assert os.path.isfile(os.path.join(BASE, "pom.xml"))
    assert os.path.isfile(os.path.join(BASE, "README.md"))
    assert os.path.isfile(os.path.join(BASE, "src/main/resources/application.yaml"))
    assert os.path.isfile(os.path.join(BASE, "src/main/resources/xslt/identity.xsl"))


def test_gen100_pom_versions_and_deps():
    pom_path = os.path.join(BASE, "pom.xml")
    with open(pom_path, "r", encoding="utf-8") as f:
        pom = f.read()
    assert re.search(r"<version>3\.3\.\d+</version>", pom)  # Spring Boot parent
    assert "<java.version>17</java.version>" in pom
    assert "<camel.version>4.7.0</camel.version>" in pom
    assert "camel-spring-boot-starter" in pom
    assert "camel-yaml-dsl" in pom
    assert "spring-boot-starter-actuator" in pom
    assert "jackson-databind" in pom
    assert "springdoc-openapi-starter-webmvc-ui" in pom


def test_gen100_routes_dir_exists():
    routes_dir = os.path.join(BASE, "src/main/resources/routes")
    assert os.path.isdir(routes_dir)


def test_gen100_optional_maven_build(monkeypatch):
    mvn = shutil.which("mvn")
    if not mvn:
        # Skip if Maven is not available in the environment
        monkeypatch.setenv("GEN100_SKIP_MVN", "1")
        assert os.getenv("GEN100_SKIP_MVN") == "1"
        return
    # If Maven exists, attempt a lightweight build without tests
    import subprocess
    proc = subprocess.run([
        mvn, "-DskipTests", "package"
    ], cwd=BASE, stdout=subprocess.PIPE, stderr=subprocess.STDOUT, text=True)
    # Build should succeed
    assert proc.returncode == 0, proc.stdout
    # JAR should exist in target directory using default coordinates
    jar_dir = os.path.join(BASE, "target")
    assert os.path.isdir(jar_dir)
    jars = [f for f in os.listdir(jar_dir) if f.endswith('.jar')]
    assert any(j.startswith("a9-like-project-") for j in jars), f"Expected runnable JAR in {jar_dir}, found: {jars}"