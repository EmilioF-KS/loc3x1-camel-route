from pathlib import Path
import sys

# Ensure repository root is on sys.path so 'agent' package is importable during tests
ROOT = Path(__file__).resolve().parents[2]
if str(ROOT) not in sys.path:
    sys.path.append(str(ROOT))