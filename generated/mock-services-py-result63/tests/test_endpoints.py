import os
import unittest
from fastapi.testclient import TestClient
from app.main import app
class TestEndpoints(unittest.TestCase):
    def test_any_endpoint_exists(self):
        meta = os.path.join(os.path.dirname(__file__), '..', 'META_ENDPOINTS.txt')
        lines = [l for l in open(meta, 'r', encoding='utf-8').read().splitlines() if l.strip()]
        self.assertTrue(len(lines) > 0)
        endpoint = lines[0].split('|')[0]
        c = TestClient(app)
        r = c.post(endpoint, data='<root/>', headers={'Content-Type':'application/xml'})
        self.assertEqual(r.status_code, 200)
