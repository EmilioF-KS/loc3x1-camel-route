import os
from fastapi import APIRouter, Request
from typing import Callable
def build_router() -> APIRouter:
    router = APIRouter()
    base_pkg = 'app.controllers'
    meta_path = os.path.join(os.path.dirname(__file__), '..', 'META_ENDPOINTS.txt')
    if os.path.isfile(meta_path):
        for line in open(meta_path, 'r', encoding='utf-8').read().splitlines():
            if not line.strip() or line.strip().startswith('#'):
                continue
            parts = line.split('|')
            if len(parts) < 3:
                continue
            endpoint, mod_name, cls_name = parts[0], parts[1], parts[2]
            mod = __import__(f'{base_pkg}.{mod_name}', fromlist=[cls_name])
            cls = getattr(mod, cls_name)
            inst = cls()
            async def handler(request: Request, delay: int = 0):
                body = await request.body()
                return await inst.handle(body.decode('utf-8'), delay)
            router.add_api_route(endpoint, handler, methods=['POST'])
    return router
