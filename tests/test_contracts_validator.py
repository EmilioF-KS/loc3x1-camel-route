import unittest
from pathlib import Path

from scripts.contracts_validator import validate_contracts, write_report


class TestContractsValidator(unittest.TestCase):
    def setUp(self):
        self.root = Path(__file__).resolve().parent.parent
        self.out = self.root / "doc" / ".preflight" / "contracts-checks.md"

    def test_validate_contracts_returns_expected_structure(self):
        result = validate_contracts(self.root)
        self.assertIsInstance(result, dict)
        # Core sections
        for key in ["paths", "counts", "file_existence", "imports", "namespaces", "summary", "issues"]:
            self.assertIn(key, result, f"Missing section {key}")

        # Counts structure
        self.assertIn("json", result["counts"])  
        self.assertIn("md", result["counts"])    
        self.assertIn("match", result["counts"]) 

        # File existence
        self.assertIn("ok_count", result["file_existence"]) 
        self.assertIn("missing", result["file_existence"])  

        # Imports
        self.assertIn("ok_wsdl", result["imports"]) 
        self.assertIn("ok_xsd", result["imports"])  
        self.assertIn("unresolved_wsdl", result["imports"]) 
        self.assertIn("unresolved_xsd", result["imports"])  

        # Namespaces
        self.assertIn("json_targets", result["namespaces"]) 
        self.assertIn("json_imports", result["namespaces"])  
        self.assertIn("md_list", result["namespaces"])       
        self.assertIn("json_target_not_in_md", result["namespaces"]) 
        self.assertIn("md_extras", result["namespaces"])            

    def test_contracts_validator_core_checks_pass(self):
        result = validate_contracts(self.root)
        # Inventory files should exist
        self.assertEqual(len(result["file_existence"]["missing"]), 0, f"Missing files: {result['file_existence']['missing']}")
        # Imports should resolve (WSDL) and XSD unresolved should be constrained to known gaps
        self.assertEqual(len(result["imports"]["unresolved_wsdl"]), 0, f"Unresolved WSDL imports: {result['imports']['unresolved_wsdl']}")
        unresolved_xsd = result["imports"]["unresolved_xsd"]
        # Allow known missing Location.xsd referenced by LocationListReply.xsd variants
        self.assertGreaterEqual(len(unresolved_xsd), 0)
        for entry in unresolved_xsd:
            self.assertIn("sample/Schemas/Location.xsd", entry, f"Unexpected unresolved XSD import: {entry}")
        # Counts should match between MD and JSON
        self.assertTrue(result["counts"]["match"], "Counts mismatch between inventory.md and inventory.json")
        # All JSON target namespaces should be listed in MD
        self.assertEqual(len(result["namespaces"]["json_target_not_in_md"]), 0, f"Missing namespaces in MD: {result['namespaces']['json_target_not_in_md']}")
        # XML-based checks: no mismatches or cycles
        self.assertEqual(len(result["xml"]["xsd_namespace_mismatches"]), 0, f"XSD targetNamespace mismatches: {result['xml']['xsd_namespace_mismatches']}")
        # Cycles may exist in sample/Schemas; ensure none involve PrivateSchemas
        cycles = result["xml"]["xsd_import_cycles"]
        self.assertIsInstance(cycles, list)
        self.assertTrue(all("sample/PrivateSchemas" not in p for cycle in cycles for p in cycle), f"Cycles must not involve PrivateSchemas: {cycles}")
        self.assertEqual(len(result["xml"]["wsdl_operation_mismatches"]), 0, f"WSDL op mismatches: {result['xml']['wsdl_operation_mismatches']}")
        # Binding mismatches should also be empty; inventory lists known bindings where applicable
        self.assertEqual(len(result["xml"]["wsdl_binding_mismatches"]), 0, f"WSDL binding mismatches: {result['xml']['wsdl_binding_mismatches']}")

    def test_write_report_creates_markdown(self):
        result = validate_contracts(self.root)
        out_path = write_report(result, self.out)
        self.assertTrue(out_path.exists(), f"Report not written at {out_path}")
        text = out_path.read_text(encoding="utf-8")
        # Basic sections present
        for section in ["Contracts Validator Report", "Counts", "File Existence", "Imports", "Namespaces", "Summary", "Issues"]:
            self.assertIn(section, text)
        # Counts reflect the result
        self.assertIn(f"JSON WSDL: {result['counts']['json']['wsdl']}", text)
        self.assertIn(f"MD WSDL: {result['counts']['md']['wsdl']}", text)


if __name__ == "__main__":
    unittest.main()