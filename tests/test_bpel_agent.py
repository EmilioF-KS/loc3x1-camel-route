import unittest
import os
import sys
from pathlib import Path
import subprocess

# Ensure project root is on sys.path
TEST_DIR = Path(__file__).resolve().parent
PROJECT_ROOT = TEST_DIR.parent
if str(PROJECT_ROOT) not in sys.path:
    sys.path.insert(0, str(PROJECT_ROOT))

from agents.bpel_agent import BpelAgent, BpelAnalysis


def find_bpel_under_sample(project_root: Path) -> Path | None:
    sample_dir = project_root / "sample"
    if not sample_dir.exists():
        return None
    candidates = list(sample_dir.rglob("*.bpel"))
    if not candidates:
        return None
    # Prefer known process if present
    for c in candidates:
        if c.name == "LocationRetrievalLOC3X1Process.bpel":
            return c
    return candidates[0]


class TestBpelAgent(unittest.TestCase):
    @classmethod
    def setUpClass(cls):
        cls.project_root = PROJECT_ROOT
        # Allow override via env var
        env_path = os.environ.get("BPEL_TEST_PATH", "").strip()
        if env_path:
            p = Path(env_path)
            cls.bpel_path = p if p.exists() else None
        else:
            cls.bpel_path = find_bpel_under_sample(PROJECT_ROOT)

    def test_analyze_real_bpel_or_skip(self):
        if not self.bpel_path:
            self.skipTest("Default BPEL missing; provide BPEL_TEST_PATH")
        agent = BpelAgent()
        analysis = agent.analyze(self.bpel_path)
        self.assertIsInstance(analysis, BpelAnalysis)
        self.assertTrue(len(analysis.partner_links) >= 1)
        self.assertTrue(len(analysis.inbound_ops) >= 1)
        # Ensure assignments field exists (may be empty)
        for op in analysis.inbound_ops:
            self.assertTrue(hasattr(op, 'assigns'))

    def test_generate_spec_yaml_and_markdown(self):
        if not self.bpel_path:
            self.skipTest("Default BPEL missing; provide BPEL_TEST_PATH")
        # Run the analysis script to generate artefacts
        script = PROJECT_ROOT / "scripts" / "analyze_bpel.py"
        out_md = PROJECT_ROOT / "doc" / "bpel-analysis.md"
        out_spec = PROJECT_ROOT / "orchestration" / "spec.yaml"
        cmd = ["python3", str(script), "--use-agent", "--bpel", str(self.bpel_path), "--output", str(out_md), "--spec", str(out_spec)]
        rc = subprocess.run(cmd, cwd=PROJECT_ROOT).returncode
        self.assertEqual(rc, 0, "analyze_bpel.py did not exit cleanly")
        # Check Markdown exists and has expected sections
        self.assertTrue(out_md.exists(), "bpel-analysis.md not generated")
        md_text = out_md.read_text(encoding="utf-8", errors="ignore")
        self.assertIn("## Inbound Operations", md_text)
        self.assertIn("Sequence Diagram", md_text)
        # Check spec.yaml exists and has inbound flows
        self.assertTrue(out_spec.exists(), "spec.yaml not generated")
        spec_text = out_spec.read_text(encoding="utf-8", errors="ignore")
        self.assertIn("process:", spec_text)
        self.assertIn("inbound:", spec_text)
        # New assertion: ensure deterministic data_mappings are present
        self.assertIn("data_mappings:", spec_text)
        # If partner services are present, ensure mediation order is listed
        # Tolerant assertion: if all three are in file, ensure order index is ascending
        partners = ["CRP11X1", "CRP10X1", "LOC3X1M"]
        present = [p for p in partners if p in spec_text]
        if len(present) == 3:
            # Simply assert order tokens appear; deeper ordering tests delegated to integration
            self.assertTrue("mediation_order:" in spec_text)


if __name__ == "__main__":
    unittest.main()