import unittest
import os
import sys
from pathlib import Path

# Ensure project root is on sys.path
TEST_DIR = Path(__file__).resolve().parent
PROJECT_ROOT = TEST_DIR.parent
if str(PROJECT_ROOT) not in sys.path:
    sys.path.insert(0, str(PROJECT_ROOT))

import scripts.preflight_agent as preflight


class TestPreflightAgent(unittest.TestCase):
    def test_check_functions_return_expected_keys(self):
        checks = [
            preflight.check_brew,
            preflight.check_java,
            preflight.check_python,
            preflight.check_ollama,
            preflight.check_langgraph,
        ]
        for check in checks:
            result = check()
            self.assertIsInstance(result, dict)
            self.assertIn("tool", result)
            self.assertIn("exists", result)
            self.assertIn("status", result)
            # 'details' and 'suggest' are optional but often present

    def test_write_report_creates_markdown(self):
        # Change cwd to project root so report path resolves inside repo
        old_cwd = Path.cwd()
        os.chdir(PROJECT_ROOT)
        try:
            results = [
                {"tool": "dummy", "exists": True, "status": True, "details": "ok", "suggest": ""}
            ]
            preflight.write_report(results)
            from scripts.utils.paths import PREVIEW_DIR
            report_path = PREVIEW_DIR / "agent-checks.md"
            self.assertTrue(report_path.exists())
            content = report_path.read_text(encoding="utf-8")
            self.assertIn("# Preflight Agent Checks", content)
            self.assertIn("dummy", content)
        finally:
            os.chdir(old_cwd)


if __name__ == "__main__":
    unittest.main()