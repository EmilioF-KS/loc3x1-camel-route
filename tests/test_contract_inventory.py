import json
import re
from pathlib import Path
import unittest


PROJECT_ROOT = Path(__file__).resolve().parents[1]
INV_JSON = PROJECT_ROOT / "contracts" / "inventory.json"
INV_MD = PROJECT_ROOT / "contracts" / "inventory.md"


class TestContractInventory(unittest.TestCase):
    def setUp(self):
        self.assertTrue(INV_JSON.exists(), f"Missing inventory.json at {INV_JSON}")
        with open(INV_JSON, "r", encoding="utf-8") as f:
            self.inv = json.load(f)

    def test_inventory_json_basic_structure(self):
        # Basic keys present
        for key in ("metadata", "wsdl_files", "xsd_files"):
            self.assertIn(key, self.inv, f"inventory.json missing key: {key}")
        # Non-empty lists
        self.assertGreater(len(self.inv["wsdl_files"]), 0, "No WSDLs found in inventory")
        self.assertGreater(len(self.inv["xsd_files"]), 0, "No XSDs found in inventory")
        # Metadata sanity
        md = self.inv["metadata"]
        self.assertIn("scan_directory", md)
        self.assertTrue(str(md["scan_directory"]).endswith("/sample"))

    def test_wsdl_operations_for_key_services(self):
        def find_wsdl(path_suffix):
            for w in self.inv["wsdl_files"]:
                if w["file_path"].endswith(path_suffix):
                    return w
            return None

        loc3x1b = find_wsdl("LocationRetrievalLOC3X1B/LocationRetrievalLOC3X1B.wsdl")
        self.assertIsNotNone(loc3x1b, "LOC3X1B wsdl not found")
        self.assertIn("GetLocationList3X1B", loc3x1b["operations"]) 
        self.assertIn("GetLocationWithTaxingJurisdictions3X1B", loc3x1b["operations"]) 

        loc3x1m = find_wsdl("LocationRetrievalLOC3X1M/LocationRetrievalLOC3X1M.wsdl")
        self.assertIsNotNone(loc3x1m, "LOC3X1M wsdl not found")
        self.assertIn("GetLocationList3X1M", loc3x1m["operations"]) 
        self.assertIn("GetLocationWithTaxingJurisdictions3X1M", loc3x1m["operations"]) 

        crp10x1 = find_wsdl("CountryRetrievalCRP10X1/CountryRetrievalCRP10X1.wsdl")
        self.assertIsNotNone(crp10x1, "CRP10X1 wsdl not found")
        self.assertIn("GetCountry", crp10x1["operations"]) 
        self.assertIn("GetCountryList", crp10x1["operations"]) 

        crp11x1 = find_wsdl("StateOrProvinceRetrievalCRP11X1/StateOrProvinceRetrievalCRP11X1.wsdl")
        self.assertIsNotNone(crp11x1, "CRP11X1 wsdl not found")
        self.assertIn("GetStateOrProvince", crp11x1["operations"]) 
        self.assertIn("GetStateOrProvinceList", crp11x1["operations"]) 

    def test_simplefault_is_imported_by_key_wsdls(self):
        def has_simplefault_import(wsdl_entry):
            imports = wsdl_entry.get("imports", [])
            for imp in imports:
                # Some entries use schemaLocation for XSD; location is for WSDL imports
                loc = imp.get("schemaLocation", "") or imp.get("location", "")
                if "SimpleFault.xsd" in loc:
                    return True
            return False

        def find_wsdl(path_suffix):
            for w in self.inv["wsdl_files"]:
                if w["file_path"].endswith(path_suffix):
                    return w
            return None

        for suffix in [
            "LocationRetrievalLOC3X1B/LocationRetrievalLOC3X1B.wsdl",
            "LocationRetrievalLOC3X1M/LocationRetrievalLOC3X1M.wsdl",
            "CountryRetrievalCRP10X1/CountryRetrievalCRP10X1.wsdl",
            "StateOrProvinceRetrievalCRP11X1/StateOrProvinceRetrievalCRP11X1.wsdl",
        ]:
            wsdl = find_wsdl(suffix)
            self.assertIsNotNone(wsdl, f"Expected WSDL not found: {suffix}")
            self.assertTrue(has_simplefault_import(wsdl), f"SimpleFault.xsd not imported in {suffix}")

    def test_inventory_md_counts_match_json(self):
        self.assertTrue(INV_MD.exists(), f"Missing inventory.md at {INV_MD}")
        md_text = INV_MD.read_text(encoding="utf-8")
        # Tolerate Markdown bold (**) after the colon, e.g., "**WSDL Files:** 8"
        wsdl_match = re.search(r"WSDL\s+Files:\*\*?\s*(\d+)", md_text)
        xsd_match = re.search(r"XSD\s+Files:\*\*?\s*(\d+)", md_text)
        self.assertIsNotNone(wsdl_match, "WSDL count not found in inventory.md")
        self.assertIsNotNone(xsd_match, "XSD count not found in inventory.md")
        wsdl_md = int(wsdl_match.group(1))
        xsd_md = int(xsd_match.group(1))
        self.assertEqual(wsdl_md, len(self.inv["wsdl_files"]))
        self.assertEqual(xsd_md, len(self.inv["xsd_files"]))


if __name__ == "__main__":
    unittest.main()