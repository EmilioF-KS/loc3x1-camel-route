#!/usr/bin/env python3
"""
Test Runner Script for BPEL Analysis Project

This script provides convenient ways to run unit tests with various options:
- Run all tests
- Run specific test files
- Run individual test methods
- Set custom BPEL file paths for testing
- Verbose or quiet output modes

Usage:
    python3 tests/run_tests.py --all                    # Run all tests
    python3 tests/run_tests.py --bpel                   # Run BPEL agent tests only
    python3 tests/run_tests.py --preflight              # Run preflight agent tests only
    python3 tests/run_tests.py --list                   # List available tests
    python3 tests/run_tests.py --custom-bpel /path/to/file.bpel --bpel  # Use custom BPEL file
"""

import argparse
import os
import sys
import subprocess
from pathlib import Path

# Add project root to path for imports
PROJECT_ROOT = Path(__file__).parent.parent.absolute()
if str(PROJECT_ROOT) not in sys.path:
    sys.path.insert(0, str(PROJECT_ROOT))


class TestRunner:
    def __init__(self):
        self.project_root = PROJECT_ROOT
        self.tests_dir = self.project_root / "tests"
        
    def run_command(self, cmd, env_vars=None):
        """Run a command with optional environment variables."""
        env = os.environ.copy()
        if env_vars:
            env.update(env_vars)
        
        print(f"Running: {' '.join(cmd)}")
        if env_vars:
            print(f"Environment: {env_vars}")
        print("-" * 50)
        
        result = subprocess.run(cmd, cwd=self.project_root, env=env)
        return result.returncode == 0
    
    def run_all_tests(self, verbose=True):
        """Run all tests."""
        cmd = ["python3", "-m", "unittest", "discover", "-s", "tests", "-p", "test_*.py"]
        if verbose:
            cmd.append("-v")
        return self.run_command(cmd)
    
    def run_bpel_tests(self, verbose=True, custom_bpel_path=None):
        """Run BPEL agent tests."""
        cmd = ["python3", "-m", "unittest", "tests.test_bpel_agent"]
        if verbose:
            cmd.append("-v")
        
        env_vars = {}
        if custom_bpel_path:
            env_vars["BPEL_TEST_PATH"] = custom_bpel_path
            
        return self.run_command(cmd, env_vars)
    
    def run_preflight_tests(self, verbose=True):
        """Run preflight agent tests."""
        cmd = ["python3", "-m", "unittest", "tests.test_preflight_agent"]
        if verbose:
            cmd.append("-v")
        return self.run_command(cmd)
    
    def run_specific_test(self, test_path, verbose=True):
        """Run a specific test method."""
        cmd = ["python3", "-m", "unittest", test_path]
        if verbose:
            cmd.append("-v")
        return self.run_command(cmd)
    
    def list_tests(self):
        """List available tests."""
        print("Available test files:")
        print("=" * 40)
        
        test_files = list(self.tests_dir.glob("test_*.py"))
        for test_file in sorted(test_files):
            print(f"  {test_file.stem}")
            
            # Try to extract test methods
            try:
                with open(test_file, 'r') as f:
                    content = f.read()
                    import re
                    methods = re.findall(r'def (test_\w+)', content)
                    for method in methods:
                        class_match = re.search(r'class (\w+)', content)
                        if class_match:
                            class_name = class_match.group(1)
                            full_path = f"tests.{test_file.stem}.{class_name}.{method}"
                            print(f"    - {method} ({full_path})")
            except Exception as e:
                print(f"    - Could not parse methods: {e}")
        
        print("\nExample usage:")
        print("  python3 tests/run_tests.py --all")
        print("  python3 tests/run_tests.py --bpel")
        print("  python3 tests/run_tests.py --preflight")
        print("  python3 tests/run_tests.py --test tests.test_bpel_agent.TestBpelAgent.test_analyze_real_bpel_or_skip")
        print("  python3 tests/run_tests.py --custom-bpel /path/to/custom.bpel --bpel")
    
    def find_sample_bpel_files(self):
        """Find BPEL files in the sample directory."""
        sample_dir = self.project_root / "sample"
        if not sample_dir.exists():
            return []
        
        bpel_files = []
        for bpel_file in sample_dir.rglob("*.bpel"):
            bpel_files.append(str(bpel_file))
        
        return sorted(bpel_files)


