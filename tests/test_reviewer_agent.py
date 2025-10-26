import unittest
import sys
from pathlib import Path

# Ensure project root is on sys.path
TEST_DIR = Path(__file__).resolve().parent
PROJECT_ROOT = TEST_DIR.parent
if str(PROJECT_ROOT) not in sys.path:
    sys.path.insert(0, str(PROJECT_ROOT))

# Import the agents runner module and call the reviewer stage directly
import importlib.util

RUN_PATH = PROJECT_ROOT / "scripts" / "agents" / "run.py"
spec = importlib.util.spec_from_file_location("agents_run", str(RUN_PATH))
agents_run = importlib.util.module_from_spec(spec)
assert spec and spec.loader
spec.loader.exec_module(agents_run)


class TestReviewerAgent(unittest.TestCase):
    def test_stage_reviewer_generates_report(self):
        # Execute the reviewer stage
        result = agents_run.stage_reviewer()
        self.assertIsInstance(result, dict)
        self.assertTrue(result.get("ok"), "Reviewer stage did not complete successfully")

        # Verify output path
        report_md = Path(result["outputs"]["reviewer_report_md"])
        self.assertTrue(report_md.exists(), "Reviewer report was not created")

        # Validate content basics
        content = report_md.read_text(encoding="utf-8")
        self.assertIn("Reviewer/Interpreter Report", content)
        self.assertIn("## Evidence Alignment", content)
        # LLM Narrative should appear when enrichment succeeds, but still allow deterministic fallback
        self.assertIn("## LLM Narrative", content)


if __name__ == "__main__":
    unittest.main()