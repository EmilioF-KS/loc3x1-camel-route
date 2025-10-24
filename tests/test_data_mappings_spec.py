import os
import subprocess
import unittest

try:
    import yaml
except ImportError:
    yaml = None

PROJECT_ROOT = os.path.dirname(os.path.dirname(__file__))
SPEC_PATH = os.path.join(PROJECT_ROOT, 'orchestration', 'spec.yaml')
ANALYZE_SCRIPT = os.path.join(PROJECT_ROOT, 'scripts', 'analyze_bpel.py')

class TestSpecDataMappings(unittest.TestCase):
    def setUp(self):
        # Ensure spec.yaml exists by running analyzer without agent for speed
        if not os.path.exists(SPEC_PATH):
            subprocess.run(['python3', ANALYZE_SCRIPT], check=True)

    def test_spec_has_map_xml_sources(self):
        self.assertIsNotNone(yaml, "PyYAML is required for this test")
        with open(SPEC_PATH, 'r', encoding='utf-8') as f:
            spec = yaml.safe_load(f)
        self.assertIn('data_mappings', spec, "spec.yaml must contain data_mappings section")
        for dm in spec.get('data_mappings', []):
            src_file = dm.get('source_file', '')
            lower = src_file.lower()
            self.assertTrue(lower.endswith('.xml') or lower.endswith('.map'), f"source_file should end with .xml or .map: {src_file}")
            self.assertIn('map', lower, f"source_file should be a mapping file: {src_file}")
            self.assertTrue(os.path.exists(os.path.join(PROJECT_ROOT, src_file)), f"source_file path must exist: {src_file}")

    def test_no_generated_java_references(self):
        with open(SPEC_PATH, 'r', encoding='utf-8') as f:
            text = f.read()
        self.assertNotIn('GeneratedJava', text, "spec.yaml must not reference GeneratedJava")

if __name__ == '__main__':
    unittest.main()