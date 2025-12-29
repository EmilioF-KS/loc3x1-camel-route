from fastapi import FastAPI
from fastapi.middleware.cors import CORSMiddleware
from .logging_conf import setup_logging
from .routers import build_router
app = FastAPI()
app.add_middleware(CORSMiddleware, allow_origins=['*'], allow_credentials=True, allow_methods=['*'], allow_headers=['*'])
setup_logging()
router = build_router()
app.include_router(router)