def main():
    parser = argparse.ArgumentParser(
        description="Test Runner for BPEL Analysis Project",
        formatter_class=argparse.RawDescriptionHelpFormatter,
        epilog="""
Examples:
  %(prog)s --all                                    # Run all tests
  %(prog)s --bpel                                   # Run BPEL agent tests
  %(prog)s --preflight                              # Run preflight tests
  %(prog)s --list                                   # List available tests
  %(prog)s --custom-bpel /path/to/file.bpel --bpel # Use custom BPEL file
  %(prog)s --test tests.test_bpel_agent.TestBpelAgent.test_analyze_real_bpel_or_skip
        """
    )
    
    # Test selection options
    test_group = parser.add_mutually_exclusive_group(required=True)
    test_group.add_argument("--all", action="store_true", help="Run all tests")
    test_group.add_argument("--bpel", action="store_true", help="Run BPEL agent tests only")
    test_group.add_argument("--preflight", action="store_true", help="Run preflight agent tests only")
    test_group.add_argument("--contracts", action="store_true", help="Run contract-related tests (inventory and validator)")
    test_group.add_argument("--section", help="Run by section: bpel | preflight | contracts | all")
    test_group.add_argument("--ordinal", help="Run by ordinal LOC-### using tests/ordinal-map.txt")
    test_group.add_argument("--test", help="Run specific test method (e.g., tests.test_bpel_agent.TestBpelAgent.test_analyze_real_bpel_or_skip)")
    test_group.add_argument("--list", action="store_true", help="List available tests")
    test_group.add_argument("--find-bpel", action="store_true", help="Find BPEL files in sample directory")
    
    # Options
    parser.add_argument("--quiet", "-q", action="store_true", help="Run tests in quiet mode (no verbose output)")
    parser.add_argument("--custom-bpel", help="Path to custom BPEL file for testing")
    
    args = parser.parse_args()
    
    runner = TestRunner()
    
    if args.list:
        runner.list_tests()
        return 0
    
    if args.find_bpel:
        bpel_files = runner.find_sample_bpel_files()
        if bpel_files:
            print("Found BPEL files in sample directory:")
            for bpel_file in bpel_files:
                print(f"  {bpel_file}")
        else:
            print("No BPEL files found in sample directory")
        return 0
    
    verbose = not args.quiet
    success = True
    
    if args.all:
        success = runner.run_all_tests(verbose)
    elif args.bpel:
        success = runner.run_bpel_tests(verbose, args.custom_bpel)
    elif args.preflight:
        success = runner.run_preflight_tests(verbose)
    elif args.contracts:
        # Run inventory and validator tests together
        modules = ["tests.test_contract_inventory", "tests.test_contracts_validator"]
        for mod in modules:
            ok = runner.run_specific_test(mod, verbose)
            if not ok:
                success = False
                break
    elif args.section:
        name = args.section.strip().lower()
        if name == "all":
            success = runner.run_all_tests(verbose)
        elif name == "bpel":
            success = runner.run_bpel_tests(verbose, args.custom_bpel)
        elif name == "preflight":
            success = runner.run_preflight_tests(verbose)
        elif name == "contracts":
            modules = ["tests.test_contract_inventory", "tests.test_contracts_validator"]
            for mod in modules:
                ok = runner.run_specific_test(mod, verbose)
                if not ok:
                    success = False
                    break
        else:
            print(f"Unknown section: {args.section}")
            success = False
    elif args.ordinal:
        # Read ordinal mapping from tests/ordinal-map.txt
        mapping_file = PROJECT_ROOT / "tests" / "ordinal-map.txt"
        target = None
        if mapping_file.exists():
            for line in mapping_file.read_text().splitlines():
                line = line.strip()
                if not line or line.startswith("#"):
                    continue
                parts = line.split()
                if len(parts) >= 2 and parts[0] == args.ordinal:
                    target = parts[1]
                    break
        if not target:
            print(f"Ordinal {args.ordinal} not found; running all.")
            success = runner.run_all_tests(verbose)
        elif target == "tests_all":
            success = runner.run_all_tests(verbose)
        elif target.startswith("tests."):
            success = runner.run_specific_test(target, verbose)
        else:
            print(f"Unsupported target mapping: {target}")
            success = False
    elif args.test:
        success = runner.run_specific_test(args.test, verbose)
    
    return 0 if success else 1


if __name__ == "__main__":
    sys.exit(main())